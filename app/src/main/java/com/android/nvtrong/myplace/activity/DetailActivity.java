package com.android.nvtrong.myplace.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private int categoryID;
    private PlaceDAO placeDAO;
    Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_a);
        ButterKnife.bind(this);
        getPlaceParcel();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportParentActivityIntent();
//        init();
    }

    private void getPlaceParcel() {
        place = getIntent().getParcelableExtra(ActivityUltis.REQUEST_PUT_PLACE_EXTRA);
        if (place != null) {
            Log.d("DDDDDDDD", place.toString());
            loadPlace(place);
        }
    }

    private void init() {
        place_ID = getIntent().getIntExtra(ActivityUltis.PLACE_KEY_PUT_EXTRA, 0);
        categoryID = getIntent().getIntExtra(ActivityUltis.CATEGORY_KEY_EXTRA, 0);
        loadPlace(place_ID);
    }

    private void loadPlace(int place_ID) {
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
            case R.id.imageButtonDelete:
                onDelete();
                break;
            case R.id.imageButtonEdit:
                onEdit();
                break;
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

    private void onDelete() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setCancelable(true)
                .setMessage(getResources().getString(R.string.message_delete))
                .setIcon(R.drawable.warning)
                .setPositiveButton(getResources().getString(R.string.text_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        placeDAO.delete(place_ID);
                        setResult(RESULT_OK, getIntent());
                        finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.text_negative), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        builder.show();
    }

    private void onEdit() {
        Intent intent = new Intent(this, ModifyActivity.class);
        intent.putExtra(ActivityUltis.PLACE_KEY_PUT_EXTRA, place_ID);
        intent.putExtra(ActivityUltis.REQUEST_TYPE, ActivityUltis.TYPE_UPDATE);
        startActivityForResult(intent, ActivityUltis.RESULT_MODIFY_EXTRA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityUltis.RESULT_MODIFY_EXTRA && resultCode == RESULT_OK && data != null) {
            RESULT_MODIFY = resultCode;
            loadPlace(place_ID);
        }
    }

    private int RESULT_MODIFY = 0;

    @Override
    public void onBackPressed() {
        setResult(RESULT_MODIFY, getIntent());
        finish();
    }
}
