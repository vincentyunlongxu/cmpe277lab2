package cmpe277.lab3yelp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.Location;

import java.util.ArrayList;

/**
 * Created by yunlongxu on 3/20/16.
 */
public class SearchResultAdapter extends ArrayAdapter<Business> {

    private final Context context;
    private final ArrayList<Business> businesses;

    public SearchResultAdapter(Context context, ArrayList<Business> values) {
        super(context, R.layout.list_search_result ,values);
        this.context = context;
        this.businesses = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rootView = inflater.inflate(R.layout.list_search_result, parent, false);

        // set up business icon
        String imageUrl = businesses.get(position).imageUrl();
        // configure the widget
        WebView webView = (WebView) rootView.findViewById(R.id.business_icon);
        // load the icon image to widget to display
        webView.loadUrl(imageUrl);

        // set up business name
        String businessName = businesses.get(position).name();
        // configure the widget
        TextView textViewName = (TextView) rootView.findViewById(R.id.business_name);
        // load the business name to widget to display
        textViewName.setText(businessName);

        // set up business rating
        Double rating = businesses.get(position).rating();
        // configure the widget
        WebView viewRating = (WebView) rootView.findViewById(R.id.business_rating);
        viewRating.setBackgroundColor(Color.TRANSPARENT);
        // load the business rating to widget to display
        viewRating.loadUrl(businesses.get(position).ratingImgUrl());

        // set up business address
        Location address = businesses.get(position).location();
        ArrayList<String> addressList = address.displayAddress();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < addressList.size(); i++){
            if(i == addressList.size()-1){
                sb.append("\n");
                sb.append(addressList.get(i));
                break;
            }
            sb.append(addressList.get(i));
            sb.append(" ");
        }
        // configure the widget
        TextView textViewAddress = (TextView) rootView.findViewById(R.id.business_address);
        // load the address to widget to display
        textViewAddress.setText(sb.toString());

        return rootView;
    }
}
