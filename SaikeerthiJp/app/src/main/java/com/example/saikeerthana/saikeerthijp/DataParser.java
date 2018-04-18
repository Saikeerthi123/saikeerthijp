package com.example.saikeerthana.saikeerthijp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by saikeerthana on 03-04-2018.
 */

public class DataParser {

    private HashMap<String ,String > getDuration(JSONArray googleDirectionsJson)
    {
        HashMap<String ,String> googleDirectionsMap=new HashMap<>();
        String duration="";
        String distance ="";




        Log.d("json response",googleDirectionsJson.toString());
        try {
            duration=googleDirectionsJson.getJSONObject(0).getJSONObject("duration").getString("text");
            distance=googleDirectionsJson.getJSONObject(0).getJSONObject("distance").getString("text");


            googleDirectionsMap.put("duration",duration);
            googleDirectionsMap.put("distance",distance);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return googleDirectionsMap;
    }



    private HashMap<String,String> getBank(JSONObject googleBankJson)
    {
        HashMap<String ,String> googleBankMap=new HashMap<>();
        String bankName="-NA-";
        String vicinity="-NA-";
        String latitude="";
        String longitude="";
        String reference="";

        try{
            if(!googleBankJson.isNull("name"))
            {
                bankName=googleBankJson.getString("name");
            }
            if(!googleBankJson.isNull("vicinity"))
            {
                vicinity=googleBankJson.getString("vicinity");
            }
            latitude=googleBankJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude=googleBankJson.getJSONObject("geometry").getJSONObject("location").getString("long");

            reference=googleBankJson.getString("reference");

            googleBankMap.put("Bank_name",bankName);
            googleBankMap.put("vicinity",vicinity);
            googleBankMap.put("lat",latitude);
            googleBankMap.put("long",longitude);
            googleBankMap.put("reference",reference);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return googleBankMap;
    }

    private List<HashMap<String,String>> getBanks(JSONArray jsonArray){
        int count=jsonArray.length();
        List<HashMap<String,String>> banksList=new ArrayList<>();
        HashMap<String,String> banksMap=null;

        for (int i=0;i<count;i++)
        {
            try {
                banksMap=getBank((JSONObject)jsonArray.get(i));
                banksList.add(banksMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return banksList;


    }


    public List<HashMap<String,String>> parse(String jsondata)
    {
        JSONArray jsonArray=null;
        JSONObject jsonObject;

        try {
            jsonObject=new JSONObject(jsondata);
            jsonArray=jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getBanks(jsonArray);

    }


    public HashMap<String ,String > parseDirections(String jsondata)
    {
        System.out.println(jsondata);
        JSONArray jsonArray=null;
        JSONObject jsonObject;

        try {
            jsonObject=new JSONObject(jsondata);
            jsonArray=jsonObject.getJSONArray("rows").getJSONObject(0).getJSONArray("elements"); //legs array
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getDuration(jsonArray);
    }

}
