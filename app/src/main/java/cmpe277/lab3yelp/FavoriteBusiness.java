package cmpe277.lab3yelp;

/**
 * Created by yunlongxu on 3/25/16.
 */
public class FavoriteBusiness {
    private String _id;
    private String _name;
    private String _display_phone;
    private String _distance;
    private String _imageUrl;
    private String _phone;
    private String _latitude;
    private String _longitude;
    private String _ratingImgUrl;
    private String _ratingImgUrlLarge;
    private String _ratingImgUrlSmall;
    private String _address;

    public FavoriteBusiness() {
    }

    public FavoriteBusiness(String _id,
                            String _name,
                            String _display_phone,
                            String _distance,
                            String _imageUrl,
                            String _phone,
                            String _latitude,
                            String _longitude,
                            String _ratingImgUrl,
                            String _ratingImgUrlLarge,
                            String _ratingImgUrlSmall,
                            String _address) {
        this._id = _id;
        this._name = _name;
        this._display_phone = _display_phone;
        this._distance = _distance;
        this._imageUrl = _imageUrl;
        this._phone = _phone;
        this._latitude = _latitude;
        this._longitude = _longitude;
        this._ratingImgUrl = _ratingImgUrl;
        this._ratingImgUrlLarge = _ratingImgUrlLarge;
        this._ratingImgUrlSmall = _ratingImgUrlSmall;
        this._address = _address;

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_display_phone() {
        return _display_phone;
    }

    public void set_display_phone(String _display_phone) {
        this._display_phone = _display_phone;
    }

    public String get_distance() {
        return _distance;
    }

    public void set_distance(String _distance) {
        this._distance = _distance;
    }

    public String get_imageUrl() {
        return _imageUrl;
    }

    public void set_imageUrl(String _imageUrl) {
        this._imageUrl = _imageUrl;
    }

    public String get_phone() {
        return _phone;
    }

    public void set_phone(String _phone) {
        this._phone = _phone;
    }

    public String get_latitude() {
        return _latitude;
    }

    public void set_latitude(String _latitude) {
        this._latitude = _latitude;
    }

    public String get_longitude() {
        return _longitude;
    }

    public void set_longitude(String _longitude) {
        this._longitude = _longitude;
    }

    public String get_ratingImgUrl() {
        return _ratingImgUrl;
    }

    public void set_ratingImgUrl(String _ratingImgUrl) {
        this._ratingImgUrl = _ratingImgUrl;
    }

    public String get_ratingImgUrlLarge() {
        return _ratingImgUrlLarge;
    }

    public void set_ratingImgUrlLarge(String _ratingImgUrlLarge) {
        this._ratingImgUrlLarge = _ratingImgUrlLarge;
    }

    public String get_ratingImgUrlSmall() {
        return _ratingImgUrlSmall;
    }

    public void set_ratingImgUrlSmall(String _ratingImgUrlSmall) {
        this._ratingImgUrlSmall = _ratingImgUrlSmall;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

}
