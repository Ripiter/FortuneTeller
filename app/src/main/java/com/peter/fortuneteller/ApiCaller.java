package com.peter.fortuneteller;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ApiCaller {
    public String url = "http://yerkee.com/api/fortune";
    ArrayList<JokeWatcher> jokers = new ArrayList<>();

    public void addListener(JokeWatcher watcher){
        jokers.add(watcher);
    }

    public void removeListener(JokeWatcher watcher){
        jokers.remove(watcher);
    }


    public void getRequest(Context context){

        RequestQueue queue = Volley.newRequestQueue(context);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            //Log.e("Repsonse", "obj: " + response.toString());

                            for (JokeWatcher watcher : jokers){
                                watcher.update(response.getString("fortune"));
                            }

                        } catch (Exception e) {

                            //Log.e("Repsonse", "E: " + e.toString());
                            for (JokeWatcher watcher : jokers){
                                watcher.errorUpdate(e.getMessage());
                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Log.e("Repsonse", "E: " + error.toString());
                        for (JokeWatcher watcher : jokers){
                            watcher.errorUpdate(error.getMessage());
                        }
                    }
                });
        queue.add(jsonObjectRequest);
    }
}
