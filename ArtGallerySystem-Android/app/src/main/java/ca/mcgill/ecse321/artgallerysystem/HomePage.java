package ca.mcgill.ecse321.artgallerysystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cz.msebera.android.httpclient.Header;

/**
 * this page is used to display all the artpieces from the system, so that user can choose one to check detailed information
 */
public class HomePage extends AppCompatActivity {
    private String error= null;
    @Override
    public Intent getIntent(){
        return super.getIntent();}
    private String username;
    private String id;
    private Map artpieces = new HashMap<String, String>();
    private List<String> descriptions = new ArrayList<>();
    private List<String> ids = new ArrayList<>();
    /**
     * this method is used to refresh error
     * @author Angelina, Amelia
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
     * this method is called everytime this page is created, it gets the information from previous page, display information
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        username = getIntent().getStringExtra("USERNAME");
        refreshList();
        descriptions = new ArrayList<String>(artpieces.values());
        TextView id = (TextView) findViewById(R.id.artpieceID);
    }
    /**
     * this method is used when My Account button is click on the top left of the page, it goes to the account information page
     * @param v
     */
    public void goAccount(View v){
        Intent intent = new Intent(HomePage.this, MainActivity.class);
        startActivity(intent);
    }
    /**
     * this method is used when Upload button is click on the top right of the page, it goes to the upload page
     * @param v
     */
    public void goUpload(View v){
        Intent intent = new Intent(HomePage.this, CreateArtPiece.class);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }
    /**
     * this method is used when Logout button is click on the buttom right of the page, it goes back to the welcom page
     * @param v
     */
    public void goWelcome(View v){
        Intent intent = new Intent(HomePage.this, MainActivity.class);
        startActivity(intent);
    }
    /**
     * this method is used to obtain availableartPieceList  from database, so that all images could be shown on the page and click one of them can goes to its information
     *
     */
    private void refreshList() {
        HttpUtils.get("/artPiece/availableartPieceList", new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for( int i = 0; i < response.length(); i++){
                    try {
                        artpieces.put(response.getJSONObject(i).getString("artPieceId").toString(), response.getJSONObject(i).getString("description").toString());
                    } catch (Exception e) {
                        error += e.getMessage();
                    }
                    refreshErrorMessage();
                }
                descriptions = new ArrayList<String>(artpieces.values());
                ids = new ArrayList<>(artpieces.keySet());
                String [] des = new String[descriptions.size()];
                for (int i = 0;i < response.length(); i++){
                    des[i]= descriptions.get(i);
                }
                refreshErrorMessage();
                ListView listView = (ListView)findViewById(R.id.viewPagerMain);
                listView.setAdapter(
                        new ImageListAdapter(
                                HomePage.this,
                                des
                        )
                );
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String art  = ids.get(position);
                        Intent intent = new Intent(HomePage.this, ArtPieceInfo.class );
                        intent.putExtra("ARTPIECE_ID", art);
                        intent.putExtra("USERNAME", username);
                        startActivity(intent);
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
    }
}
