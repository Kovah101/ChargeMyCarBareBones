package com.example.android.chargemycar;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by et_bo on 21/09/2017.
 */

public class ChargePointAdapter extends ArrayAdapter<ChargePoint> {


    public ChargePointAdapter(Activity context, ArrayList<ChargePoint> chargePoints){
        super(context, 0, chargePoints);
    }

//    public ChargePointAdapter(LocationListener locationListener, ArrayList<ChargePoint> chargePoints) {
//        super((Context) locationListener, 0, chargePoints);
    //}


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);

        }

        ChargePoint currentChargePoint = getItem(position);


        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.name);
        nameTextView.setText(currentChargePoint.getCPName());

        TextView distanceTextView = (TextView) listItemView.findViewById(R.id.distance);
        distanceTextView.setText(currentChargePoint.getDistance());
        GradientDrawable circle = (GradientDrawable)distanceTextView.getBackground().getCurrent();
        circle.setColor(ContextCompat.getColor(getContext(),currentChargePoint.getColor()));


        TextView postcodeTextView = (TextView) listItemView.findViewById(R.id.postcode);
        postcodeTextView.setText(currentChargePoint.getPostCode());


        ImageView locationType = (ImageView) listItemView.findViewById(R.id.locationType);
        int locationImage = R.mipmap.ic_launcher; //will use case structure to vary icon depending on location
        locationType.setImageResource(locationImage);

        TextView statusTextView = (TextView) listItemView.findViewById(R.id.status);
        boolean status = currentChargePoint.getStatus();
        if (status){
            statusTextView.setText("In Service " + currentChargePoint.getWorkingConnectors() + "/" + currentChargePoint.getConnectorNumber());
            statusTextView.setBackgroundResource(R.color.statusGreen);
        }
        else {
            statusTextView.setText("No Service " + currentChargePoint.getWorkingConnectors() + "/" + currentChargePoint.getConnectorNumber());
            statusTextView.setBackgroundResource(R.color.statusRed);
        }

        TextView connectorType = (TextView) listItemView.findViewById(R.id.connectorType);
        connectorType.setText(currentChargePoint.getConnectorType());

        return listItemView;

    }
}
