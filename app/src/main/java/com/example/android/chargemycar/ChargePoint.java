package com.example.android.chargemycar;

import static com.example.android.chargemycar.MainActivity.myLat;
import static com.example.android.chargemycar.MainActivity.myLong;

/**
 * Created by et_bo on 21/09/2017.
 */

public class ChargePoint {

    //TODO add Device Controller + hyperlink to website
    //TODO add LocationType + corresponding symbol (car park, on street, leisure centre, OTHER)

        private String mName;

        private String mPostCode;

        private String mDistance;

        private boolean mStatus;

        private String mConnectorType;

        private double mlatitude;

        private double mlongitude;

        private int mColor;

        private double mRealDistance;

        private int mConnectorNumber;

        private int mWorkingConnectors;

        public ChargePoint(String name, String postCode, double latitude, double longitude, boolean status, String connectorType, int connectorNumber, int workingConnectors)
        {
            mName = name;
            mPostCode = postCode;
            mlatitude = latitude;
            mlongitude = longitude;
            mStatus = status;
            mConnectorType = connectorType;
            mConnectorNumber = connectorNumber;
            mWorkingConnectors = workingConnectors;

            //find distance between two points on the earth
            //round to km with 1decimal place of accuracy
            mRealDistance = Haversine.distance(myLat,myLong,mlatitude,mlongitude);
            mDistance = String.format("%.2f",mRealDistance);
            mColor = DistanceColor.getColors(mRealDistance);
        }



        public String getCPName(){
            return mName;
        }

        public String getPostCode(){
            return mPostCode;
        }

        public String getDistance(){
            return mDistance;
        }

        public boolean getStatus(){
            return mStatus;
        }

        public String getConnectorType(){
            return mConnectorType;
        }

        public int getColor(){
            return mColor;
        }

        public double getRealDistance(){
            return mRealDistance;
        }

        public int getConnectorNumber(){
            return mConnectorNumber;
        }

        public int getWorkingConnectors (){
            return mWorkingConnectors;
        }

        public double getLatitude (){return mlatitude;}

        public double getLongitude (){return mlongitude;}
    }

