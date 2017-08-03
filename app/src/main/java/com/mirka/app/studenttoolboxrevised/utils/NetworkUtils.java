package com.mirka.app.studenttoolboxrevised.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Miras on 7/31/2017.
 */

public class NetworkUtils {


    /* NETWORK ERRORS */
    public static final int INVALID_NETWORK_RESPONSE = 102;
    public static final int CONNECTION_ERROR = 101;

    public static JSONObject getJsonResponseFromURL(String url){
        String responseBody = getResponseFromUrl(url);

        JSONObject jObject = null;
        JSONArray jsonArray = null;
        if (responseBody == null) {
            return ErrorUtils.buildErrorMessage(CONNECTION_ERROR, "Error connecting to server");
        }
        try {
            jsonArray = new JSONArray(responseBody);
            jObject = new JSONObject();
            jObject.put("list", jsonArray);
        } catch (JSONException e) {
            try {
                jObject = new JSONObject(responseBody);
            } catch (JSONException e1) {
                e.printStackTrace();
                return ErrorUtils.buildErrorMessage(INVALID_NETWORK_RESPONSE, e1.getMessage());
            }
        }

        return jObject;
    }

    public static String getResponseFromUrl(String url) {
        String responseBody;

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();


        try {
            Response response = client.newCall(request).execute();
            ResponseBody rb = response.body();
            if (rb != null) {
                responseBody = rb.string();
            } else {
                throw new IOException("no response");
            }
        } catch (IOException e) {
            responseBody = null;
            e.printStackTrace();
        }
        return responseBody;
    }


}
