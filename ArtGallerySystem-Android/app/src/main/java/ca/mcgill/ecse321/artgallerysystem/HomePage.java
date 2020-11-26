package ca.mcgill.ecse321.artgallerysystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
    private HashMap artpieces = new HashMap<String, String>();
    private List<String> descriptions = new ArrayList<>();
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
        Intent intent = new Intent(HomePage.this, MainActivity.class);
        //intent.putExtra("USERNAME", username);
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
                        artpieces.put(response.getJSONObject(i).getString("artPieceId"), response.getJSONObject(i).getString("description"));
                    } catch (Exception e) {
                        error += e.getMessage();
                    }
                    refreshErrorMessage();
                }
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
