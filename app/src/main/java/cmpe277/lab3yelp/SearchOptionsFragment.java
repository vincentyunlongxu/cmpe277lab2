package cmpe277.lab3yelp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunlongxu on 3/19/16.
 */
public class SearchOptionsFragment extends Fragment {

    private SearchInfo selectedOption;
    private double latitude;
    private double longitude;
    private boolean isFavorite;

    public SearchOptionsFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragement_search_options, container, false);
        List<SearchInfo> searchInfo = getListSearchInfo();
        final ListView lv = (ListView)rootView.findViewById(R.id.lv_search);
        lv.setAdapter(new SearchOptionsAdapter(getActivity(), searchInfo));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedOption = (SearchInfo) lv.getItemAtPosition(position);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                ResultDetailFragment fragment = new ResultDetailFragment();
                fragment.setData(selectedOption.information, null, latitude, longitude, isFavorite);
                transaction.replace(R.id.search_detail, fragment);
                transaction.addToBackStack("Result Options");
                transaction.commit();
            }
        });
        return rootView;
    }

    public void setCoordinate(double latitude, double longitude, boolean isFavorite) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.isFavorite = isFavorite;
    }

    public List<SearchInfo> getListSearchInfo() {
        List<SearchInfo> searchInfos = new ArrayList<>();
        SearchInfo restaurant_info = new SearchInfo("Restaurant", R.drawable.ic_restaurant_black_24dp);
        SearchInfo bar_info = new SearchInfo("Bars", R.drawable.ic_local_bar_black_24dp);
        SearchInfo nightLife = new SearchInfo("Nightlife", R.drawable.ic_local_bar_black_24dp);
        SearchInfo coffee = new SearchInfo("Coffee & Tea", R.drawable.ic_local_cafe_black_24dp);
        SearchInfo newBusiness = new SearchInfo("Hot New Business", R.drawable.ic_flash_on_black_24dp);
        SearchInfo delivery = new SearchInfo("Delivery", R.drawable.ic_local_shipping_black_24dp);
        SearchInfo gasStation = new SearchInfo("Gas & Service Stations", R.drawable.ic_local_gas_station_black_24dp);
        SearchInfo drugStore = new SearchInfo("Drugstores", R.drawable.ic_local_hospital_black_24dp);
        SearchInfo reservation = new SearchInfo("Reservation", R.drawable.ic_event_black_24dp);
        SearchInfo favorite = new SearchInfo("Favorite", R.drawable.ic_star_black_24dp);
        searchInfos.add(restaurant_info);
        searchInfos.add(bar_info);
        searchInfos.add(nightLife);
        searchInfos.add(coffee);
        searchInfos.add(newBusiness);
        searchInfos.add(delivery);
        searchInfos.add(gasStation);
        searchInfos.add(drugStore);
        searchInfos.add(reservation);
        searchInfos.add(favorite);

        return searchInfos;
    }
}
