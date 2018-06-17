package com.android.nvtrong.myplace.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by nvtrong on 4/4/2018.
 */

public class PlaceSQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "place";
    private static final int DB_VERSTION = 1;
    public static final String tbl_place = "place";
    public static final String tbl_category = "category";
    public static final String CREATE_PLACE_TBL_SQL =
            "CREATE TABLE " + DBUltis.PLACE_TBL_NAME + "("
                    + DBUltis.COLUMN_PLACE_ID + " " + DBUltis.INTEGER_DATA_TYPE + " " + DBUltis.PRIMARY_KEY + " AUTOINCREMENT, "
                    + DBUltis.COLUMN_PLACE_CATEGORY_ID + " " + DBUltis.INTEGER_DATA_TYPE + " " + DBUltis.NOT_NULL + ", "
                    + DBUltis.COLUMN_PLACE_NAME + " " + DBUltis.TEXT_DATA_TYPE + " " + DBUltis.NOT_NULL + ", "
                    + DBUltis.COLUMN_PLACE_ADDRESS + " " + DBUltis.TEXT_DATA_TYPE + " " + DBUltis.NOT_NULL + ", "
                    + DBUltis.COLUMN_PLACE_DESCRIPTION + " " + DBUltis.TEXT_DATA_TYPE +  ", "
                    + DBUltis.COLUMN_PLACE_IMAGE + " " + DBUltis.BLOB_DATA_TYPE + " " +  ", "
                    + DBUltis.COLUMN_PLACE_LAT + " " + DBUltis.REAL_DATA_TYPE + " "  + ", "
                    + DBUltis.COLUMN_PLACE_LNG + " " + DBUltis.REAL_DATA_TYPE + " " + ", "
                    + DBUltis.COLUMN_PLACE_URLICON + " " + DBUltis.TEXT_DATA_TYPE
                    + ")";
    public static final String CREATE_CATEGORY_TBL_SQL =
            "CREATE TABLE " + DBUltis.CATEGORY_TBL_NAME + "("
                    + DBUltis.COLUMN_CATEGORY_ID + " " + DBUltis.INTEGER_DATA_TYPE + " " + DBUltis.PRIMARY_KEY + " AUTOINCREMENT, "
                    + DBUltis.COLUMN_CATEGORY_NAME + " " + DBUltis.TEXT_DATA_TYPE + " " + DBUltis.NOT_NULL
                    + ")";
    public static final String INSERT_CATEGORY_SQL =
            "INSERT INTO " + DBUltis.CATEGORY_TBL_NAME
                    + "(" + DBUltis.COLUMN_CATEGORY_NAME + ") "
                    + "VALUES "
                    + "('Restaurant'),"
                    + "('Cinema'),"
                    + "('Fashion'),"
                    + "('ATM')";

    public PlaceSQLiteHelper(Context context) {
        super( context, DB_NAME, null, DB_VERSTION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PLACE_TBL_SQL);
        sqLiteDatabase.execSQL(CREATE_CATEGORY_TBL_SQL);
        sqLiteDatabase.execSQL(INSERT_CATEGORY_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("11111111111111", "Co update");
    }
}
