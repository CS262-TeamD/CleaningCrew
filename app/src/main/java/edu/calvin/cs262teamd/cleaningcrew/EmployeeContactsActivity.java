package edu.calvin.cs262teamd.cleaningcrew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EmployeeContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_contacts);

        // set title to employees
        setTitle("Employees");
    }
}
