package cmpe277.lab3yelp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunlongxu on 3/19/16.
 */
public class DisplayResultFragment extends Fragment {

    public DisplayResultFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragement_display_result, container, false);
        List<SearchInfo> searchInfo = getListSearchInfo();
        ListView lv = (ListView)rootView.findViewById(R.id.lv_search);
        lv.setAdapter(new DisplayResultAdapter(getActivity(), searchInfo));
        return rootView;
    }

    public List<SearchInfo> getListSearchInfo() {
        List<SearchInfo> searchInfos = new ArrayList<>();
        SearchInfo restaurant_info = new SearchInfo("Restaurant", R.drawable.ic_restaurant_black_24dp);
        SearchInfo bar_info = new SearchInfo("Bars", R.drawable.ic_local_bar_black_24dp);
        SearchInfo coffee = new SearchInfo("Coffee & Tea", R.drawable.ic_local_cafe_black_24dp);
        searchInfos.add(restaurant_info);
        searchInfos.add(bar_info);
        searchInfos.add(coffee);
        return searchInfos;
    }
}
