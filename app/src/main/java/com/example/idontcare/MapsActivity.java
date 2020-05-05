package com.example.idontcare;

import androidx.fragment.app.FragmentActivity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Collections;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap; // map object
    private FusedLocationProviderClient fusedLocationClient; // location service (needed to find last location of device)
    private static final String TAG = "MapsActivity"; // debug tag
    private Location userHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String apiKey = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

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
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        userHome = location;
                    }
                }
            });

            /* Initialized places API to find businesses */
            Places.initialize(getApplicationContext(), apiKey);
            PlacesClient placesClient = Places.createClient(this);

            List<Place.Field> placeFields = Collections.singletonList(Place.Field.NAME);

            /* Obtain the SupportMapFragment and get notified when the map is ready to be used. */
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (userHome == null) {
            Toast toast = Toast.makeText(getApplicationContext(), "Error finding user's last location.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        // Add a marker in Sydney and move the camera
        LatLng home = new LatLng(userHome.getLatitude(), userHome.getLongitude());
        LatLng sydney = new LatLng(-34, 151);
        /*mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        mMap.moveCamera(CameraUpdateFactory.newLatLng(home));
    }
}
