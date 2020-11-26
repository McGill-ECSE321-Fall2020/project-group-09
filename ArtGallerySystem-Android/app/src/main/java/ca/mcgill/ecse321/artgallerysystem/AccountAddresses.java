package ca.mcgill.ecse321.artgallerysystem;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
 * The page to show all the frequently used addresses saved by the user.
 * An Intent with an extra String USERNAME is needed.
 * @author Zhekai Jiang
 */
public class AccountAddresses extends AppCompatActivity {

    private String userName;
    private String msg; // Could be message about total number of addresses or error.
    private TableLayout addressesTable;

    /**
     * Operations when the activity is started.
     * @author Zhekai Jiang
     * @param savedInstanceState See Activity.onCreate.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.account_addresses);

        ActionBar actionBar = getSupportActionBar(); // Top bar
        actionBar.setDisplayHomeAsUpEnabled(true);
            // Set the back button on the top-left -> https://geekstocode.com/add-back-button-in-android-title-bar/
        actionBar.setTitle("Frequently Used Addresses");

        userName = getIntent().getStringExtra("USERNAME");

        msg = "";

        // dynamic table -> https://www.tutorialspoint.com/how-to-add-table-rows-dynamically-in-android-layout
        addressesTable = findViewById(R.id.account_addresses_table);
        addressesTable.setStretchAllColumns(true);

        if(userName == null || userName.length() == 0) {
            msg += "User name is empty";
            refreshMessage();
        } else {
            addAllArtPiecesToTable();
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
     * Add all the addresses the user have saved to the table shown on the screen.
     * @author Zhekai Jiang
     */
    private void addAllArtPiecesToTable() {
        HttpUtils.get("addresses/user/" + userName, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                int numAddresses = response.length();
                msg = getString(R.string.account_addresses_msg_text, numAddresses);

                for(int i = 0; i < numAddresses; ++i) {
                    JSONObject address = null;
                    try {
                        address = response.getJSONObject(i);
                    } catch(Exception e) {
                        msg += e.getMessage();
                    }
                    addAddressToTable(address, i);
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
     * Add one address to the table.
     * Each address takes two "sub-rows":
     *   Name
     *   Street Address preview
     * To add rows dynamically -> https://stackoverflow.com/questions/18207470/adding-table-rows-dynamically-in-android
     * @author Zhekai Jiang
     * @param address A JSONObject for the address to be added.
     * @param index The index of the address.
     */
    private void addAddressToTable(JSONObject address, int index) {
        TableRow row = TableLayoutUtils.initializeRow(AccountAddresses.this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        addressesTable.addView(row);

        LinearLayout rowVerticalLayout = new LinearLayout(AccountAddresses.this);
        rowVerticalLayout.setOrientation(LinearLayout.VERTICAL);
        row.addView(rowVerticalLayout);

        TextView nameView = new TextView(AccountAddresses.this);
        nameView.setTextAppearance(getApplicationContext(), R.style.boldText);
            // Set TextView style -> https://www.tutorialspoint.com/how-to-change-a-textview-style-at-runtime-in-android
        rowVerticalLayout.addView(nameView);

        TextView previewView = new TextView(AccountAddresses.this);
        rowVerticalLayout.addView(previewView);

        try {
            nameView.setText(address.getString("name"));
            previewView.setText(address.getString("streetAddress"));
        } catch(Exception e) {
            msg += e.getMessage();
        }
    }

    /**
     * Refresh the message shown on top of the page.
     * @author Zhekai Jiang
     */
    private void refreshMessage() {
        TextView msgTextView = findViewById(R.id.account_addresses_msg);
        msgTextView.setText(msg);
    }

}
