package com.example.android.chargemycar;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.util.ArrayList;

import static com.example.android.chargemycar.myLocationListener.myLatitude;
import static com.example.android.chargemycar.myLocationListener.myLongitude;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getName();
    private LocationManager locationManager;
    private LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO Change to ViewModel + LiveData to save on memory as screen is rotated
        // TODO new symbol? graphics for loading screen too?
        // Find a reference to the {@link ListView} in the layout
        final ListView chargePointListView = (ListView) findViewById(R.id.list);

        // Create a ChargingPointAdapter, whose data source is a list of ChargePoints, which creates listview items for each item
        //static or not (same with AsyncTask)
        ChargePointAdapter adapter = new ChargePointAdapter(this, new ArrayList<ChargePoint>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        chargePointListView.setAdapter(adapter);

        //Assign TextView when the List is empty
        TextView emptyStateTextView = (TextView) findViewById(R.id.empty_view);
        chargePointListView.setEmptyView(emptyStateTextView);

        ProgressBar indeterminantLoading = (ProgressBar) findViewById(R.id.loading_bar);

        //Get a reference to the Connectivity Manager to check state of connection
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //Get details on the data network
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        //if there is a connection then check location and retrieve data
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {

            /* create locationListener to request GPS data and track current position in another asynctask?? */
            locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
            myLocationListener ll = new myLocationListener(adapter, this);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 30, ll);
                //time in milliseconds
                //distance in meters
            }
        } else {
            //Otherwise hide progress bar & display error
            indeterminantLoading.setVisibility(View.INVISIBLE);
            emptyStateTextView.setText(R.string.no_internet);

        }

        //create on item click listener to wait for selected charge point and send to maps on click
        chargePointListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String myLatString = Double.toString(myLatitude);
                String myLongString = Double.toString(myLongitude);

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

    //overrides for options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
