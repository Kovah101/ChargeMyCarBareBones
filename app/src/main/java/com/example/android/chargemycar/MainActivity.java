package com.example.android.chargemycar;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getName();

    public static double myLat;
    public static double myLong;

    public static  String ChargePoint_REQUEST_URL; //= "http://chargepoints.dft.gov.uk/api/retrieve/registry/postcode/SW15+5QS/dist/7/format/json/limit/10";

    private static ChargePointAdapter adapter;

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO add burger menu for changing URL String components, use current location or set location
        // TODO new symbol? graphics for loading screen too?
        // Find a reference to the {@link ListView} in the layout
        final ListView chargePointListView = (ListView) findViewById(R.id.list);

        // Create a ChargingPointAdapter, whose data source is a list of ChargePoints, which creates listview items for each item
        adapter = new ChargePointAdapter(this, new ArrayList<ChargePoint>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        chargePointListView.setAdapter(adapter);

        /* create locationListener to request GPS data and track current position in asynctask?? */
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            //when location changes, rewrite URL, retrieve charge points and change display
            public void onLocationChanged(Location location) {
                //set lat & long variables
                myLat = location.getLatitude();
                myLong = location.getLongitude();
                String myLatString = Double.toString(myLat);
                String myLongString = Double.toString(myLong);
                Log.e(LOG_TAG,"\n"+myLatString+"\n"+myLongString);

                //create request URL using live location
                //TODO add variables for distance and limit
                ChargePoint_REQUEST_URL = "https://chargepoints.dft.gov.uk/api/retrieve/registry/lat/" + myLat + "/long/" + myLong + "/dist/10/format/json/limit/10";

                Log.i(LOG_TAG, "executing asynchronous thread");
                //start asynchronous thread to retrieve charge points
                new retrieveChargePoints().execute(ChargePoint_REQUEST_URL);

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
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 10, locationListener);
            //time in milliseconds
            //distance in meters
        }

        //create on item click listener to wait for selected charge point and send to maps on click
        chargePointListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String myLatString = Double.toString(myLat);
                String myLongString = Double.toString(myLong);

                ChargePoint currentChargePoint = (ChargePoint) chargePointListView.getItemAtPosition(position);
                double destinationLatitude = currentChargePoint.getLatitude();
                double destinationLongitude = currentChargePoint.getLongitude();
                String destLatString = Double.toString(destinationLatitude);
                String destLongString = Double.toString(destinationLongitude);


                //create uri for map intent with Google Maps
                String url = "http://maps.google.com/maps?saddr="+myLatString+","+myLongString+"&daddr="+destLatString+","+destLongString+"&travelmode=driving";
                Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 10, locationListener);
            }
        }
    }



// added static to prevent leaks 14/4/2020
    //Async Task used for network request on background thread
    private static class retrieveChargePoints extends AsyncTask<String, Void, List<ChargePoint>> {
        //Context context;
        @Override
        protected List<ChargePoint> doInBackground(String... url) {
            //check and deal with no input url
            if (url == null) {
                return null;
            }
            //Perform the HTTP request for charge point data and process response
            return QueryUtils.fetchChargePointData(url[0]);
        }

        //made adapter static to match retrieveChargePoints
        @Override
        protected void onPostExecute(List<ChargePoint> chargePoints) {
            // Clear the adapter of previous data
             adapter.clear();
            //check for null charge point list, return early if that is the case, if there is a valid list then add to the adapter
            if (chargePoints != null && !chargePoints.isEmpty()){
                Log.e(LOG_TAG, "Charge points full");
                adapter.addAll(chargePoints);
            }
            //TODO add return if chargePoints is null + error message

        }

    }

}
