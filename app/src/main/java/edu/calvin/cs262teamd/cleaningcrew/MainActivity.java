package edu.calvin.cs262teamd.cleaningcrew;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<MainTask> taskList = new ArrayList<>();
    private ListView taskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set title to "Housekeeping Log"
        setTitle("Housekeeping Log");

        taskListView = (ListView) findViewById(R.id.taskListView);

        Log.d("begin", "get");
        new GetPlayerTask().execute(createURL());
        new GetTask().execute(createURL());

        // updateDisplay();
    }

    /**
     * Formats a URL for the webservice specified in the string resources.
     *
     * @return URL formatted for openweathermap.com
     */
    private URL createURL() {
        try {
            Log.d("URL", "create");
            String urlString = "http://cs262.cs.calvin.edu:8084/cs262dCleaningCrew/task/cjp27";
            return new URL(urlString);
        } catch (Exception e) {
//            Toast.makeText(this, getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    /**
     * Inner class for GETing the current weather data from openweathermap.org asynchronously
     */
    private class GetTask extends AsyncTask<URL, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(URL... params) {

            Log.d("here", "test");
            HttpURLConnection connection = null;
            StringBuilder jsonText = new StringBuilder();
            JSONArray new_result = null;
            try {
                JSONObject jsonData = new JSONObject();
                //Log.d(new_url.toString(), "test");
                connection = (HttpURLConnection) params[0].openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setFixedLengthStreamingMode(jsonData.toString().length());
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(jsonData.toString());
                out.flush();
                out.close();
                Log.d(params[0].toString(), "test");
                Log.d("put output", connection.getInputStream().toString());

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Log.d("put output", "test");
                connection.disconnect();
            }

            return null;
        }
    }
    private class GetPlayerTask extends AsyncTask<URL, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(URL... params) {
            Log.d("player", "test");
            HttpURLConnection connection = null;
            StringBuilder jsonText = new StringBuilder();
            JSONArray result = null;
            try {
                connection = (HttpURLConnection) params[0].openConnection();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String line;
                    Log.d("player", "here");
                    while ((line = reader.readLine()) != null) {
                        jsonText.append(line);
                    }
                    //Log.d(TAG, jsonText.toString());
                    if (jsonText.charAt(0) == '[') {
                        result = new JSONArray(jsonText.toString());
                    } else if (jsonText.toString().equals("null")) {
                        result = new JSONArray();
                    } else {
                        result = new JSONArray().put(new JSONObject(jsonText.toString()));
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
            Log.d("result", result.toString());
            return result;
        }

        @Override
        protected void onPostExecute(JSONArray players) {
            taskList.clear();
            if (players == null) {
                // Toast.makeText(MainActivity.this, getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
            } else if (players.length() == 0) {
//                Toast.makeText(MainActivity.this, getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
            } else {
                convertJSONtoArrayList(players);
            }
            MainActivity.this.updateDisplay();
        }


        /**
         * Converts the JSON player data to an arraylist suitable for a listview adapter
         *
         * @param players JSON array of player objects
         */
        private void convertJSONtoArrayList(JSONArray players) {
            try {
                for (int i = 0; i < players.length(); i++) {
                    JSONObject player = players.getJSONObject(i);
                    taskList.add(new MainTask(
                            player.getInt("id"),
                            player.optString("description", "no name"),
                            player.getInt("roomNumber"),
                            player.optString("buildingName", "no email"),
                            player.getBoolean("isComplete")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

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