package cmpe277.lab3yelp;

import android.os.StrictMode;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.Location;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;


/**
 * Created by yunlongxu on 3/20/16.
 */
public class GetYelpData {

    private final String consumerKey = "GS4PX8uSw5AIQmKHXb6V3Q";
    private final String consumerSecret = "3xrJYgc_fYkaDC1D7ds1ORc1aco";
    private final String token = "x13WSeMGalm_EnlrpGr-u0rKt6XBZMvw";
    private final String tokenSecret = "dgiBbljiMcB4_skOHLsg0CyU64s";

    private String searchContent;
    private String searchLocation;
    private double latitude;
    private double longitude;
    private Call<SearchResponse> call = null;

    private ArrayList<Business> businesses;

    public GetYelpData(String searchContent, String searchLocation, double latitude, double longitude) {
        this.searchContent = searchContent;
        this.searchLocation = searchLocation;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void getData() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        YelpAPIFactory apiFactory = new YelpAPIFactory(consumerKey, consumerSecret, token, tokenSecret);
        YelpAPI yelpAPI = apiFactory.createAPI();

        Map<String, String> params = new HashMap<>();

        // general params
//        params.put("term", "restaurants");
//        params.put("limit", "20");
//        params.put("sort","1");

        if (searchLocation == null || searchLocation.length() == 0) {
            CoordinateOptions coordinate = CoordinateOptions.builder()
                    .latitude(latitude)
                    .longitude(longitude).build();
            params.put("term", searchContent);
            params.put("limit", "20");
            params.put("sort", "1");

            call = yelpAPI.search(coordinate, params);

        } else {
            params.put("term", searchContent);
            params.put("limit", "20");
            params.put("sort", "1");

            call = yelpAPI.search(searchLocation, params);
        }

        SearchResponse searchResponse = null;

        try {
            searchResponse = call.execute().body();
            int totalNumberOfResult = searchResponse.total();  // 3
            businesses = searchResponse.businesses();
            //business icon
            String image = businesses.get(0).imageUrl();
            //business name
            String businessName = businesses.get(0).name();  // "JapaCurry Truck"
            //business rating
            Double rating = businesses.get(0).rating();  // 4.0

            //business address
            Location address = businesses.get(0).location();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Business> getBusinesses() {
        return businesses;
    }

}
