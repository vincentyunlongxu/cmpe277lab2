package cmpe277.lab3yelp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunlongxu on 3/17/16.
 */
public class SearchOptionsDetailFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected List<SearchInfo> searchInfoList;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize the data set
        initializeData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_options_detail, container, false);

        // BEGIN_INCLUDE(initializeRecyclerView)
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.rv);
//        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);
//        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        initializeAdapter();

        return rootView;
    }

    private void initializeData() {
        searchInfoList = new ArrayList<>();
        searchInfoList.add(new SearchInfo("Restaurants", R.drawable.ic_restaurant_black_24dp));
        searchInfoList.add(new SearchInfo("Bars", R.drawable.ic_local_bar_black_24dp));
        searchInfoList.add(new SearchInfo("Coffee & Tea", R.drawable.ic_local_cafe_black_24dp));
    }

    private void initializeAdapter() {
        RVAdapter adapter = new RVAdapter(searchInfoList);
        mRecyclerView.setAdapter(adapter);
    }
}
