package cmpe277.lab3yelp;

import android.app.Activity;
import android.content.Intent;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;


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
    private final int PLACE_PICKER_REQUEST=1;
    //ivan
    private String name_google;
    private String address_google;
    private String att_google;
    private LatLng location_picker;



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



//ivan begin
        locationView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getX() <= (locationView.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
                        System.out.println("onclick on the left icon");
                        try {
                            PlacePicker.IntentBuilder intentBuilder =
                                    new PlacePicker.IntentBuilder();
                            Intent intent = intentBuilder.build(MainActivity.this);
                            startActivityForResult(intent, PLACE_PICKER_REQUEST);

                        } catch (GooglePlayServicesRepairableException
                                | GooglePlayServicesNotAvailableException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
        //ivan end
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

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {

            final Place place = PlacePicker.getPlace(this, data);
            this.location_picker = place.getLatLng();
            this.name_google = place.getName().toString();
            this.address_google = place.getAddress().toString();
            this.att_google = (String) place.getAttributions();
            if (this.att_google == null) {
                this.att_google = "";
            }
            if(address_google.length() > 0) {
                locationView.setText(address_google);
            }else{
                locationView.setText(name_google);
            }
            if(location_picker!=null)
            {
                latitude=location_picker.latitude;
                longitude=location_picker.longitude;
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
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
