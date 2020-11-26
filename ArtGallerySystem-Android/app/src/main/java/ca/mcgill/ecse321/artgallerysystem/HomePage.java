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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        username = getIntent().getStringExtra("USERNAME");
        refreshList();
        descriptions = new ArrayList<String>(artpieces.values());
        TextView id = (TextView) findViewById(R.id.artpieceID);
        //ViewPager mViewPager;
        //ViewPagerAdapter mViewPagerAdapter;
        //mViewPager = (ViewPager)findViewById(R.id.viewPagerMain);
       // int[] images = new int[descriptions.size()];
       // ImageView imageView=(ImageView) findViewById(R.id.imageView);

        //String url = "https://pic.cnblogs.com/avatar/1142647/20170416093225.png";
        //Glide.with(ArtPieceInfo.this).load(description).into(imageView);

        // Initializing the ViewPagerAdapter
        //mViewPagerAdapter = new ViewPagerAdapter(HomePage.this, descriptions);
        // Adding the Adapter to the ViewPager
        //mViewPager.setAdapter(mViewPagerAdapter);
    }

    /**
     * this method is used when detail button is click for a specfic artpiece, it passes the parameter used to display a specific artpiece
     * and the username to potentially perform purchase later
     * @param v
     */

    public void goDetail(View v){
        Intent intent = new Intent(HomePage.this, ArtPieceInfo.class );
        intent.putExtra("ARTPIECE_ID", "888888");
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }
    public void goAccount(View v){
        Intent intent = new Intent(HomePage.this, MainActivity.class);
        //intent.putExtra("USERNAME", username);
        startActivity(intent);
    }
    public void goUpload(View v){
        Intent intent = new Intent(HomePage.this, CreateArtPiece.class);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }
    public void goWelcome(View v){
        Intent intent = new Intent(HomePage.this, MainActivity.class);
        startActivity(intent);
    }
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
                /*ViewPager mViewPager;
                ViewPagerAdapter mViewPagerAdapter;
                mViewPager = (ViewPager)findViewById(R.id.viewPagerMain);
                ImageView imageView=(ImageView) findViewById(R.id.imageView);
                mViewPagerAdapter = new ViewPagerAdapter(HomePage.this, descriptions);
                // Adding the Adapter to the ViewPager
                 mViewPager.setAdapter(mViewPagerAdapter);*/
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
