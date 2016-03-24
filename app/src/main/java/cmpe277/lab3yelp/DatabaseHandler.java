package cmpe277.lab3yelp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.apache.velocity.runtime.log.LogDisplayWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunlongxu on 3/24/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // all static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "FavoriteBusinessManager";

    // Table Name
    private static final String TABLE_NAME = "FavoritesLists";

    // favorite lists table column
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DISPLAY_PHONE = "display_phone";
    private static final String KEY_DISTANCE = "distance";
    private static final String KEY_IMAGEURL = "imageUrl";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_RATINGURL = "rating_imgUrl";
    private static final String KEY_RATINGURLLARGE = "rating_imgUrlLarge";
    private static final String KEY_RATINGURLSMALL = "rating_imgUrlSmall";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAVORITES_TABLE = "CREATE TABLE "
                + TABLE_NAME + "("
                + KEY_ID + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_DISPLAY_PHONE + " TEXT,"
                + KEY_DISTANCE + " TEXT,"
                + KEY_IMAGEURL + " TEXT,"
                + KEY_PHONE + " TEXT,"
                + KEY_LATITUDE + " TEXT,"
                + KEY_LONGITUDE + " TEXT,"
                + KEY_RATINGURL + " TEXT,"
                + KEY_RATINGURLLARGE + " TEXT,"
                + KEY_RATINGURLSMALL + " TEXT" + ")";
        db.execSQL(CREATE_FAVORITES_TABLE);
        Log.d("Database", "table " + TABLE_NAME + " created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addFavorites(FavoriteBusiness favoriteBusiness) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, favoriteBusiness.get_id());
        values.put(KEY_NAME, favoriteBusiness.get_name());
        values.put(KEY_DISPLAY_PHONE, favoriteBusiness.get_display_phone());
        values.put(KEY_DISTANCE, favoriteBusiness.get_distance());
        values.put(KEY_IMAGEURL, favoriteBusiness.get_imageUrl());
        values.put(KEY_PHONE, favoriteBusiness.get_phone());
        values.put(KEY_LATITUDE, favoriteBusiness.get_latitude());
        values.put(KEY_LONGITUDE, favoriteBusiness.get_longitude());
        values.put(KEY_RATINGURL, favoriteBusiness.get_ratingImgUrl());
        values.put(KEY_RATINGURLLARGE, favoriteBusiness.get_ratingImgUrlLarge());
        values.put(KEY_RATINGURLSMALL, favoriteBusiness.get_ratingImgUrlSmall());

        db.insert(TABLE_NAME, null, values);
        Log.d("New Favorite Business", values.toString());
        db.close();

    }

    public List<FavoriteBusiness> getAllFavoriteBusiness() {

        // prepare list for all favorite businesses
        List<FavoriteBusiness> favoriteBusinesses = new ArrayList<>();
        // prepare the query statement
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d("Get all businesses", "data got");

        if (cursor.moveToFirst()) {
            do {
                FavoriteBusiness favoriteBusiness = new FavoriteBusiness();
                favoriteBusiness.set_id(cursor.getString(0));
                favoriteBusiness.set_name(cursor.getString(1));
                favoriteBusiness.set_display_phone(cursor.getString(2));
                favoriteBusiness.set_distance(cursor.getString(3));
                favoriteBusiness.set_imageUrl(cursor.getString(4));
                favoriteBusiness.set_phone(cursor.getString(5));
                favoriteBusiness.set_latitude(cursor.getString(6));
                favoriteBusiness.set_longitude(cursor.getString(7));
                favoriteBusiness.set_ratingImgUrl(cursor.getString(8));
                favoriteBusiness.set_ratingImgUrlLarge(cursor.getString(9));
                favoriteBusiness.set_ratingImgUrlSmall(cursor.getString(10));
                favoriteBusinesses.add(favoriteBusiness);
            } while (cursor.moveToNext());
        }

        return favoriteBusinesses;
    }

    public int getFavoriteBusinessCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public void deleteFavorites(FavoriteBusiness favoriteBusiness) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[] { String.valueOf(favoriteBusiness.get_id()) });
        db.close();

    }
}
