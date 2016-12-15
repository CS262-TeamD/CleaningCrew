package edu.calvin.cs262teamd.cleaningcrew;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

public class AdminMainActivity extends AppCompatActivity {

    private ListView adminTaskListView;
    private final List<MainTask> adminTaskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        adminTaskListView = (ListView) findViewById(R.id.adminTaskListView);

        Button submitCommentsButton = (Button) findViewById(R.id.admin_submit_comment);

        EditText adminCommentBox = (EditText) findViewById(R.id.admin_commentBox);

//        LinearLayout parentRow = (LinearLayout) adminTaskListView.getParent();
//
//        submitCommentsButton = (Button) parentRow.getChildAt(6);
//
//        View.OnClickListener buttonClick = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new PutComment().execute(createURL("3"));
//            }
//        };
//
//        submitCommentsButton.setOnClickListener(buttonClick);

        new GetPlayerTask().execute(createURL("task/cjp27"));

        updateDisplay();

        FloatingActionButton helpButton = (FloatingActionButton) findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminMainActivity.this);
                builder.setTitle("HELP");
                builder.setMessage("On this page, you can see your employees' progress on their tasks. The checkboxes will update as they complete their tasks and mark them.\n\n" +
                        "You can also leave comments for them to read by entering your comment in the box, and pressing \"Submit Comment\"");
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

    /**
     * Updates the display with some sample data (for now)
     * !TODO - Update updateDisplay() method to use actual data and be able to have varying numbers of tasks in each room
     * TODO - make the "select all" checkbox work
     */
    private void updateDisplay() {

        ArrayList<HashMap<String, String>> data = new ArrayList<>();


        int i = 0;
        for (MainTask item : adminTaskList) {
            Log.d("Comment", Integer.toString(i));
            if (i == 0) {
                HashMap<String, String> map1 = new HashMap<>();
                map1.put("room_name", "Physical Plant Front Entrance");
                map1.put("task1", "Clean Glass");
                map1.put("task2", "Dust");
                map1.put("task3", "Vacuum");
                map1.put("comment", item.getComments());
                map1.put("submit", "Submit Comment");
                data.add(map1);
            } else if (i == 1) {
                HashMap<String, String> map2 = new HashMap<>();
                map2.put("room_name", "Physical Plant Front Office");
                map2.put("task1", "Dust Ledges and Counters");
                map2.put("task2", "Trash and Recycling");
                map2.put("task3", "Vacuum");
                map2.put("comment", item.getComments());
                map2.put("submit", "Submit Comment");
                data.add(map2);
            } else if (i == 2) {
                HashMap<String, String> map3 = new HashMap<>();
                map3.put("room_name", "Physical Plant South Offices/Work Areas");
                map3.put("task1", "Trash and Recycling");
                map3.put("task2", "Dust");
                map3.put("task3", "Vacuum");
                map3.put("comment", item.getComments());
                map3.put("submit", "Submit Comment");
                data.add(map3);
            } else if (i == 3) {
                HashMap<String, String> map4 = new HashMap<>();
                map4.put("room_name", "Physical Plant Break Room");
                map4.put("task1", "Disinfect Tables and Chairs");
                map4.put("task2", "Clean Sink and Counters");
                map4.put("task3", "Trash and Recycling");
                map4.put("comment", item.getComments());
                map4.put("submit", "Submit Comment");
                data.add(map4);
            } else if (i == 4) {
                HashMap<String, String> map5 = new HashMap<>();
                map5.put("room_name", "Physical Plant Mechanical Maint. Office");
                map5.put("task1", "Dust");
                map5.put("task2", "Sweep/Wet Mop");
                map5.put("task3", "Trash and Recycling");
                map5.put("comment", item.getComments());
                map5.put("submit", "Submit Comment");
                data.add(map5);
            } else if (i == 5) {
                HashMap<String, String> map6 = new HashMap<>();
                map6.put("room_name", "Physical Plant North Offices and Hallway");
                map6.put("task1", "Trash and Recycling");
                map6.put("task2", "Dust");
                map6.put("task3", "Vacuum");
                map6.put("comment", item.getComments());
                map6.put("submit", "Submit Comment");
                data.add(map6);
            } else if (i == 6) {
                HashMap<String, String> map7 = new HashMap<>();
                map7.put("room_name", "Physical Plant Main Restrooms");
                map7.put("task1", "Sweep/Wet Mop");
                map7.put("task2", "Disinfect Toilets/Urinals & Sinks ");
                map7.put("task3", "Clean Glass");
                map7.put("comment", item.getComments());
                map7.put("submit", "Submit Comment");
                data.add(map7);
            }
            i++;
        }

        int resource = R.layout.admin_task_list;
        String[] from = {"room_name", "task1", "task2", "task3", "comment", "submit"};
        int[] to = {R.id.admin_room_name, R.id.admin_task_1, R.id.admin_task_2, R.id.admin_task_3, R.id.admin_commentBox};

        SimpleAdapter adapter = new SimpleAdapter(this, data, resource, from, to);
        adminTaskListView.setAdapter(adapter);
    }

    /**
     * Formats a URL for the webservice specified in the string resources.
     *
     * @return URL formatted for openweathermap.com
     */
    private URL createURL(String id) {
        try {
            Log.d("URL", "create");
            String urlString = "http://cs262.cs.calvin.edu:8084/cs262dCleaningCrew/" + id;
            return new URL(urlString);
        } catch (Exception e) {
//            Toast.makeText(this, getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
        }

        return null;
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
            case R.id.admin_logout:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class PutComment extends AsyncTask<URL, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(URL...
                                                   params) {
            HttpURLConnection connection = null;
            StringBuilder jsonText = new StringBuilder();
            JSONArray result = null;
            try {
                JSONObject jsonData = new JSONObject();
                jsonData.put("id", 0);
                jsonData.put("comment", "");
                connection = (HttpURLConnection) params[0].openConnection();
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setFixedLengthStreamingMode(jsonData.toString().length());
                Log.d("before", "test");
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                Log.d("after", "test");
                out.writeBytes(jsonData.toString());
                out.flush();
                out.close();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonText.append(line);
                    }
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
            return result;
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
            Log.d("result", result != null ? result.toString() : null);
            return result;
        }

        @Override
        protected void onPostExecute(JSONArray players) {
            adminTaskList.clear();
            if (players == null) {
                // Toast.makeText(MainActivity.this, getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
            } else if (players.length() == 0) {
//                Toast.makeText(MainActivity.this, getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
            } else {
                convertJSONtoArrayList(players);
            }
            AdminMainActivity.this.updateDisplay();
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
                    adminTaskList.add(new MainTask(
                            player.getInt("id"),
                            player.optString("description", "no name"),
                            player.getInt("roomNumber"),
                            player.optString("buildingName", "no email"),
                            player.optString("comment"),
                            player.getBoolean("isComplete")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public void postComments() {
        new PutComment().execute(createURL("comment"));
    }
}
