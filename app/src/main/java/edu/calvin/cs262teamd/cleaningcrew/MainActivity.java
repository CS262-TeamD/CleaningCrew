package edu.calvin.cs262teamd.cleaningcrew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Menu menu;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView invalidTextView;
    private Button loginButton;
    private boolean rightPassword;
    private boolean rightUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        invalidTextView = (TextView) findViewById(R.id.invalidTextView);
        invalidTextView.setVisibility(View.GONE);
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        usernameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String username = usernameEditText.getText().toString();
                Log.d(username, "asdfhkl");
                if (username.equals("admin")) {
                    rightUsername = true;
                } else {
                    rightUsername = false;
                }
                Log.d(String.valueOf(rightUsername), "lokjvghn");
                return false;
            }
        });
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String password = passwordEditText.getText().toString();
                // When the correct password is entered set image to visible and text to gone
                if (password.equals("safepassword")) {
                    rightPassword = true;
                } else {
                    rightPassword = false;
                }
                Log.d(String.valueOf(rightUsername), "ergdsaf");
                Log.d(String.valueOf(rightPassword), "vdsa");
                return false;
            }
        });
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightPassword && rightUsername){
                    invalidTextView.setVisibility(View.VISIBLE);
                    invalidTextView.setText("Welcome, Admin");
                    MenuItem item = menu.findItem(R.id.menu_assigncleaning);
                    switch (item.getItemId()) {
                        case R.id.menu_assigncleaning:
                            startActivity(new Intent(getApplicationContext(), AssignCleaning.class));
                        default:
                    }
                } else {
                    invalidTextView.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    // Initilizes the menu for the app
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        this.menu = menu;
        return true;
    }


    // Change context based on menu selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_assigncleaning:
                startActivity(new Intent(getApplicationContext(), AssignCleaning.class));
                return true;
            case R.id.menu_about:
                startActivity(new Intent(getApplicationContext(), About.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
