package algonquin.cst2355.xu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import algonquin.cst2355.xu.R;
import algonquin.cst2355.xu.data.MainViewModel;
import algonquin.cst2355.xu.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding variableBinding;
    private MainViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupLoginButton();
        loadSavedEmailAddress();

        Log.w(TAG, "onCreate: The activity is being created.");
    }
    private void setupLoginButton() {
        Button loginButton = findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress = getEmailAddressFromEditText();
                saveEmailAddressToSharedPreferences(emailAddress);
                navigateToSecondActivity(emailAddress);
            }
        });
    }

    private String getEmailAddressFromEditText() {
        EditText emailEditText = findViewById(R.id.editTextEmail);
        return emailEditText.getText().toString();
    }

    private void saveEmailAddressToSharedPreferences(String emailAddress) {
        // Save the email address to SharedPreferences
        // Replace "MyData" with your SharedPreferences file name
        getSharedPreferences("MyData", MODE_PRIVATE)
                .edit()
                .putString("LoginName", emailAddress)
                .apply();
    }

    private void navigateToSecondActivity(String emailAddress) {
        Intent nextPage = new Intent(MainActivity.this, algonquin.cst2355.xu.SecondActivity.class);
        nextPage.putExtra("EmailAddress", emailAddress);
        startActivity(nextPage);
    }

    private void loadSavedEmailAddress() {
        String emailAddress = getEmailAddressFromSharedPreferences();
        setEmailAddressToEditText(emailAddress);
    }

    private String getEmailAddressFromSharedPreferences() {
        // Load the saved email address from SharedPreferences
        // Replace "MyData" with your SharedPreferences file name
        return getSharedPreferences("MyData", MODE_PRIVATE)
                .getString("LoginName", "");
    }

    private void setEmailAddressToEditText(String emailAddress) {
        EditText emailEditText = findViewById(R.id.editTextEmail);
        emailEditText.setText(emailAddress);
    }

// Other lifecycle methods (onStart, onResume, onPause, onStop, onDestroy) can remain unchanged.


    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, "onStart: The activity is about to become visible.");
    }
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "onResume: The activity has become visible and is now in the foreground.");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.w(TAG, "onPause: The activity is partially obscured or going into the background.");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.w(TAG, "onStop: The activity is no longer visible.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.w(TAG, "onDestroy: The activity is being destroyed.");
    }}
// Saving email address

