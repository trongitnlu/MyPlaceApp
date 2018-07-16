package com.android.nvtrong.myplace.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.nvtrong.myplace.ActivityUltis;
import com.android.nvtrong.myplace.R;
import com.android.nvtrong.myplace.data.Loading;
import com.android.nvtrong.myplace.data.google.GPSTracker;
import com.android.nvtrong.myplace.data.google.Location;
import com.android.nvtrong.myplace.data.model.Place;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener {
    private GoogleMap googleMap;
    private GoogleApiClient apiClient;
    private LocationRequest locationRequest;
    private Location currentLocation;
    private List<Place> places;
    private ImageView btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        init();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    Loading loading;

    private void init() {
        btnSearch = findViewById(R.id.imgbtnsearch);
        loading = Loading.create(this);
        loading.show();
        places = getIntent().getParcelableArrayListExtra(ActivityUltis.REQUEST_PUT_PLACE_EXTRA);
        btnSearch.setOnClickListener((view)-> ClickSearch(view));
    }

    private void buildApiClient() {
        apiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
        apiClient.connect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ActivityUltis.REQUEST_PERMISSIONS_GPS_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (apiClient == null) {
                            buildApiClient();
                        }
                        googleMap.setMyLocationEnabled(true);
                    } else {
                        Toast.makeText(this, "Permission Error!", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setOnMarkerClickListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildApiClient();
            this.googleMap.setMyLocationEnabled(true);
            getLocation();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ActivityUltis.REQUEST_PERMISSIONS_GPS_CODE);
            }
        }
        displayPlaceOnMaps();
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void displayPlaceOnMaps() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (places != null) {
                    showOnMaps(places);
                }
                loading.dismiss();
            }
        }, 1000);
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            GPSTracker gpsTracker = new GPSTracker(this);
            currentLocation = new Location(gpsTracker.getLatitude(), gpsTracker.getLongitude());
            if (apiClient != null) {
                gpsTracker.stopUsingGPS();
            }
            googleMap.addMarker(new MarkerOptions().position(new LatLng(currentLocation.getLat(), currentLocation.getLng())).title("My location"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLat(), currentLocation.getLng()), 17));
        }
    }

    Polyline polyline;

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    public void ClickSearch(View view) {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    MarkerOptions marker;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                com.google.android.gms.location.places.Place place = PlaceAutocomplete.getPlace(this, data);
                marker = new MarkerOptions().position(place.getLatLng()).title(place.getName().toString());
                Log.i("DDDDDD", "Place: " + place.getName());
                googleMap.addMarker(marker);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 17));
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i("DDDDDD", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    public void showOnMaps(List<Place> list) {
        for (Place place : list) {
            LatLng latLng = new LatLng(place.getPlaceLat(), place.getPlaceLng());
            MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                    .flat(true)
                    .snippet(place.getAddress())
                    .title(place.getName());
            googleMap.addMarker(markerOptions);
            if (list.size() == 1) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
            }
        }
    }

}
