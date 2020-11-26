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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class Signup extends AppCompatActivity {
    private String error= null;
    private List<String> names = new ArrayList<>();
    private String user = null;
    private String password = null;
    private String email = null;
    private String avatar = null;
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
        setContentView(R.layout.signup);
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
    public void goBack(View v)
    {
        Intent intent = new Intent(Signup.this, MainActivity.class);
        startActivity(intent);
    }
    public void signUp(View v){
        TextView name = (TextView)findViewById(R.id.editTextTextPersonName);
        user = String.valueOf(name.getText());
        TextView emailt = (TextView)findViewById(R.id.editTextTextEmailAddress);
        email = String.valueOf(emailt.getText());
        TextView paws = (TextView)findViewById(R.id.editTextTextPassword);
        password = String.valueOf(paws.getText());
        TextView repeat = (TextView)findViewById(R.id.editTextRepeatPassword);
        String repeated = String.valueOf(repeat.getText());
        TextView ava = (TextView)findViewById(R.id.editTextAvatar);
        avatar = String.valueOf(ava.getText());
        if (user.equals("")){
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
        else if (!password.equals(repeated)){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage("Password does not match")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Alright", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }
        else if (names.contains(user)){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage("User already exist")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("choose a different name", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Login instead", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Signup.this, Login.class);
                            startActivity(intent);
                        }
                    })
                    .show();
        }
        else if (!validate(email)){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage("Please input valid email")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Alright", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }
        else if (avatar.equals("")){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage("Please input valid avator")
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
            rp.add("name", user);
            rp.add("email", email);
            rp.add("password", password);
            rp.add("avatar", avatar);
            HttpUtils.post("/user/", rp, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    refreshErrorMessage();
                    createCustomer(user);

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
    public void createCustomer(String user) {
        RequestParams rp = new RequestParams();
        rp.add("user", user);
        rp.add("balance", "0");
        HttpUtils.post("/customer/createCustomer/"+user, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                createArtist(user);
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
    public void createArtist(String user) {
        RequestParams rp = new RequestParams();
        rp.add("user", user);
        rp.add("credit", "0");
        HttpUtils.post("/artist/createArtist/"+user+"--Artist", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                AlertDialog alertDialog = new AlertDialog.Builder(Signup.this)
                        .setMessage("Success!")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("go to home page", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Signup.this, HomePage.class);
                                intent.putExtra("USERNAME", user);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Return", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Signup.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
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
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }



}
