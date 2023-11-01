package algonquin.cst2355.xu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import algonquin.cst2355.xu.databinding.ActivityMainBinding;

/** This class is the starting point of the application
 * @author Hanliying Xu
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *        shut down then this Bundle contains the data it most recently
     *        supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginButton.setOnClickListener(click -> {
            String userInput = binding.passwordText.getText().toString();

            boolean hasUpperAndLowerCase = containsUpperAndLowerCase(userInput);
            boolean hasDigit = containsDigit(userInput);
            boolean hasSpecialChar = containsSpecialCharacters(userInput);

            String message = "You shall not pass!";
            boolean hasMissing = false;

            if (!hasUpperAndLowerCase || !hasDigit || !hasSpecialChar) {
                //message += "missing upper and lower case letter";
                hasMissing = true;
            }
            if (!hasDigit) {
                //message += " missing digits";
                hasMissing = true;
            }
            if (!hasSpecialChar) {
                //message += " missing special characters *, #, ?, ";
                hasMissing = true;
            }

            if (hasMissing) {
                binding.responseText.setText(message);
            } else {
                binding.responseText.setText("Your password is complex enough.");
            }
        });
    }

    /** This function checks str and returns true if str
     *  has an upper and lower case letter in it
     * @param str a String str to store user input
     * @return Returns true if str has upper and lower case, otherwise false
     */
    boolean containsUpperAndLowerCase(String str) {
        boolean foundUpper = false;
        boolean foundLower = false;

        for (int i = 0; i < str.length(); i++) {
            //get each character in the string
            char c = str.charAt(i);
            if (Character.isLowerCase(c)) {
                foundLower = true;
            } else if (Character.isUpperCase(c)) {
                foundUpper = true;
            }
        }
        //after looked at every character
        return foundLower && foundUpper;
    }

    /** This function checks str and returns true if str
     *  has digits in it
     * @param str a String str to store user input
     * @return Returns true if str has digits, otherwise false
     */
    boolean containsDigit(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    /** This function checks str and returns true if str
     *  has special characters in it
     * @param str a String str to store user input
     * @return Returns true if str has special characters, otherwise false
     */
    boolean containsSpecialCharacters(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '*' || c == '#' || c == '?') {
                return true;
            }
        }
        return false;
    }
}
