package com.example.mymapsapp;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "Maps";
    private GoogleMap mMap;
    private LatLng mountEverest = new LatLng(28.001377, 86.928129);
    private LatLng mountKilimanjaro = new LatLng(-3.075558, 37.344363);
    private LatLng theAlps = new LatLng(47.368955, 9.702579);

    private Marker everestMarker;
    private Marker kelimanjaroMarker;
    private Marker theAlpsMarker;

    private ArrayList<Marker> markerArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        markerArrayList = new ArrayList<>();

        // Add a marker in Sydney and move the camera
        kelimanjaroMarker = mMap.addMarker(new MarkerOptions()
                .position(mountKilimanjaro)
                .title("Mt Kelimanjaro")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        markerArrayList.add(kelimanjaroMarker);

        theAlpsMarker = mMap.addMarker(new MarkerOptions()
                .position(theAlps)
                .title("The Alps")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        markerArrayList.add(theAlpsMarker);

        everestMarker = mMap.addMarker(new MarkerOptions()
                .position(mountEverest)
                .title("Mt. Everest")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        markerArrayList.add(everestMarker);


        for(Marker marker: markerArrayList) {
            LatLng latLng = new LatLng(marker.getPosition().latitude,marker.getPosition().longitude);
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
//            Log.d(TAG, "onMapReady: "+marker.getTitle());
        }

//        LatLng gwalior = new LatLng(26,78);
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.addMarker(new MarkerOptions().position(gwalior).title("Marker in Gwalior")
//        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).alpha(0.5f));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(gwalior));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gwalior,8));
    }
}
