package ca.mcgill.ecse321.artgallerysystem;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cz.msebera.android.httpclient.Header;

import static androidx.core.content.ContextCompat.startActivity;


public class CreateArtPiece extends AppCompatActivity{

    private String error= null;
    private String artpieceName;
    private String author;
    private double price;
    private String artpieceURL;
    private Date date;




    private void CreateArtPiece() {

        TextView tv2 = (TextView) findViewById(R.id.editTextArtPieceName);
        TextView tv3 = (TextView) findViewById(R.id.editTextImageURL);
        TextView tv4 = (TextView) findViewById(R.id.editTextTextAuthor);
        TextView tv5 = (TextView) findViewById(R.id.editPrice);
        TextView tv6 = (TextView) findViewById(R.id.newArtpiece_date);

        String name = tv2.getText().toString();
        String des = tv3.getText().toString();
        String author = tv4.getText().toString();
        String price = tv5.getText().toString();
        String date = tv6.getText().toString();
        String comps[] = date.split("-");
        int year = Integer.parseInt(comps[2]);
        int month = Integer.parseInt(comps[1]);
        int day = Integer.parseInt(comps[0]);

        if (name ==null||des==null||author==null||price==null||date==null){
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

            NumberFormat formatter = new DecimalFormat("00");

            rp.add("name",name);
            rp.add("des",des);
            rp.add("author",author);
            rp.add("price",price);
            rp.add("date", year + "-" + formatter.format(month) + "-" + formatter.format(day));
            //todo: add artists

            HttpUtils.post("/artpiece", rp, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        JSONObject serverResp = new JSONObject(response.toString());
                    } catch (JSONException e) {
                        error += e.getMessage();
                    }
                    refreshErrorMessage();
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



    }


    /**
     * this method is used to refresh error
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
        setContentView(R.layout.create_artpiece);


        artpieceName= getIntent().getStringExtra("ARTPIECE_NAME");
        artpieceURL = getIntent().getStringExtra("URL");
        author = getIntent().getStringExtra("Author");
        price= getIntent().getDoubleExtra("Price",0);


    }


    private Bundle getDateFromLabel(String text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split("-");
        int day = 1;
        int month = 1;
        int year = 1;

        if (comps.length == 3) {
            day = Integer.parseInt(comps[0]);
            month = Integer.parseInt(comps[1]);
            year = Integer.parseInt(comps[2]);
        }

        rtn.putInt("day", day);
        rtn.putInt("month", month-1);
        rtn.putInt("year", year);

        return rtn;
    }

    public void showDatePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getDateFromLabel(tf.getText().toString());
        args.putInt("id", v.getId());

        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void setDate(int id, int d, int m, int y) {
        TextView tv = (TextView) findViewById(R.id.newArtpiece_date);
        tv.setText(String.format("%02d-%02d-%04d", d, m + 1, y));
    }


    public void goHome(View v){
        Intent intent = new Intent(CreateArtPiece.this, HomePage.class );

        startActivity(intent);
    }
    public void addArtPiece(View v){
        CreateArtPiece();
    }



}






