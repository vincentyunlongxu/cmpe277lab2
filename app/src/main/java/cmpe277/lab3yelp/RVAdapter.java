package cmpe277.lab3yelp;

import android.support.v7.widget.CardView;
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

    public class SearchInfoViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView searchInfo;
        ImageView icon;


        public SearchInfoViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            searchInfo = (TextView)itemView.findViewById(R.id.search_info);
            icon = (ImageView)itemView.findViewById(R.id.category_icon);
        }
    }

    List<SearchInfo> searchInfoList;

    RVAdapter(List<SearchInfo> searchInfoList) {
        this.searchInfoList = searchInfoList;
    }

    @Override
    public SearchInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
        SearchInfoViewHolder searchInfoViewHolder = new SearchInfoViewHolder(v);
        return searchInfoViewHolder;
    }

    @Override
    public void onBindViewHolder(SearchInfoViewHolder searchInfoViewHolder, int position) {
        searchInfoViewHolder.searchInfo.setText(searchInfoList.get(position).information);
        searchInfoViewHolder.icon.setImageResource(searchInfoList.get(position).iconId);
    }

    @Override
    public int getItemCount() {
        return searchInfoList.size();
    }
}
