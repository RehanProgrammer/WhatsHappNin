package com.example.dipapc.myfirebaseapp;

import android.*;
import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends MenuActivity implements OnMapReadyCallback{


    private static final String TAG = "MapActivity";
    private static final String FINE_L = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_L = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int REQUEST_PERMISSION_CODE = 1234;
    private static final float DEFAULT_ZOOM = 5f;

    private Boolean mLocationPermissionGranted = false;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference mref,cref,gref;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GoogleMap mMap;
    private Marker mym, arg;
    private Event event;
    private int counter;
    private HashMap<String,String> key;
    private String k;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        intMap();
        getLocationPermission();



    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        key = new HashMap<>();
        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
        getDeviceLocation();

        database = FirebaseDatabase.getInstance();
        mref = database.getReference();
        cref = mref.child("EventList");
        cref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {


                Event event = dataSnapshot.getValue(Event.class);
                String ti = event.getName();
                counter = event.getGuetNo();
                key.put(event.getName(),dataSnapshot.getKey().toString());
                //String si = event.getLoaction()+"\n"+event.getDescription();
                LatLng newLocation = new LatLng(
                        event.getLat(),event.getLon()
                );


                Log.d("Map:",dataSnapshot.toString()+dataSnapshot.getChildrenCount()+counter);
                // if it is a party event
                if(event.getEventtype().equals("party")){
                    mym = mMap.addMarker(new MarkerOptions()
                            .position(newLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.balloons))
                            .title(ti).snippet("address:"+event.getLoaction()+"\n"+"description:"+event.getDescription()+"\ndate:"
                                    +event.getEventdate()+"\ntime:"+event.getEventtime()+"\nGuest No:"+event.getGuetNo()));
                    //Log.d("Map:",dataSnapshot.toString()+dataSnapshot.getChildrenCount()+counter);
                }
                //if it is a sport event
                if(event.getEventtype().equals("sport")){
                    mym = mMap.addMarker(new MarkerOptions()
                            .position(newLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.sport))
                            .title(ti).snippet("address:"+event.getLoaction()+"\n"+"description:"+event.getDescription()+"\ndate:"
                                    +event.getEventdate()+"\ntime:"+event.getEventtime()+"\nGuest No:"+event.getGuetNo()));
                    //Log.d("Map:",dataSnapshot.toString()+dataSnapshot.getChildrenCount()+counter);
                }
                //if it is a study event
                if(event.getEventtype().equals("study")){
                    mym = mMap.addMarker(new MarkerOptions()
                            .position(newLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.book))
                            .title(ti).snippet("address:"+event.getLoaction()+"\n"+"description:"+event.getDescription()+"\ndate:"
                                    +event.getEventdate()+"\ntime:"+event.getEventtime()+"\nGuest No:"+event.getGuetNo()));
                    //Log.d("Map:",dataSnapshot.toString()+dataSnapshot.getChildrenCount()+counter);
                }
                //if it is any other type of event
                if(event.getEventtype().equals("other")){
                    mym = mMap.addMarker(new MarkerOptions()
                            .position(newLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.other))
                            .title(ti).snippet("address:"+event.getLoaction()+"\n"+"description:"+event.getDescription()+"\ndate:"
                                    +event.getEventdate()+"\ntime:"+event.getEventtime()+"\nGuest No:"+event.getGuetNo()));
                    Log.d("Map:",dataSnapshot.toString()+dataSnapshot.getChildrenCount()+counter);}



//


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {
                print(key);


                arg = arg0;
                if (key.containsKey(arg0.getTitle()))
                {
                    //suint g = counter++;
                    k = key.get(arg.getTitle());
                    Log.d("value:",k);


                }



                final Dialog dialog = new Dialog(MapsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("Title...");

                //set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.eventtitle);
                text.setText(arg0.getTitle());
                TextView text1 = (TextView) dialog.findViewById(R.id.eventdescription);
                text1.setText(arg0.getSnippet());
                Button joinButton = (Button) dialog.findViewById(R.id.buttonjoin);
                //if button is clicked, close the custom dialog
                joinButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int g = counter + 1;
                        Log.d("value:",k);

                        mref.child("EventList").child(k).child("guetNo").setValue(g);
                        // ev.setGuetNo(ev.getGuetNo()+1);



                        //Toast.makeText(MapsActivity.this,"The reference clicked"+s, Toast.LENGTH_SHORT).show();





                        dialog.dismiss();


                    }
                });
                dialog.show();
            }


            // Add a marker in Sydney and move the camera
            //LatLng sydney = new LatLng(-34, 151);


        });



    }

    public void intMap() {
        Log.d(TAG, "Initialized map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
        //second way of initializing map
       /* mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            //onMapReady(GoogleMap googleMap):will prepare our map
            public void onMapReady(GoogleMap googleMap) {

                mMap = googleMap;

            }
        });*/

    }

    // we need to explicitly get the permissions
    private void getLocationPermission() {
        //array of permissions
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_L) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_L) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                intMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION_CODE);

            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION_CODE);
        }
        Log.d(TAG, "Got location permission");


    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE: {
                if (grantResults.length > 0) {
                    //check if all the permissions are granted
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            Log.d(TAG, "Inside location permission result");
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    intMap();
                }

            }
        }
    }

    //get device location
    private void getDeviceLocation() {
        Log.d(TAG, "getting device location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete:Found location");
                            Location currentLocation = (Location) task.getResult();
                            double lat = currentLocation.getLatitude();
                            double lon = currentLocation.getLongitude();
                            LatLng ll = new LatLng(lat, lon);
                            moveCamera(ll, DEFAULT_ZOOM);


                            mMap.setMyLocationEnabled(true);






                            //move camera here
                        } else {
                            Log.d(TAG, "onComplete:Can not find location");
                            Toast.makeText(MapsActivity.this, "Unable to find location", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

        } catch (SecurityException e) {
            Log.e(TAG, "Get device location: Security Exception: " + e.getMessage());
        }
    }

    public void moveCamera(LatLng latLng, float zoom) {
        Log.d("TAG", "Moving the camera to lat:" + latLng.latitude + "lon:" + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        MarkerOptions mp = new MarkerOptions().position(latLng).title("My location").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        mMap.addMarker(mp);
    }

    public static void print(Map<String,String> map)
    {
        if (map.isEmpty())
        {
            System.out.println("map is empty");
        }

        else
        {
            Log.d("MAP", map.toString());
        }
    }

    public void addGuest(String s){

    }



}
