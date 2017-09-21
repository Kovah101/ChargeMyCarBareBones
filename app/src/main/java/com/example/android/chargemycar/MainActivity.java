package com.example.android.chargemycar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a fake list of ChargingPoint objects.
        ArrayList<ChargePoint> chargePoints = new ArrayList<ChargePoint>();
        chargePoints.add(new ChargePoint("San Francisco", "SW157DW", 3.42, true, "type 2"));
        chargePoints.add(new ChargePoint("London", "SW134RF", 1.53, false, "type 3"));
        chargePoints.add(new ChargePoint("Tokyo", "SE38UI", 7.34, true, "type 2"));
        chargePoints.add(new ChargePoint("Mexico City", "EC25TR", 4.84, true, "type 2"));
        chargePoints.add(new ChargePoint("Moscow", "TW14FN", 2.79, true, "type 3"));
        chargePoints.add(new ChargePoint("Rio de Janeiro", "SE94ED", 6.94, false, "type 2"));
        chargePoints.add(new ChargePoint("Paris", "NW139NK", 11.43, true, "type 2"));


        // Create a ChargingPointAdapter, whose data source is a list of ChargePoints, which creates listview items for each item
        ChargePointAdapter adapter = new ChargePointAdapter(this, chargePoints);


        // Find a reference to the {@link ListView} in the layout
        ListView chargePointListView = (ListView) findViewById(R.id.list);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        chargePointListView.setAdapter(adapter);

    }
}
