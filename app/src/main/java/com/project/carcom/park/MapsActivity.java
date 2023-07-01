package com.project.carcom.park;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.carcom.R;

/**
 *  MapsActivity class handle map display, zoom-in and location of parked car in google map.
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Marker marker;

    /**
     * This method focus map camera at current latitude and longitude.
     */
    void focus() {
        float zoomLevel = 10.0f;
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Carcom/prav");
        myRef.child("Lat").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Long value = snapshot.getValue(Long.class);
                    if (value != null) {
                        LatLng tarLatLng = new LatLng(value, marker.getPosition().longitude);
                        marker.setPosition(tarLatLng);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tarLatLng, zoomLevel));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(tarLatLng, zoomLevel));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(tarLatLng));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        myRef.child("Long").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Long value = snapshot.getValue(Long.class);
                    if (value != null) {
                        LatLng tarLatLng = new LatLng(marker.getPosition().latitude, value);
                        marker.setPosition(tarLatLng);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tarLatLng, zoomLevel));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(tarLatLng, zoomLevel));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        FloatingActionButton fabMap = findViewById(R.id.fabMap);
        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                focus();
            }
        });

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
        marker = mMap.addMarker(new MarkerOptions().position(new LatLng(38, 97))
                .flat(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_1)));
        LatLng tarLatLng = new LatLng(38, 97);
        marker.setPosition(tarLatLng);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tarLatLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(tarLatLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tarLatLng));
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
        }
        focus();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Carcom/prav");
        myRef.child("Lat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                marker.setPosition(new LatLng(snapshot.getValue(Long.class).intValue(), marker.getPosition().longitude));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 10.0f));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 10.0f));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        myRef.child("Long").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                marker.setPosition(new LatLng(marker.getPosition().latitude, snapshot.getValue(Long.class).intValue()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 10.0f));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 10.0f));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
