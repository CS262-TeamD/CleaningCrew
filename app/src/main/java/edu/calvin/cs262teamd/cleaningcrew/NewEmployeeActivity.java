package edu.calvin.cs262teamd.cleaningcrew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Caleb on 12/7/16.
 */

public class NewEmployeeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("New Employee");
        setContentView(R.layout.new_employee);
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
                startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                return true;
        }
        return true;
    }
}
