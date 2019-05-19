package com.wikav.voulu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;
import com.wikav.voulu.Adeptor.CoustomSwipeAdeptor;
import com.wikav.voulu.fragments.FullScreenDialog;
import com.wikav.voulu.fragments.FullScreenDialogForNoInternet;

public class JobDiscriptionForNotification extends AppCompatActivity  {

    ViewPager viewPager;
    CoustomSwipeAdeptor coustomSwipeAdeptor;
    List<String> imageArry;
    Button showComment;
    CircleImageView profile;
    ImageView mainImage;
    TextView username,jobtTitle,jobdes,paise;
    String Url="https://voulu.in/api/getSinglePostForjobDes.php";
    String id,title;
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.new_job_dec );
            imageArry=new ArrayList<>();

            id=getIntent().getExtras().getString("id");
        initilization();
        getPost(id);

checkIntenet();
       getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        showComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click();
            }
        });

    }

    private void getPost(final String id) {
        RequestQueue requestQueue;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("FullRes", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("Post");
                            if (success.equals("1")) {
                                Log.d("FullRes", response);
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String  title = object.getString("title").trim();
                                    String jobdis = object.getString("des").trim();
                                    String  pese = object.getString("rate").trim();
                                    String  image = object.getString("img").trim();
                                    String img2 = object.getString("img2").trim();
                                    String img3 = object.getString("img3").trim();
                                    String jobstatus = object.getString("status").trim();
                                    String jobGiverProfile = object.getString("profile").trim();
                                    String obGIverName = object.getString("username").trim();
                                    setValues(jobGiverProfile,obGIverName,title,jobdis,pese,image,img2,img3,jobstatus);

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
                        Toast.makeText(JobDiscriptionForNotification.this, "Something went wrong..." + error, Toast.LENGTH_LONG).show();
                        // noData.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("postId", id);
                params.put("key", "9195A3CDB388F894B3EE3BD665DFD");
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();

    }


    private void setValues(String profile, String username, String title, String des, String rate, String img, String img2, String img3, String jobstatus) {
        Glide.with(this).load(profile).into(this.profile);
        this.title=title;
        this.username.setText(username);
        jobtTitle.setText(title);
        jobdes.setText(des);
        paise.setText("₹ "+rate);
        if(!img.equals("NO"))
        {
            imageArry.add(img);
            if(!img2.equals("NO"))
            {
                imageArry.add(img2);
                if (!img3.equals("NO")) {
                    imageArry.add(img3);
                }
            }
        }
        coustomSwipeAdeptor = new CoustomSwipeAdeptor( this,imageArry);
        viewPager.setAdapter( coustomSwipeAdeptor );
    }

    private void initilization() {

        viewPager = findViewById( R.id.viewpagerjob );

        profile = findViewById( R.id.postProfile );
        mainImage = findViewById( R.id.postMainImage );
        username = findViewById( R.id.postUsername );
        jobtTitle = findViewById( R.id.postJobTitle );
        showComment = findViewById( R.id.showComment );
        jobdes = findViewById( R.id.postJobdes );
        paise = findViewById( R.id.paisePost );
        showComment = findViewById( R.id.showComment );
    }

    public void click() {

        FullScreenDialog dialog =new FullScreenDialog();
        Bundle b=new Bundle();
        b.putString("id",id);
        b.putString("title",title);
        dialog.setArguments(b);
        dialog.show(getSupportFragmentManager(),"TAG");

    }
    public void checkIntenet()
    {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int [] type={ ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI};
                if(ConnectivityReceiver.isNetworkAvailable(context,type))
                {
                    return;
                }
                else {
                    FullScreenDialogForNoInternet full=new FullScreenDialogForNoInternet();
                    full.show(getSupportFragmentManager(),"show");
                }
            }
        };
        registerReceiver(broadcastReceiver,intentFilter);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (broadcastReceiver!= null)
            unregisterReceiver(broadcastReceiver);

    }

}
