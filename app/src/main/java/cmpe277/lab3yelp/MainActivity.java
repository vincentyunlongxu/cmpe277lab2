package cmpe277.lab3yelp;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    private ImageButton search_button;
    private EditText searchView;
    private EditText locationView;
    private String[] menuOptions;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private GPSTracker gps;
    private String searchContent;
    private String searchLocation;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enable Toolbar as Actionbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setBackgroundColor(Color.parseColor("#c41200"));
        setSupportActionBar(myToolbar);
        myToolbar.setLogo(R.drawable.actionbar_icon_small);
        myToolbar.setTitleTextColor(0xFFFFFFFF);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Enable the edit text as search bar, link navigation drawer with tool bar
        searchView = (EditText) findViewById(R.id.searchview);
        locationView = (EditText) findViewById(R.id.locationview);
        menuOptions = getResources().getStringArray(R.array.menu_option);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, myToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );

        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, menuOptions));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchContent = (String)(mDrawerList.getItemAtPosition(position));
                mDrawerLayout.closeDrawer(mDrawerList);
                setLocationData();
                startResultFragment();
            }
        });

        // active to get current location latitude & longitude
        search_button = (ImageButton)findViewById(R.id.button_search);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchContent = searchView.getText().toString();
                searchLocation = locationView.getText().toString();
                setLocationData();
                startResultFragment();
            }
        });


        if (savedInstanceState == null) {
            setLocationData();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SearchOptionsFragment fragment = new SearchOptionsFragment();
            fragment.setCoordinate(latitude, longitude);
            transaction.replace(R.id.search_detail, fragment);
            transaction.addToBackStack("Search Options");
            transaction.commit();
        }
    }

    private void startResultFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ResultDetailFragment fragment = new ResultDetailFragment();
        fragment.setData(searchContent, searchLocation, latitude, longitude);
        transaction.replace(R.id.search_detail, fragment);
        transaction.addToBackStack("Result Options");
        transaction.commit();
        searchView.setText(searchContent);
    }

    // set location data
    public void setLocationData() {
        gps = new GPSTracker(MainActivity.this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        } else {
            gps.showSettingAlert();
        }
    }

    // inflate the menu to toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // which item is selected in the tool bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
