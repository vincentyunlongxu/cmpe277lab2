package cmpe277.lab3yelp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements ResultDetailFragment.OnLayoutSelectListener {

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
    private ImageButton sort_Button;
    private FrameLayout detail_layout;
    private boolean isFavorite = false;

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
        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
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

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() <= (locationView.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
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



        // sort option
        sort_Button = (ImageButton) findViewById(R.id.button_sort);
        sort_Button.setOnClickListener(new View.OnClickListener() {
            //default by distance
            @Override
            public void onClick(View v) {
                //current is by relevance, then it will change to by distance
                if (sort_Button.getTag().equals(R.string.ic_star_black_24dp)) {
                    sort_Button.setImageResource(R.drawable.ic_star_border_black_24dp);
                    sort_Button.setTag(R.string.ic_star_border_black_24dp);
                    Toast.makeText(getApplicationContext(), "Search by distance", Toast.LENGTH_SHORT).show();
                }
                //current is by distance, then it will change to by relevance
                else {
                    sort_Button.setImageResource(R.drawable.ic_star_black_24dp);
                    sort_Button.setTag(R.string.ic_star_black_24dp);
                    Toast.makeText(getApplicationContext(), "Search by relevance", Toast.LENGTH_SHORT).show();
                }

                ArrayList<Fragment> fragmentList = (ArrayList)getSupportFragmentManager().getFragments();
                boolean sort_search = false;
                for(int i=0; i<fragmentList.size(); i++)
                    if(fragmentList.get(i).getClass().getName().equals("cmpe277.lab3yelp.ResultDetailFragment"))
                        sort_search=true;
                    else
                        sort_search=false;
                if(sort_search){
                    startResultFragment(false);
                }
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
                searchContent = (String) (mDrawerList.getItemAtPosition(position));
                mDrawerLayout.closeDrawer(mDrawerList);
                setLocationData();
                if (searchContent.equals("Favorite")) {
                    startResultFragment(true);
                    isFavorite = false;
                } else {
                    startResultFragment(false);
                }

            }
        });
        final View.OnClickListener originalToolbarListener = drawerToggle.getToolbarNavigationClickListener();
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 2) {
                    drawerToggle.setDrawerIndicatorEnabled(false);
                    drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getSupportFragmentManager().popBackStack();
                            detail_layout.setVisibility(View.INVISIBLE);
                        }
                    });
                } else {
                    drawerToggle.setDrawerIndicatorEnabled(true);
                    drawerToggle.setToolbarNavigationClickListener(originalToolbarListener);
                }
            }
        });
        mDrawerLayout.setDrawerListener(drawerToggle);

        // active to get current location latitude & longitude
        search_button = (ImageButton)findViewById(R.id.button_search);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchContent = searchView.getText().toString();
                searchLocation = locationView.getText().toString();
                setLocationData();
                if (searchContent.equals("Favorite")) {
                    startResultFragment(true);
                    isFavorite = false;
                } else {
                    startResultFragment(false);
                }
            }
        });

        if (savedInstanceState == null) {
            setLocationData();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SearchOptionsFragment fragment = new SearchOptionsFragment();
            fragment.setCoordinate(latitude, longitude, isFavorite);
            transaction.replace(R.id.search_detail, fragment);

            transaction.addToBackStack("Search Options");
            transaction.commit();
        }
    }

//ivan
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
    //ivan end

    private void startResultFragment(boolean isFavorite) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ResultDetailFragment fragment = new ResultDetailFragment();
        fragment.setData(searchContent, searchLocation, latitude, longitude, isFavorite);
        transaction.replace(R.id.search_detail, fragment);
        transaction.addToBackStack("Result Options");
        transaction.commit();
        searchView.setText(searchContent);
    }

    // set location data
    public void setLocationData() {
        gps = new GPSTracker(MainActivity.this);
        System.out.print("setlocationdata");
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

    @Override
    public void onLayoutSelected() {
        detail_layout = (FrameLayout)findViewById(R.id.detail_layout);
        detail_layout.setVisibility(View.VISIBLE);
    }
}
