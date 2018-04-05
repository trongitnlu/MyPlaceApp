package com.android.nvtrong.myplace.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.nvtrong.myplace.ActivityUltis;
import com.android.nvtrong.myplace.R;
import com.android.nvtrong.myplace.adapter.PlaceAdapter;
import com.android.nvtrong.myplace.data.model.Place;
import com.android.nvtrong.myplace.data.model.PlaceDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.nvtrong.myplace.R.string.text_progress_load;

public class PlaceActivity extends AppCompatActivity {
    @BindView(R.id.listViewPlace)
    ListView listViewPlace;
    @BindView(R.id.textViewNodata)
    TextView textViewNoData;

    private int categoryID;
    private List<Place> places = new ArrayList<>();
    private PlaceAdapter placeAdapter;
    private PlaceDAO placeDAO;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        ButterKnife.bind(this);
        init();
        getPlace();
    }

    private void init() {
        categoryID = getIntent().getIntExtra(ActivityUltis.CATEGORY_KEY_EXTRA, 0);
        placeDAO = PlaceDAO.getInstance(this);
        placeAdapter = new PlaceAdapter(this, places);
        listViewPlace.setAdapter(placeAdapter);
        iniProgressDialog();
//        progressDialog.show();
        onClickPlaceItem();
    }

    private void getPlace() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                places = placeDAO.getListPlaceByCategoryID(categoryID);
                addTestData();
                if (!places.isEmpty()) {
                    textViewNoData.setVisibility(View.GONE);
                }
                placeAdapter.updateListPlace(places);
//                progressDialog.dismiss();
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }
        }, 1000);
    }

    public void addTestData() {
        Place place = new Place.Builder()
                .setId(0)
                .setCategoryID(categoryID)
                .setName("ABC")
                .setImage(null)
                .setAddress("1234")
                .setDescription("Ngon com")
                .setPlaceLat(0)
                .setPlaceLng(0)
                .build();

        places.add(place);
    }

    private void iniProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setInverseBackgroundForced(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(getResources().getString(text_progress_load));
    }

    private void onClickPlaceItem() {
        listViewPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(PlaceActivity.this, DetailActivity.class);
                Place place = places.get(i);
                intent.putExtra(ActivityUltis.PLACE_KEY_PUT_EXTRA, place.getId());
                startActivity(intent);
            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.floatingActionButton:
                break;
            case R.id.btnShowAllOnMap:
                break;
            default:
                break;
        }
    }
}
