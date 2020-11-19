package ca.mcgill.ecse321.artgallerysystem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

import cz.msebera.android.httpclient.Header;

public class ArtPieceInfo extends AppCompatActivity {
    private String error= null;

    @Override
    public Intent getIntent() {
        return super.getIntent();
    }
    private String id;
    private String author;
    private double price;
    private String description;
    private String name;
    private String username;
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
        setContentView(R.layout.activity_artpieceinfo);
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        id = getIntent().getStringExtra("ARTPIECE_ID");
        username = getIntent().getStringExtra("USERNAME");
        getDetail();
        TextView tv1 = (TextView) findViewById(R.id.textViewARTID);
        tv1.setText(id);
        refreshErrorMessage();

    }
    public void getDetail(){
        HttpUtils.get("artPiece/getArtPiece/" + id, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try{
                    author = response.get("author").toString();
                    price= response.getDouble("price");
                    name = response.get("name").toString();
                    description= response.get("description").toString();
                    description= "\""+description+"\"";
                    //URL url = new URL(description);
                   // URL url = new URL ("https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg");
                    URL url = new URL ("https://www.google.ca/imgres?imgurl=https%3A%2F%2Fwordstream-files-prod.s3.amazonaws.com%2Fs3fs-public%2Fstyles%2Fsimple_image%2Fpublic%2Fimages%2Fsharing-vanity-urls.png%3FHhy.8KHSWcbcd88M6dPvhS7mEg4TDzgH%26itok%3DWdO9kSD2&imgrefurl=https%3A%2F%2Fwww.wordstream.com%2Fblog%2Fws%2F2012%2F10%2F10%2Fvanity-url&tbnid=zJAD7eoqZ_dDoM&vet=12ahUKEwjfhObR8IztAhVNHN8KHQyKDGUQMygCegUIARCkAQ..i&docid=50gFPk30S0rlyM&w=1072&h=801&q=image%20url%20example&client=safari&ved=2ahUKEwjfhObR8IztAhVNHN8KHQyKDGUQMygCegUIARCkAQ");
                    //LoadImage loadImage = new LoadImage()
                    InputStream is = new BufferedInputStream(url.openStream());
                    //Bitmap bitmap;
                    //bitmap = createB
                    ImageView i = (ImageView) findViewById(R.id.imageView);
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
                    i.setImageBitmap(bitmap);


                }catch (Exception e){
                    error+= e.getMessage();
                }
                refreshErrorMessage();
                TextView tv2 = (TextView) findViewById(R.id.textViewAUTHOR);
                tv2.setText(author);
                TextView tv3 = (TextView) findViewById(R.id.textViewPRICE);
                tv3.setText(Double.toString(price));
                //tv.setText("");
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
    public void goPurchase(View v){
        Intent intent = new Intent(ArtPieceInfo.this, Purchase.class );
        intent.putExtra("ARTPIECE_ID", id);
        intent.putExtra("ARTPIECE_NAME", name);
        intent.putExtra("ARTPIECE_PRICE", Double.toString(price));
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }
    public void goHome(View v){
        Intent intent = new Intent(ArtPieceInfo.this, HomePage.class );
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }
}
