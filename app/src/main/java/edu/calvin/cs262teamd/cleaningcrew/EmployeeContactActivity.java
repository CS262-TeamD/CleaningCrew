package edu.calvin.cs262teamd.cleaningcrew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EmployeeContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_contact);

        // set title to Employees
        setTitle("Employees");
    }
}
