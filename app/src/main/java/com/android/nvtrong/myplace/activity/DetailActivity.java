package com.android.nvtrong.myplace.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.nvtrong.myplace.ActivityUltis;
import com.android.nvtrong.myplace.R;
import com.android.nvtrong.myplace.data.Loading;
import com.android.nvtrong.myplace.data.model.Place;
import com.android.nvtrong.myplace.data.model.PlaceDAO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.editTextPlaceName)
    EditText editTextPlaceName;
    @BindView(R.id.editTextPlaceAddress)
    EditText editTextPlaceAddress;
    @BindView(R.id.editTextDescription)
    EditText editTextDescription;
    @BindView(R.id.imageView)
    ImageView imageView;

    private int place_ID;
    private PlaceDAO placeDAO;
    Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        getPlaceParcel();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportParentActivityIntent();
    }

    private void getPlaceParcel() {
        place = getIntent().getParcelableExtra(ActivityUltis.REQUEST_PUT_PLACE_EXTRA);
        if (place != null) {
            loadPlace(place);
        }
    }

    private void loadPlace(Place place) {
        placeDAO = PlaceDAO.getInstance(this);
        Picasso.get().load(place.getUrlIcon()).placeholder(R.drawable.location).error(R.drawable.logo).into(imageView);

        editTextPlaceName.setText(place.getName());
        editTextPlaceAddress.setText(place.getAddress());
        editTextDescription.setText(place.getDescription());
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imageButtonDirection:
                onDirection(place);
                break;
            case R.id.btn_save:
                savePlace(place);
                break;
            default:
                break;
        }
    }

    private void savePlace(Place place) {
        PlaceDAO dao = PlaceDAO.getInstance(this);
        Loading loading = Loading.create(this);
        loading.show();
        boolean result = dao.insert(place);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (result) {
                    Toast.makeText(DetailActivity.this, "Successful! ", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(DetailActivity.this, "Location already exists! ", Toast.LENGTH_SHORT).show();

                }
                loading.dismiss();
            }
        }, 1000);

    }

    private void onDirection(Place place) {
        Intent intent = new Intent(this, MapsActivity.class);
        ArrayList<Place> places = new ArrayList<>();
        places.add(place);
        intent.putExtra(ActivityUltis.REQUEST_PUT_PLACE_EXTRA, places);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
