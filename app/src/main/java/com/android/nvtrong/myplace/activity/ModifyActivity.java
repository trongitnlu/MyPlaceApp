package com.android.nvtrong.myplace.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.nvtrong.myplace.ActivityUltis;
import com.android.nvtrong.myplace.R;
import com.android.nvtrong.myplace.data.model.Place;
import com.android.nvtrong.myplace.data.model.PlaceDAO;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModifyActivity extends AppCompatActivity {
    @BindView(R.id.editTextPlaceName)
    EditText editTextPlaceName;
    @BindView(R.id.editTextPlaceAddress)
    EditText editTextPlaceAddress;
    @BindView(R.id.editTextDescription)
    EditText editTextDescription;
    @BindView(R.id.imageView)
    ImageView imageView;


    PlaceDAO placeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        ButterKnife.bind(this);
        init();
    }

    Place place;

    private void init() {
        placeDAO = PlaceDAO.getInstance(this);
        int place_id = getIntent().getIntExtra(ActivityUltis.PLACE_KEY_PUT_EXTRA, 0);
        place = placeDAO.getPlace(place_id);
        editTextPlaceName.setText(place.getName());
        editTextPlaceAddress.setText(place.getAddress());
        editTextDescription.setText(place.getDescription());
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imageView:
                break;
            case R.id.buttonSubmit:
                updatePlace(place);
                break;
            default:
                break;
        }
    }

    private void updatePlace(Place place) {
        place.setName(editTextPlaceName.getText().toString());
        place.setAddress(editTextPlaceAddress.getText().toString());
        place.setDescription(editTextDescription.getText().toString());
        placeDAO.update(place);
        setResult(RESULT_OK,getIntent());
        finish();
    }
}
