package ca.mcgill.ecse321.artgallerysystem;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cz.msebera.android.httpclient.Header;

import static androidx.core.content.ContextCompat.startActivity;
import static java.util.Collections.unmodifiableList;


public class CreateArtPiece extends AppCompatActivity{

    private String error= null;
    private String artpieceName;
    private String author;
    private double price;
    private String artpieceURL;
    private Date date;
    private List<String> artists;
    private static final String TAG = "CreateArtPiece";
    private String id = orderNumber();
    private String user;




    private void CreateArtPiece() {

        TextView tv2 = (TextView) findViewById(R.id.editTextArtPieceName);
        TextView tv3 = (TextView) findViewById(R.id.editTextImageURL);
        TextView tv4 = (TextView) findViewById(R.id.editTextTextAuthor);
        TextView tv5 = (TextView) findViewById(R.id.editPrice);
        TextView tv6 = (TextView) findViewById(R.id.newArtpiece_date);
        MultiSpinnerSearch multiSelectSpinnerWithSearch = findViewById(R.id.multipleItemSelectionSpinner);
       List<KeyPairBoolData> sel = multiSelectSpinnerWithSearch.getSelectedItems();
        String name = tv2.getText().toString();
        String des = tv3.getText().toString();
        String author = tv4.getText().toString();
        String price = tv5.getText().toString();
        String date = tv6.getText().toString();
        String comps[] = date.split("-");
        int year = Integer.parseInt(comps[2]);
        int month = Integer.parseInt(comps[0]);
        int day = Integer.parseInt(comps[1]);

        if (name ==null||des==null||author==null||price==null||date==null||sel.size()==0){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage("Please input all information")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Alright", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }
        else if ( !URLUtil.isValidUrl(des)){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage("Please input valid url")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Alright", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }
        else{
            RequestParams rp = new RequestParams();

            NumberFormat formatter = new DecimalFormat("00");
            //id = orderNumber();

            rp.add("name",name);
            rp.add("id", id);
            rp.add("des",des);
            rp.add("author",author);
            rp.add("price",price);
            rp.add("status", "Available");
            rp.add("date", year + "-" + formatter.format(month) + "-" + formatter.format(day));
            //todo: add artists

            HttpUtils.post("/artPiece/createArtPiece", rp, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    addArtist();
                    AlertDialog alertDialog = new AlertDialog.Builder(CreateArtPiece.this)
                            .setMessage("Successfully create!")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNegativeButton("go back home", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(CreateArtPiece.this, HomePage.class);
                                    intent.putExtra("USERNAME", user);
                                    startActivity(intent);
                                }
                            })
                            .setPositiveButton("Create another one", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                   // } catch (JSONException e) {
                   //     error += e.getMessage();
                   // }
                    refreshErrorMessage();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    try {
                        error += errorResponse.get("message").toString();
                    } catch (JSONException e) {
                        error += e.getMessage();
                    }
                    error += year + "-" + formatter.format(month) + "-" + formatter.format(day);
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
        user = getIntent().getStringExtra("USERNAME");
        artists= getArtists();

        /*MultiSpinnerSearch multiSelectSpinnerWithSearch = findViewById(R.id.multipleItemSelectionSpinner);
        // Pass true If you want searchView above the list. Otherwise false. default = true.
        multiSelectSpinnerWithSearch.setSearchEnabled(true);

        // A text that will display in search hint.
        multiSelectSpinnerWithSearch.setSearchHint("Select your mood");

        // Set text that will display when search result not found...
        multiSelectSpinnerWithSearch.setEmptyTitle("Not Data Found!");

        // If you will set the limit, this button will not display automatically.
        multiSelectSpinnerWithSearch.setShowSelectAllButton(true);

        //A text that will display in clear text button
        multiSelectSpinnerWithSearch.setClearText("Close & Clear");

        KeyPairBoolData k = new KeyPairBoolData();

        List<KeyPairBoolData> listArray0 = new ArrayList<>();
        for (int i = 0; i < artists.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(artists.get(i));
            h.setSelected(false);
            listArray0.add(h);
        }
        final List<KeyPairBoolData> listArray1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName("a");
            h.setSelected(false);
            listArray1.add(h);
        }
        //multiSelectSpinnerWithSearch.setItems();
        //multiSelectSpinnerWithSearch.setItems(listArray0);
        multiSelectSpinnerWithSearch.setItems(listArray1, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });*/
        refreshErrorMessage();


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
    public void addArtist(){
        List<String> artists = new ArrayList<>();
        MultiSpinnerSearch multiSelectSpinnerWithSearch = findViewById(R.id.multipleItemSelectionSpinner);
        List<KeyPairBoolData> results = multiSelectSpinnerWithSearch.getSelectedItems();
        for (KeyPairBoolData k : results){
            if(k.isSelected()==true){
                artists.add(k.getName());
            }
        }
        for (int i = 0;i<artists.size();i++){
            RequestParams rp = new RequestParams();
            rp.add("artistid", artists.get(i));
            //int finalI = i;
            HttpUtils.put("/artPiece/addArtist/"+id, rp, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    //if (i == artists.size()-1){
                        /*AlertDialog alertDialog = new AlertDialog.Builder(CreateArtPiece.this)
                                .setMessage("Successfully create!")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setNegativeButton("go back home", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(CreateArtPiece.this, HomePage.class);
                                        intent.putExtra("USERNAME", user);
                                        startActivity(intent);
                                    }
                                })
                                .setPositiveButton("Create another one", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                                .show();*/
                   // }
                   // error+=finalI;
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

    public List<String> getArtists() {
        List<String> result = new ArrayList<>();
        HttpUtils.get("/artist/artistids", new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                final List<KeyPairBoolData> listArray1 = new ArrayList<>();
                for( int i = 0; i < response.length(); i++){
                    try {
                        KeyPairBoolData h = new KeyPairBoolData();
                        h.setId(i + 1);
                        h.setName(response.getString(i));
                        h.setSelected(false);
                        listArray1.add(h);
                        result.add(response.getString(i));
                    } catch (Exception e) {
                        error += e.getMessage();
                    }
                    refreshErrorMessage();
                }
                MultiSpinnerSearch multiSelectSpinnerWithSearch = findViewById(R.id.multipleItemSelectionSpinner);
                // Pass true If you want searchView above the list. Otherwise false. default = true.
                multiSelectSpinnerWithSearch.setSearchEnabled(true);

                // A text that will display in search hint.
                multiSelectSpinnerWithSearch.setSearchHint("Select artist");

                // Set text that will display when search result not found...
                multiSelectSpinnerWithSearch.setEmptyTitle("Not Data Found!");

                // If you will set the limit, this button will not display automatically.
                multiSelectSpinnerWithSearch.setShowSelectAllButton(true);

                //A text that will display in clear text button
                multiSelectSpinnerWithSearch.setClearText("Close & Clear");

                multiSelectSpinnerWithSearch.setItems(listArray1, new MultiSpinnerListener() {
                    @Override
                    public void onItemsSelected(List<KeyPairBoolData> items) {
                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).isSelected()) {
                                Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                            }
                        }
                    }
                });

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
        return result;

    }



}






