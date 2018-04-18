package com.example.saikeerthana.saikeerthijp;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocationMarker;
    public static final int PERMISSION_REQUEST_LOCATION_CODE=99;
    double srclat,srclng;
    double destlat,destlng;
    long ontime;
    String url;
    String bankname;
    String branchname;
    String intime;
    String address;
    String bankaddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case PERMISSION_REQUEST_LOCATION_CODE:
                if(grantResults.length >0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    //permission is granted
                    if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                    {
                        if(client==null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    //permission is not granted
                    Toast.makeText(this,"Permission denied!!",Toast.LENGTH_LONG).show();
                }
                break;

            //case R.id.btn_time:

            //break;
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    public void onClick(View v) throws Exception
    {

        if(v.getId()==R.id.btnmap)
        {
            Toast.makeText(MapsActivity.this, "button clicked", Toast.LENGTH_SHORT).show();

            bankname=getIntent().getExtras().getString("bankname");
            branchname=getIntent().getExtras().getString("branchname");
            Toast.makeText(MapsActivity.this,bankname, Toast.LENGTH_SHORT).show();
            intime=getIntent().getExtras().getString("time");
            address=getIntent().getExtras().getString("userloc");
            Toast.makeText(MapsActivity.this,intime, Toast.LENGTH_SHORT).show();
            bankaddr=bankname+","+branchname;
            Toast.makeText(MapsActivity.this,bankaddr, Toast.LENGTH_SHORT).show();


            List<Address> addressList=null;
            MarkerOptions mo=new MarkerOptions();
            MarkerOptions m=new MarkerOptions();

            Toast.makeText(MapsActivity.this,"entering address", Toast.LENGTH_SHORT).show();
            float results[]=new float[10];
            if(!address.equals(""))
            {
                Toast.makeText(MapsActivity.this,"entering user address", Toast.LENGTH_SHORT).show();
                Geocoder geocoder=new Geocoder(this);
                try {
                    addressList=geocoder.getFromLocationName(address,5);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(addressList!=null);

                for (int i=0;i<addressList.size();i++)
                {
                    Address myaddress=addressList.get(i);
                    LatLng latLng=new LatLng(myaddress.getLatitude(),myaddress.getLongitude());
                    mo.position(latLng);
                    mMap.addMarker(mo);
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                }
            }

            if(!bankaddr.equals(""))
            {
                Toast.makeText(MapsActivity.this,"entering bank addr", Toast.LENGTH_SHORT).show();
                Geocoder geocoder=new Geocoder(this);
                try {
                    addressList=geocoder.getFromLocationName(bankaddr,5);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int i=0;i<addressList.size();i++)
                {
                    Address myaddress=addressList.get(i);
                    LatLng latLng=new LatLng(myaddress.getLatitude(),myaddress.getLongitude());
                    m.position(latLng);
                    m.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                    mMap.addMarker(m);
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
            srclat=mo.getPosition().latitude;
            srclng=mo.getPosition().longitude;
            Toast.makeText(MapsActivity.this,srclat+","+srclng, Toast.LENGTH_SHORT).show();

            destlat=m.getPosition().latitude;
            destlng=m.getPosition().longitude;
            Toast.makeText(MapsActivity.this,destlat+","+destlng, Toast.LENGTH_SHORT).show();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = sdf.parse(intime);
            ontime = date.getTime();
            System.out.println("time:"+ontime);




            //TextView tv=(TextView)findViewById(R.id.tvqw);


           /*Location.distanceBetween(srclat,srclng,destlat,destlng,results);
           MarkerOptions mar=new MarkerOptions().position(new LatLng(destlat,destlng)).title("distance").snippet(String.valueOf(results[0]));
           mMap.addMarker(mar);
           tv.setText("tvqwe : "+results[0]);*/


            //input.setText("tvinput:" +intime);

            Object dataTransfer[]=new Object[3];
            url=getDirectionsUrl();
            getDirectionsData getDirectionsData=new getDirectionsData();
            dataTransfer[0]=mMap;
            dataTransfer[1]=url;
            dataTransfer[2]=new LatLng(destlat,destlng);
            //dataTransfer[3]=ontime;

            getDirectionsData.execute(dataTransfer);


        }

    }

    private String getDirectionsUrl()
    {

        StringBuilder googleDirectionsUrl=new StringBuilder("https://maps.googleapis.com/maps/api/distancematrix/json?");
        googleDirectionsUrl.append("origins="+srclat+","+srclng);
        googleDirectionsUrl.append("&destinations="+destlat+","+destlng);
        googleDirectionsUrl.append("&units="+"metric");
        googleDirectionsUrl.append("&departure_time="+ontime);
        //googleDirectionsUrl.append("&mode=transit");
        googleDirectionsUrl.append("&key="+"AIzaSyC87jjqT0m1jj5nt4tjuJDjuKbDbRKHg9U");
        System.out.println(googleDirectionsUrl.toString());
        return googleDirectionsUrl.toString();
    }


    protected synchronized void buildGoogleApiClient()
    {
        client=new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        client.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest=new LocationRequest();

        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }

    public Boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_LOCATION_CODE);
            }
            return false;
        }
        else {
            return true;
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

        lastLocation=location;
        if(currentLocationMarker!=null)
        {
            currentLocationMarker.remove();
        }

        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("cuurent location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));

        currentLocationMarker=mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));
        if(client!=null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }

    }
}
