package com.example.saikeerthana.saikeerthijp;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by saikeerthana on 03-04-2018.
 */

public class getNearbyBanks extends AsyncTask<Object,String,String> {

    String googleBanks;
    GoogleMap mMap;
    String url;

    @Override
    protected String doInBackground(Object... objects) {

        mMap=(GoogleMap)objects[0];
        url=(String)objects[1];

        DownloadUrl downloadUrl=new DownloadUrl();
        try {
            googleBanks=downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return googleBanks;
    }

    @Override
    protected void onPostExecute(String s) {

        List<HashMap<String,String >> nearbyPlaceList=null;
        DataParser parser=new DataParser();
        nearbyPlaceList=parser.parse(s);
        showNearbyBanks(nearbyPlaceList);
    }

    private void showNearbyBanks(List<HashMap<String,String> >nearbyPlaceList)
    {
        for (int i=0;i<nearbyPlaceList.size();i++)
        {
            MarkerOptions markerOptions=new MarkerOptions();
            HashMap<String,String >googlePlace=nearbyPlaceList.get(i);


            String placeName=googlePlace.get("place_name");
            String vicinity=googlePlace.get("vicinity");
            double lat=Double.parseDouble(googlePlace.get("lat"));
            double lng=Double.parseDouble(googlePlace.get("lng"));


            LatLng latLng=new LatLng(lat,lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName+":"+vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));



        }
    }
}
