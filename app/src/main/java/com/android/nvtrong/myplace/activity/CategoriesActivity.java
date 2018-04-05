package com.android.nvtrong.myplace.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nvtrong.myplace.ActivityUltis;
import com.android.nvtrong.myplace.R;
import com.android.nvtrong.myplace.data.model.Category;
import com.android.nvtrong.myplace.data.model.PlaceDAO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesActivity extends AppCompatActivity {
    @BindView(R.id.textViewRestaurant)
    TextView textViewRestaurant;
    @BindView(R.id.textViewCinema)
    TextView textViewCinema;
    @BindView(R.id.textViewFashion)
    TextView textViewFashion;
    @BindView(R.id.textViewATM)
    TextView textViewATM;

    private PlaceDAO placeDAO;
    private List<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        placeDAO = PlaceDAO.getInstance(this);
        categories = placeDAO.getAllCategory();
        TextView[] textViews = {textViewRestaurant, textViewCinema, textViewFashion, textViewATM};
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setText(categories.get(i).getName());
        }
    }

    private void startPlaceActivity(int category_id) {
        Intent intent = new Intent(this, PlaceActivity.class);
        intent.putExtra(ActivityUltis.CATEGORY_KEY_EXTRA, category_id);
        startActivity(intent);

    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layoutRestaurant:
                startPlaceActivity(categories.get(0).getId());
                break;
            case R.id.layoutCinema:
                startPlaceActivity(categories.get(1).getId());
                break;
            case R.id.layoutFashion:
                startPlaceActivity(categories.get(2).getId());
                break;
            case R.id.layoutATM:
                startPlaceActivity(categories.get(3).getId());
                break;
            default:
                break;
        }
    }
}
