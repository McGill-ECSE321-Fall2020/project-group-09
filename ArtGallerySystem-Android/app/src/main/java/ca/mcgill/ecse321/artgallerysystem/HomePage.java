package ca.mcgill.ecse321.artgallerysystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

/**
 * this page is used to display all the artpieces from the system, so that user can choose one to check detailed information
 */
public class HomePage extends AppCompatActivity {
    private String error= null;
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
    }

    /**
     * this method is used when detail button is click for a specfic artpiece, it passes the parameter used to display a specific artpiece
     * and the username to potentially perform purchase later
     * @param v
     */

    public void goDetail(View v){
        Intent intent = new Intent(HomePage.this, ArtPieceInfo.class );
        intent.putExtra("ARTPIECE_ID", "888888");
        intent.putExtra("USERNAME", "Angelina");
        startActivity(intent);
    }
}
