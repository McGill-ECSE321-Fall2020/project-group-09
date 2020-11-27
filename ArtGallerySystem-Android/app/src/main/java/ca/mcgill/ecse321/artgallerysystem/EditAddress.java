package ca.mcgill.ecse321.artgallerysystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * The page where the user add a new address or edit/delete an existing address.
 * An intent with extra String-s
 * - OPERATION, which could be "Add" for adding a new address, or "Edit" for editing an existing address,
 * - ADDRESSID, the id of the address to be added / edited / deleted, and
 * - CUSTOMERID, the id of the customer role associated with the address
 * is needed.
 * @author Zhekai Jiang
 */
public class EditAddress extends AppCompatActivity {

    private String operation;
    private String addressId;
    private String customerId;

    private String error;
    private TextView errorView;

    private String name, phone, street, city, province, postalCode, country;
    private EditText nameField, phoneField, streetField, cityField, provinceField, postalCodeField, countryField;

    /**
     * Operations when the activity is created.
     * @author Zhekai Jiang
     * @param savedInstanceState See Activity.onCreate.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        operation = getIntent().getStringExtra("OPERATION");
        addressId = getIntent().getStringExtra("ADDRESSID");
        customerId = getIntent().getStringExtra("CUSTOMERID");

        setContentView(R.layout.edit_address);

        error = "";
        errorView = findViewById(R.id.edit_address_error);

        if(operation == null || (!operation.equals("Add") && !operation.equals("Edit"))) {
            error += "Operation can only be Add or Edit. ";
        }
        if(addressId == null || addressId.length() == 0) {
            error += "Address ID cannot be empty. ";
        }

        ActionBar actionBar = getSupportActionBar(); // Top bar
        actionBar.setDisplayHomeAsUpEnabled(true);
            // Set the back button on the top-left -> https://geekstocode.com/add-back-button-in-android-title-bar/
        actionBar.setTitle((operation.equals("Add") ? operation : "Edit") + " Address");

        if(error.length() > 0) {
            refreshErrorMessage();
        } else {
            initializeContent();
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
     * Initialize all the elements shown.
     * @author Zhekai Jiang
     */
    private void initializeContent() {
        TextView orderIdView = findViewById(R.id.edit_address_id_view);
        orderIdView.setText(getString(R.string.edit_address_id, addressId));

        linkTextEditFields();

        Button confirmButton = findViewById(R.id.edit_address_confirm_button);
        Button deleteButton = findViewById(R.id.edit_address_delete_button);

        if(operation.equals("Edit")) {
            fillInitialAddress();
            confirmButton.setOnClickListener(v -> onConfirmEditButtonClicked());
            deleteButton.setOnClickListener(v -> onDeleteButtonClicked());
        } else {
            confirmButton.setOnClickListener(v -> onConfirmAddButtonClicked());
            deleteButton.setVisibility(View.GONE); // No deletion if adding a new address.
        }
    }

    /**
     * Helper method to link all the TextEdit-s.
     * @author Zhekai Jiang
     */
    private void linkTextEditFields() {
        nameField = findViewById(R.id.edit_address_name_field);
        phoneField = findViewById(R.id.edit_address_phone_field);
        streetField = findViewById(R.id.edit_address_street_field);
        cityField = findViewById(R.id.edit_address_city_field);
        provinceField = findViewById(R.id.edit_address_province_field);
        postalCodeField = findViewById(R.id.edit_address_postal_code_field);
        countryField = findViewById(R.id.edit_address_country_field);
    }

