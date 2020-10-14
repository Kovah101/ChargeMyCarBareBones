package com.example.android.chargemycar;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class retrieveChargePointsSeparate extends AsyncTask<String, Void, List<ChargePoint>> {

    private ChargePointAdapter adapter;
    private Context mContext;

    retrieveChargePointsSeparate(ChargePointAdapter cPAdaptor, Context context){
        //indeterminantLoading = loadingBar;
        //emptyStateTextView=emptyTextView;
       adapter=cPAdaptor;
        mContext=context;
    }

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
        //hide loading bar
        ProgressBar indeterminantLoading = (ProgressBar) ((Activity)mContext).findViewById(R.id.loading_bar);
        indeterminantLoading.setVisibility(View.INVISIBLE);
        //default state of no charge points
        TextView emptyStateTextView = (TextView) ((Activity) mContext).findViewById(R.id.empty_view);
        emptyStateTextView.setText(R.string.no_charge_points);
        // Clear the adapter of previous data
        adapter.clear();
        //check for null charge point list, return early if that is the case, if there is a valid list then add to the adapter
        if (chargePoints != null && !chargePoints.isEmpty()){
            //Log.e(LOG_TAG, "Charge points full");
            //update UI
            adapter.addAll(chargePoints);
        }//otherwise display no charge point message
        //TODO add return if chargePoints is null + error message

    }
}
