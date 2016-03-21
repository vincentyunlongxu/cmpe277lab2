package cmpe277.lab3yelp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yunlongxu on 3/19/16.
 */
public class SearchOptionsAdapter extends BaseAdapter {

    private static List<SearchInfo> searchInfos;
    private LayoutInflater mInflater;

    public SearchOptionsAdapter(Context displayResultFragment, List<SearchInfo> searchInfos) {
        this.searchInfos = searchInfos;
        mInflater = LayoutInflater.from(displayResultFragment);
    }

    @Override
    public int getCount() {
        return searchInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return searchInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_row_element, null);
            holder = new ViewHolder();
            holder.searchInfo = (TextView)convertView.findViewById(R.id.search_info);
            holder.icon = (ImageView)convertView.findViewById(R.id.category_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.searchInfo.setText(searchInfos.get(position).information);
        holder.icon.setImageResource(searchInfos.get(position).iconId);

        return convertView;
    }

    static class ViewHolder {
        TextView searchInfo;
        ImageView icon;
    }
}
