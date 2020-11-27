package ca.mcgill.ecse321.artgallerysystem;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * The page to show all the purchases made by the user as a customer.
 * An Intent with an extra String USERNAME is needed.
 * @author Zhekai Jiang
 */
public class AccountPurchases extends AppCompatActivity {

    private String userName;
    private String msg; // Could be message about total number of purchases or error.
    private TableLayout purchasesTable;

    /**
     * Operations when the activity is started.
     * @author Zhekai Jiang
     * @param savedInstanceState See Activity.onCreate.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.account_purchases);

        ActionBar actionBar = getSupportActionBar(); // Top bar
        actionBar.setDisplayHomeAsUpEnabled(true);
            // Set the back button on the top-left -> https://geekstocode.com/add-back-button-in-android-title-bar/
        actionBar.setTitle("My Purchases");

        userName = getIntent().getStringExtra("USERNAME");

        msg = "";

        // dynamic table -> https://www.tutorialspoint.com/how-to-add-table-rows-dynamically-in-android-layout
        purchasesTable = findViewById(R.id.account_purchases_table);
        purchasesTable.setStretchAllColumns(true);

        if(userName == null || userName.length() == 0) {
            msg += "User name is empty";
            refreshMessage();
        } else {
            addAllPurchasesToTable();
        }

    }

    /**
     * Back to previous activity when the home (here set for back) button is clicked.
     * -> https://alvinalexander.com/source-code/android/how-get-android-actionbar-backup-button-work-android-back-button/
     * @param item The menu item that was selected. Same as Activity.onOptionsItemSelected.
     * @return boolean See Activity.onOptionsItemSelected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            this.onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Add all the purchases the user have made to the table shown on the screen.
     * @author Zhekai Jiang
     */
    private void addAllPurchasesToTable() {
        HttpUtils.get("purchasesbyuser/" + userName, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                int numPurchases = response.length();
                msg = getString(R.string.account_purchases_msg_text, numPurchases); // "You have made %1$d purchase(s)."

                for(int i = 0; i < numPurchases; ++i) {
                    JSONObject purchase = null;
                    try {
                        purchase = response.getJSONObject(i);
                    } catch(Exception e) {
                        msg += e.getMessage();
                    }
                    addPurchaseToTable(purchase);
                }

                refreshMessage();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    msg += errorResponse.get("message").toString();
                } catch(Exception e) {
                    msg += e.getMessage();
                }
                refreshMessage();
            }
        });
    }

    /**
     * Add one purchase to the table.
     * Each purchase takes two "sub-rows":
     *   Art Piece Name
     *   Date | Status
     * To add rows dynamically -> https://stackoverflow.com/questions/18207470/adding-table-rows-dynamically-in-android
     * @author Zhekai Jiang
     * @param purchase A JSONObject for the purchase to be added.
     */
    private void addPurchaseToTable(JSONObject purchase) {
        TableRow row = TableLayoutUtils.initializeRow(AccountPurchases.this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        purchasesTable.addView(row);

        LinearLayout rowVerticalLayout = new LinearLayout(AccountPurchases.this);
        rowVerticalLayout.setOrientation(LinearLayout.VERTICAL);
        row.addView(rowVerticalLayout);

        LinearLayout subRow1 = new LinearLayout(AccountPurchases.this);
        subRow1.setOrientation(LinearLayout.HORIZONTAL);
        rowVerticalLayout.addView(subRow1);

        LinearLayout subRow2 = new LinearLayout(AccountPurchases.this);
        subRow2.setOrientation(LinearLayout.HORIZONTAL);
        rowVerticalLayout.addView(subRow2);

        TextView nameView = new TextView(AccountPurchases.this);
        nameView.setTextAppearance(getApplicationContext(), R.style.boldText);
            // Set TextView style -> https://www.tutorialspoint.com/how-to-change-a-textview-style-at-runtime-in-android
        subRow1.addView(nameView);

        TextView dateView = new TextView(AccountPurchases.this);
        dateView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                // weight 1 to fill the remaining space thus right-aligning status
                // -> https://stackoverflow.com/questions/4305564/android-layout-right-align
        subRow2.addView(dateView);

        TextView statusView = new TextView(AccountPurchases.this);
        subRow2.addView(statusView);

        try {
            nameView.setText(purchase.getJSONObject("artPiece").getString("name"));
            dateView.setText(purchase.getString("date"));
            statusView.setText(purchase.getString("deliveryStatus"));
        } catch(Exception e) {
            msg += e.getMessage();
        }

    }

    /**
     * Refresh the message shown on top of the page.
     * @author Zhekai Jiang
     */
    private void refreshMessage() {
        TextView msgTextView = findViewById(R.id.account_purchases_msg);
        msgTextView.setText(msg);
    }
}
