package com.wikav.voulu;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;


public class JobSchedulerService extends JobService {
    String TAG = "myJob";
    String Url = "https://voulu.in/api/getJobPost.php";
    String Url2 = "https://voulu.in/api/pendingTask.php";
    DatabaseHelper mydb;
    boolean reponseHit = true;
    boolean jobCancelled = false;
    String JobWork="MyJobWork";
SharedPreferences sharedpreferences;
    public String getJobWork() {
        return JobWork;
    }



    CountDownTimer newTime;
    @Override
    public boolean onStartJob(final JobParameters params) {
        mydb = new DatabaseHelper(getApplicationContext());
        doBackgroundWork(params);
        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        SessionManger sessionManger = new SessionManger(this);
        HashMap<String,String> user=sessionManger.getUserDetail();
        final String  phone = user.get(sessionManger.MOBILE);


        if (!jobCancelled) {

            newTime = new CountDownTimer(15 * 60 * 1000, 10000) {
                public void onTick(long millisUntilFinished) {
                    checkPendingTask(phone);
                    arraydata();
                    Log.d(TAG, "00:" + millisUntilFinished / 10000);

                }

                public void onFinish() {
                    doBackgroundWork(params);
                    Log.d(TAG, "JobStop");
                }
            }.start();
        }
        else
        {
            newTime.cancel();
        }
        Log.d(TAG, "JobStop");
        jobFinished(params, false);

    }


    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob:");
        jobCancelled = true;
        newTime.cancel();
        return false;
    }

    private void arraydata() {
      /*  sharedpreferences = getSharedPreferences(JobWork, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedpreferences.edit();
        editor.putString("mywork","job");
        editor.apply();*/
        Log.d(TAG, "JabStart");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("allPost");
                            if (success.equals("1")) {
                                reponseHit = false;
                                Log.d("Response", response);
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String title = object.getString("title").trim();
                                    String mobile = object.getString("mobile").trim();
                                    String des = object.getString("des").trim();
                                    String rate = object.getString("rate").trim();
                                    String img = object.getString("img").trim();
                                    String img2 = object.getString("img2").trim();
                                    String img3 = object.getString("img3").trim();
                                    String id = object.getString("id").trim();
                                    String time = object.getString("time").trim();
                                    String profile = object.getString("profile").trim();
                                    String username = object.getString("username").trim();
                                    String like = object.getString("like").trim();
                                    // String share = object.getString("share").trim();
                                    String status = object.getString("status").trim();
                                    boolean db = mydb.insertData(username, profile, title, des, time, id, img, like, img2, img3, status, mobile, rate);
                                    // list.add(new ModelList(img, title, des, rate, id, time, mobile, like, profile, username, status, img2, img3));
                                    if (db) {
                                        Log.i("data", "" + i);
                                    } else {
                                        Log.i("No", "" + i);

                                    }
                                }

                            } else {

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // noData.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", "9195A3CDB388F894B3EE3BD665DFD");
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
       // requestQueue.getCache().clear();

    }
    private void checkPendingTask(final String phone) {

        sharedpreferences = getSharedPreferences(JobWork, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor= sharedpreferences.edit();

        Log.d(TAG, "JabStart");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("task");
                            if (success.equals("1")) {
                                Log.d("JOB_Response", response);

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String taskId = object.getString("complete_id").trim();
                                    editor.putString("taskId",taskId);
                                    editor.putBoolean("showNote",true);
                                    editor.apply();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // noData.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", phone);
                params.put("key", "9195A3CDB388F894B3EE3BD665DFD");
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        // requestQueue.getCache().clear();

    }
}
