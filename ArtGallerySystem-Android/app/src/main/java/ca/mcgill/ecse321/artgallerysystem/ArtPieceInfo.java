package ca.mcgill.ecse321.artgallerysystem;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

import cz.msebera.android.httpclient.Header;

/**
 * this class is used to display detailed art piece information and provides the button to either return to home page or go purchase
 * @author amelia
 */
public class ArtPieceInfo extends AppCompatActivity {
    private String error= "";

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
     * get useful parameters from last page when create and display artpiece id
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artpieceinfo);
        id = getIntent().getStringExtra("ARTPIECE_ID");
        username = getIntent().getStringExtra("USERNAME");
        getDetail();
        TextView tv1 = (TextView) findViewById(R.id.textViewARTID);
        tv1.setText(id);
        refreshErrorMessage();

    }

    /**
     * this method is used to display image and other information about artpiece
     */
    public void getDetail(){
        HttpUtils.get("artPiece/getArtPiece/" + id, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try{
                    author = response.get("author").toString();
                    price= response.getDouble("price");
                    name = response.get("name").toString();
                    description= response.get("description").toString();
                    ImageView imageView=(ImageView) findViewById(R.id.imageView);
                    Glide.with(ArtPieceInfo.this).load(description).into(imageView);
                }catch (Exception e){
                    error += e.getMessage();
                }
                refreshErrorMessage();
                TextView tv2 = (TextView) findViewById(R.id.textViewAUTHOR);
                tv2.setText(author);
                TextView tv3 = (TextView) findViewById(R.id.textViewPRICE);
                tv3.setText(Double.toString(price));
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

    /**
     * this method is used when click purchase button, it passes required parameters in order to perform purchase operation
     * @param v
     */
    public void goPurchase(View v){
        Intent intent = new Intent(ArtPieceInfo.this, Purchase.class );
        intent.putExtra("ARTPIECE_ID", id);
        intent.putExtra("ARTPIECE_NAME", name);
        intent.putExtra("ARTPIECE_PRICE", Double.toString(price));
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }

    /**
     * this method is used when click back button, it passes required parameters in order to go back to home page
     * @param v
     */
    public void goHome(View v){
        Intent intent = new Intent(ArtPieceInfo.this, HomePage.class );
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }
    private Drawable LoadImageFromWebOperations(String url)
    {
        try{
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        }catch (Exception e) {
            System.out.println("Exc="+e);
            return null;
        }
    }

}
