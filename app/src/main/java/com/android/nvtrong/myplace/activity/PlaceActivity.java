package com.android.nvtrong.myplace.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nvtrong.myplace.ActivityUltis;
import com.android.nvtrong.myplace.R;
import com.android.nvtrong.myplace.adapter.PlaceAdapter;
import com.android.nvtrong.myplace.data.Loading;
import com.android.nvtrong.myplace.data.google.GPSTracker;
import com.android.nvtrong.myplace.data.google.GeocodingRoot;
import com.android.nvtrong.myplace.data.google.Geometry;
import com.android.nvtrong.myplace.data.google.MapsUltis;
import com.android.nvtrong.myplace.data.google.Photos;
import com.android.nvtrong.myplace.data.google.Result;
import com.android.nvtrong.myplace.data.model.Place;
import com.android.nvtrong.myplace.data.model.PlaceDAO;
import com.android.nvtrong.myplace.extension.SharePreferences;
import com.android.nvtrong.myplace.service.APIUltis;
import com.android.nvtrong.myplace.service.ServiceAPI;

import java.util.ArrayList;
import java.util.List;

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

    private List<Place> places = new ArrayList<>();
    private PlaceAdapter placeAdapter;

    private String MAP_SEARCH_RADIUS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        ButterKnife.bind(this);
        MAP_SEARCH_RADIUS = String.valueOf(SharePreferences.getPreferencesRadius(this));
        String types = getIntent().getStringExtra(ActivityUltis.CATEGORY_NAME_EXTRA);
        placeAdapter = new PlaceAdapter(this, places);
        listViewPlace.setAdapter(placeAdapter);
        showListPlaceGoogleMaps(types);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportParentActivityIntent();
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
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
            intent1.putExtra(ActivityUltis.REQUEST_PUT_PLACE_EXTRA, (ArrayList) places);
            startActivity(intent1);
        }

    }


    private void showListPlaceGoogleMaps(String typeName) {
        loadingPanel.setVisibility(View.GONE);
        textViewNoData.setVisibility(View.GONE);
        final Loading loading = Loading.create(this);
        loading.show();
        ServiceAPI serviceAPI = APIUltis.getData();
        GPSTracker gpsTracker = new GPSTracker(this);
        String key = getResources().getString(R.string.google_api_key);
        Call<GeocodingRoot> rootCall = serviceAPI.getLocationByType(gpsTracker.getStringLocation(), MAP_SEARCH_RADIUS, typeName, key);
        rootCall.enqueue(new Callback<GeocodingRoot>() {
            @Override
            public void onResponse(Call<GeocodingRoot> call, Response<GeocodingRoot> response) {
                GeocodingRoot geocodingRoot = response.body();
                List<Result> results = geocodingRoot.getResults();
                if (results == null || results.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Not Found!", Toast.LENGTH_SHORT).show();
                    textViewNoData.setVisibility(View.VISIBLE);
                } else {
                    for (Result result : results) {
                        Geometry geometry = result.getGeometry();
                        List<Photos> photos = result.getPhotos();
                        Place place = new Place.Builder()
                                .setName(result.getName())
                                .setPlaceLng(geometry.getLocation().getLng())
                                .setPlaceLat(geometry.getLocation().getLat())
                                .setAddress(result.getVicinity())
                                .setUrlIcon(photos == null ? result.getIcon() : MapsUltis.apiUrlPhotos + photos.get(0).getPhoto_reference())
                                .setDescription(result.getGeometry().toString())
                                .build();
                        places.add(place);
                    }
                }
                placeAdapter.updateListPlace(places);
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<GeocodingRoot> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                textViewNoData.setVisibility(View.VISIBLE);
            }
        });

    }
}
