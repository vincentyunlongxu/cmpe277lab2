package cmpe277.lab3yelp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    public interface OnLayoutSelectListener {
        public void onLayoutSelected();
    }

    public ResultDetailFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_result, container, false);
        getYelpData = new GetYelpData(searchContent, searchLocation, latitude, longitude);
        getYelpData.getData();
        ArrayList<Business> businessList = getYelpData.getBusinesses();
        ListView lv_result = (ListView)rootView.findViewById(R.id.lv_result);
        lv_result.setAdapter(new SearchResultAdapter(getActivity(), businessList));

        return rootView;
    }

    public void setData(String searchContent, String searchLocation, double latitude, double longitude) {
        this.searchContent = searchContent;
        this.searchLocation = searchLocation;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
