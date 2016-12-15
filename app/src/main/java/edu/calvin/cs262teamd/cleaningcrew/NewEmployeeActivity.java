package edu.calvin.cs262teamd.cleaningcrew;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class NewEmployeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("New Employee");
        setContentView(R.layout.new_employee);
        Button submitButton = (Button) findViewById(R.id.newUserSubmitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO - Do something with the new user name and ID
                startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
            }
        });

        FloatingActionButton helpButton = (FloatingActionButton) findViewById(R.id.helpButton);

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewEmployeeActivity.this);
                builder.setTitle("HELP");
                builder.setMessage("On this page, you can add new employees as you hire them.\n\n" +
                                    "Simply enter their name in the top box and their calvin email id (e.g. \"abc123\"@students.calvin.edu) " +
                                    "in the bottom box and press Submit.");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_employee:
                startActivity(new Intent(getApplicationContext(), NewEmployeeActivity.class));
                return true;
            case R.id.admin_main:
                startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
                return true;
            case R.id.admin_logout:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                return true;
        }
        return true;
    }
}