    /**
     * For an existing address, fill in the existing address to the TextEdit-s.
     * @author Zhekai Jiang
     */
    private void fillInitialAddress() {
        HttpUtils.get("addresses/" + addressId, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    name = response.getString("name");
                    nameField.setText(name);
                    phone = response.getString("phoneNumber");
                    phoneField.setText(phone);
                    street = response.getString("streetAddress");
                    streetField.setText(street);
                    city = response.getString("city");
                    cityField.setText(city);
                    province = response.getString("province");
                    provinceField.setText(province);
                    postalCode = response.getString("postalCode");
                    postalCodeField.setText(postalCode);
                    country = response.getString("country");
                    countryField.setText(country);
                } catch(Exception e) {
                    error += e.getMessage();
                    refreshErrorMessage();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch(Exception e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }

    /**
     * When the user confirms the edit:
     * - Read all the input,
     * - Check all the fields are non-empty, and
     * - Update the address.
     * @author Zhekai Jiang
     */
    private void onConfirmEditButtonClicked() {
        getInputFields();
        if(checkNonEmpty()) {
            putAddress();
        }
    }

    /**
     * When the user requests to delete the address, create a dialog for confirmation.
     * @author Zhekai Jiang
     */
    private void onDeleteButtonClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditAddress.this);
        builder.setMessage("Are you sure you want to delete this address?")
                .setPositiveButton("Yes", (dialog, which) -> deleteAddress())
                .setNegativeButton("No", (dialog, which) -> {});
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Helper method to delete the address via the REST controller.
     * Create a dialog for result and take the user back to previous activity.
     * @author Zhekai Jiang
     */
    private void deleteAddress() {
        RequestParams params = new RequestParams();
        params.add("addressid", addressId);
        HttpUtils.put("customer/deleteAddress/" + customerId.replace(" ", "%20"), params, new JsonHttpResponseHandler() {
            // Somehow white space in url crashes the program only in this activity, but not others...

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                showMessageDialogAndBack("Success", "Address deleted successfully.");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    showMessageDialog("Fail", errorResponse.get("message").toString());
                } catch(Exception e) {
                    showMessageDialog("Fail", e.getMessage());
                }
            }
        });
    }

    /**
     * When the user confirms addition,
     * - Read all input,
     * - Check all the fields are non-empty, and
     * - Add the address
     * @author Zhekai Jiang
     */
    private void onConfirmAddButtonClicked() {
        getInputFields();
        if(checkNonEmpty()) {
            postAddress();
        }
    }

    /**
     * Helper method to read all the input fields.
     * @author Zhekai Jiang
     */
    private void getInputFields() {
        name = nameField.getText().toString();
        phone = phoneField.getText().toString();
        street = streetField.getText().toString();
        city = cityField.getText().toString();
        province = provinceField.getText().toString();
        postalCode = postalCodeField.getText().toString();
        country = countryField.getText().toString();
    }

    /**
     * Helper method to check all the fields are non-empty.
     * @author Zhekai Jiang
     * @return true if valid, false if there is any empty field.
     */
    private boolean checkNonEmpty() {
        return checkNonEmpty(name) && checkNonEmpty(phone) && checkNonEmpty(street) && checkNonEmpty(city)
                && checkNonEmpty(province) && checkNonEmpty(postalCode) && checkNonEmpty(country);
    }

    /**
     * Helper method to check whether a string is non-empty.
     * @author Zhekai Jiang
     * @param s The string to be checked.
     * @return true if non-empty, false if empty or null.
     */
    private boolean checkNonEmpty(String s) {
        return s != null && s.length() != 0;
    }

    /**
     * Helper method to add the address via REST controller.
     * Create a dialog showing the result (in method associateCustomerToAddress) and takes the user back to previous activity.
     * @author Zhekai Jiang
     */
    private void postAddress() {
        RequestParams params = new RequestParams();
        params.add("id", addressId);
        params.add("country", country);
        params.add("city", city);
        params.add("postcode", postalCode);
        params.add("province", province);
        params.add("streetaddress", street);
        params.add("number", phone);
        params.add("name", name);
        HttpUtils.post("address", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                associateCustomerToAddress(); // !!!
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    showMessageDialog("Fail", errorResponse.get("message").toString());
                } catch(Exception e) {
                    showMessageDialog("Fail", e.getMessage());
                }
            }
        });
    }

    /**
     * Helper method to associate the address to the customer via REST controller.
     * @author Zhekai Jiang
     */
    private void associateCustomerToAddress() {
        RequestParams params = new RequestParams();
        params.add("address", addressId);
        HttpUtils.put("customer/addAddress/" + customerId.replace(" ", "%20"), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                showMessageDialogAndBack("Success", "Address created successfully.");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    showMessageDialog("Fail", errorResponse.get("message").toString());
                } catch(Exception e) {
                    showMessageDialog("Fail", e.getMessage());
                }
            }
        });
    }

    /**
     * Helper method to update the address.
     * @author Zhekai Jiang
     */
    private void putAddress() {
        RequestParams params = new RequestParams();
        params.add("id", addressId);
        params.add("name", name);
        params.add("phone", phone);
        params.add("streetaddress", street);
        params.add("city", city);
        params.add("province", province);
        params.add("postalcode", postalCode);
        params.add("country", country);
        HttpUtils.put("address/updatefull/" + addressId, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                showMessageDialog("Success", "Address updated successfully");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    showMessageDialog("Success", errorResponse.get("message").toString());
                } catch(Exception e) {
                    showMessageDialog("Fail", e.getMessage());
                }
            }
        });
    }

    /**
     * Helper method to construct a pop-up dialog with only a title, a message, and a Confirm button,
     * with an OnClickListener specifying the action when the OK button is clicked.
     * @author Zhekai Jiang
     * @param title The title of the dialog.
     * @param message The message in the dialog.
     */
    private void showMessageDialogWithListener(String title, String message, DialogInterface.OnClickListener listener) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(EditAddress.this);
        builder.setTitle(title).setMessage(message).setPositiveButton("OK", listener);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Helper method to construct a pop-up dialog with only a title, a message, and a Confirm button.
     * @author Zhekai Jiang
     * @param title The title of the dialog.
     * @param message The message in the dialog.
     */
    private void showMessageDialog(String title, String message) {
        showMessageDialogWithListener(title, message, (dialog, which) -> {});
    }

    /**
     * Helper method to construct a pop-up dialog with only a title, a message, and a Confirm button.
     * When the user clicks on OK, return to previous activity.
     * @author Zhekai Jiang
     * @param title The title of the dialog.
     * @param message The message in the dialog.
     */
    private void showMessageDialogAndBack(String title, String message) {
        showMessageDialogWithListener(title, message, (dialog, which) -> onBackPressed());
    }

    /**
     * Helper method to refresh error message, if any, at the bottom of the page.
     * @author Zhekai Jiang
     */
    private void refreshErrorMessage() {
        if(error.length() > 0) {
            errorView.setVisibility(View.VISIBLE);
            errorView.setText(error);
        } else {
            errorView.setVisibility(View.GONE);
        }
    }
}
