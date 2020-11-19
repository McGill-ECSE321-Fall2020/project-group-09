package ca.mcgill.ecse321.artgallerysystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class SuccessfulPayment extends AppCompatActivity {
    private String error= null;
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
        setContentView(R.layout.successful_payment);
        username=getIntent().getStringExtra("USERNAME");

    }

    public void goBack(View v){
        Intent intent = new Intent(this, HomePage.class );
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }
}
