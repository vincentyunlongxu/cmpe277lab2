package cmpe277.lab3yelp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yelp.clientlib.entities.Business;

/**
 * Created by yunlongxu on 3/22/16.
 */
public class BusinessDetailFragment extends Fragment {

    private Business business = null;
    private TextView businessName;
    private TextView distance;
    private WebView rating;

    private SupportMapFragment fragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_business_detail, container, false);

        // set business name
        businessName = (TextView)rootView.findViewById(R.id.bus_name);
        businessName.setText(business.name());

        // set rating
        rating = (WebView)rootView.findViewById(R.id.rate_img_detail);
        rating.setBackgroundColor(Color.TRANSPARENT);
        rating.loadUrl(business.ratingImgUrl());

        distance = (TextView)rootView.findViewById(R.id.distance);
        distance.setText(business.distance() + " Miles");

        setUpGoogleMap();


        return rootView;
    }

    private void setUpGoogleMap() {
        FragmentManager fm = getChildFragmentManager();
        GoogleMapOptions options = new GoogleMapOptions();
        LatLng latLng = new LatLng(business.location().coordinate().latitude(), business.location().coordinate().longitude());

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng) // Center Set
                .zoom(11.0f)                // Zoom
                .bearing(90)                // Orientation of the camera to east
                .tilt(0)                   // Tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        options.mapType(GoogleMap.MAP_TYPE_NORMAL)
                .compassEnabled(false)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false).camera(cameraPosition).liteMode(true);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance(options);
            fragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    googleMap.addMarker(new MarkerOptions().position(new LatLng(business.location().coordinate().latitude(), business.location().coordinate().longitude()))
                            .title("Marker")
                            .snippet("Please move the marker if needed.")
                            .draggable(true));
                    LatLng latLng;
                    if (business.location() != null) {
                        latLng = new LatLng(business.location().coordinate().latitude(), business.location().coordinate().longitude());
                    } else {
                        latLng = new LatLng(0, 0);
                    }
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(latLng) // Center Set
                            .zoom(15.0f)                // Zoom
                            .bearing(90)                // Orientation of the camera to east
                            .tilt(0)                   // Tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                }
            });
            fm.beginTransaction().replace(R.id.map, fragment).addToBackStack("detail_map").commit();
        }
    }

    public void setBusiness(Business business) {
        this.business = business;
    }
}
