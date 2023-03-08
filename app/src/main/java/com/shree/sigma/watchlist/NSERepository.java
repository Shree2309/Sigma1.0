package com.shree.sigma.watchlist;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NSERepository {
    private RequestQueue requestQueue;
    private MutableLiveData<List<NSEModel>> mDataList;
    private static final String TAG = "ApiRepository";
    private Application application;

    public NSERepository(Application application) {
        requestQueue = Volley.newRequestQueue(application);
        mDataList = new MutableLiveData<>();
    }

    ///DONT DELETE THIS COMMENT...***************************IMPORTANT************************************8
//    public LiveData<List<MCXModel>> getList() {
//        String url = "https://latest-stock-price.p.rapidapi.com/any";
//
//        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                try {
//                    List<MCXModel> mList = new ArrayList<>();
//                    JSONObject res = new JSONObject(response);
//                    JSONObject obj = res.getJSONObject("Time Series (5min)");
//
//                    Iterator<String> keys = obj.keys();
//
//                    while(keys.hasNext()) {
//                        String key = keys.next();
//
//                        JSONObject object = obj.getJSONObject(key);
//
//                        String open = object.getString("1. open");
//                        String high = object.getString("2. high");
//                        String low = object.getString("3. low");
//                        String close = object.getString("4. close");
//                        String volume = object.getString("5. volume");
//
//                        Log.d(TAG, "onResponse: key = "+key+" value = "+obj.keys());
//                        mList.add(new MCXModel(open,key,volume,high,low));
//
//                    }
//
//                    mDataList.setValue(mList);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(application, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }){
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//
//                Map<String, String> headers = new HashMap<>();
//                headers.put("X-RapidAPI-Key","3974559149msh84258e2243efa2cp1bdfd2jsn297814c42e1e");
//                headers.put("X-RapidAPI-Host","latest-stock-price.p.rapidapi.com");
//                return headers;
//            }
//        };
//
//        requestQueue.add(request);
//        return mDataList;
//    }

    public LiveData<List<NSEModel>> getList() {
        String url = "https://latest-stock-price.p.rapidapi.com/any";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                    try {
                        List<NSEModel> mList = new ArrayList<>();
                        for(int i=0;i<response.length();i++) {
                            JSONObject object = response.getJSONObject(i);

                            String symbol = object.getString("symbol");
                            String date = object.getString("lastUpdateTime");
                            String dayHigh = object.getString("dayHigh");
                            String dayLow = object.getString("dayLow");
                            String pChange = object.getString("pChange");

                            mList.add(new NSEModel(symbol,date,pChange,dayHigh,dayLow));

                        }
                        mDataList.setValue(mList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(application, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> headers = new HashMap<>();
                headers.put("X-RapidAPI-Key","3974559149msh84258e2243efa2cp1bdfd2jsn297814c42e1e");
                headers.put("X-RapidAPI-Host","latest-stock-price.p.rapidapi.com");
                return headers;
            }
        };

        requestQueue.add(request);
        return mDataList;
    }
}
