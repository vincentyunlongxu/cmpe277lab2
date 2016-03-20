package cmpe277.lab3yelp;

import android.Manifest;
import android.app.ActionBar;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private ImageButton search_button;
    private EditText searchView;
    private String[] menuOptions;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private GPSTracker gps;

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
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // active to get current location latitude & longitude
        search_button = (ImageButton)findViewById(R.id.button_search);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocationData();
            }
        });


        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            DisplayResultFragment fragment = new DisplayResultFragment();
            transaction.replace(R.id.search_detail, fragment);
            //transaction.addToBackStack("Search Options");
            transaction.commit();
        }
    }

    // set location data
    public void setLocationData() {
        gps = new GPSTracker(MainActivity.this);
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            Toast.makeText(getApplicationContext(), "Your Location is -\nLat: " + latitude + "\nlong: "
                    + longitude, Toast.LENGTH_LONG).show();
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

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
//        Fragment fragment = new PlanetFragment();
//        Bundle args = new Bundle();
//        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//        fragment.setArguments(args);
//
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//
//        // update selected item and title, then close the drawer
//        mDrawerList.setItemChecked(position, true);
//        setTitle(mPlanetTitles[position]);
//        mDrawerLayout.closeDrawer(mDrawerList);
    }
}
