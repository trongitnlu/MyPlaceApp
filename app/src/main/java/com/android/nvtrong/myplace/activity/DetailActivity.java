package com.android.nvtrong.myplace.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.nvtrong.myplace.ActivityUltis;
import com.android.nvtrong.myplace.R;
import com.android.nvtrong.myplace.data.model.Place;
import com.android.nvtrong.myplace.data.model.PlaceDAO;

import butterknife.BindView;


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
    private int categoryID;
    private PlaceDAO placeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
    }

    private void init() {
        place_ID = getIntent().getIntExtra(ActivityUltis.PLACE_KEY_PUT_EXTRA, 0);
        categoryID = getIntent().getIntExtra(ActivityUltis.CATEGORY_KEY_EXTRA, 0);
        placeDAO = PlaceDAO.getInstance(this);

        Place place = placeDAO.getPlace(place_ID);
        if (place.getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(place.getImage(), 0, place.getImage().length);
            imageView.setImageBitmap(bitmap);
        }
        editTextPlaceName.setText(place.getName());
        editTextPlaceAddress.setText(place.getAddress());
        editTextDescription.setText(place.getDescription());
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imageButtonDelete:
                break;
            case R.id.imageButtonEdit:
                break;
            case R.id.imageButtonDirection:
                break;
            default:
                break;
        }
    }
}
