package com.example.a1mgclone.network;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.a1mgclone.utils.ApplicationContext;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class API {
    // interface to get the response
    public interface ResponseListener {
        void onResponse(JSONObject response);
    }

    // interface to get the error
    public interface ErrorListener {
        void onError(String error);
    }

    // api method
    private int method;
    private String url = "";
    private JSONObject body;
    static private RequestQueue queue =
            Volley.newRequestQueue(ApplicationContext.getContext());

    public static API request(String url) {
        API request = new API();
        request.url = url;
        request.method = Request.Method.POST;
        return request;
    }

    public static API request(String url, int method) {
        API request = new API();
        request.url = url;
        request.method = method;
        return request;
    }

    public API jsonBody(JSONObject body) {
        this.body = body;
        return this;
    }

    public void call(ResponseListener responseListener, ErrorListener errorListener) {
        final String serverUrl = "http://192.168.1.4:4000" + url;
        JsonObjectRequest request = new JsonObjectRequest(
                method, serverUrl, body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")) {
                                responseListener.onResponse(response);
                            } else {
                                String error = response.getString("error");
                                errorListener.onError(error);
                            }
                        } catch (Exception ex) {
                            errorListener.onError(ex.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorListener.onError(error.toString());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences preferences =
                        PreferenceManager.getDefaultSharedPreferences(ApplicationContext.getContext());
                String token = preferences.getString("token", null);
                if (token == null) {
                    return super.getHeaders();
                } else {
                    // add token to the headers
                    HashMap<String, String> map = new HashMap<>();
                    map.put("token", token);
                    return map;
                }
            }
        };

        queue.add(request);
    }


}
