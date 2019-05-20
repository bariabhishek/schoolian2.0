package com.wikav.voulu;

import androidx.appcompat.app.AppCompatActivity;
import com.wikav.voulu.fragments.BottomSheetFragmentui;
import com.wikav.voulu.fragments.FullScreenDialogForNoInternet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AcceptedActivity extends AppCompatActivity {
    String Url = "https://voulu.in/api/getSiglePost.php";
    String id, title, jobGiverMobile,jobstatus,otp,jobsekerProfile, jobdis, jobGIverName, time, image, jobGiverProfile, pese, img2, img3,jobSeker_mobile,jobsekerName;
    BroadcastReceiver broadcastReceiver;
    TextView job_status,jobGiverName,jobSekerName,jobTitle,jobDis,jobTime,contactNumber;
    ImageView jobGiverPro,jobSeekerPro,statusMrk;
    String Url2="https://voulu.in/api/sendDataCompleteTaskAcceptePost.php";
    //push Test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_confirm_layout);
        id = getIntent().getStringExtra("id");
        job_status=findViewById(R.id.jobStatus);
        jobGiverName=findViewById(R.id.jobgivername);
        jobSekerName=findViewById(R.id.jobseekername);
        jobTitle=findViewById(R.id.jobtitle);
        jobDis=findViewById(R.id.jobdis);
        jobTime=findViewById(R.id.time);
        contactNumber=findViewById(R.id.contact);
        jobGiverPro=findViewById(R.id.jobgiver);
        jobSeekerPro=findViewById(R.id.jobseeker);
        statusMrk=findViewById(R.id.statusMark);
        checkIntenet();
        getPost(id);
    }

    private void getPost(final String id) {
        RequestQueue requestQueue;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("Post");
                            if (success.equals("1")) {
                                Log.d("Response", response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    title = object.getString("title").trim();
                                    jobGiverMobile = object.getString("mobile").trim();
                                    jobsekerProfile = object.getString("seeker_profile").trim();
                                    jobdis = object.getString("des").trim();
                                    pese = object.getString("rate").trim();
                                    image = object.getString("img").trim();
                                    img2 = object.getString("img2").trim();
                                    img3 = object.getString("img3").trim();
                                    jobSeker_mobile = object.getString("job_seeker").trim();
                                    jobstatus = object.getString("job_status").trim();
                                    jobsekerName = object.getString("seeker_name").trim();
                                    time = object.getString("time").trim();
                                    jobGiverProfile = object.getString("profile").trim();
                                    jobGIverName = object.getString("username").trim();
                                    otp = object.getString("otp").trim();
                                    setValue(title,jobGiverMobile,jobsekerName,jobGIverName,jobSeker_mobile,jobdis,time,jobstatus,jobGiverProfile,jobsekerProfile);
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
                        Toast.makeText(AcceptedActivity.this, "Something went wrong..." + error, Toast.LENGTH_LONG).show();
                        // noData.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("postId", id);
                params.put("key", "9195A3CDB388F894B3EE3BD665DFD");
                return params;
            }
        };
        stringRequest.setShouldCache(true);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();

    }

    private void setValue(String title, String jobGiverMobile,
                          String jobsekerName, String jobGIverName,
                          String jobSeker_mobile, String jobdis, String time, String jobstatus, String jobGiverProfile, String jobsekerProfile) {
    job_status.setText(jobstatus);
    jobTitle.setText(title);
    jobTime.setText(time);
    contactNumber.setText(jobSeker_mobile);
    jobDis.setText(jobdis);
    jobGiverName.setText(jobGIverName);
    jobSekerName.setText(jobsekerName);
        Glide.with(this).load(jobsekerProfile).into(jobSeekerPro);
        Glide.with(this).load(jobGiverProfile).into(jobGiverPro);
    }
    public void getOtp(View view) {
        BottomSheetFragmentui bottomSheetFragmentui=new BottomSheetFragmentui();
        Bundle bundle=new Bundle();
        bundle.putString("otp",otp);
        bundle.putString("seekerName",jobsekerName);
        bundle.putString("seekerMobile",jobSeker_mobile);
        bottomSheetFragmentui.setArguments(bundle);
        bottomSheetFragmentui.show(getSupportFragmentManager(),"bottomSheet");

    }
    public void checkIntenet() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int[] type = {ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI};
                if (ConnectivityReceiver.isNetworkAvailable(context, type)) {
                    return;
                } else {
                    FullScreenDialogForNoInternet full = new FullScreenDialogForNoInternet();
                    full.show(getSupportFragmentManager(), "show");
                }
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
    }



    @Override
    protected void onPause() {
        super.onPause();
        if (broadcastReceiver!= null)
            unregisterReceiver(broadcastReceiver);

    }
}
