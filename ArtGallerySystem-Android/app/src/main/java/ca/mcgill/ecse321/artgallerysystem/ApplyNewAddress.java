package ca.mcgill.ecse321.artgallerysystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

/**
 * this is the apply new address page which allows the user to create an address on the fly when purchase
 * so that he/she does not necessarily need to use the addresses saved for parcel delivery
 * @author amelia
 */
public class ApplyNewAddress extends AppCompatActivity {
    private String error= null;
    private String addressID;
    private Context context = this;
    private String username;
    private String artpieceID;
    private String paymentMethod;
    private String artpieceName;
    private String price;

    /**
     * this method is used in every class to detect error message
     */
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

    /**
     * get useful parameters from last page when create
     * @param savedInstanceState
     */
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

    /**
     * random ID generator
     * @return random ID
     */
    private String orderNumber (){
        Random rand = new Random();
        int rand_int1 = rand.nextInt(1000);
        int rand_int2 = rand.nextInt(1000);
        int rand_int3 = rand.nextInt(1000);
        String a = Integer.toString(rand_int1);
        String b = Integer.toString(rand_int2);
        String c = Integer.toString(rand_int3);
        return a+"-"+ b+"-"+ c;
    }

    /**
     * return to purchase page if cancel apply new address
     * @param v
     */
    public void goBackPurchase(View v){
        Intent intent = new Intent(ApplyNewAddress.this, Purchase.class );
        intent.putExtra("ARTPIECE_ID", artpieceID);
        intent.putExtra("USERNAME", username);
        intent.putExtra("PAYMENT_METHOD", paymentMethod);
        intent.putExtra("ARTPIECE_NAME", artpieceName);
        intent.putExtra("ARTPIECE_PRICE", price);
        startActivity(intent);
    }

    /**
     * main method to add new address, called when confirm button clicks
     * it first check for empty boxes, show warning if there is empty field
     * else, send a request to the backend to create a new address in the database
     * @param v
     */
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
        if (country.equals("")||city.equals("")||postcode.equals("")||streetaddress.equals("")||province.equals("")||number.equals("")||name.equals("")){
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

    /**
     * the dialog shows after successfully created new address, the user can choose to use it or not
     * if choose to use it, directly create corresponding purchase, payment, delivery
     * else, return to the purchase page and pass parameters
     */
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

    /**
     * this is the same create purchase method used in purchase page, put it here to create a purchase directly in this page so we dont have to go back
     * @param username
     * @param id
     * @param successful
     */
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
                error += thisDate;
                error += username;
                refreshErrorMessage();
            }
        });
    }

    /**
     * this is the same create payment method as the purchase page
     * @param orderNum  purchase id
     */

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
                error += username;
                refreshErrorMessage();
            }
        });
    }

    /**
     * this is the same parcel delivery creating method as purchase page, the address provided here is the newly created address in this page
     * @param orderNum purchase id
     * @param address address id
     */
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
                error += username;
                refreshErrorMessage();
            }
        });

    }


}
