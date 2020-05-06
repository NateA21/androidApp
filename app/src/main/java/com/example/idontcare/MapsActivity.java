package com.example.idontcare;

import android.Manifest;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 100; // Codes for permission requests
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 200;
    private static final int PERMISSIONS_REQUEST_INTERNET = 300;
    private static final String KEY_CAMERA_POSITION = "camera_position"; // Keys for saving camera position
    private static final String KEY_LOCATION = "location";
    private GoogleMap mMap; // map object
    private FusedLocationProviderClient fusedLocationClient; // location service (needed to find last location of device)
    private PlacesClient placesClient; // place initialization
    private static final String TAG = "MapsActivity"; // debug tag
    private Location userHome;
    private Parcelable cameraPos;
    boolean cPermissionGranted;
    boolean fPermissionGranted;
    boolean iPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String apiKey = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        /* Try to retrieve previous map position */
        if (savedInstanceState != null) {
            userHome = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPos = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        /* Try to retrieve Google Maps API Key from the manifest */
        try {
            ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            apiKey = bundle.getString("com.google.android.geo.API_KEY");
        } catch(PackageManager.NameNotFoundException e) {
            Log.e(TAG, "apiKey not properly configured in manifest.");
        }

        /* Attempt to use the key to retrieve the user's current location. */
        if (apiKey == null) {
            Log.e(TAG, "apiKey returned null.");
        } else {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    userHome = location;
                }
            });

            /* Initialized places API to find businesses */
            Places.initialize(getApplicationContext(), apiKey);
            placesClient = Places.createClient(this);

            /* Obtain the SupportMapFragment and get notified when the map is ready to be used. */
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     * Permissions are requested, device location
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        boolean success = googleMap.setMapStyle(new MapStyleOptions(getResources()
                .getString(R.string.style_json)));

        if (!success) {
            Log.e(TAG, "Style parsing failed.");
        }

        mMap = googleMap;

        updateLocationUI();
        getDeviceLocation();
        mMap.setOnPoiClickListener(this::onPoiClick);
    }

    /* Saves state when the activity pauses. */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, userHome);
            super.onSaveInstanceState(outState);
        }
    }

    /* Requests necessary permissions from the user to access the internet and collect current device location */
    private void requestNecessaryPermissions() {
        if (ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION }, PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        } else {
            cPermissionGranted = true;
        }

        if (ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            fPermissionGranted = true;
        }

        if (ContextCompat.checkSelfPermission( this, Manifest.permission.INTERNET ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] { Manifest.permission.INTERNET }, PERMISSIONS_REQUEST_INTERNET);
        } else {
            iPermissionGranted = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        fPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fPermissionGranted = true;
                }
            }
            case PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cPermissionGranted = true;
                }
            }
            case PERMISSIONS_REQUEST_INTERNET: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    iPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (fPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                userHome = null;
                requestNecessaryPermissions();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /*
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     */
    private void getDeviceLocation() {
        try {
            if (fPermissionGranted) {
                Task locationResult = fusedLocationClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            userHome = (Location) task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(userHome.getLatitude(),
                                            userHome.getLongitude()), 16)); /* 10 is the default zoom */
                        } else {
                            Log.d(TAG, "Current location is null.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, 10));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    public void onPoiClick(PointOfInterest poi) {
        Toast.makeText(getApplicationContext(), "Clicked: " +
                        poi.name + "\nPlace ID:" + poi.name +
                        "\nLatitude:" + poi.latLng.latitude +
                        " Longitude:" + poi.latLng.longitude,
                Toast.LENGTH_SHORT).show();

        /*mMap.addMarker(new MarkerOptions()
                .title(poi.name)
                .position(poi.latLng));*/
    }
}
