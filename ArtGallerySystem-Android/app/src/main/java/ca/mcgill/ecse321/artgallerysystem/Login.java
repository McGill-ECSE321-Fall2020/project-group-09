package ca.mcgill.ecse321.artgallerysystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Login extends AppCompatActivity {
    private String error= null;
    private List<String> names = new ArrayList<>();
    private String user = null;
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
        setContentView(R.layout.login);
        HttpUtils.get("/userids", new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for( int i = 0; i < response.length(); i++){
                    try {
                        names.add(response.getString(i));
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
        refreshErrorMessage();
    }
    public void checkUser(){
        TextView username = (TextView)findViewById(R.id.editTextTextPersonName);
        user = String.valueOf(username.getText());
        TextView password = (TextView)findViewById(R.id.editTextTextPassword);
        String pass = String.valueOf(password.getText());
        if(user.equals("")){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage("Please input valid username")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Alright", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }
        if(!names.contains(user)) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage("User does not exist!")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Create A new user", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Login.this, Signup.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Alright", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }else{
            HttpUtils.get("/users/"+user, new RequestParams(), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        if(response.get("password").toString().equalsIgnoreCase(pass)){
                            Intent intent = new Intent(Login.this, HomePage.class);
                            intent.putExtra("USERNAME", user);
                            startActivity(intent);
                        }else{
                            AlertDialog alertDialog = new AlertDialog.Builder(Login.this)
                                    .setMessage("Wrong password")
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton("Alright", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    })
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        error += e.getMessage();
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
    public void goHome(View v){
        Intent intent = new Intent(Login.this, HomePage.class);
        intent.putExtra("USERNAME", user);
        startActivity(intent);
    }
    public void login(View v){
        checkUser();
    }
    public void goSignUp(View v){
        Intent intent = new Intent(Login.this, Signup.class);
        startActivity(intent);
    }
}
