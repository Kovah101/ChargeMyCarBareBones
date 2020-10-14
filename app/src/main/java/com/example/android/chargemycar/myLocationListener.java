package com.example.android.chargemycar;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class myLocationListener implements LocationListener {
    private static final String LOG_TAG = myLocationListener.class.getName();
    public static double myLatitude;
    public static double myLongitude;
    private ChargePointAdapter adapter;
    private Context mContext;

    public myLocationListener (ChargePointAdapter CPAdapter, Context context){
        adapter=CPAdapter;
        this.mContext=context;
    }

    @Override
    //when location changes, rewrite URL, retrieve charge points and change display
    public void onLocationChanged(Location location) {
        //set lat & long variables
        myLatitude = location.getLatitude();
        myLongitude = location.getLongitude();
        String myLatString = Double.toString(myLatitude);
        String myLongString = Double.toString(myLongitude);
       // Log.e(LOG_TAG, "\n" + myLatString + "\n" + myLongString);

        //create request URL using live location

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        //get max distance from preferences fragment through resource strings
        String maxDistance = sharedPref.getString(
                mContext.getString(R.string.settings_max_distance_key),
                mContext.getString(R.string.settings_max_distance_default)
        );
        //get max number of charge points from preferences fragment through resource strings
        String cpLimit = sharedPref.getString(
                mContext.getString(R.string.settings_cp_limit_key),
                mContext.getString(R.string.settings_cp_limit_default)
        );
                   //Construct URL string
        String chargePoint_REQUEST_URL = "https://chargepoints.dft.gov.uk/api/retrieve/registry/lat";
        Uri baseUri = Uri.parse(chargePoint_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendPath(myLatString);
        uriBuilder.appendPath("long");
        uriBuilder.appendPath(myLongString);
        uriBuilder.appendPath("dist");
        uriBuilder.appendPath(maxDistance);
        uriBuilder.appendPath("format");
        uriBuilder.appendPath("json");
        uriBuilder.appendPath("limit");
        uriBuilder.appendPath(cpLimit);
                   //Log.i(LOG_TAG, "executing asynchronous thread");
                   //start asynchronous thread to retrieve charge points
                 new retrieveChargePointsSeparate(adapter, mContext).execute(uriBuilder.toString());
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
}
