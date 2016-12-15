package edu.calvin.cs262teamd.cleaningcrew;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class UserInfoActivity extends AppCompatActivity {

    private Button submitButton;
    private EditText numberEditText;
    private EditText emailEditText;

    private String numberText;
    private String emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // set the title to settings
        setTitle("Settings");

        submitButton = (Button) findViewById(R.id.submitContactInfo);
        numberEditText = (EditText) findViewById(R.id.editTextPhoneNumberInfo);
        emailEditText = (EditText) findViewById(R.id.editTextEmailInfo);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissKeyboard(numberEditText);
                dismissKeyboard(emailEditText);

                numberText = numberEditText.getText().toString();
                emailText = emailEditText.getText().toString();

                try {
                    new PutInfoTask().execute(new URL("http://cs262.cs.calvin.edu:8084/cs262dCleaningCrew/info"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        updateDisplay();
    }

    /**
     * Deitel's method for programmatically dismissing the keyboard.
     *
     * @param view the TextView currently being edited
     */
    private void dismissKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Inner class for PUTing existing players asynchronously
     * This is for testing purposes only.
     */
    private class PutInfoTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            HttpURLConnection connection = null;
            StringBuilder jsonText = new StringBuilder();
            String result = null;
            try {
                JSONObject jsonData = new JSONObject();
                jsonData.put("id", "zdw3");
                jsonData.put("emailaddress", emailText);
                jsonData.put("phonenumber", numberText);
                connection = (HttpURLConnection) params[0].openConnection();
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setFixedLengthStreamingMode(jsonData.toString().length());
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(jsonData.toString());
                out.flush();
                out.close();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result = line;
                    }
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                Toast.makeText(UserInfoActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
            } else if (result.length() == 0) {
                Toast.makeText(UserInfoActivity.this, "No Results Error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(UserInfoActivity.this, result, Toast.LENGTH_SHORT).show();
            }
            UserInfoActivity.this.updateDisplay();
        }
    }

    /*
     * updateDisplay()
     *
     * Takes the list of employees and pushes it to the screen.
     */
    private void updateDisplay() {
        emailEditText.setText("");
        numberEditText.setText("");
    }
}
