package com.example.android.chargemycar;

/**
 * Created by et_bo on 27/09/2017.
 */

public class DistanceColor {

    public static int getColors (double distance){
        int color = 0;
        //distance is in 1km units

         if (distance<1){
             color = R.color.distance1;
         }
        else if (distance>=1&&distance<2){
             color = R.color.distance2;
         }
         else if (distance>=2&&distance<3){
             color = R.color.distance3;
         }
         else if (distance>=3&&distance<4){
             color = R.color.distance4;
         }
         else if (distance>=4&&distance<5){
             color = R.color.distance5;
         }
         else if (distance>=5&&distance<6){
             color = R.color.distance6;
         }
         else if (distance>=6&&distance<7){
             color = R.color.distance7;
         }
         else if (distance>=7&&distance<8){
             color = R.color.distance8;
         }
         else if (distance>=8&&distance<9){
             color = R.color.distance9;
         }
         else if (distance>=9&&distance<10){
             color = R.color.distance10;
         }
         else {
             color = R.color.distance10plus;
         }
        return color;
    }

}
