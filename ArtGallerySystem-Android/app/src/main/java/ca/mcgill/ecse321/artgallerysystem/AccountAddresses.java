package ca.mcgill.ecse321.artgallerysystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
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
    private String customerId;
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
        getCustomerId();

        msg = "";

        // dynamic table -> https://www.tutorialspoint.com/how-to-add-table-rows-dynamically-in-android-layout
        addressesTable = findViewById(R.id.account_addresses_table);
        addressesTable.setStretchAllColumns(true);
    }

    /**
     * Clear and load (or refresh) the addresses shown when the activity is started or resumed (when back from adding, editing, or deleting).
     * @author Zhekai Jiang
     */
    @Override
    protected void onResume() {
        super.onResume();

        addressesTable.removeAllViews();
        if(userName == null || userName.length() == 0) {
            msg += "User name is empty";
            refreshMessage();
        } else {
            addAllAddressesToTable();
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
     * Helper method to get the id of the customer role, used for associating new address to the role.
     * @author Zhekai Jiang
     */
    private void getCustomerId() {
        HttpUtils.get("customer/user/" + userName, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    customerId = response.getString("userRoleId");
                } catch(Exception e) {
                    msg += e.getMessage();
                    refreshMessage();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                msg += "Failed to retrieve customer role.";
                refreshMessage();
            }
        });
    }

    /**
     * Add all the addresses the user have saved to the table shown on the screen.
     * @author Zhekai Jiang
     */
    private void addAllAddressesToTable() {
        HttpUtils.get("addresses/user/" + userName, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // Top message
                msg = getString(R.string.account_addresses_msg_text);

                // Addresses
                for(int i = 0; i < response.length(); ++i) {
                    JSONObject address = null;
                    try {
                        address = response.getJSONObject(i);
                    } catch(Exception e) {
                        msg += e.getMessage();
                    }
                    addAddressToTable(address);
                }

                // Add Address button
                Button addButton = new Button(AccountAddresses.this);
                addButton.setText(R.string.account_addresses_add_button_text);
                addButton.setOnClickListener(v -> {
                    Intent intent = new Intent(AccountAddresses.this, EditAddress.class);
                    intent.putExtra("ADDRESSID", generateNewAddressId());
                    intent.putExtra("CUSTOMERID", customerId);
                    intent.putExtra("OPERATION", "Add");
                    startActivity(intent);
                });
                addressesTable.addView(addButton);

                // Error messages
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
     * Generate a random id for a new address to be added.
     * @return A string consisting of three segments of random integers.
     */
    private String generateNewAddressId() {
        return (int)(Math.random() * 10000) + "-" + (int)(Math.random() * 10000) + "-" + (int)(Math.random() * 10000);
    }

    /**
     * Add one address to the table.
     * Each address takes two "sub-rows":
     *   Name
     *   Street Address preview
     * To add rows dynamically -> https://stackoverflow.com/questions/18207470/adding-table-rows-dynamically-in-android
     * @author Zhekai Jiang
     * @param address A JSONObject for the address to be added.
     */
    private void addAddressToTable(JSONObject address) {
        TableRow row = TableLayoutUtils.initializeRow(AccountAddresses.this, v -> {
            Intent intent = new Intent(AccountAddresses.this, EditAddress.class);
            try {
                intent.putExtra("OPERATION", "Edit");
                intent.putExtra("ADDRESSID", address.getString("addressId"));
                intent.putExtra("CUSTOMERID", customerId);
                startActivity(intent);
            } catch(Exception e) {
                msg += e.getMessage();
                refreshMessage();
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
