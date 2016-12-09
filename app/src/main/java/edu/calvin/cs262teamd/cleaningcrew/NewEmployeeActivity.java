package edu.calvin.cs262teamd.cleaningcrew;

import android.content.Intent;
import android.os.Bundle;
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
