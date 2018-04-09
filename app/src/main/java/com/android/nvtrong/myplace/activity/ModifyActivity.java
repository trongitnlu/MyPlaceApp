package com.android.nvtrong.myplace.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.nvtrong.myplace.ActivityUltis;
import com.android.nvtrong.myplace.R;
import com.android.nvtrong.myplace.data.model.Place;
import com.android.nvtrong.myplace.data.model.PlaceDAO;

import java.io.ByteArrayOutputStream;

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
        Intent intent = getIntent();
        if (ActivityUltis.TYPE_UPDATE.equals(intent.getStringExtra(ActivityUltis.REQUEST_TYPE))) {
            placeDAO = PlaceDAO.getInstance(this);
            int place_id = getIntent().getIntExtra(ActivityUltis.PLACE_KEY_PUT_EXTRA, 0);
            place = placeDAO.getPlace(place_id);
            if (place != null) {
                editTextPlaceName.setText(place.getName());
                editTextPlaceAddress.setText(place.getAddress());
                editTextDescription.setText(place.getDescription());
                if (place.getImage() != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(place.getImage(), 0, place.getImage().length);
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imageView:
                openCamera();
                break;
            case R.id.buttonSubmit:
                if (getIntent().getStringExtra(ActivityUltis.REQUEST_TYPE).equalsIgnoreCase(ActivityUltis.TYPE_UPDATE)) {
                    updatePlace(place);
                } else {
                    savePlace();
                }
                break;
            default:
                break;
        }
    }

    private void updatePlace(Place place) {
        place.setName(editTextPlaceName.getText().toString());
        place.setAddress(editTextPlaceAddress.getText().toString());
        place.setDescription(editTextDescription.getText().toString());
        place.setImage(convertBitmapToByte(bitmap));
        placeDAO.update(place);
        setResult(RESULT_OK, getIntent());
        finish();
    }

    private void savePlace() {
        String name = editTextPlaceName.getText().toString();
        String address = editTextPlaceAddress.getText().toString();
        String description = editTextDescription.getText().toString();
        int category_id = getIntent().getIntExtra(ActivityUltis.CATEGORY_KEY_EXTRA, 0);
        if (Place.validate(name, address, description)) {
            Place place = new Place.Builder()
                    .setName(name)
                    .setAddress(address)
                    .setDescription(description)
                    .setCategoryID(category_id)
                    .setImage(convertBitmapToByte(bitmap))
                    .build();
            placeDAO = PlaceDAO.getInstance(this);
            placeDAO.insert(place);
            Toast.makeText(ModifyActivity.this, "Save success!", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK, getIntent());
            finish();
        } else {
            View view = findViewById(R.id.contex_view);
            Snackbar.make(view, "Please fill in place's information!", Snackbar.LENGTH_SHORT).show();
        }

    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, ActivityUltis.REQUEST_IMAGE_CAPTURE);

    }

    Bitmap bitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ActivityUltis.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Please take a picture", Toast.LENGTH_SHORT).show();
            } else {
                bitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private byte[] convertBitmapToByte(Bitmap bitmap) {
        byte[] byteArray = null;
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        }
        return byteArray;
    }
}
