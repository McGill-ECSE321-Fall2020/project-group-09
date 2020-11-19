package ca.mcgill.ecse321.artgallerysystem;

//import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class Purchase extends AppCompatActivity {
    private String error= null;
    private String id;
    private String author;
    private String price;
    private String description;
    private String name;
    private String username;
    private ArrayAdapter<String> storeAdapter;
    private ArrayAdapter<String> methodAdapter;
    private ArrayAdapter<String> parcelAdapter;
    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);

        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase);
        refreshErrorMessage();
        id = getIntent().getStringExtra("ARTPIECE_ID");
        name= getIntent().getStringExtra("ARTPIECE_NAME");
        price=getIntent().getStringExtra("ARTPIECE_PRICE");
        username=getIntent().getStringExtra("USERNAME");
        TextView tv1 = (TextView) findViewById(R.id.artpieceID);
        tv1.setText(id);
        TextView tv2 = (TextView) findViewById(R.id.artpieceName);
        tv2.setText(name);
        TextView tv3 = (TextView) findViewById(R.id.artpiecePrice);
        tv3.setText(price);
        Spinner stores = (Spinner) findViewById(R.id.spinnerStore);
        List<String> store = new ArrayList<>();
        store.add("Please select...");
        store.add("StoreA");
        store.add("StoreB");
        storeAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, store);
        stores.setAdapter(storeAdapter);
        Spinner methods = (Spinner) findViewById(R.id.spinnerMethod);
        List<String> method = new ArrayList<>();
        method.add("Please select...");
        method.add("CreditCard");
        method.add("DebitCard");
        methodAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, method);
        methods.setAdapter(methodAdapter);
        Spinner parcels = (Spinner) findViewById(R.id.spinnerParcel);
        List<String> addresses = new ArrayList<>();
        parcelAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, addresses);
        refreshList(parcelAdapter, addresses);
        parcels.setAdapter(parcelAdapter);
        }
    private void refreshList(final ArrayAdapter<String> adapter, final List<String> names) {
        HttpUtils.get("/addresses/user/"+username, new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                names.clear();
                names.add("Please select...");
                for( int i = 0; i < response.length(); i++){
                    try {
                        names.add(response.getJSONObject(i).getString("addressId")+"--"+response.getJSONObject(i).getString("name"));
                    } catch (Exception e) {
                        error += e.getMessage();
                    }
                    refreshErrorMessage();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }
    //private AlertDialog builder1 = new AlertDialog.Builder(this);
    public void createPurchase(View v){
        Spinner methods = (Spinner) findViewById(R.id.spinnerMethod);
        Spinner stores = (Spinner) findViewById(R.id.spinnerStore);
        Spinner parcels = (Spinner) findViewById(R.id.spinnerParcel);
        TextView cardID = (TextView) findViewById(R.id.editTextNumber);
        CharSequence card = cardID.getText();
        String method = methods.getSelectedItem().toString();
        String selectedStore = stores.getSelectedItem().toString();
        String selectedParcel = parcels.getSelectedItem().toString();

        if (method.equalsIgnoreCase("Please select...")){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage("Please select a payment method")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Alright", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }
        else if (card.length()!= 16){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage("Please enter 16-digit card ID")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Alright", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }
        else if (selectedStore.equalsIgnoreCase("Please select..." ) && selectedParcel.equalsIgnoreCase("Please select..." ) ){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage("Please select an address")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Alright", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }
        else if (!(selectedStore.equalsIgnoreCase("Please select..." )) && !(selectedParcel.equalsIgnoreCase("Please select..." )) ){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage("Please select ONLY one address")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Alright", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }
        else {
            Date today = new Date();
            createPur(username, id, today, "Successful");
        }


    }

    private void createPur(String username, String id, Date today, String successful) {
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        String orderNum = orderNumber();
        RequestParams rp = new RequestParams();
        rp.add("customerid", username);
        rp.add("artpieceid", id);
        rp.add("date", thisDate);
        rp.add("status", successful);
        HttpUtils.post("/purchase/"+(orderNum), rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                createPayment(orderNum);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //try {
                    error += thisDate;
                error += username;
               // } catch (JSONException e) {
               //     error += thisDate;
               // }
                refreshErrorMessage();
            }
        });
    }

    private void createPayment(String orderNum) {
        Spinner methods = (Spinner) findViewById(R.id.spinnerMethod);
        Spinner stores = (Spinner) findViewById(R.id.spinnerStore);
        Spinner parcels = (Spinner) findViewById(R.id.spinnerParcel);
        RequestParams rp = new RequestParams();
        rp.add("id", orderNumber());
        rp.add("success", "true");
        rp.add("method", methods.getSelectedItem().toString());
        rp.add("status", "Successful");
        rp.add("purchaseid", orderNum);
        HttpUtils.post("/payment/", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                if (stores.getSelectedItem().toString().equalsIgnoreCase("StoreA")){
                    createStorePickup(orderNum, "StoreA");
                }else if (stores.getSelectedItem().toString().equalsIgnoreCase("StoreB")){
                    createStorePickup(orderNum, "StoreB");
                }else if (parcels.getSelectedItem().toString()!=null){
                    String addressID = parcels.getSelectedItem().toString().split("--")[0];
                    createParcel(orderNum, addressID);
                }
                //createPayment(orderNum);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //try {
               // error += thisDate;
                error += username;
                // } catch (JSONException e) {
                //     error += thisDate;
                // }
                refreshErrorMessage();
            }
        });
    }

    private void createStorePickup(String orderNum, String store) {
        String deliveryID = orderNumber();
        RequestParams rp = new RequestParams();
        rp.add("deliveryid", deliveryID);
        rp.add("pickUpReferenceNumber", deliveryID);
        rp.add("inStorePickUpStatus", "Pending");
        rp.add("storeAddress", store);
        rp.add("purchaseid", orderNum);
        HttpUtils.post("/inStorePickUp", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                Intent intent = new Intent(Purchase.this, SuccessfulPayment.class );
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //try {
                // error += thisDate;
                error += username;
                // } catch (JSONException e) {
                //     error += thisDate;
                // }
                refreshErrorMessage();
            }
        });

    }
    private void createParcel(String orderNum, String address) {
        String deliveryID = orderNumber();
        RequestParams rp = new RequestParams();
        rp.add("deliveryid", deliveryID);
        rp.add("trackingNumber", "");
        rp.add("carrier", "");
        rp.add("parcelDeliveryStatus", "Pending");
        rp.add("deliveryAddress", address);
        rp.add("purchaseid", orderNum);
        HttpUtils.post("/parcelDelivery", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                Intent intent = new Intent(Purchase.this, SuccessfulPayment.class );
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //try {
                // error += thisDate;
                error += username;
                // } catch (JSONException e) {
                //     error += thisDate;
                // }
                refreshErrorMessage();
            }
        });

    }

    private String orderNumber (){
        //String now = (new Date()).toString();
        Random rand = new Random();
        int rand_int1 = rand.nextInt(1000);
        int rand_int2 = rand.nextInt(1000);
        int rand_int3 = rand.nextInt(1000);
        String a = Integer.toString(rand_int1);
        String b = Integer.toString(rand_int2);
        String c = Integer.toString(rand_int3);
        return a+"-"+ b+"-"+ c;
    }
    public void applyNew(View v){
        Spinner methods = (Spinner) findViewById(R.id.spinnerMethod);
        String method = methods.getSelectedItem().toString();
        TextView cardID = (TextView) findViewById(R.id.editTextNumber);
        CharSequence card = cardID.getText();
        if (method.equalsIgnoreCase("Please select...")){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage("Please select a payment method")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Alright", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }
        else if (card.length()!= 16){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage("Please enter 16-digit card ID")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Alright", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }else{
            Intent intent = new Intent(this, ApplyNewAddress.class );
            intent.putExtra("ARTPIECE_ID", id);
            intent.putExtra("USERNAME", username);
            intent.putExtra("PAYMENT_METHOD", method);
            intent.putExtra("ARTPIECE_NAME", name);
            intent.putExtra("ARTPIECE_PRICE", price);
            startActivity(intent);
        }

    }
    public void cancel(View v){
        Intent intent = new Intent(this, ArtPieceInfo.class );
        intent.putExtra("ARTPIECE_ID", id);
        intent.putExtra("USERNAME", username);
        intent.putExtra("ARTPIECE_NAME", name);
        intent.putExtra("ARTPIECE_PRICE", price);
        startActivity(intent);
    }



}
