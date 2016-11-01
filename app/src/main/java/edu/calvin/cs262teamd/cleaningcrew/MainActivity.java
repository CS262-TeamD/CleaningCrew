package edu.calvin.cs262teamd.cleaningcrew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ListView taskListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set title to "Housekeeping Log"
        setTitle("Housekeeping Log");

        taskListView = (ListView) findViewById(R.id.taskListView);

        updateDisplay();

    }

    /**
     * Determines behavior of the options menu in the action bar
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * Handles the user selection of an item in the options menu
     * @param item
     * @return true if selection valid, else call superclass method
     */
    @Override 
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // navigate to the about page
            case R.id.about_page:
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                return true;
            // go to employee contacts page
            case R.id.employee_contacts_page:
                startActivity(new Intent(getApplicationContext(), EmployeeContactsActivity.class));
                return true;
            case R.id.settings_page:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;
            case R.id.login_page:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Updates the display with some sample data (for now)
     * !TODO - Update updateDisplay() method to use actual data and be able to have varying numbers of tasks in each room
     * TODO - make the "select all" checkbox work
     */
    private void updateDisplay() {

        ArrayList<HashMap<String, String>> data = new ArrayList<>();

//        Sample Data
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("room_name", "room 1");
        map1.put("task1", "sample task 1");
        map1.put("task2", "sample task 2");
        map1.put("task3", "sample task 3");
        data.add(map1);

        HashMap<String, String> map2 = new HashMap<>();
        map2.put("room_name", "room 2");
        map2.put("task1", "sample task 1");
        map2.put("task2", "sample task 2");
        map2.put("task3", "sample task 3");
        data.add(map2);

        HashMap<String, String> map3 = new HashMap<>();
        map3.put("room_name", "room 3");
        map3.put("task1", "sample task 1");
        map3.put("task2", "sample task 2");
        map3.put("task3", "sample task 3");
        data.add(map3);

        HashMap<String, String> map4 = new HashMap<>();
        map4.put("room_name", "room 4");
        map4.put("task1", "sample task 1");
        map4.put("task2", "sample task 2");
        map4.put("task3", "sample task 3");
        data.add(map4);

        HashMap<String, String> map5 = new HashMap<>();
        map5.put("room_name", "room 5");
        map5.put("task1", "sample task 1");
        map5.put("task2", "sample task 2");
        map5.put("task3", "sample task 3");
        data.add(map5);

        HashMap<String, String> map6 = new HashMap<>();
        map6.put("room_name", "room 6");
        map6.put("task1", "sample task 1");
        map6.put("task2", "sample task 2");
        map6.put("task3", "sample task 3");
        data.add(map6);

        int resource = R.layout.task_list;
        String[] from = {"room_name", "task1", "task2", "task3"};
        int[] to = {R.id.room_name, R.id.task_1, R.id.task_2, R.id.task_3};

        SimpleAdapter adapter = new SimpleAdapter(this, data, resource, from, to);
        taskListView.setAdapter(adapter);
    }
}
