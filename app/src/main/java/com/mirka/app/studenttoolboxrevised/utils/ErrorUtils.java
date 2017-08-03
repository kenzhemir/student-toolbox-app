package com.mirka.app.studenttoolboxrevised.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Miras on 8/3/2017.
 */

public class ErrorUtils {
    private static  String TAG = "ErrorUtils: ";
    public static final String STATUS_CODE_KEY = "internal_status_code";
    public static final String ERROR_MESSAGE_KEY = "internal_error_message";

    public static JSONObject buildErrorMessage(int code, String message) {
        try {
            return new JSONObject("{'"+ STATUS_CODE_KEY +"': "+ code +", '" + ERROR_MESSAGE_KEY + "': " + message + "}");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
}
