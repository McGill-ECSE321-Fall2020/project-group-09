package ca.mcgill.ecse321.artgallerysystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class ApplyNewAddress extends AppCompatActivity {
    private String error= null;
    private String addressID;
    private Context context = this;
    private String username;
    private String artpieceID;
    private String paymentMethod;
    private String artpieceName;
    private String price;
    //public Intent intent = new Intent(this, Purchase.class);
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
        setContentView(R.layout.apply_new_address);
        TextView tv1 = (TextView) findViewById(R.id.addressID);
        addressID= orderNumber();
        tv1.setText(addressID);
        username=getIntent().getStringExtra("USERNAME");
        artpieceID=getIntent().getStringExtra("ARTPIECE_ID");
        paymentMethod= getIntent().getStringExtra("PAYMENT_METHOD");
        artpieceName= getIntent().getStringExtra("ARTPIECE_NAME");
        price= getIntent().getStringExtra("ARTPIECE_PRICE");
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
    public void goBackPurchase(View v){
        Intent intent = new Intent(ApplyNewAddress.this, Purchase.class );
        intent.putExtra("ARTPIECE_ID", artpieceID);
        intent.putExtra("USERNAME", username);
        intent.putExtra("PAYMENT_METHOD", paymentMethod);
        intent.putExtra("ARTPIECE_NAME", artpieceName);
        intent.putExtra("ARTPIECE_PRICE", price);
        startActivity(intent);
    }
    public void addAdress(View v){
        TextView tv2 = (TextView) findViewById(R.id.editTextaddressName);
        TextView tv3 = (TextView) findViewById(R.id.editTextStreetAddress);
        TextView tv4 = (TextView) findViewById(R.id.editCountry);
        TextView tv5 = (TextView) findViewById(R.id.editTextCity);
        TextView tv6 = (TextView) findViewById(R.id.editTextPhone);
        TextView tv7 = (TextView) findViewById(R.id.editTextProvince);
        TextView tv8 = (TextView) findViewById(R.id.editTextTextPostalAddress);
        String country = tv4.getText().toString();
        String city = tv5.getText().toString();
        String postcode = tv8.getText().toString();
        String streetaddress = tv3.getText().toString();
        String province = tv7.getText().toString();
        String number = tv6.getText().toString();
        String name = tv2.getText().toString();
        if (country==null||city==null||postcode==null||streetaddress==null||province==null||number==null||name==null){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage("Please input all information")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Alright", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }else{
            RequestParams rp = new RequestParams();
            rp.add("id", addressID);
            rp.add("country", country);
            rp.add("city", city);
            rp.add("postcode", postcode);
            rp.add("streetaddress", streetaddress);
            rp.add("province", province);
            rp.add("number", number);
            rp.add("name", name);
            HttpUtils.post("/address", rp, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    refreshErrorMessage();
                    useitOrNot();

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    refreshErrorMessage();
                }
            });
        }












    }

    private void useitOrNot() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage("Do you want to use it? ")
            .setIcon(android.R.drawable.ic_dialog_alert)
        .setPositiveButton("Sure", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                createPur(username, artpieceID, "Successful");
            }
        })
        .setNegativeButton("I changed my mind", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent intent = new Intent(ApplyNewAddress.this, Purchase.class );
                intent.putExtra("ARTPIECE_ID", artpieceID);
                intent.putExtra("USERNAME", username);
                intent.putExtra("PAYMENT_METHOD", paymentMethod);
                intent.putExtra("ARTPIECE_NAME", artpieceName);
                intent.putExtra("ARTPIECE_PRICE", price);
                startActivity(intent);
            }
        })
                .show();
    }
    private void createPur(String username, String id, String successful) {
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
        RequestParams rp = new RequestParams();
        rp.add("id", orderNumber());
        rp.add("success", "true");
        rp.add("method", paymentMethod);
        rp.add("status", "Successful");
        rp.add("purchaseid", orderNum);
        HttpUtils.post("/payment/", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                    createParcel(orderNum, addressID);
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
                Intent intent = new Intent(ApplyNewAddress.this, SuccessfulPayment.class );
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


}
