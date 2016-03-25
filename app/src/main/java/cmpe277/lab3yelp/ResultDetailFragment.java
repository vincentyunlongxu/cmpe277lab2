package cmpe277.lab3yelp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yelp.clientlib.entities.Business;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunlongxu on 3/20/16.
 */
public class ResultDetailFragment extends Fragment {

    private GetYelpData getYelpData;
    private String searchContent;
    private String searchLocation;
    private double latitude;
    private double longitude;
    private OnLayoutSelectListener mCallBack;
    private Business selectedBusiness = null;
    private boolean isFavorite = false;
    private ArrayList<FavoriteBusiness> favoriteBusinesses;

    public interface OnLayoutSelectListener {
        public void onLayoutSelected();
    }

    public ResultDetailFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_result, container, false);
        if (!isFavorite) {
            getYelpData = new GetYelpData(searchContent, searchLocation, latitude, longitude);
            getYelpData.getData();
            ArrayList<Business> businessList = getYelpData.getBusinesses();
            final ListView lv_result = (ListView)rootView.findViewById(R.id.lv_result);
            lv_result.setAdapter(new SearchResultAdapter(getActivity(), businessList));
            lv_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedBusiness = (Business)lv_result.getItemAtPosition(position);
                    mCallBack.onLayoutSelected();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    BusinessDetailFragment fragment = new BusinessDetailFragment();
                    fragment.setBusiness(selectedBusiness);
                    transaction.replace(R.id.detail_layout, fragment);
                    transaction.addToBackStack("Business Detail");
                    transaction.commit();
                }
            });
        } else {
            DatabaseHandler db = new DatabaseHandler(getActivity());
            favoriteBusinesses = db.getAllFavoriteBusiness();
            final ListView lv_result = (ListView)rootView.findViewById(R.id.lv_result);
            lv_result.setAdapter(new FavoriteResultAdapter(getActivity(), favoriteBusinesses));
            db.close();
        }
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallBack = (OnLayoutSelectListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnLayoutSelectListener");
        }
    }

    public void setData(String searchContent, String searchLocation, double latitude, double longitude, boolean isFavorite) {
        this.searchContent = searchContent;
        this.searchLocation = searchLocation;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isFavorite = isFavorite;
    }
}
