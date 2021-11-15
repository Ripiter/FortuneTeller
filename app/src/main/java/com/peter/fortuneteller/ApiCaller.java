package com.peter.fortuneteller;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ApiCaller implements  Response.Listener, Response.ErrorListener{
    public String url = "http://yerkee.com/api/fortune";
    ArrayList<FortuneWatchable> fortuneListeners = new ArrayList<>();

    public void addListener(FortuneWatchable watcher){
        fortuneListeners.add(watcher);
    }

    public void removeListener(FortuneWatchable watcher){
        fortuneListeners.remove(watcher);
    }


    public void getRequest(Context context){

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(Object response) {
        try {

            //Log.e("Repsonse", "obj: " + response.toString());

            Gson gson = new Gson();
            Fortune fortune = gson.fromJson(response.toString(), Fortune.class);

            //Log.e("Repsonse", "fort: " + fortune.getFortune());

            for (FortuneWatchable watcher : fortuneListeners){
                watcher.update(fortune);
            }

        } catch (Exception e) {

            //Log.e("Repsonse", "E: " + e.toString());
            for (FortuneWatchable watcher : fortuneListeners){
                watcher.errorUpdate(e.getMessage());
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //Log.e("Repsonse", "obj: " + error.toString());
        for (FortuneWatchable watcher : fortuneListeners){
            watcher.errorUpdate(error.getMessage());
        }
    }
}
