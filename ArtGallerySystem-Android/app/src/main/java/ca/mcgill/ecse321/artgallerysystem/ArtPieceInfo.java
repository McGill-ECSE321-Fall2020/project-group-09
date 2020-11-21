package ca.mcgill.ecse321.artgallerysystem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpConnection;

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
        //ImageView imgView =(ImageView)findViewById(R.id.imageView);
       // Drawable drawable = LoadImageFromWebOperations("http://www.avajava.com/images/avajavalogo.jpg");
        //imgView.setImageDrawable(drawable);
        //new DownloadImageTask((ImageView) findViewById(R.id.imageView))
         //       .execute("http://www.avajava.com/images/avajavalogo.jpg");
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

                    //URL url = new URL(description);
                    //URL url = new URL ("https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg");
                   // URL url = new URL("http://www.avajava.com/images/avajavalogo.jpg");
                    //URL url = new URL("https://pic.cnblogs.com/avatar/1142647/20170416093225.png");

                    //URL url = (new File("/Users/amelia/Desktop/demo.png").toURI().toURL());
                    //URL url = new URL ("data:image/jpeg;base64,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");
                    //LoadImage loadImage = new LoadImage()
                    /*HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setRequestMethod("GET");
                    if (connection.getResponseCode()==200){
                        InputStream is = connection.getInputStream();
                        ImageView i = (ImageView) findViewById(R.id.imageView);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        i.setImageBitmap(bitmap);
                       // Bitmap bitmap = BitmapFactory.decodeStream(is);
                    }*/

                  //  connection.connect();
                    //InputStream is = connection.getInputStream();
                  //  InputStream is = url.openStream();
                    //InputStream is = new InputStream(url.openStream());
                    //Bitmap bitmap;
                    //bitmap = createB
                   // ImageView i = (ImageView) findViewById(R.id.imageView);
                  //  Bitmap bitmap = BitmapFactory.decodeStream(is);
                  //  i.setImageBitmap(bitmap);
                    //new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                    //        .execute("http://www.avajava.com/images/avajavalogo.jpg");
                    author = response.get("author").toString();
                    price= response.getDouble("price");
                    name = response.get("name").toString();
                    description= response.get("description").toString();
                    //description= "\""+description+"\"";
                    ImageView imageView=(ImageView) findViewById(R.id.imageView);

                    //String url = "https://pic.cnblogs.com/avatar/1142647/20170416093225.png";
                    Glide.with(ArtPieceInfo.this).load(description).into(imageView);



                }catch (Exception e){
                    error += e.getMessage();
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
