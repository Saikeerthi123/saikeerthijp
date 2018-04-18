package com.example.saikeerthana.saikeerthijp;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by saikeerthana on 03-04-2018.
 */

public class getDirectionsData extends AsyncTask<Object,String,String> {

    GoogleMap mMap;
    String url;
    //int inputtime;
    String googleDirectionsData;
    String duration,distance;
    LatLng latLng;

    @Override
    protected String doInBackground(Object... objects) {
        mMap=(GoogleMap)objects[0];
        url=(String)objects[1];
        latLng=(LatLng)objects[2];
        //inputtime=(int) objects[3];

        DownloadUrl downloadUrl=new DownloadUrl();
        try {
            googleDirectionsData=downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return googleDirectionsData;
    }

    @Override
    protected void onPostExecute(String s) {

        HashMap<String ,String > directionsList=null;
        DataParser parser=new DataParser();
        directionsList=parser.parseDirections(s);
        duration=directionsList.get("duration");
        distance=directionsList.get("distance");

        mMap.clear();
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(latLng);
        //markerOptions.draggable(true);
        markerOptions.title("duration : "+duration);
        markerOptions.snippet("distance : "+distance);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));

        mMap.addMarker(markerOptions);


    }
}
