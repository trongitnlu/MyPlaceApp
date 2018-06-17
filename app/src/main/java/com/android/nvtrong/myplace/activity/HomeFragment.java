package com.android.nvtrong.myplace.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.nvtrong.myplace.ActivityUltis;
import com.android.nvtrong.myplace.R;
import com.android.nvtrong.myplace.data.Loading;
import com.android.nvtrong.myplace.data.google.DirectionRoot;
import com.android.nvtrong.myplace.data.google.GPSTracker;
import com.android.nvtrong.myplace.data.google.Leg;
import com.android.nvtrong.myplace.data.google.Location;
import com.android.nvtrong.myplace.data.google.Route;
import com.android.nvtrong.myplace.data.model.Place;
import com.android.nvtrong.myplace.service.APIUltis;
import com.android.nvtrong.myplace.service.ServiceAPI;
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
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener {
    private GoogleMap googleMap;
    private GoogleApiClient apiClient;
    private LocationRequest locationRequest;
    private Location currentLocation;

    private List<Place> places;

    private final String MAP_TYPE_SEARCH_RESTAURANT = "restaurant";
    private final String MAP_TYPE_SEARCH_CINEMA = "movie_theater";
    private final String MAP_TYPE_SEARCH_FASHION = "shopping_mall";
    private final String MAP_TYPE_SEARCH_ATM = "atm";
    private final String MAP_SEARCH_RADIUS = "500";


    Loading loading;

    private void init() {
        loading = Loading.create(getContext());
        loading.show();
//        categoryID = getIntent().getIntExtra(ActivityUltis.CATEGORY_KEY_EXTRA, 0);
//        placeDAO = PlaceDAO.getInstance(this);
//        places = placeDAO.getListPlaceByCategoryID(categoryID);
        places = new ArrayList<>();
        Log.d("DDDDDDDDDDDDDDDD4", String.valueOf(places.size()));
    }

    private void buildApiClient() {
        apiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(), this)
                .build();
        apiClient.connect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ActivityUltis.REQUEST_PERMISSIONS_GPS_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (apiClient == null) {
                            buildApiClient();
                        }
                        googleMap.setMyLocationEnabled(true);
                        getLocation();
                    } else {
                        Toast.makeText(getContext(), "Permission Error!", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setOnMarkerClickListener(this);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
        GPSTracker gpsTracker1 = new GPSTracker(getContext());
        Log.d("DDDDDDDDDDD", gpsTracker1.getLatitude() + ", " + gpsTracker1.getLongitude());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            GPSTracker gpsTracker = new GPSTracker(getContext());
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
        Log.d("DDD_GetDirecting", "Please");
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
                    Toast.makeText(getContext(), "Not found address!", Toast.LENGTH_SHORT).show();
                } else {
                    String polylines = directionRoot.getRoutes().get(0).getOverview_polyline().getPoints();
                    Leg leg = directionRoot.getRoutes().get(0).getLegs().get(0);
                    Log.d("DDDDDDDDDD", polylines);
                    View view = getView().findViewById(R.id.viewGroup);
                    Snackbar.make(view, "Distance: " + leg.getDistance().getText() + "\nDuration: " + leg.getDuration().getText(), Snackbar.LENGTH_LONG).show();
                    List<LatLng> decodePath = PolyUtil.decode(polylines);
                    PolylineOptions polylineOptions = new PolylineOptions().addAll(decodePath);
                    polyline = googleMap.addPolyline(polylineOptions);
                    polyline.setColor(Color.BLUE);
                }
            }

            @Override
            public void onFailure(Call<DirectionRoot> call, Throwable t) {
                Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
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
                            .build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    MarkerOptions marker;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                com.google.android.gms.location.places.Place place = PlaceAutocomplete.getPlace(getContext(), data);
                marker = new MarkerOptions().position(place.getLatLng()).title(place.getName().toString());
                Log.i("DDDDDD", "Place: " + place.getName());
                googleMap.addMarker(marker);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 17));
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getContext(), data);
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
                LatLng currentLatLng = new LatLng(currentLocation.getLat(), currentLocation.getLng());
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                getDirection(latLng, currentLatLng);
            }
            Log.d("DDDDShowOnMap", place.toString() + "--" + list.size());
        }
        Log.d("DDDDShowOnMap", "Exit");

    }

    public static final String TITLE = "Home";

    public static HomeFragment newInstance() {

        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_maps, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if(mapFragment==null){
            Toast.makeText(getActivity(), "Map fail!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), "Map OK !", Toast.LENGTH_SHORT).show();
            mapFragment.getMapAsync(this);
        }
    }
}
