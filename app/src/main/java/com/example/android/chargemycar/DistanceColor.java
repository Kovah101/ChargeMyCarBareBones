package com.example.android.chargemycar;

/**
 * Created by et_bo on 27/09/2017.
 */

public class DistanceColor {

    public static int getColors (double distance){
        int color = 0;
        //distance is in 1km units
        //TODO better granulate colour
         if (distance<0.25){
             color = R.color.distance1;
         }
        else if (distance>=0.25&&distance<0.5){
             color = R.color.distance2;
         }
         else if (distance>=0.5&&distance<1.5){
             color = R.color.distance3;
         }
         else if (distance>=1.5&&distance<3){
             color = R.color.distance4;
         }
         else if (distance>=3&&distance<4.5){
             color = R.color.distance5;
         }
         else if (distance>=4.5&&distance<6){
             color = R.color.distance6;
         }
         else if (distance>=6&&distance<9){
             color = R.color.distance7;
         }
         else if (distance>=9&&distance<12){
             color = R.color.distance8;
         }
         else if (distance>=12&&distance<15){
             color = R.color.distance9;
         }
         else {
             color = R.color.distance10plus;
         }
        return color;
    }

}
