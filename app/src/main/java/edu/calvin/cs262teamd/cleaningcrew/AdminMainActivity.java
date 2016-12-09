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

public class AdminMainActivity extends AppCompatActivity {

    private ListView adminTaskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        adminTaskListView = (ListView) findViewById(R.id.adminTaskListView);

        updateAdminDisplay();
    }

    private void updateAdminDisplay() {

        ArrayList<HashMap<String, String>> data = new ArrayList<>();

//        Sample Data
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("room_name", "Physical Plant Front Entrance");
        map1.put("task1", "Clean Glass");
        map1.put("task2", "Dust");
        map1.put("task3", "Vacuum");
        map1.put("comment", "");
        map1.put("submit", "Submit Comment");
        data.add(map1);

        HashMap<String, String> map2 = new HashMap<>();
        map2.put("room_name", "Physical Plant Front Office");
        map2.put("task1", "Dust Ledges and Counters");
        map2.put("task2", "Trash and Recycling");
        map2.put("task3", "Vacuum");
        map2.put("comment", "");
        map2.put("submit", "Submit Comment");
        data.add(map2);

        HashMap<String, String> map3 = new HashMap<>();
        map3.put("room_name", "Physical Plant South Offices/Work Areas");
        map3.put("task1", "Trash and Recycling");
        map3.put("task2", "Dust");
        map3.put("task3", "Vacuum");
        map3.put("comment", "");
        map3.put("submit", "Submit Comment");
        data.add(map3);

        HashMap<String, String> map4 = new HashMap<>();
        map4.put("room_name", "Physical Plant Break Room");
        map4.put("task1", "Disinfect Tables and Chairs");
        map4.put("task2", "Clean Sink and Counters");
        map4.put("task3", "Trash and Recycling");
        map4.put("comment", "");
        map4.put("submit", "Submit Comment");
        data.add(map4);

        HashMap<String, String> map5 = new HashMap<>();
        map5.put("room_name", "Physical Plant Mechanical Maint. Office");
        map5.put("task1", "Dust");
        map5.put("task2", "Sweep/Wet Mop");
        map5.put("task3", "Trash and Recycling");
        map5.put("comment", "");
        map5.put("submit", "Submit Comment");
        data.add(map5);

        HashMap<String, String> map6 = new HashMap<>();
        map6.put("room_name", "Physical Plant North Offices and Hallway");
        map6.put("task1", "Trash and Recycling");
        map6.put("task2", "Dust");
        map6.put("task3", "Vacuum");
        map6.put("comment", "");
        map6.put("submit", "Submit Comment");
        data.add(map6);

        HashMap<String, String> map7 = new HashMap<>();
        map7.put("room_name", "Physical Plant Main Restrooms");
        map7.put("task1", "Sweep/Wet Mop");
        map7.put("task2", "Disinfect Toilets/Urinals & Sinks ");
        map7.put("task3", "Clean Glass");
        map7.put("comment", "");
        map7.put("submit", "Submit Comment");
        data.add(map7);

        int resource = R.layout.admin_task_list;
        String[] from = {"room_name", "task1", "task2", "task3", "comment", "submit"};
        int[] to = {R.id.admin_room_name, R.id.admin_task_1, R.id.admin_task_2, R.id.admin_task_3, R.id.admin_commentBox, R.id.admin_submit_comment};

        SimpleAdapter adapter = new SimpleAdapter(this, data, resource, from, to);
        adminTaskListView.setAdapter(adapter);
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
        }
        return true;
    }
}
