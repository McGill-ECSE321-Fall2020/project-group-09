package ca.mcgill.ecse321.artgallerysystem;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * This activity shows the details of a given purchase.
 * It might be accessed by a customer from the purchase list, or by an artist from the art piece list.
 * When accessing by an artist, the page will show an extra button to enable updating the delivery status and tracking information.
 * An intent with extra String ORDERID and boolean ISARTIST (indicating whether the activity is being accessed by an artist) is needed.
 * @author Zhekai Jiang
 */
public class PurchaseDetail extends AppCompatActivity {

    private String purchaseId;
    private JSONObject purchase;

    private String deliveryId;
    private JSONObject delivery;
    private String status;
    private String carrier, trackingNumber;

    private boolean isAccessedByArtist;

    private LinearLayout layout; // overall layout of the entire page

    private TextView statusView;
    private TextView carrierView, trackingNumberView;

    private TextView errorView;
    private String error;

    /**
     * Operations when the activity is started.
     * @author Zhekai Jiang
     * @param savedInstanceState See Activity.onCreate.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.purchase_detail);

        ActionBar actionBar = getSupportActionBar(); // Top bar
        actionBar.setDisplayHomeAsUpEnabled(true);
            // Set the back button on the top-left -> https://geekstocode.com/add-back-button-in-android-title-bar/
        actionBar.setTitle("Purchase Detail");

        errorView = findViewById(R.id.purchase_detail_error);
        error = "";

        layout = findViewById(R.id.purchase_detail_linear_layout);

        purchaseId = getIntent().getStringExtra("ORDERID");
        isAccessedByArtist = getIntent().getBooleanExtra("ISARTIST", false);
        showPurchaseDetail();

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
     * Get the detail of the purchase (via REST controller) and show on the screen.
     * @author Zhekai Jiang
     */
    private void showPurchaseDetail() {
        HttpUtils.get("purchase/" + purchaseId, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                PurchaseDetail.this.purchase = response;
                if(purchase == null) {
                    error += "Purchase " + purchaseId + " not found.";
                    refreshErrorMessage();
                } else {
                    showPurchaseDetailElements();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                appendJsonErrorResponse(errorResponse);
            }
        });
    }

    /**
     * Add all elements to the linear layout and at the same time retrieve some key information of the purchase.
     * @author Zhekai Jiang
     */
    private void showPurchaseDetailElements() {
        try {
            LinearLayoutUtils.addTextViewNarrowMargin("<b>Order ID:</b> " + purchase.getString("orderId"), layout, PurchaseDetail.this);
            LinearLayoutUtils.addTextViewNarrowMargin(
                    "<b>Art Piece:</b> " + purchase.getJSONObject("artPiece").getString("name"), layout, PurchaseDetail.this);
            LinearLayoutUtils.addTextViewNarrowMargin("<b>Purchase Date:</b> " + purchase.getString("date"), layout, PurchaseDetail.this);

            String deliveryMethod = purchase.getString("deliveryMethod");
            LinearLayoutUtils.addTextViewNarrowMargin("<b>Delivery Method:</b> " + deliveryMethod, layout, PurchaseDetail.this);

            status = purchase.getString("deliveryStatus");
            statusView = LinearLayoutUtils.addTextViewNarrowMargin(
                    "<b>Delivery Status:</b> " + convertToShownStatus(status), layout, PurchaseDetail.this);

            delivery = purchase.getJSONObject("delivery");
            deliveryId = delivery.getString("deliveryId");

            if(deliveryMethod.equals("Parcel Delivery")) {
                carrier = delivery.getString("carrier");
                carrierView = LinearLayoutUtils.addTextViewNarrowMargin("<b>Carrier:</b> " + carrier, layout, PurchaseDetail.this);

                trackingNumber = delivery.getString("trackingNumber");
                trackingNumberView = LinearLayoutUtils.addTextViewNarrowMargin(
                        "<b>Tracking Number:</b> " + trackingNumber, layout, PurchaseDetail.this);

                LinearLayoutUtils.addTextViewNarrowMargin("<b>Delivery Address:</b>", layout, PurchaseDetail.this);
                showAddress(delivery.getJSONObject("deliveryAddress"));

                if(isAccessedByArtist) {
                    addButton("Update Delivery Status",
                            v -> onUpdateParcelDeliveryStatusButtonClicked());
                    addButton("Update Tracking Information",
                            v -> onUpdateParcelDeliveryTrackingInformationButtonClicked());
                }
            }

            if (deliveryMethod.equals("In-Store Pick-Up")) {
                LinearLayoutUtils.addTextViewNarrowMargin("<b>Reference Number:</b> " + deliveryId, layout, PurchaseDetail.this);
                LinearLayoutUtils.addTextViewNarrowMargin("<b>Store Address:</b>", layout, PurchaseDetail.this);
                showAddress(delivery.getJSONObject("storeAddress"));

                if(isAccessedByArtist) {
                    addButton("Update Pick-Up Status",
                            v -> onUpdateInStorePickUpStatusButtonClicked());
                }
            }

        } catch(Exception e) {
            error += e.getMessage();
            refreshErrorMessage();
        }
    }

    /**
     * Helper method to add all the content of an address to the linear layout.
     * @author Zhekai Jiang
     * @param address The address, as JSONObject, to be added.
     */
    private void showAddress(JSONObject address) {
        try {
            LinearLayoutUtils.addTextViewNarrowMargin(address.getString("name"), layout, PurchaseDetail.this);
            LinearLayoutUtils.addTextViewNarrowMargin(address.getString("phoneNumber"), layout, PurchaseDetail.this);
            LinearLayoutUtils.addTextViewNarrowMargin(address.getString("streetAddress"), layout, PurchaseDetail.this);
            LinearLayoutUtils.addTextViewNarrowMargin(address.getString("city")
                    + " " + address.getString("province")
                    + " " + address.getString("postalCode")
                    + " " + address.getString("country")
                    , layout, PurchaseDetail.this);
        } catch(Exception e) {
            error += e.getMessage();
            refreshErrorMessage();
        }
    }

    /**
     * Helper method to add a button to the linear layout.
     * @author Zhekai Jiang
     * @param text The text on the button.
     * @param listener The OnClickListener for the button.
     */
    private void addButton(String text, View.OnClickListener listener) {
        Button button = new Button(PurchaseDetail.this);
        button.setText(text);
        layout.addView(button);
        button.setOnClickListener(listener);
    }

    /**
     * Operations when the artist requests to update the status of the parcel delivery.
     * (Show up a pop-up dialog.)
     * @author Zhekai Jiang
     */
    private void onUpdateParcelDeliveryStatusButtonClicked() {
        String[] statuses = new String[]{"Pending", "Shipped", "Delivered"};
        AlertDialog.Builder builder = new AlertDialog.Builder(PurchaseDetail.this);
        builder.setTitle("Update Parcel Delivery Status")
                .setItems(statuses, (dialog, which) -> {
                    try {
                        updateParcelDelivery(statuses[which], carrier, trackingNumber);
                    } catch(Exception e) {
                        error += e.getMessage();
                        refreshErrorMessage();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Operations when the artist requests to update the tracking information (i.e., carrier and tracking number) of the parcel delivery.
     * (Show a pop-up dialog.)
     * @author Zhekai Jiang
     */
    private void onUpdateParcelDeliveryTrackingInformationButtonClicked() {
        LinearLayout dialogLayout = LinearLayoutUtils.initVerticalLinearLayout(PurchaseDetail.this);

        LinearLayoutUtils.addTextViewInDialogForm("<b>Carrier:</b>", dialogLayout, PurchaseDetail.this);

        EditText carrierText = LinearLayoutUtils.addEditTextInDialogForm(carrier, dialogLayout, PurchaseDetail.this);

        LinearLayoutUtils.addTextViewInDialogForm("<b>Tracking Number:</b>", dialogLayout, PurchaseDetail.this);

        EditText trackingNumberText = LinearLayoutUtils.addEditTextInDialogForm(trackingNumber, dialogLayout, PurchaseDetail.this);

        AlertDialog.Builder builder = new AlertDialog.Builder(PurchaseDetail.this);
        builder.setTitle("Update Tracking Information")
                .setView(dialogLayout)
                .setPositiveButton("Confirm", (dialog, which) ->
                        updateParcelDelivery(status, carrierText.getText().toString(), trackingNumberText.getText().toString()));
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Helper method update the status, carrier, and/or tracking number of the parcel delivery (by calling the REST controller).
     * @author Zhekai Jiang
     * @param status The new status of the parcel delivery, which could be "Pending", "Shipped", or "Delivered".
     * @param carrier The new carrier of the parcel.
     * @param trackingNumber The new tracking number of the parcel.
     */
    private void updateParcelDelivery(String status, String carrier, String trackingNumber) {
        RequestParams params = new RequestParams();
        params.add("parcelDeliveryStatus", status);
        params.add("carrier", carrier);
        params.add("trackingNumber", trackingNumber);
        HttpUtils.put("parcelDelivery/updateFull/" + deliveryId, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                showMessageDialog("Success", "Parcel delivery information updated successfully.");
                refreshParcelDelivery();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                showErrorDialogForJsonErrorResponse(errorResponse);
            }
        });
    }

    /**
     * Helper method to refresh the status, carrier, and tracking number of the parcel delivery shown on the screen.
     * @author Zhekai Jiang
     */
    private void refreshParcelDelivery() {
        HttpUtils.get("parcelDeliveryes/" + deliveryId, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                delivery = response;
                try {
                    status = response.getString("parcelDeliveryStatus");
                    statusView.setText(Html.fromHtml("<b>Delivery Status: </b> " + status));
                    carrier = response.getString("carrier");
                    carrierView.setText(Html.fromHtml("<b>Carrier: </b> " + carrier));
                    trackingNumber = response.getString("trackingNumber");
                    trackingNumberView.setText(Html.fromHtml("<b>Tracking Number: </b> " + trackingNumber));
                } catch(Exception e) {
                    error += e.getMessage();
                    refreshErrorMessage();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                appendJsonErrorResponse(errorResponse);
            }
        });
    }

    /**
     * Operations when the artist requests to update the in-store pick-up status.
     * (Show a pop-up dialog.)
     * @author Zhekai Jiang
     */
    private void onUpdateInStorePickUpStatusButtonClicked() {
        String[] shownStatuses = new String[]{"Pending", "Available for Pick-Up", "Picked Up"};
        String[] internalStatuses = new String[]{"Pending", "Available", "PickedUp"};

        AlertDialog.Builder builder = new AlertDialog.Builder(PurchaseDetail.this);
        builder.setTitle("Update In-Store Pick-Up Status")
                .setItems(shownStatuses, (dialog, which) -> {
                    try {
                        updateInStorePickUp(internalStatuses[which]);
                    } catch(Exception e) {
                        error += e.getMessage();
                        refreshErrorMessage();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Helper method to update the status of the in-store pick-up (by calling the REST controller).
     * @author Zhekai Jiang
     * @param status The new status of the in-store pick-up, which could be "Pending", "Available", or "PickedUp".
     */
    private void updateInStorePickUp(String status) {
        RequestParams params = new RequestParams();
        params.add("inStorePickUp", status);
        HttpUtils.put("inStorePickUp/update/" + deliveryId, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                showMessageDialog("Success", "In-store pick-up status updated successfully.");
                refreshInStorePickUp();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                showErrorDialogForJsonErrorResponse(errorResponse);
            }
        });
    }

    /**
     * Helper method to refresh the status of the in-store pick-up shown on the screen.
     * @author Zhekai Jiang
     */
    private void refreshInStorePickUp() {
        HttpUtils.get("inStorePickUps/" + deliveryId, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                delivery = response;
                try {
                    status = response.getString("inStorePickUpStatus");
                    statusView.setText(Html.fromHtml("<b>Delivery Status: </b> " + convertToShownStatus(status)));
                } catch(Exception e) {
                    error += e.getMessage();
                    refreshErrorMessage();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                appendJsonErrorResponse(errorResponse);
            }
        });
    }

    /**
     * Helper method to construct a pop-up dialog with only a title, a message, and a Confirm button.
     * @author Zhekai Jiang
     * @param title The title of the dialog.
     * @param message The message in the dialog.
     */
    private void showMessageDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PurchaseDetail.this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {});
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Helper method to convert the delivery status stored internally to what is supposed to be shown to the user.
     * Could be used for both parcel delivery or in-store pick-up.
     * For "AvailableForPickUp" and "PickedUp" statuses for in-store pick-up, add proper spaces before shown to the user.
     * Otherwise, they are the same.
     * @author Zhekai Jiang
     * @param status The internal status of the delivery as String,
     *               which could be "Pending", "Shipped", or "Delivered" for parcel delivery,
     *               or "Pending", "Available", or "PickedUp" for in-store pick-up.
     * @return The status that is supposed to be shown to the user.
     *         "Available" and "PickedUp" will be converted to "Available for Pick-Up" and "Picked Up" respectively,
     *         and other possible statuses will remain the same.
     */
    private String convertToShownStatus(String status) {
        switch(status) {
            case "AvailableForPickUp":
                return "Available for Pick-Up";
            case "PickedUp":
                return "Picked Up";
            default:
                return status;
        }
    }

    /**
     * Helper method to show the error message in a pop-up dialog based on the error message responded in a JSON object.
     * Used for showing errors when the user submits a form in a dialog.
     * @author Zhekai Jiang
     * @param errorResponse The JSONObject containing the error message.
     */
    private void showErrorDialogForJsonErrorResponse(JSONObject errorResponse) {
        String putError = "";
        try {
            putError += errorResponse.get("message").toString();
        } catch(Exception e) {
            putError += e.getMessage();
        }
        showMessageDialog("Fail", putError);
    }

    /**
     * Helper method to append the error message to the field on top of the screen based on the response in a JSON object.
     * @author Zhekai Jiang
     * @param errorResponse The JSONObject containing the error message.
     */
    private void appendJsonErrorResponse(JSONObject errorResponse) {
        try {
            error += errorResponse.get("message").toString();
        } catch(Exception e) {
            error += e.getMessage();
        }
        refreshErrorMessage();
    }

    /**
     * Refresh the error message shown on top of the screen.
     * @author Zhekai Jiang
     */
    private void refreshErrorMessage() {
        if(error != null && error.length() > 0) {
            errorView.setVisibility(View.VISIBLE);
            errorView.setText(error);
        } else {
            errorView.setVisibility(View.GONE);
        }
    }
}
