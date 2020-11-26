package ca.mcgill.ecse321.artgallerysystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * My Account page, where an Intent with extra string USERNAME is needed.
 * @author Zhekai Jiang
 */
public class MyAccount extends AppCompatActivity {

    private String userName;
    private String error;

    /**
     * Operations when the activity is started.
     * @author Zhekai Jiang
     * @param savedInstanceState See Activity.onCreate
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_account);

        ActionBar actionBar = getSupportActionBar(); // Top bar
        actionBar.setDisplayHomeAsUpEnabled(true); // Set the back button on the top-left -> https://geekstocode.com/add-back-button-in-android-title-bar/
        actionBar.setTitle("My Account");

        error = "";

        userName = getIntent().getStringExtra("USERNAME");

        if(userName == null || userName.length() == 0) {
            error += "User name is empty";
            // Default greeting "Hello!"
        } else {
            // "Do not concatenate text displayed with setText. Use resource string with placeholders."
            // -> https://developer.android.com/guide/topics/resources/string-resource#formatting-strings
            String greeting = getString(R.string.account_greeting, userName); // Hello, %1$s!
            TextView greetingView = findViewById(R.id.account_greeting);
            greetingView.setText(greeting);
        }

        refreshErrorMessage();
    }

    /**
     * Back to previous activity when the home (here set for back) button is clicked.
     * -> https://alvinalexander.com/source-code/android/how-get-android-actionbar-backup-button-work-android-back-button/
     * @param item The menu item that was selected. Same as Activity.onOptionsItemSelected.
     * @return boolean See Activity.onOptionsItemSelected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            this.onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Redirect to AccountPurchases activity when the button Purchases is clicked.
     * @author Zhekai Jiang
     * @param view The view.
     */
    public void onPurchasesClicked(View view) {
        startActivityWithUserName(AccountPurchases.class);
    }

    /**
     * Redirect to AccountArtPieces activity when the button Uploaded Art Pieces is clicked.
     * @author Zhekai Jiang
     * @param view The view.
     */
    public void onUploadedArtPiecesClicked(View view) {
        startActivityWithUserName(AccountArtPieces.class);
    }

    /**
     * Redirect to AccountAddresses activity when the button Frequently Used Addresses is clicked.
     * @author Zhekai Jiang
     * @param view The view.
     */
    public void onAddressesClicked(View view) {
        startActivityWithUserName(AccountAddresses.class);
    }

    /**
     * Helper method to start the given activity, with extra string username added in intent.
     * @author Zhekai Jiang
     * @param activityClass The class of the target activity
     */
    private void startActivityWithUserName(Class<?> activityClass) {
        Intent intent = new Intent(MyAccount.this, activityClass);
        intent.putExtra("USERNAME", userName);
        startActivity(intent);
    }

    /**
     * Refresh error message shown on the screen.
     * @author Zhekai Jiang
     */
    public void refreshErrorMessage() {
        TextView errorTextView = findViewById(R.id.my_account_error);
        errorTextView.setText(error);
    }
}
