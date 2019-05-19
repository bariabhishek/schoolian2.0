package com.wikav.voulu;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class VolleyRequest {
    public Context context;
   private RequestQueue requestQueue;// = Volley.newRequestQueue(getActivity());

       private String mainResponse;
   private String Url;
   private Map<String, String> params;


    public VolleyRequest(Context context, Map<String, String> params,String Url) {
        this.context = context;
        this.params = params;
        this.Url = Url;
    }


    public void stringRequests(boolean clear) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            setMainResponse(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Something went wrong..."+error, Toast.LENGTH_LONG).show();
                        // noData.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        stringRequest.setShouldCache(true);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue=Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
        if (clear) {
                requestQueue.getCache().clear();
            }


        }
    public String getMainResponse() {
        return mainResponse;
    }

    public void setMainResponse(String mainResponse) {
        this.mainResponse = mainResponse;
    }
}
