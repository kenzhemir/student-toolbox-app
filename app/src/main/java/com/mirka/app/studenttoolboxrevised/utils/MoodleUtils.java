package com.mirka.app.studenttoolboxrevised.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Miras on 7/31/2017.
 */

public class MoodleUtils {


    /* MOODLE ERRORS */
    public static final int INVALID_USERNAME = 201;
    public static final int INVALID_TOKEN = 202;
    public static final int GENERIC_ERROR = 203;

    public static JSONObject getMoodleResponse(String moodleUrl, String token, String function, String urlParams){

        String serverurl = moodleUrl + "/webservice/rest/server.php?wstoken=" + token + "&wsfunction=" + function + "&moodlewsrestformat=json" + urlParams;
        JSONObject jsonObject = NetworkUtils.getJsonResponseFromURL(serverurl);
        if (jsonObject.has("exception")) {
            try {
                if (jsonObject.getString("errorcode").equals("invalidtoken")) {
                    jsonObject = ErrorUtils.buildErrorMessage(INVALID_TOKEN, "Invalid token - token not found");
                } else {
                    // TODO add more error types?
                    jsonObject = ErrorUtils.buildErrorMessage(GENERIC_ERROR, jsonObject.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                try {
                    jsonObject = ErrorUtils.buildErrorMessage(GENERIC_ERROR, jsonObject.getString("message"));
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    jsonObject = ErrorUtils.buildErrorMessage(GENERIC_ERROR, "no message");
                }
            }
        }
        return jsonObject;
    }


    /**
     *
     * This function used to get a token from moodle-based website
     *
     * @param moodleURI
     * @param username
     * @param password
     * @return
     */
    public static JSONObject getToken(String moodleURI, String username, String password){

        String url = "https://" + moodleURI + "/login/token.php?username=" + username + "&password=" + password + "&service=moodle_mobile_app";
        JSONObject jsonObject = NetworkUtils.getJsonResponseFromURL(url);
        if (jsonObject.has("error")) {
            try {
                jsonObject = ErrorUtils.buildErrorMessage(INVALID_USERNAME, jsonObject.getString("error"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    public static JSONObject getUserInfo(String moodleUrl, String token) {
        return getMoodleResponse(moodleUrl, token, "moodle_webservice_get_siteinfo", "");
    }

}
