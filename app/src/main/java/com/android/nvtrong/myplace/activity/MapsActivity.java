package com.android.nvtrong.myplace.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.nvtrong.myplace.ActivityUltis;
import com.android.nvtrong.myplace.R;
import com.android.nvtrong.myplace.data.Loading;
import com.android.nvtrong.myplace.data.google.DirectionRoot;
import com.android.nvtrong.myplace.data.google.GPSTracker;
import com.android.nvtrong.myplace.data.google.GeocodingRoot;
import com.android.nvtrong.myplace.data.google.Geometry;
import com.android.nvtrong.myplace.data.google.Leg;
import com.android.nvtrong.myplace.data.google.Location;
import com.android.nvtrong.myplace.data.google.Result;
import com.android.nvtrong.myplace.data.google.Route;
import com.android.nvtrong.myplace.data.model.Place;
import com.android.nvtrong.myplace.data.model.PlaceDAO;
import com.android.nvtrong.myplace.service.APIUltis;
import com.android.nvtrong.myplace.service.ServiceAPI;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener {
    private GoogleMap googleMap;
    private GoogleApiClient apiClient;
    private LocationRequest locationRequest;
    private Location currentLocation;

    private PlaceDAO placeDAO;
    private int categoryID;
    private List<Place> places;

    private final String MAP_TYPE_SEARCH_RESTAURANT = "restaurant";
    private final String MAP_TYPE_SEARCH_CINEMA = "movie_theater";
    private final String MAP_TYPE_SEARCH_FASHION = "shopping_mall";
    private final String MAP_TYPE_SEARCH_ATM = "atm";
    private final String MAP_SEARCH_RADIUS = "500";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        init();
        initNavigationView();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    DrawerLayout mDrawerLayout;

    private void initNavigationView() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        onOptionsItemSelected(menuItem);
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
    }

    Loading loading;

    private void init() {
        loading = Loading.create(this);
        loading.show();
        categoryID = getIntent().getIntExtra(ActivityUltis.CATEGORY_KEY_EXTRA, 0);
        placeDAO = PlaceDAO.getInstance(this);
        places = placeDAO.getListPlaceByCategoryID(categoryID);
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
        displayPlaceOnMaps();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildApiClient();
            this.googleMap.setMyLocationEnabled(true);
            getLocation();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ActivityUltis.REQUEST_PERMISSIONS_GPS_CODE);
            }
        }
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
                for (Place e : places) {
                    LatLng placeLatLng = new LatLng(e.getPlaceLat(), e.getPlaceLng());
                    googleMap.addMarker(new MarkerOptions().position(placeLatLng).title(e.getName()));
//                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placeLatLng, 10));
                    loading.dismiss();
                }
            }
        }, 2000);
    }

    private void getLocation() {
        GPSTracker gpsTracker1 = new GPSTracker(this);
        Log.d("DDDDDDDDDDD", gpsTracker1.getLatitude() + ", " + gpsTracker1.getLongitude());
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

    private void getDirection(LatLng origin, LatLng destination) {
        ServiceAPI serviceAPI = APIUltis.getData();
        String originAddress = String.valueOf(origin.latitude) + "," + String.valueOf(origin.longitude);
        String destinationAddress = String.valueOf(destination.latitude) + "," + String.valueOf(destination.longitude);
        Call<DirectionRoot> rootCall = serviceAPI.getDirection(originAddress, destinationAddress);
        rootCall.enqueue(new Callback<DirectionRoot>() {
            @Override
            public void onResponse(Call<DirectionRoot> call, Response<DirectionRoot> response) {
                DirectionRoot directionRoot = response.body();
                List<Route> routes = directionRoot.getRoutes();
                if (routes == null || routes.isEmpty()) {
                    Toast.makeText(MapsActivity.this, "Not found address!", Toast.LENGTH_SHORT).show();
                } else {
                    String polylines = directionRoot.getRoutes().get(0).getOverview_polyline().getPoints();
                    Leg leg = directionRoot.getRoutes().get(0).getLegs().get(0);
                    Log.d("DDDDDDDDDD", polylines);
                    View view = findViewById(R.id.viewGroup);
                    Snackbar.make(view, "Distance: " + leg.getDistance().getText() + "\nDuration: " + leg.getDuration().getText(), Snackbar.LENGTH_LONG).show();
                    List<LatLng> decodePath = PolyUtil.decode(polylines);
                    PolylineOptions polylineOptions = new PolylineOptions().addAll(decodePath);
                    polyline = googleMap.addPolyline(polylineOptions);
                    polyline.setColor(Color.BLUE);
                    polyline.setGeodesic(true);
                }
            }

            @Override
            public void onFailure(Call<DirectionRoot> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (polyline != null) {
            polyline.remove();
        }
        LatLng currentLatLng = new LatLng(currentLocation.getLat(), currentLocation.getLng());
        getDirection(currentLatLng, marker.getPosition());
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.restaurant:
                showListPlaceGoogleMaps(MAP_TYPE_SEARCH_RESTAURANT, R.drawable.restaurant_market);
                break;
            case R.id.fashion:
                showListPlaceGoogleMaps(MAP_TYPE_SEARCH_FASHION, R.drawable.shopping_market);
                break;
            case R.id.cinema:
                showListPlaceGoogleMaps(MAP_TYPE_SEARCH_CINEMA, R.drawable.movies_market);
                break;
            case R.id.atm:
                showListPlaceGoogleMaps(MAP_TYPE_SEARCH_ATM, R.drawable.atm_market);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_navigation_items, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void showListPlaceGoogleMaps(String typeName, final int resource) {
        final Loading loading = Loading.create(this);
        loading.show();
        ServiceAPI serviceAPI = APIUltis.getData();
        GPSTracker gpsTracker = new GPSTracker(this);
        String key = getResources().getString(R.string.google_api_key);
        Call<GeocodingRoot> rootCall = serviceAPI.getLocationByType(gpsTracker.getStringLocation(), MAP_SEARCH_RADIUS, typeName, key);
        Log.d("DDDDDDDDDDDD3", gpsTracker.getStringLocation());
        rootCall.enqueue(new Callback<GeocodingRoot>() {
            @Override
            public void onResponse(Call<GeocodingRoot> call, Response<GeocodingRoot> response) {
                GeocodingRoot geocodingRoot = response.body();
                List<Result> results = geocodingRoot.getResults();
                if (results == null || results.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Not Found!", Toast.LENGTH_SHORT).show();
                } else {
                    for (Result result : results) {
                        Geometry geometry = result.getGeometry();
                        LatLng latLng = new LatLng(geometry.getLocation().getLat(), geometry.getLocation().getLng());
                        MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                                .flat(true)
                                .icon(BitmapDescriptorFactory.fromResource(resource))
                                .snippet(result.getVicinity())
                                .title(result.getName());
                        Marker marker = googleMap.addMarker(markerOptions);
                        Log.d("DDDDDDDDDD1", result.toString());
                    }
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<GeocodingRoot> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                Log.d("DDDDDDDDDDDDD2", t.toString());
                loading.dismiss();
            }
        });

    }

}
