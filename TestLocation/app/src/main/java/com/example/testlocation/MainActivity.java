package com.example.testlocation;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks
,GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final int ALL_PERMISSIONS_RESULT = 1111;
    private GoogleApiClient client;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissions = new ArrayList<>();
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private TextView locationTextView;
    private LocationRequest locationRequest;
    public static final long UPDATE_INTERVAL = 5000;
    public static final long FASTEST_INTERVAL = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locationTextView = findViewById(R.id.location_text_view);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        //Lets add permissions we need to request locations of the users
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionsToRequest = permissionsToRequest(permissions);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(permissionsToRequest.size()>0){
                requestPermissions(permissionsToRequest.toArray(
                        new String[permissionsToRequest.size()]),
                        ALL_PERMISSIONS_RESULT
                );
            }
        }

        client = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();
        for(String perm: wantedPermissions){
            if(!hasPermission(perm)){
                result.add(perm);//if permission is not requested yet..
            }
        }
        return result;
    }

    private void checkPayServices(){
        //Here we will invoke some google play services classes
        //returns the single instance of GoogleApiAvailability(Singleton Class)
        int errorCode = GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(this);
        if(errorCode != ConnectionResult.SUCCESS){
            Dialog errorDialog = GoogleApiAvailability.getInstance()
                    .getErrorDialog(this, errorCode, errorCode, new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            Toast.makeText(MainActivity.this,"No Services",Toast.LENGTH_LONG)
                                    .show();
                            finish();
                        }
                    });
            errorDialog.show();
        } else {
            Toast.makeText(MainActivity.this,"All is good",Toast.LENGTH_LONG)
                    .show();
        }
    }

    private boolean hasPermission(String perm) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return checkSelfPermission(perm) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(client!=null){
            client.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        client.disconnect();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        checkPayServices();
    }

    @Override
    protected void onPause() {
        //on pause we are removing our client
        super.onPause();
        if(client != null && client.isConnected()){
            LocationServices.getFusedLocationProviderClient(this)
                    .removeLocationUpdates(new LocationCallback(){});
            client.disconnect();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        //here we get last known location. But it could be null
                        if(location != null){
                            Toast.makeText(MainActivity.this,"Finally",Toast.LENGTH_LONG).show();
                            locationTextView.setText(MessageFormat.format("Lat: {0} Lon: {1}", location.getLatitude(), location.getLongitude()));
                        }
                    }
                });
        startLocationUpdates(); //we have created this method
    }

    private void startLocationUpdates() {
        //LocationRequest objects are used to request a quality of service for location updates from the FusedLocationProviderApi.
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(MainActivity.this,
                    "You need to enable permission",Toast.LENGTH_LONG)
                    .show();
        }
        //Used for receiving notifications from the FusedLocationProviderApi
        // when the device location has changed or can no longer be determined.
        LocationServices.getFusedLocationProviderClient(MainActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback(){
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        //A data class representing a geographic location result from the fused location provider.
                        if(locationResult != null){
                            Location location = locationResult.getLastLocation();
                            locationTextView.setText(MessageFormat.format("Lat: {0} Lon: {1}",
                                    location.getLatitude(), location.getLongitude()));
                        }
                    }

                    @Override
                    public void onLocationAvailability(LocationAvailability locationAvailability) {
                        super.onLocationAvailability(locationAvailability);
                    }
                }, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case ALL_PERMISSIONS_RESULT:
                for(String perm: permissionsToRequest){
                    if(!hasPermission(perm)){
                        permissionsRejected.add(perm);
                    }
                }
                if(permissionsRejected.size()>0){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if(shouldShowRequestPermissionRationale(permissionsRejected.get(0))){
                            new AlertDialog.Builder(MainActivity.this)
                                    .setMessage("These Permissions are mandatory to get location")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                                requestPermissions(permissionsRejected.toArray(
                                                        new String[permissionsRejected.size()])
                                                , ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    }).setNegativeButton("CANCEL", null).create().show();
                        }
                    }
                } else {
                    if(client != null){
                        client.connect();
                    }
                }
                break;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null){
            locationTextView.setText(MessageFormat.format("Lat: {0} Lon: {1}",
                    location.getLatitude(), location.getLongitude()));
        }
    }
}
