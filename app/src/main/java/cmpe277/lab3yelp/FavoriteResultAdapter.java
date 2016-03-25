package cmpe277.lab3yelp;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yunlongxu on 3/24/16.
 */
public class FavoriteResultAdapter extends ArrayAdapter<FavoriteBusiness> {

    private final Context context;
    private final ArrayList<FavoriteBusiness> businesses;

    public FavoriteResultAdapter(Context context, ArrayList<FavoriteBusiness> values) {
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
        String imageUrl = businesses.get(position).get_imageUrl();
        // configure the widget
        WebView webView = (WebView) rootView.findViewById(R.id.business_icon);
        // load the icon image to widget to display
        webView.loadUrl(imageUrl);

        // set up business name
        String businessName = businesses.get(position).get_name();
        // configure the widget
        TextView textViewName = (TextView) rootView.findViewById(R.id.business_name);
        // load the business name to widget to display
        textViewName.setText(businessName);

        // set up business rating
        // configure the widget
        WebView viewRating = (WebView) rootView.findViewById(R.id.business_rating);
        viewRating.setBackgroundColor(Color.TRANSPARENT);
        // load the business rating to widget to display
        viewRating.loadUrl(businesses.get(position).get_ratingImgUrl());

        // set up business address
        String address = businesses.get(position).get_address();

        // configure the widget
        TextView textViewAddress = (TextView) rootView.findViewById(R.id.business_address);
        // load the address to widget to display
        textViewAddress.setText(address);

        return rootView;
    }
}
