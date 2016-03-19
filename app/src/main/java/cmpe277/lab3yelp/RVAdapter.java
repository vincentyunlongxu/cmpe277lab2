package cmpe277.lab3yelp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * Created by yunlongxu on 3/18/16.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.SearchInfoViewHolder> {

    /**
     * BEGIN_INCLUDE(recyclerViewSampleViewHolder)
     *
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class SearchInfoViewHolder extends RecyclerView.ViewHolder {
        private final TextView searchInfo;
        private final ImageView icon;

        public SearchInfoViewHolder(View itemView) {
            super(itemView);
            searchInfo = (TextView)itemView.findViewById(R.id.search_info);
            icon = (ImageView)itemView.findViewById(R.id.category_icon);
        }

        public TextView getTextView() {
            return searchInfo;
        }

        public ImageView getImageView() {
            return icon;
        }
    }
    // END_INCLUDE(recyclerViewSampleViewHolder)

    List<SearchInfo> searchInfoList;

    /**
     * Initialize the dataset of the Adapter
     *
     * @param searchInfoList
     */
    RVAdapter(List<SearchInfo> searchInfoList) {
        this.searchInfoList = searchInfoList;
    }

    /**
     * BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
     * Create new views (invoked by the layout manager)
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public SearchInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_row_element, parent, false);
        SearchInfoViewHolder searchInfoViewHolder = new SearchInfoViewHolder(v);
        return searchInfoViewHolder;
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    /**
     * BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
     * Replace the contents of a view (invoked by the layout manager)
     *
     * @param searchInfoViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(SearchInfoViewHolder searchInfoViewHolder, int position) {
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        searchInfoViewHolder.getTextView().setText(searchInfoList.get(position).information);
        searchInfoViewHolder.getImageView().setImageResource(searchInfoList.get(position).iconId);

//        searchInfoViewHolder.searchInfo.setText(searchInfoList.get(position).information);
//        searchInfoViewHolder.icon.setImageResource(searchInfoList.get(position).iconId);
    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return searchInfoList.size();
    }
}
