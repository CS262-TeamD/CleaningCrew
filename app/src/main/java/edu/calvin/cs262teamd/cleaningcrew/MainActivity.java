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
     * @param menu the menu object associated with this view to be inflated within this method
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * Handles the user selection of an item in the options menu
     * @param item the selected menu item
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
            case R.id.user_info_page:
                startActivity(new Intent(getApplicationContext(), UserInfoActivity.class));
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
        map1.put("room_name", "Physical Plant Front Entrance");
        map1.put("task1", "Clean Glass");
        map1.put("task2", "Dust");
        map1.put("task3", "Vacuum");
        map1.put("comment", "Make sure you vacuum in all the corners!");
        data.add(map1);

        HashMap<String, String> map2 = new HashMap<>();
        map2.put("room_name", "Physical Plant Front Office");
        map2.put("task1", "Dust Ledges and Counters");
        map2.put("task2", "Trash and Recycling");
        map2.put("task3", "Vacuum");
        map2.put("comment", "The windowsills were a bit dusty last time, make sure you get all of them.");
        data.add(map2);

        HashMap<String, String> map3 = new HashMap<>();
        map3.put("room_name", "Physical Plant South Offices/Work Areas");
        map3.put("task1", "Trash and Recycling");
        map3.put("task2", "Dust");
        map3.put("task3", "Vacuum");
        map3.put("comment", "Nice job!");
        data.add(map3);

        HashMap<String, String> map4 = new HashMap<>();
        map4.put("room_name", "Physical Plant Break Room");
        map4.put("task1", "Disinfect Tables and Chairs");
        map4.put("task2", "Clean Sink and Counters");
        map4.put("task3", "Trash and Recycling");
        map4.put("comment", "Please wash all the dishes in the sink when you clean it.");
        data.add(map4);

        HashMap<String, String> map5 = new HashMap<>();
        map5.put("room_name", "Physical Plant Mechanical Maint. Office");
        map5.put("task1", "Dust");
        map5.put("task2", "Sweep/Wet Mop");
        map5.put("task3", "Trash and Recycling");
        map5.put("comment", "Looks good, keep up the good work!");
        data.add(map5);

        HashMap<String, String> map6 = new HashMap<>();
        map6.put("room_name", "Physical Plant North Offices and Hallway");
        map6.put("task1", "Trash and Recycling");
        map6.put("task2", "Dust");
        map6.put("task3", "Vacuum");
        map6.put("comment", "No comment.");
        data.add(map6);

        HashMap<String, String> map7 = new HashMap<>();
        map7.put("room_name", "Physical Plant Main Restrooms");
        map7.put("task1", "Sweep/Wet Mop");
        map7.put("task2", "Disinfect Toilets/Urinals & Sinks ");
        map7.put("task3", "Clean Glass");
        map7.put("comment", "Sorry the bathrooms are so nasty today...");
        data.add(map7);

        int resource = R.layout.task_list;
        String[] from = {"room_name", "task1", "task2", "task3", "comment"};
        int[] to = {R.id.room_name, R.id.task_1, R.id.task_2, R.id.task_3, R.id.commentBox};

        SimpleAdapter adapter = new SimpleAdapter(this, data, resource, from, to);
        taskListView.setAdapter(adapter);
    }
}
