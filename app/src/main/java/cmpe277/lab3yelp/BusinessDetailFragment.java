package cmpe277.lab3yelp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.Location;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunlongxu on 3/22/16.
 */
public class BusinessDetailFragment extends Fragment {

    private Business business = null;
    private TextView businessName;
    private TextView distance;
    private WebView rating;
    private TextView addressView;
    private TextView phone;
    private ImageButton icon_bookmark;
    private TextView book_mark;
    private boolean favorite = false;

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
        String calDistance = business.distance().toString();
        distance.setText(calDistance + " Meters");

        String address = "";
        Location location = business.location();
        ArrayList<String> addressList = location.displayAddress();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < addressList.size(); i++){
            if(i == addressList.size()-1){
                sb.append(" ");
                sb.append(addressList.get(i));
                break;
            }
            sb.append(addressList.get(i));
            sb.append(" ");
        }
        addressView = (TextView)rootView.findViewById(R.id.google_address);
        addressView.setText(sb.toString());

        phone = (TextView)rootView.findViewById(R.id.telephone);
        phone.setText(business.displayPhone());

        icon_bookmark = (ImageButton) rootView.findViewById(R.id.bookmark_icon);
        icon_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToDatabase();
            }
        });
        book_mark = (TextView)rootView.findViewById(R.id.bookmark);
        book_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToDatabase();
            }
        });

        setUpGoogleMap();


        return rootView;
    }

    private void connectToDatabase() {
        DatabaseHandler db = new DatabaseHandler(getActivity());
        Location location = business.location();
        ArrayList<String> addressList = location.displayAddress();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < addressList.size(); i++){
            if(i == addressList.size()-1){
                sb.append(" ");
                sb.append(addressList.get(i));
                break;
            }
            sb.append(addressList.get(i));
            sb.append(" ");
        }
//        System.out.println(sb.toString());
        Toast toast = Toast.makeText(getActivity(), sb.toString(), Toast.LENGTH_LONG);
        toast.show();
        FavoriteBusiness favoriteBusiness = new FavoriteBusiness(
                business.id(),
                business.name(),
                business.displayPhone(),
                business.distance().toString(),
                business.imageUrl(),
                business.phone(),
                business.location().coordinate().latitude().toString(),
                business.location().coordinate().longitude().toString(),
                business.ratingImgUrl(),
                business.ratingImgUrlLarge(),
                business.ratingImgUrlSmall(),
                sb.toString()
        );
        if (favorite == false) {
            favorite = true;
            icon_bookmark.setImageResource(R.drawable.ic_bookmark_black_24dp);
            Log.d("Insert: ", "Inserting ..");
            db.addFavorites(favoriteBusiness);

        } else{
            icon_bookmark.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
            favorite = false;
            List<FavoriteBusiness> favoriteBusinessList = db.getAllFavoriteBusiness();
            System.out.println(favoriteBusinessList.get(0));
        }
        db.close();
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
            fm.beginTransaction().replace(R.id.map_google_service, fragment).addToBackStack("detail_map").commit();
        }
    }

    public void setBusiness(Business business) {
        this.business = business;
    }
}
