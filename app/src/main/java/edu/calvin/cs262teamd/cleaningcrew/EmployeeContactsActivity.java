package edu.calvin.cs262teamd.cleaningcrew;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import static android.icu.lang.UCharacter.toLowerCase;

public class EmployeeContactsActivity extends AppCompatActivity {

    private ListView employeeListView;
    private JSONArray employees;
    private String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_contacts);

        // set title to employees
        setTitle("Employees");

        employeeListView = (ListView) findViewById(R.id.employeeListView);

        // Set the search to its initial state
        search = "";

        // Get the list of employees
        getEmployees();

        updateDisplay();
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
    public void getEmployees() {
        // InputStream to the text file
        InputStream is = getBaseContext().getResources().openRawResource(R.raw.test_contacts);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        String employeearray = null;
        JSONObject jobject = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            employeearray = sb.toString();
            jobject = new JSONObject(employeearray);
            if(jobject!=null) {
                employees = jobject.getJSONArray("employees");
            }
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        } catch (org.json.JSONException je) {
            je.printStackTrace();
        }
    }

    /*
     * updateDisplay()
     *
     * Takes the list of employees and pushes it to the screen.
     */
    private void updateDisplay() {

        ArrayList<HashMap<String, String>> data = getData(search);

        int resource = R.layout.employee_list;
        String[] from = {"employee_name", "number", "email", "hours"};
        int[] to = {R.id.employeeName, R.id.employeePhoneNumber, R.id.employeeEmailAddress, R.id.employeeHours};

        SimpleAdapter adapter = new SimpleAdapter(this, data, resource, from, to);
        employeeListView.setAdapter(adapter);

    }

    /*
     * getData(String search)
     *
     * @param String search, the desired search pattern.
     * @return ArrayList, the list of employees that satisfy the search terms.
     *
     * TODO: Make so capitalizations don't matter.
     */
    public ArrayList<HashMap<String, String>> getData(String search) {

        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        for(Integer i=0; i < employees.length(); i++) {
            SearchView sv = (SearchView) findViewById(R.id.action_search);
            try  {
                JSONObject employee = employees.getJSONObject(i);
                if(toLowerCase(employee.getString("name")).contains(toLowerCase(search))) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("employee_name", employee.getString("name"));
                    map.put("number", employee.getString("phone"));
                    map.put("email", employee.getString("email"));
                    map.put("hours", employee.getString("hours"));
                    data.add(map);
                }
            } catch (org.json.JSONException je) {
                je.printStackTrace();
            }
        }
        return data;
    }
}
