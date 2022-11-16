package com.example.ezapp3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FindChargingStationActivity extends AppCompatActivity
        implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback{

    String nowPlace;
    String placList[];
    String place[];
    final String[] addr = new String[1];
    LatLng latLng;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = "Check";

    private boolean permissionDenied = false;

    private GoogleMap map;

    private FusedLocationProviderClient fusedLocationClient;

    List<Place.Field> placeFields = Arrays.asList(
            Place.Field.NAME, Place.Field.ADDRESS, Place.Field.ID, Place.Field.LAT_LNG);

    FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);

    PlacesClient placesClient;

    Button APIbtn;

    //충전기 타입 다이얼로그
    ImageButton type_btn;
    List<String> mSelectedItems;
    AlertDialog.Builder typeDialog;
    boolean type_boolean[] = new boolean[6];

    BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_charging_station);

        ImageButton add_marker_btn = (ImageButton) findViewById(R.id.add_near_btn);
        type_btn = (ImageButton) findViewById(R.id.type_btn);
        APIbtn = findViewById(R.id.APIDatabtn);
        type_boolean[1] = true; type_boolean[2] = true;

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        type_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.favorites_button:
//                        Intent intent1 = new Intent(getApplicationContext(), com.example.ezapp3.FindChargingStationActivity.class);
//                        startActivity(intent1);
                        Toast.makeText(getApplicationContext(), "favorites",
                                Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.home_button:
                        Intent intent2 = new Intent(getApplicationContext(), com.example.ezapp3.MainActivity.class);
                        startActivity(intent2);
//                        Toast.makeText(getApplicationContext(), "홈 화면 입니다.",
//                                Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.profile_button:
                        Intent intent3 = new Intent(getApplicationContext(), com.example.ezapp3.ElectricVehicleTips.class);
                        startActivity(intent3);
//                        Toast.makeText(getApplicationContext(), "정보 화면 입니다.",
//                                Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Places.initialize(this, BuildConfig.MAPS_API_KEY);
        placesClient = Places.createClient(this);

        add_marker_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_around_charging_station();
            }
        });

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(placeFields);
//        autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS);
//        autocompleteFragment.setLocationRestriction(RectangularBounds.newInstance(
//                new LatLng(39, 124),
//                new LatLng(33, 132)));
        autocompleteFragment.setCountries("KR");

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 17));
                Toast.makeText(getApplicationContext(),
                        String.format("Place '%s', address '%s', Id '%s'",
                                place.getName(), place.getAddress(), place.getId()),
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        APIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), APISempleActivity.class);
                startActivity(intent);
            }
        });

    }

    public void showDialog(){

        mSelectedItems = new ArrayList<String>();
        typeDialog = new AlertDialog.Builder(this);
        typeDialog.setTitle("타입을 선택해 주세요");
        typeDialog.setMultiChoiceItems(R.array.types, type_boolean, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                String[] items = getResources().getStringArray(R.array.types);
                if(isChecked){
                    mSelectedItems.add(items[which]);
                    type_boolean[which] = true;
                }else if(mSelectedItems.contains(items[which])){
                    mSelectedItems.remove(items[which]);
                    type_boolean[which] = false;
                }
            }
        });

        typeDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                for(int j = 0; j < 6; j++){
//                    Toast.makeText(getApplicationContext(), j+" "+b[j], Toast.LENGTH_SHORT).show();
//                }

//                for(String item : mSelectedItems){
//                    final_selection = final_selection + "\n" + item;
//                }
            }
        });

        typeDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = typeDialog.create();
        alertDialog.show();
    }

    public void search_around_charging_station(){
        APITask apiTask = new APITask();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            @SuppressLint("MissingPermission") Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);
            placeResponse.addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    FindCurrentPlaceResponse response = task.getResult();
                    for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                        /*Toast.makeText(this, String.format
                                ("Place '%s', address '%s', Id '%s' has likelihood: %f",
                                placeLikelihood.getPlace().getName(),
                                placeLikelihood.getPlace().getAddress(),
                                placeLikelihood.getPlace().getId(),
                                placeLikelihood.getLikelihood()), Toast.LENGTH_SHORT)
                                .show();
                        Log.i(TAG, String.format("Place '%s' has likelihood: %f",
                                placeLikelihood.getPlace().getName(),
                                placeLikelihood.getLikelihood()));*/
                        addr[0] = placeLikelihood.getPlace().getAddress();
                        apiTask.setNowPlace(addr[0]);
                        break;
                    }
                } else {
                    Exception exception = task.getException();
                    if (exception instanceof ApiException) {
                        ApiException apiException = (ApiException) exception;
                        Log.e(TAG, "Place not found: " + apiException.getStatusCode());
                    }
                }
            });
        } else {
            // A local method to request required permissions;
            // See https://developer.android.com/training/permissions/requesting
//            getLocationPermission();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

                try {
                    nowPlace = apiTask.getAPIData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        placList = nowPlace.split("\n\n\n");
                        //0 ~ 6 :  충전소명, 충전소 타입, 주소, let, lng, 이용시간, 충전기상태, 상태갱신
                        for(int i = 0; i < placList.length; i++){
                            place = placList[i].split("\n\n");
                            latLng = new LatLng(Double.parseDouble(place[3]), Double.parseDouble(place[4]));
                            Log.i("MyTag", place[0]);
                            add_marker_to_map(latLng, place[0], placList[i]);
                        }

                    }
                });
            }
        }).start();

    }

    public void add_marker_to_map(LatLng marker_location, String title, String snippet) {
        this.map.addMarker(new MarkerOptions()
                .position(marker_location)
                .title(title)
                .snippet(snippet));
//        melbourne.showInfoWindow();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        // TODO: Before enabling the My Location layer, you must request
        // location permission from the user. This sample does not include
        // a request for location permission.
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        map.setOnMarkerClickListener(this);
        enableMyLocation();

        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(true);
        map.getUiSettings().setZoomGesturesEnabled(true);
        map.getUiSettings().setScrollGesturesEnabled(true);


        map.getUiSettings().setTiltGesturesEnabled(false);
        map.getUiSettings().setRotateGesturesEnabled(false);

        CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(this);
        map.setInfoWindowAdapter(adapter);
    }

    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED
        || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);

