package com.example.android.chargemycar;

/**
 * Created by et_bo on 21/09/2017.
 */

public class ChargePoint {

        private String mName;

        private String mPostCode;

        private double mDistance;

        private boolean mStatus;

        private String mConnectorType;


        public ChargePoint(String name, String postCode, double distance, boolean status, String connectorType)
        {
            mName = name;
            mPostCode = postCode;
            mDistance = distance;
            mStatus = status;
            mConnectorType = connectorType;
        }

        public String getCPName(){
            return mName;
        }

        public String getPostCode(){
            return mPostCode;
        }

        public double getDistance(){
            return mDistance;
        }

        public boolean getStatus(){
            return mStatus;
        }

        public String getConnectorType(){
            return mConnectorType;
        }

    }

