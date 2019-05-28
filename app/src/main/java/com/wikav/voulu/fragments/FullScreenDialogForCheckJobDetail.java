package com.wikav.voulu.fragments;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;
import com.wikav.voulu.AcceptedActivity;
import com.wikav.voulu.Adeptor.CommentAdaptor;
import com.wikav.voulu.Adeptor.CommentModel;
import com.wikav.voulu.R;
import com.wikav.voulu.SessionManger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class FullScreenDialogForCheckJobDetail extends DialogFragment {

    String Url = "https://voulu.in/api/getSiglePost.php";
    String id, title, jobGiverMobile,jobstatus,otp,jobsekerProfile, jobdis, jobGIverName, time, image, jobGiverProfile, pese, img2, img3,jobSeker_mobile,jobsekerName;
    BroadcastReceiver broadcastReceiver;
    TextView job_status,jobGiverName,jobSekerName,jobTitle,jobDis,jobTime,contactNumber;
    ImageView jobGiverPro,jobSeekerPro,statusMrk;
    String Url2="https://voulu.in/api/sendDataCompleteTaskAcceptePost.php";
    //push Test
    Snackbar snackbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);


        id=getArguments().getString("id");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_full_screen_dialog_for_check, container, false);
        job_status=view.findViewById(R.id.jobStatus);
        jobGiverName=view.findViewById(R.id.jobgivername);
        jobSekerName=view.findViewById(R.id.jobseekername);
        jobTitle=view.findViewById(R.id.jobtitle);
        jobDis=view.findViewById(R.id.jobdis);
        jobTime=view.findViewById(R.id.time);
        contactNumber=view.findViewById(R.id.contact);
        jobGiverPro=view.findViewById(R.id.jobgiver);
        jobSeekerPro=view.findViewById(R.id.jobseeker);
        getPost(id);
        return view;
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
                        Toast.makeText(getActivity(), "Something went wrong..." + error, Toast.LENGTH_LONG).show();
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
        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();

    }
    private void setValue(String title, String jobGiverMobile, String jobsekerName, String jobGIverName, String jobSeker_mobile, String jobdis, String time, String jobstatus, String jobGiverProfile, String jobsekerProfile) {

        if(jobstatus.equals("Done")||jobstatus.equals("done"))
        {
            job_status.setText(jobstatus);
        }
        else {
            statusMrk.setImageResource(R.drawable.ic_info_black_24dp);
            job_status.setTextColor(Color.parseColor("#FFB45A"));
            job_status.setText(jobstatus);
        }
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
        bundle.putString("seekerName",jobGIverName);
        bundle.putString("seekerMobile",jobGiverMobile);
        bottomSheetFragmentui.setArguments(bundle);
        bottomSheetFragmentui.show(getActivity().getSupportFragmentManager(),"bottomSheet");

    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }


}
