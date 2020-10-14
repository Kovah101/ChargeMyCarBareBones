package com.example.android.chargemycar;

/*
 * Created by et_bo on 26/09/2017.
 */

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Helper methods related to requesting and receiving Charge point data from website.
 */
public final class QueryUtils {

    /** Sample JSON response for a Charge point query */
    //private static final String SAMPLE_JSON_RESPONSE = "{\"Scheme\":{\"SchemeCode\":\"NA\",\"SchemeData\":{\"OrganisationName\":\"NA\",\"Website\":\"NA\",\"TelephoneNo\":\"NA\"}},\"ChargeDevice\":[{\"ChargeDeviceId\":\"219f81cc6c7826d1bb55686944865323\",\"ChargeDeviceRef\":\"SRC_LDN60207\",\"ChargeDeviceName\":\"Asda Roehampton\",\"ChargeDeviceText\":null,\"ChargeDeviceLocation\":{\"Latitude\":\"51.439365\",\"Longitude\":\"-0.245991\",\"Address\":{\"SubBuildingName\":null,\"BuildingName\":\"Asda Roehampton Superstore\",\"BuildingNumber\":\"31\",\"Thoroughfare\":\"Roehampton Vale\",\"Street\":\"Roehampton\",\"DoubleDependantLocality\":null,\"DependantLocality\":null,\"PostTown\":\"Putney\",\"County\":\"Greater London\",\"PostCode\":\"SW15 3DT\",\"Country\":\"gb\",\"UPRN\":null},\"LocationShortDescription\":null,\"LocationLongDescription\":\"\"},\"ChargeDeviceManufacturer\":null,\"ChargeDeviceModel\":null,\"PublishStatusID\":\"1\",\"DateCreated\":\"2014-08-19 05:15:15\",\"DateUpdated\":\"2015-08-26 11:39:37\",\"Attribution\":\"Source London\",\"DateDeleted\":\"n\\/a\",\"Connector\":[{\"ConnectorId\":\"1\",\"ConnectorType\":\"3-pin Type G (BS1363)\",\"RatedOutputkW\":\"3.7\",\"RatedOutputVoltage\":\"230\",\"RatedOutputCurrent\":\"16\",\"ChargeMethod\":\"Single Phase AC\",\"ChargeMode\":\"1\",\"ChargePointStatus\":\"In service\",\"TetheredCable\":\"0\",\"Information\":\"  x 3-pin square (BS 1363) - Standard (up to 3.7kW, 13-16A)\",\"Validated\":\"0\"},{\"ConnectorId\":\"2\",\"ConnectorType\":\"4-pin Type G (BS1363)\",\"RatedOutputkW\":\"3.7\",\"RatedOutputVoltage\":\"230\",\"RatedOutputCurrent\":\"16\",\"ChargeMethod\":\"Single Phase AC\",\"ChargeMode\":\"1\",\"ChargePointStatus\":\"In service\",\"TetheredCable\":\"0\",\"Information\":\"  x 3-pin square (BS 1363) - Standard (up to 3.7kW, 13-16A)\",\"Validated\":\"0\"}],\"DeviceOwner\":{\"OrganisationName\":\"Source London\",\"SchemeCode\":\"SRC_LDN\",\"Website\":\"https:\\/\\/www.sourcelondon.net\",\"TelephoneNo\":\"020 3056 8989\"},\"DeviceController\":{\"OrganisationName\":\"Source London\",\"SchemeCode\":\"SRC_LDN\",\"Website\":\"https:\\/\\/www.sourcelondon.net\",\"TelephoneNo\":\"020 3056 8989\"},\"DeviceAccess\":[],\"DeviceNetworks\":\"Source London\",\"ChargeDeviceStatus\":\"In service\",\"PublishStatus\":\"Published\",\"DeviceValidated\":\"0\",\"RecordModerated\":\"Y\",\"RecordLastUpdated\":\"2015-08-26 11:39:37\",\"RecordLastUpdatedBy\":\"NCR Admin\",\"PaymentRequiredFlag\":false,\"PaymentDetails\":\"\",\"SubscriptionRequiredFlag\":true,\"SubscriptionDetails\":\"\\u00a35 per annum for RFiD card\",\"ParkingFeesFlag\":false,\"ParkingFeesDetails\":\"\",\"ParkingFeesUrl\":null,\"AccessRestrictionFlag\":false,\"AccessRestrictionDetails\":\"\",\"PhysicalRestrictionFlag\":false,\"PhysicalRestrictionText\":\"\",\"OnStreetFlag\":false,\"LocationType\":\"Retail car park\",\"Bearing\":null,\"Accessible24Hours\":false},{\"ChargeDeviceId\":\"41f3cba68ffb2f2a2181b08abb545f89\",\"ChargeDeviceRef\":\"SRC_LDN60246\",\"ChargeDeviceName\":\"Charing Cross Hospital\",\"ChargeDeviceText\":null,\"ChargeDeviceLocation\":{\"Latitude\":\"51.486559\",\"Longitude\":\"-0.220487\",\"Address\":{\"SubBuildingName\":null,\"BuildingName\":\"\",\"BuildingNumber\":\"214\",\"Thoroughfare\":\"Fulham Palace Road\",\"Street\":\"Hammersmith\",\"DoubleDependantLocality\":null,\"DependantLocality\":null,\"PostTown\":\"Hammersmith\",\"County\":\"Greater London\",\"PostCode\":\"W6 9NT\",\"Country\":\"gb\",\"UPRN\":null},\"LocationShortDescription\":null,\"LocationLongDescription\":\"\"},\"ChargeDeviceManufacturer\":null,\"ChargeDeviceModel\":null,\"PublishStatusID\":\"1\",\"DateCreated\":\"2014-08-19 05:15:41\",\"DateUpdated\":\"2015-09-02 14:33:40\",\"Attribution\":\"Source London\",\"DateDeleted\":\"n\\/a\",\"Connector\":[{\"ConnectorId\":\"1\",\"ConnectorType\":\"3-pin Type G (BS1363)\",\"RatedOutputkW\":\"3.7\",\"RatedOutputVoltage\":\"230\",\"RatedOutputCurrent\":\"16\",\"ChargeMethod\":\"Single Phase AC\",\"ChargeMode\":\"1\",\"ChargePointStatus\":\"In service\",\"TetheredCable\":\"0\",\"Information\":\"  x 3-pin square (BS 1363) - Standard (up to 3.7kW, 13-16A)\",\"Validated\":\"0\"},{\"ConnectorId\":\"2\",\"ConnectorType\":\"3-pin Type G (BS1363)\",\"RatedOutputkW\":\"3.7\",\"RatedOutputVoltage\":\"230\",\"RatedOutputCurrent\":\"16\",\"ChargeMethod\":\"Single Phase AC\",\"ChargeMode\":\"1\",\"ChargePointStatus\":\"In service\",\"TetheredCable\":\"0\",\"Information\":\"  x 3-pin square (BS 1363) - Standard (up to 3.7kW, 13-16A)\",\"Validated\":\"0\"}],\"DeviceOwner\":{\"OrganisationName\":\"Source London\",\"SchemeCode\":\"SRC_LDN\",\"Website\":\"https:\\/\\/www.sourcelondon.net\",\"TelephoneNo\":\"020 3056 8989\"},\"DeviceController\":{\"OrganisationName\":\"Source London\",\"SchemeCode\":\"SRC_LDN\",\"Website\":\"https:\\/\\/www.sourcelondon.net\",\"TelephoneNo\":\"020 3056 8989\"},\"DeviceAccess\":[],\"DeviceNetworks\":\"Source London\",\"ChargeDeviceStatus\":\"In service\",\"PublishStatus\":\"Published\",\"DeviceValidated\":\"0\",\"RecordModerated\":\"Y\",\"RecordLastUpdated\":\"2015-09-02 14:33:40\",\"RecordLastUpdatedBy\":\"NCR Admin\",\"PaymentRequiredFlag\":false,\"PaymentDetails\":\"\",\"SubscriptionRequiredFlag\":true,\"SubscriptionDetails\":\"\\u00a35 per annum for RFiD card\",\"ParkingFeesFlag\":false,\"ParkingFeesDetails\":\"\",\"ParkingFeesUrl\":null,\"AccessRestrictionFlag\":false,\"AccessRestrictionDetails\":\"\",\"PhysicalRestrictionFlag\":true,\"PhysicalRestrictionText\":\"\",\"OnStreetFlag\":false,\"LocationType\":\"NHS property\",\"Bearing\":null,\"Accessible24Hours\":false},{\"ChargeDeviceId\":\"a38b49209472b0772d3937c7ec229295\",\"ChargeDeviceRef\":\"SRC_LDN60286\",\"ChargeDeviceName\":\"Exchange Shopping Centre Car Park 1\",\"ChargeDeviceText\":null,\"ChargeDeviceLocation\":{\"Latitude\":\"51.463980\",\"Longitude\":\"-0.216975\",\"Address\":{\"SubBuildingName\":null,\"BuildingName\":\"\",\"BuildingNumber\":\"\",\"Thoroughfare\":\"Putney High Street\",\"Street\":\"\",\"DoubleDependantLocality\":null,\"DependantLocality\":null,\"PostTown\":\"Putney\",\"County\":\"Greater London\",\"PostCode\":\"SW15 1TW\",\"Country\":\"gb\",\"UPRN\":null},\"LocationShortDescription\":null,\"LocationLongDescription\":\"\"},\"ChargeDeviceManufacturer\":null,\"ChargeDeviceModel\":\"\",\"PublishStatusID\":\"1\",\"DateCreated\":\"2014-08-19 05:16:07\",\"DateUpdated\":\"2015-10-15 10:06:38\",\"Attribution\":\"Source London\",\"DateDeleted\":\"n\\/a\",\"Connector\":[{\"ConnectorId\":\"1\",\"ConnectorType\":\"Type 2 Mennekes (IEC62196)\",\"RatedOutputkW\":\"7.0\",\"RatedOutputVoltage\":\"230\",\"RatedOutputCurrent\":\"32\",\"ChargeMethod\":\"Single Phase AC\",\"ChargeMode\":\"3\",\"ChargePointStatus\":\"Out of service\",\"TetheredCable\":\"0\",\"Information\":\"  x 7-pin \'Smart\' eg Mennekes (IEC 62196) - Fast (7kW, 32A)\",\"Validated\":\"0\"},{\"ConnectorId\":\"2\",\"ConnectorType\":\"Type 2 Mennekes (IEC62196)\",\"RatedOutputkW\":\"7.0\",\"RatedOutputVoltage\":\"230\",\"RatedOutputCurrent\":\"32\",\"ChargeMethod\":\"Single Phase AC\",\"ChargeMode\":\"3\",\"ChargePointStatus\":\"Out of service\",\"TetheredCable\":\"0\",\"Information\":\"  x 7-pin \'Smart\' eg Mennekes (IEC 62196) - Fast (7kW, 32A)\",\"Validated\":\"0\"}],\"DeviceOwner\":{\"OrganisationName\":\"Source London\",\"SchemeCode\":\"SRC_LDN\",\"Website\":\"https:\\/\\/www.sourcelondon.net\",\"TelephoneNo\":\"020 3056 8989\"},\"DeviceController\":{\"OrganisationName\":\"Source London\",\"SchemeCode\":\"SRC_LDN\",\"Website\":\"https:\\/\\/www.sourcelondon.net\",\"TelephoneNo\":\"020 3056 8989\"},\"DeviceAccess\":[],\"DeviceNetworks\":\"Source London\",\"ChargeDeviceStatus\":\"Out of service\",\"PublishStatus\":\"Published\",\"DeviceValidated\":\"0\",\"RecordModerated\":\"Y\",\"RecordLastUpdated\":\"2015-10-15 10:06:38\",\"RecordLastUpdatedBy\":\"NCR Admin\",\"PaymentRequiredFlag\":false,\"PaymentDetails\":\"\",\"SubscriptionRequiredFlag\":true,\"SubscriptionDetails\":\"\\u00a35 per annum for RFiD card\",\"ParkingFeesFlag\":false,\"ParkingFeesDetails\":\"\",\"ParkingFeesUrl\":null,\"AccessRestrictionFlag\":false,\"AccessRestrictionDetails\":\"\",\"PhysicalRestrictionFlag\":false,\"PhysicalRestrictionText\":\"\",\"OnStreetFlag\":false,\"LocationType\":\"Retail car park\",\"Bearing\":null,\"Accessible24Hours\":false},{\"ChargeDeviceId\":\"4bdb3b694acca9162c97f75efe9faca1\",\"ChargeDeviceRef\":\"SRC_LDN60514\",\"ChargeDeviceName\":\"Putney Leisure Centre\",\"ChargeDeviceText\":null,\"ChargeDeviceLocation\":{\"Latitude\":\"51.463908\",\"Longitude\":\"-0.228811\",\"Address\":{\"SubBuildingName\":null,\"BuildingName\":null,\"BuildingNumber\":null,\"Thoroughfare\":null,\"Street\":null,\"DoubleDependantLocality\":null,\"DependantLocality\":null,\"PostTown\":null,\"County\":null,\"PostCode\":null,\"Country\":\"gb\",\"UPRN\":null},\"LocationShortDescription\":null,\"LocationLongDescription\":\"Dryburgh Road, Putney, Wandsworth, London, SW15 1BL\"},\"ChargeDeviceManufacturer\":null,\"ChargeDeviceModel\":null,\"PublishStatusID\":\"1\",\"DateCreated\":\"2014-08-19 05:18:36\",\"DateUpdated\":\"2015-09-09 14:37:12\",\"Attribution\":\"Source London\",\"DateDeleted\":\"n\\/a\",\"Connector\":[{\"ConnectorId\":\"1\",\"ConnectorType\":\"3-pin Type G (BS1363)\",\"RatedOutputkW\":\"3.7\",\"RatedOutputVoltage\":\"230\",\"RatedOutputCurrent\":\"16\",\"ChargeMethod\":\"Single Phase AC\",\"ChargeMode\":\"1\",\"ChargePointStatus\":\"Out of service\",\"TetheredCable\":\"0\",\"Information\":\"  x 3-pin square (BS 1363) - Standard (up to 3.7kW, 13-16A)\",\"Validated\":\"0\"}],\"DeviceOwner\":{\"OrganisationName\":\"Source London\",\"SchemeCode\":\"SRC_LDN\",\"Website\":\"https:\\/\\/www.sourcelondon.net\",\"TelephoneNo\":\"020 3056 8989\"},\"DeviceController\":{\"OrganisationName\":\"Source London\",\"SchemeCode\":\"SRC_LDN\",\"Website\":\"https:\\/\\/www.sourcelondon.net\",\"TelephoneNo\":\"020 3056 8989\"},\"DeviceAccess\":[],\"DeviceNetworks\":\"Source London\",\"ChargeDeviceStatus\":\"Out of service\",\"PublishStatus\":\"Published\",\"DeviceValidated\":\"0\",\"RecordModerated\":\"N\",\"RecordLastUpdated\":null,\"RecordLastUpdatedBy\":\"\",\"PaymentRequiredFlag\":false,\"PaymentDetails\":null,\"SubscriptionRequiredFlag\":true,\"SubscriptionDetails\":\"\\u00a35 per annum for RFiD card\",\"ParkingFeesFlag\":false,\"ParkingFeesDetails\":null,\"ParkingFeesUrl\":null,\"AccessRestrictionFlag\":false,\"AccessRestrictionDetails\":null,\"PhysicalRestrictionFlag\":false,\"PhysicalRestrictionText\":null,\"OnStreetFlag\":false,\"LocationType\":\"Leisure centre\",\"Bearing\":null,\"Accessible24Hours\":false},{\"ChargeDeviceId\":\"0d3d81a9e226a7158a77a5b862311b4e\",\"ChargeDeviceRef\":\"SRC_LDN60568\",\"ChargeDeviceName\":\"St Johns Avenue\",\"ChargeDeviceText\":null,\"ChargeDeviceLocation\":{\"Latitude\":\"51.459622\",\"Longitude\":\"-0.217764\",\"Address\":{\"SubBuildingName\":null,\"BuildingName\":null,\"BuildingNumber\":null,\"Thoroughfare\":null,\"Street\":null,\"DoubleDependantLocality\":null,\"DependantLocality\":null,\"PostTown\":null,\"County\":null,\"PostCode\":null,\"Country\":\"gb\",\"UPRN\":null},\"LocationShortDescription\":null,\"LocationLongDescription\":\"St Johns Avenue, Putney, Wandsworth, London, SW15 6AF\"},\"ChargeDeviceManufacturer\":null,\"ChargeDeviceModel\":null,\"PublishStatusID\":\"1\",\"DateCreated\":\"2014-08-19 05:19:16\",\"DateUpdated\":\"0000-00-00 00:00:00\",\"Attribution\":\"Source London\",\"DateDeleted\":\"n\\/a\",\"Connector\":[{\"ConnectorId\":\"1\",\"ConnectorType\":\"3-pin Type G (BS1363)\",\"RatedOutputkW\":\"3.7\",\"RatedOutputVoltage\":\"230\",\"RatedOutputCurrent\":\"16\",\"ChargeMethod\":\"Single Phase AC\",\"ChargeMode\":\"1\",\"ChargePointStatus\":\"In service\",\"TetheredCable\":\"0\",\"Information\":\"  x 3-pin square (BS 1363) - Standard (up to 3.7kW, 13-16A)\",\"Validated\":\"0\"},{\"ConnectorId\":\"2\",\"ConnectorType\":\"3-pin Type G (BS1363)\",\"RatedOutputkW\":\"3.7\",\"RatedOutputVoltage\":\"230\",\"RatedOutputCurrent\":\"16\",\"ChargeMethod\":\"Single Phase AC\",\"ChargeMode\":\"1\",\"ChargePointStatus\":\"Out of service\",\"TetheredCable\":\"0\",\"Information\":\"  x 3-pin square (BS 1363) - Standard (up to 3.7kW, 13-16A)\",\"Validated\":\"0\"}],\"DeviceOwner\":{\"OrganisationName\":\"Source London\",\"SchemeCode\":\"SRC_LDN\",\"Website\":\"https:\\/\\/www.sourcelondon.net\",\"TelephoneNo\":\"020 3056 8989\"},\"DeviceController\":{\"OrganisationName\":\"Source London\",\"SchemeCode\":\"SRC_LDN\",\"Website\":\"https:\\/\\/www.sourcelondon.net\",\"TelephoneNo\":\"020 3056 8989\"},\"DeviceAccess\":[],\"DeviceNetworks\":\"Source London\",\"ChargeDeviceStatus\":\"In service\",\"PublishStatus\":\"Published\",\"DeviceValidated\":\"0\",\"RecordModerated\":\"N\",\"RecordLastUpdated\":null,\"RecordLastUpdatedBy\":\"\",\"PaymentRequiredFlag\":false,\"PaymentDetails\":null,\"SubscriptionRequiredFlag\":true,\"SubscriptionDetails\":\"\\u00a35 per annum for RFiD card\",\"ParkingFeesFlag\":false,\"ParkingFeesDetails\":null,\"ParkingFeesUrl\":null,\"AccessRestrictionFlag\":false,\"AccessRestrictionDetails\":null,\"PhysicalRestrictionFlag\":false,\"PhysicalRestrictionText\":null,\"OnStreetFlag\":true,\"LocationType\":\"Other\",\"Bearing\":null,\"Accessible24Hours\":false},{\"ChargeDeviceId\":\"81e98219f751b64b9e7ec0e4e5c6d0e4\",\"ChargeDeviceRef\":\"NCRA131\",\"ChargeDeviceName\":\"Exchange Shopping Centre Car Park 2\",\"ChargeDeviceText\":null,\"ChargeDeviceLocation\":{\"Latitude\":\"51.463906\",\"Longitude\":\"-0.216287\",\"Address\":{\"SubBuildingName\":null,\"BuildingName\":\"\",\"BuildingNumber\":\"5\",\"Thoroughfare\":\"Lacy Road\",\"Street\":\"\",\"DoubleDependantLocality\":null,\"DependantLocality\":null,\"PostTown\":\"Putney\",\"County\":\"Greater London\",\"PostCode\":\"SW15 1TW\",\"Country\":\"gb\",\"UPRN\":null},\"LocationShortDescription\":null,\"LocationLongDescription\":\"\"},\"ChargeDeviceManufacturer\":null,\"ChargeDeviceModel\":\"\",\"PublishStatusID\":\"1\",\"DateCreated\":\"2015-10-15 10:07:28\",\"DateUpdated\":\"2015-10-15 10:07:28\",\"Attribution\":\"Source London\",\"DateDeleted\":\"n\\/a\",\"Connector\":[{\"ConnectorId\":\"1\",\"ConnectorType\":\"Type 2 Mennekes (IEC62196)\",\"RatedOutputkW\":\"7.0\",\"RatedOutputVoltage\":\"230\",\"RatedOutputCurrent\":\"32\",\"ChargeMethod\":\"Single Phase AC\",\"ChargeMode\":\"3\",\"ChargePointStatus\":\"Out of service\",\"TetheredCable\":\"0\",\"Information\":\"\",\"Validated\":\"0\"},{\"ConnectorId\":\"2\",\"ConnectorType\":\"Type 2 Mennekes (IEC62196)\",\"RatedOutputkW\":\"7.0\",\"RatedOutputVoltage\":\"230\",\"RatedOutputCurrent\":\"32\",\"ChargeMethod\":\"Single Phase AC\",\"ChargeMode\":\"3\",\"ChargePointStatus\":\"Out of service\",\"TetheredCable\":\"0\",\"Information\":\"\",\"Validated\":\"0\"}],\"DeviceOwner\":{\"OrganisationName\":\"Source London\",\"SchemeCode\":\"SRC_LDN\",\"Website\":\"https:\\/\\/www.sourcelondon.net\",\"TelephoneNo\":\"020 3056 8989\"},\"DeviceController\":{\"OrganisationName\":\"Source London\",\"SchemeCode\":\"SRC_LDN\",\"Website\":\"https:\\/\\/www.sourcelondon.net\",\"TelephoneNo\":\"020 3056 8989\"},\"DeviceAccess\":[],\"DeviceNetworks\":\"Source London\",\"ChargeDeviceStatus\":\"Out of service\",\"PublishStatus\":\"Published\",\"DeviceValidated\":\"0\",\"RecordModerated\":\"N\",\"RecordLastUpdated\":null,\"RecordLastUpdatedBy\":\"\",\"PaymentRequiredFlag\":false,\"PaymentDetails\":\"\",\"SubscriptionRequiredFlag\":false,\"SubscriptionDetails\":\"\",\"ParkingFeesFlag\":false,\"ParkingFeesDetails\":\"\",\"ParkingFeesUrl\":null,\"AccessRestrictionFlag\":false,\"AccessRestrictionDetails\":\"\",\"PhysicalRestrictionFlag\":false,\"PhysicalRestrictionText\":\"\",\"OnStreetFlag\":false,\"LocationType\":\"Retail car park\",\"Bearing\":null,\"Accessible24Hours\":false},{\"ChargeDeviceId\":\"c7051dbf9f079a789189c4e6cfe490a5\",\"ChargeDeviceRef\":\"NCRA132\",\"ChargeDeviceName\":\"Exchange Shopping Centre Car Park 3\",\"ChargeDeviceText\":null,\"ChargeDeviceLocation\":{\"Latitude\":\"51.463906\",\"Longitude\":\"-0.216287\",\"Address\":{\"SubBuildingName\":null,\"BuildingName\":\"\",\"BuildingNumber\":\"5\",\"Thoroughfare\":\"Lacy Road\",\"Street\":\"\",\"DoubleDependantLocality\":null,\"DependantLocality\":null,\"PostTown\":\"Putney\",\"County\":\"Greater London\",\"PostCode\":\"SW15 1TW\",\"Country\":\"gb\",\"UPRN\":null},\"LocationShortDescription\":null,\"LocationLongDescription\":\"\"},\"ChargeDeviceManufacturer\":null,\"ChargeDeviceModel\":\"\",\"PublishStatusID\":\"1\",\"DateCreated\":\"2015-10-15 10:08:31\",\"DateUpdated\":\"2015-10-15 10:08:31\",\"Attribution\":\"Source London\",\"DateDeleted\":\"n\\/a\",\"Connector\":[{\"ConnectorId\":\"1\",\"ConnectorType\":\"Type 2 Mennekes (IEC62196)\",\"RatedOutputkW\":\"7.0\",\"RatedOutputVoltage\":\"230\",\"RatedOutputCurrent\":\"32\",\"ChargeMethod\":\"Single Phase AC\",\"ChargeMode\":\"3\",\"ChargePointStatus\":\"Out of service\",\"TetheredCable\":\"0\",\"Information\":\"\",\"Validated\":\"0\"},{\"ConnectorId\":\"2\",\"ConnectorType\":\"Type 2 Mennekes (IEC62196)\",\"RatedOutputkW\":\"7.0\",\"RatedOutputVoltage\":\"230\",\"RatedOutputCurrent\":\"32\",\"ChargeMethod\":\"Single Phase AC\",\"ChargeMode\":\"3\",\"ChargePointStatus\":\"Out of service\",\"TetheredCable\":\"0\",\"Information\":\"\",\"Validated\":\"0\"}],\"DeviceOwner\":{\"OrganisationName\":\"Source London\",\"SchemeCode\":\"SRC_LDN\",\"Website\":\"https:\\/\\/www.sourcelondon.net\",\"TelephoneNo\":\"020 3056 8989\"},\"DeviceController\":{\"OrganisationName\":\"Source London\",\"SchemeCode\":\"SRC_LDN\",\"Website\":\"https:\\/\\/www.sourcelondon.net\",\"TelephoneNo\":\"020 3056 8989\"},\"DeviceAccess\":[],\"DeviceNetworks\":\"Source London\",\"ChargeDeviceStatus\":\"Out of service\",\"PublishStatus\":\"Published\",\"DeviceValidated\":\"0\",\"RecordModerated\":\"N\",\"RecordLastUpdated\":null,\"RecordLastUpdatedBy\":\"\",\"PaymentRequiredFlag\":false,\"PaymentDetails\":\"\",\"SubscriptionRequiredFlag\":false,\"SubscriptionDetails\":\"\",\"ParkingFeesFlag\":false,\"ParkingFeesDetails\":\"\",\"ParkingFeesUrl\":null,\"AccessRestrictionFlag\":false,\"AccessRestrictionDetails\":\"\",\"PhysicalRestrictionFlag\":false,\"PhysicalRestrictionText\":\"\",\"OnStreetFlag\":false,\"LocationType\":\"Retail car park\",\"Bearing\":null,\"Accessible24Hours\":false}]}";
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    //Tag for log messages
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /*
     * Query the National Charge Point Registry database and return an {@Link ArrayList} to represent a list of charge points
     */
    public static List<ChargePoint> fetchChargePointData (String requestUrl) {
        //Create URL object
        URL url = createUrl(requestUrl);
            Log.e(LOG_TAG, requestUrl);
        //Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@Link ArrayList)
        //List<ChargePoint> chargePoints = extractChargePoints(jsonResponse);
        //commented out for redundancy
        //Return the (@Link ArrayList)
        return extractChargePoints(jsonResponse);
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode() +" "+ urlConnection.getResponseMessage());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the charge point JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        //Log.e(LOG_TAG,"HTTP request done");
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link ChargePoint} objects that has been built up from
     * parsing a JSON response.
     */
    private static List<ChargePoint> extractChargePoints(String chargePointJSON) {

        // Create an empty ArrayList that we can start adding charge points to
        List<ChargePoint> chargePoints = new ArrayList<>();

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(chargePointJSON)){
            Log.e(LOG_TAG, "JSON string is empty");
            return null;
        }

        // Try to parse the chargePointJSON. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            //Log.e(LOG_TAG, "trying to separate charge point data");
            // build up a list of Charge Point objects with the corresponding data.
            JSONObject chargePointData = new JSONObject(chargePointJSON);
            JSONArray chargeDevices = chargePointData.getJSONArray("ChargeDevice");
            //Log.e(LOG_TAG, "there are "+chargeDevices.length()+" devices in the data");

            for (int i=0; i<chargeDevices.length(); i++){
                JSONObject chargeDevice = chargeDevices.getJSONObject(i);
                //extracts name of device
                String name = chargeDevice.getString("ChargeDeviceName");
                //extracts device status
                String status = chargeDevice.getString("ChargeDeviceStatus");
                boolean chargePointStatus;
                chargePointStatus = status.equals("In service");
                //extracts location (latitude, longitude, address and postcode
                JSONObject deviceLocation = chargeDevice.getJSONObject("ChargeDeviceLocation");
                double latitude = deviceLocation.getDouble("Latitude");
                double longitude = deviceLocation.getDouble("Longitude");
                JSONObject deviceAddress = deviceLocation.getJSONObject("Address");
                String postcode = deviceAddress.getString("PostCode");
                //try to catch null value displaying
                if (postcode == "null"){
                    postcode = "not available";
                }

                //find info on individual connectors
                JSONArray connectors = chargeDevice.getJSONArray("Connector");
                int connectorNumber = connectors.length();
                int workingConnectors = 0;
                String[] connectorTypes =  new String[connectorNumber];
                //iterate through each connector and check their Service Status and connector type
                for (int j = 0; j<connectorNumber; j++){
                    JSONObject connector = connectors.getJSONObject(j);
                    String individualStatus = connector.getString("ChargePointStatus");
                    connectorTypes[j] = connector.getString("ConnectorType");
                    if (individualStatus.equals("In service")){
                        workingConnectors++;
                    }
                }
                //determine if all the connectors are of the same type
                boolean sameConnectors = true;
                for (int k = 1; k<connectorNumber; k++) {
                    String s1 = connectorTypes[k-1];
                    String s2 = connectorTypes[k];
                    sameConnectors = s1.equals(s2);
                }
                //extract connector type
                JSONObject connector1 = connectors.getJSONObject(0);
                String connectorType = connector1.getString("ConnectorType");
                if (!sameConnectors){
                     connectorType = "Multiple Connector Types";
                }


                //Create a new {@Link ChargePoint} object with all relevant details
                ChargePoint chargePoint = new ChargePoint(name,postcode,latitude,longitude,chargePointStatus,connectorType,connectorNumber, workingConnectors);
                //Add the new [@Link ChargePoint} to the list of chargePoints
                chargePoints.add(i,chargePoint);
            }



        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the charge point JSON results", e);
        }

       // Sort list of charge points by their distance from the user
        Collections.sort(chargePoints, new Comparator<ChargePoint>() {
            @Override
            public int compare(ChargePoint o1, ChargePoint o2) {
//                if (o1.getRealDistance() < o2.getRealDistance()) return -1;
//                if (o1.getRealDistance() > o2.getRealDistance()) return 1;
//                return 0;
                return Double.compare(o1.getRealDistance(), o2.getRealDistance());
            }
        });

        // Return the list of charge points
        return chargePoints;
    }

}
