package com.mirka.app.studenttoolboxrevised.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Miras on 7/31/2017.
 */

public class MoodleUtils {


    public static String getToken(String moodleURI, String username, String password){

        String url = "https://" + moodleURI + "/login/token.php?username=" + username + "&password=" + password + "&service=moodle_mobile_app";
        String responseBody;
        String token = "";

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();


        try {

            Log.i("TOKEN_RESPONSE", "Sent request");
            Response response = client.newCall(request).execute();
            Log.i("TOKEN_RESPONSE", "Got a reponse");
            if (response.body() != null) {
                responseBody = response.body().string();
                Log.i("TOKEN_RESPONSE", responseBody);
                JSONObject jObject = new JSONObject(responseBody);
                token = jObject.getString("token");
            } else {
                Log.i("TOKEN_RESPONSE", "response body is null");
                throw new IOException("no response");
            }
        } catch (JSONException | IOException e) {
            // TODO Auto-generated catch block

            Log.i("TOKEN_RESPONSE", e.getMessage());
            e.printStackTrace();
        }

        return token;
    }

}