//            moved_last_location();
            moved_last_location();
            search_current_place();

            return;
        }
        PermissionUtils.requestLocationPermissions(this, LOCATION_PERMISSION_REQUEST_CODE, true);

    }

    /*
    *   가장 최근의 위치 받아오는 함수
     */

    @SuppressLint("MissingPermission")
    private void moved_last_location(){
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            LatLng myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//                                Log.d("location", "AAA" +location.toString());
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 17));
                            // Logic to handle location object
                        }
                        else{
                            search_current_place();
                        }
                    }
                });
    }

/*
*   최신의 현재위치 받아오는 함수
 */
    @SuppressLint("MissingPermission")
    private void search_current_place(){
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(300)
                .setFastestInterval(200);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if(locationResult == null)
                    return;
                for(Location myLocation:locationResult.getLocations()){
                    LatLng myLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 17));
                    fusedLocationClient.removeLocationUpdates(this);
                }
            }
        };
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION) || PermissionUtils
                .isPermissionGranted(permissions, grantResults,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // [START_EXCLUDE]
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
            // [END_EXCLUDE]
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Toast.makeText(this, "Marker button clicked"
                + marker.getId() + marker.getPosition(), Toast.LENGTH_SHORT)
                .show();

        return false;
    }

    /*
    InfoWindowAdapter
    GoogleMap.setInfoWindowAdapter
    getInfoWindow
    getInfoContents
     */

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT)
                .show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG)
                .show();
    }

}