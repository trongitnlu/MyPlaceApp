package com.android.nvtrong.myplace.data.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.android.nvtrong.myplace.data.DBUltis;
import com.android.nvtrong.myplace.data.PlaceSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nvtrong on 4/4/2018.
 */

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

    public List<Category> getAllCategory() {
        List<Category> categories = new ArrayList<>();
        SQLiteDatabase database = placeSQLiteHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DBUltis.CATEGORY_TBL_NAME;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category(cursor.getInt(0), cursor.getString(1));
                categories.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return categories;
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

    public List<Place> getListPlaceByCategoryID(int categoryID) {
        List<Place> places = new ArrayList<>();
        SQLiteDatabase database = placeSQLiteHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DBUltis.PLACE_TBL_NAME + " WHERE categoryID=?";
        String[] selection = new String[]{String.valueOf(categoryID)};
        Cursor cursor = database.rawQuery(query, selection);
        if (cursor.moveToFirst()) {
            do {
                Place place = new Place.Builder()
                        .setId(cursor.getInt(0))
                        .setCategoryID(cursor.getInt(1))
                        .setName(cursor.getString(2))
                        .setAddress(cursor.getString(3))
                        .setDescription(cursor.getString(4))
                        .setImage(cursor.getBlob(5))
                        .setPlaceLat(cursor.getDouble(6))
                        .setPlaceLng(cursor.getDouble(7))
                        .build();
                places.add(place);
            } while (cursor.moveToNext());
        }
        return places;
    }

    public Place getPlace(int id) {
        Place place = null;
        String query = "SELECT * FROM " + DBUltis.PLACE_TBL_NAME + " WHERE id=?";
        SQLiteDatabase database = placeSQLiteHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            do {
                place = new Place.Builder()
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
            } while (cursor.moveToNext());
        }
        return place;
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

    public boolean update(Place place) {
        SQLiteDatabase database = placeSQLiteHelper.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBUltis.COLUMN_PLACE_ID, place.getId());
        contentValues.put(DBUltis.COLUMN_PLACE_NAME, place.getName());
        contentValues.put(DBUltis.COLUMN_PLACE_ADDRESS, place.getAddress());
        contentValues.put(DBUltis.COLUMN_PLACE_DESCRIPTION, place.getDescription());
        contentValues.put(DBUltis.COLUMN_PLACE_IMAGE, place.getImage());
        contentValues.put(DBUltis.COLUMN_PLACE_LAT, place.getPlaceLat());
        contentValues.put(DBUltis.COLUMN_PLACE_LNG, place.getPlaceLng());

        int result = database.update(DBUltis.PLACE_TBL_NAME, contentValues, DBUltis.COLUMN_PLACE_ID + "=?", new String[]{String.valueOf(place.getId())});
        database.close();
        return result > 0;
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
