package algonquin.cst2355.xu;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {
    private EditText phoneNumberEditText;
    private ImageView imageView;
    private Button callButton;
    private Button changePictureButton;
    private Bitmap profilePicture;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    Intent cameraIntent;

    ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getExtras() != null) {
                            profilePicture = (Bitmap) data.getExtras().get("data");
                            imageView.setImageBitmap(profilePicture);

                            // Save the picture to disk
                            saveProfilePictureToDisk(profilePicture);
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Retrieve the email address data from the Intent
        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");

        // Find the TextView widget in your layout
        TextView emailTextView = findViewById(R.id.textViewEmail);

        // Set the retrieved email address to the TextView
        emailTextView.setText("Welcome back " + emailAddress);

        // Find the Call Number button in your layout
        callButton = findViewById(R.id.buttonCall);
        phoneNumberEditText = findViewById(R.id.editTextPhoneNumber);

        // Load the phone number from SharedPreferences and set it in the EditText
        loadPhoneNumberFromSharedPreferences();

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the phone number from the EditText
                String phoneNumber = phoneNumberEditText.getText().toString();

                // Create an Intent to dial the phone number
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));

                // Save the phone number to SharedPreferences
                savePhoneNumberToSharedPreferences(phoneNumber);

                // Start the phone call activity
                startActivity(callIntent);
            }
        });

        // Find the Change Picture button in your layout
        changePictureButton = findViewById(R.id.buttonChangePicture);
        imageView = findViewById(R.id.imageView);
        // Initialize the camera intent
        cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        changePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch the camera activity
                cameraResult.launch(cameraIntent);
            }
        });
    }

    // Helper method to save the profile picture to disk
    private void saveProfilePictureToDisk(Bitmap bitmap) {
        if (bitmap != null) {
            try {
                File file = new File(getFilesDir(), "profile_picture.png");
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Helper method to save the phone number to SharedPreferences
    private void savePhoneNumberToSharedPreferences(String phoneNumber) {
        SharedPreferences prefs = getSharedPreferences("MyData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("TelephoneNumber", phoneNumber);
        editor.apply();
    }
    // Helper method to load the phone number from SharedPreferences
    private void loadPhoneNumberFromSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences("MyData", MODE_PRIVATE);
        String phoneNumber = prefs.getString("TelephoneNumber", "");

        // Set the retrieved telephone number to the EditText
        phoneNumberEditText.setText(phoneNumber);
    }
}