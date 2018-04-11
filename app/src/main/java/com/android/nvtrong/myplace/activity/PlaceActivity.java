package com.android.nvtrong.myplace.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nvtrong.myplace.ActivityUltis;
import com.android.nvtrong.myplace.R;
import com.android.nvtrong.myplace.adapter.PlaceAdapter;
import com.android.nvtrong.myplace.data.google.GPSTracker;
import com.android.nvtrong.myplace.data.google.GeocodingRoot;
import com.android.nvtrong.myplace.data.google.Geometry;
import com.android.nvtrong.myplace.data.google.Result;
import com.android.nvtrong.myplace.data.model.Place;
import com.android.nvtrong.myplace.data.model.PlaceDAO;
import com.android.nvtrong.myplace.service.APIUltis;
import com.android.nvtrong.myplace.service.ServiceAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.nvtrong.myplace.R.string.text_progress_load;

public class PlaceActivity extends AppCompatActivity {
    @BindView(R.id.listViewPlace)
    ListView listViewPlace;
    @BindView(R.id.textViewNodata)
    TextView textViewNoData;
    @BindView(R.id.loadingPanel)
    RelativeLayout loadingPanel;

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
    }

    private void init() {
        categoryID = getIntent().getIntExtra(ActivityUltis.CATEGORY_KEY_EXTRA, 0);
        placeDAO = PlaceDAO.getInstance(this);
        placeAdapter = new PlaceAdapter(this, places);
        listViewPlace.setAdapter(placeAdapter);
        iniProgressDialog();
//        progressDialog.show();
        onClickPlaceItem();
        getPlaces(categoryID);


    }

    private void getPlaces(final int categoryID) {
        loadingPanel.setVisibility(View.VISIBLE);
        textViewNoData.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                addTestData();
                places = placeDAO.getListPlaceByCategoryID(categoryID);
                if (!places.isEmpty()) {
                    textViewNoData.setVisibility(View.GONE);
                }
                placeAdapter.updateListPlace(places);
//                progressDialog.dismiss();
                loadingPanel.setVisibility(View.GONE);
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
        placeDAO = PlaceDAO.getInstance(this);
        placeDAO.insert(place);
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
                startActivityForResult(intent, ActivityUltis.REQUEST_DETAIL_PLACE);
            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.floatingActionButton:
                Intent intent = new Intent(this, ModifyActivity.class);
                intent.putExtra(ActivityUltis.CATEGORY_KEY_EXTRA, categoryID);
                intent.putExtra(ActivityUltis.REQUEST_TYPE, ActivityUltis.TYPE_INSERT);
                startActivityForResult(intent, ActivityUltis.REQUEST_INSERT);

                break;
            case R.id.btnShowAllOnMap:
                showAllMaps();
                break;
            default:
                break;
        }
    }

    private void showAllMaps() {
        if (places.isEmpty()) {
            Toast.makeText(this, "Please add a locations!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent1 = new Intent(this, MapsActivity.class);
            intent1.putExtra(ActivityUltis.CATEGORY_KEY_EXTRA, categoryID);
            startActivity(intent1);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == ActivityUltis.REQUEST_DETAIL_PLACE || requestCode == ActivityUltis.REQUEST_INSERT) && resultCode == RESULT_OK && data != null) {
            getPlaces(categoryID);
        }
    }
}
