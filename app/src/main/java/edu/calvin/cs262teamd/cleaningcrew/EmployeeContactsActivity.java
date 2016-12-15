package edu.calvin.cs262teamd.cleaningcrew;

import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmployeeContactsActivity extends AppCompatActivity {

    private ListView employeeListView;
    private JSONArray employees;
    private String search;

    private List<Person> employeeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_contacts);

        // set title to employees
        setTitle("Employees");

        employeeListView = (ListView) findViewById(R.id.employeeListView);

        // Set the search to its initial state
        search = "";

//        // Get the list of employees
//        getEmployees();

        try {
            new GetContactsTask().execute(new URL("http://cs262.cs.calvin.edu:8084/cs262dCleaningCrew/contact/cjp27"));
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ERROR in EXECUTE", "test");
        }

//        updateDisplay();
    }

    /*
     * OnCreateOptionsMenu(Menu menu)
     *
     * Initializes the search bar on this activity
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu to get the search bar on the menu block
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_employee, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) EmployeeContactsActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(EmployeeContactsActivity.this.getComponentName()));
        }

        // Initialize the SearchView listener
        SearchView sv = (SearchView) menu.findItem(R.id.action_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callSearch(newText);
                return true;
            }

            public void callSearch(String query) {
                search = query;
                updateDisplay();
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /*
     * getEmployees()
     *
     * Currently reads in employees from a file, puts it in this class' employees JSONArray
     *
     * TODO: Change from reading from file to reading from DB.
     */
    private void getEmployees() {
        // InputStream to the text file
        InputStream is = getBaseContext().getResources().openRawResource(R.raw.test_contacts);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            employees = new JSONObject(sb.toString()).getJSONArray("employees");
        } catch (java.io.IOException | org.json.JSONException ioe) {
            ioe.printStackTrace();
        }
    }


    /**
     * Inner class for GETing the contact list from the  server asynchronously
     */
    private class GetContactsTask extends AsyncTask<URL, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(URL... params) {
            HttpURLConnection connection = null;
            StringBuilder jsonText = new StringBuilder();
            JSONArray result = null;
            try {
                connection = (HttpURLConnection) params[0].openConnection();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String line;
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
            return result;
        }

        @Override
        protected void onPostExecute(JSONArray emp) {
            employeeList.clear();
            if (emp == null) {
                Toast.makeText(EmployeeContactsActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
            } else if (emp.length() == 0) {
                Toast.makeText(EmployeeContactsActivity.this, "No Results Error", Toast.LENGTH_SHORT).show();
            } else {
                employees = convertToEmployeeList(emp);
            }
            EmployeeContactsActivity.this.updateDisplay();
        }

    }


    private JSONArray convertToEmployeeList(JSONArray employeeArray) {
        try {
            JSONArray emptest = new JSONArray();
            for (int i = 0; i < employeeArray.length(); i++) {
                JSONObject emp = new JSONObject();
                JSONObject empI = employeeArray.getJSONObject(i);
                if(empI.has("name")) {
                    emp.put("name", empI.get("name"));
                }
                if(empI.has("emailaddress")) {
                    emp.put("emailaddress", empI.get("emailaddress"));
                }
                if(empI.has("phonenumber")) {
                    emp.put("phonenumber", empI.get("phonenumber"));
                }
                emptest.put(emp);
            }
            return emptest;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * updateDisplay()
     *
     * Takes the list of employees and pushes it to the screen.
     */
    private void updateDisplay() {
        ArrayList<HashMap<String, String>> data = getData(search);

        int resource = R.layout.employee_list;
        String[] from = {"employee_name", "number", "email"};
        int[] to = {R.id.employeeName, R.id.employeePhoneNumber, R.id.employeeEmailAddress};

        SimpleAdapter adapter = new SimpleAdapter(this, data, resource, from, to);
        employeeListView.setAdapter(adapter);

    }

    /*
     * getData(String search)
     *
     * @param String search, the desired search pattern.
     * @return ArrayList, the list of employees that satisfy the search terms.
     *
     */
    private ArrayList<HashMap<String, String>> getData(String search) {
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        for(Integer i=0; i < employees.length(); i++) {
            try  {
                JSONObject employee = employees.getJSONObject(i);
                if(employee.getString("name").contains(search)) {
                    HashMap<String, String> map = new HashMap<>();
                    if(employee.has("name")) {
                        map.put("employee_name", employee.getString("name"));
                    } else {
                        map.put("employee_name", "Name Not Found");
                    }
                    if(employee.has("phonenumber")) {
                        map.put("number", employee.getString("phonenumber"));
                    } else {
                        map.put("number", "Number Not Found");
                    }
                    if(employee.has("emailaddress")) {
                        map.put("email", employee.getString("emailaddress"));
                    } else {
                        map.put("email", "E-mail Address Not Found");
                    }
                    data.add(map);
                }
            } catch (org.json.JSONException je) {
                je.printStackTrace();
            }
        }

        return data;
    }
}
