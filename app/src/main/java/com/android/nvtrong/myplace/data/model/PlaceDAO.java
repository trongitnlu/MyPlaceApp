package com.android.nvtrong.myplace.data.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.nvtrong.myplace.data.DBUltis;
import com.android.nvtrong.myplace.data.PlaceSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class PlaceDAO {
    private static PlaceDAO INSTANCE;
    private PlaceSQLiteHelper placeSQLiteHelper;

    private PlaceDAO(Context context) {
        placeSQLiteHelper = new PlaceSQLiteHelper(context);
    }

    public static PlaceDAO getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (PlaceDAO.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PlaceDAO(context);
                }
            }
        }
        return INSTANCE;
    }

    public List<Place> getAllPlace() {
        List<Place> places = new ArrayList<>();
        SQLiteDatabase database = placeSQLiteHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DBUltis.PLACE_TBL_NAME;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Place place = new Place.Builder()
                        .setId(cursor.getInt(0))
                        .setCategoryID(cursor.getInt(1))
                        .setName(cursor.getString(2))
                        .setAddress(cursor.getString(3))
                        .setDescription(cursor.getString(4))
                        .setImage(cursor.getBlob(5))
                        .setPlaceLat(cursor.getLong(6))
                        .setPlaceLng(cursor.getLong(7))
                        .setUrlIcon(cursor.getString(8))
                        .build();
                places.add(place);
            } while (cursor.moveToNext());
        }
        return places;
    }


    public boolean checkPlace(List<Place> list, Place place2){
        for (Place place1 : list) {
            if (place1.getName().equals(place2.getName()) && place1.getAddress().equals(place2.getAddress())){
                return true;
            }
        }
        return false;
    }

    public synchronized boolean insert(Place place) {
        boolean a = false;
        List<Place> list = getAllPlace();
        //ds rong
        if (list.isEmpty() || ! checkPlace(list,place)) {
            SQLiteDatabase database = placeSQLiteHelper.getReadableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(DBUltis.COLUMN_PLACE_CATEGORY_ID, place.getCategoryID());
            contentValues.put(DBUltis.COLUMN_PLACE_NAME, place.getName());
            contentValues.put(DBUltis.COLUMN_PLACE_ADDRESS, place.getAddress());
            contentValues.put(DBUltis.COLUMN_PLACE_DESCRIPTION, place.getDescription());
            contentValues.put(DBUltis.COLUMN_PLACE_IMAGE, place.getImage());
            contentValues.put(DBUltis.COLUMN_PLACE_LAT, place.getPlaceLat());
            contentValues.put(DBUltis.COLUMN_PLACE_LNG, place.getPlaceLng());
            contentValues.put(DBUltis.COLUMN_PLACE_URLICON, place.getUrlIcon());
            Log.d("danh sach trong", "khong giong nhau");
            long result = database.insert(DBUltis.PLACE_TBL_NAME, null, contentValues);
            database.close();
            a = result > 0;
        }
        return a;
    }

    public boolean delete(int idPlace) {
        SQLiteDatabase database = placeSQLiteHelper.getReadableDatabase();
        String selection = DBUltis.COLUMN_PLACE_ID + "=?";
        int result = database.delete(DBUltis.PLACE_TBL_NAME, selection, new String[]{String.valueOf(idPlace)});
        database.close();
        Log.d("DDDDDDDDD", String.valueOf(result));
        return result > 0;
    }

}
