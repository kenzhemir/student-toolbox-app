package com.mirka.app.studenttoolboxrevised.utils;

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
        String responseBody;
        JSONObject jObject = null;

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
                jObject = new JSONObject(responseBody);
            } else {
                throw new IOException("no response");
            }
        } catch (IOException e) {
            jObject = ErrorUtils.buildErrorMessage(CONNECTION_ERROR, e.getMessage());
            e.printStackTrace();
        } catch (JSONException e) {
            jObject = ErrorUtils.buildErrorMessage(INVALID_NETWORK_RESPONSE, e.getMessage());
            e.printStackTrace();
        }

        return jObject;
    }


}
