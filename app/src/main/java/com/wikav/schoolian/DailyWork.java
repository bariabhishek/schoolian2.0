package com.wikav.schoolian;

import android.os.Bundle;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.wikav.schoolian.Adeptor.AdepterForDailyWork;
import com.wikav.schoolian.Adeptor.CoustomSwipeAdeptorForHome;
import com.wikav.schoolian.Adeptor.MyAdeptor;
import com.wikav.schoolian.Adeptor.MyModelList;
import com.wikav.schoolian.Adeptor.MyModelListForDailyWork;
import com.wikav.schoolian.fragments.UplodadPic;
import com.wikav.schoolian.fragments.UplodadQuery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class DailyWork extends AppCompatActivity {

    SessionManger sessionManger;
    RecyclerView recyclerView;
    List<MyModelListForDailyWork> list;
    TextView noData;
    List<String> imageArry;
    ImageButton imageButton;
    String Url = "https://schoolian.in/android/newApi/getDailyWorkData.php",Sid,Class;
    private RequestQueue requestQueue;
    private ShimmerFrameLayout mShimmerViewContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_work);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.recyclerViewForDailyWork);
        mShimmerViewContainer=findViewById(R.id.shimmerForDailywork);
        noData=findViewById(R.id.noData);
        list=new ArrayList<>();
        sessionManger=new SessionManger(this);
        HashMap<String, String> user = sessionManger.getUserDetail();
        final String sclId = user.get(sessionManger.SCL_ID);
        Sid = user.get(sessionManger.SID);
        Class = user.get(sessionManger.CLAS);

        arraydata(sclId,Class,Sid);

        FloatingActionButton fab = findViewById(R.id.fabAicon);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UplodadQuery uplodadPic = new UplodadQuery();
                uplodadPic.show(getSupportFragmentManager(), "show");
            }
        });
    }


    public void setupRecycle(List<MyModelListForDailyWork> list) {
        mShimmerViewContainer.startShimmerAnimation();
        AdepterForDailyWork a = new AdepterForDailyWork(this, list,Sid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(a);
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);
    }

    private void arraydata(final String  sclId, final String cls, final String sid) {
        mShimmerViewContainer.startShimmerAnimation();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("MAINResponse", response);
                        try {
                            JSONObject json = new JSONObject(response);
                            String success = json.getString("success");
                            JSONArray jsonArray = json.getJSONArray("userPost");
                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    MyModelListForDailyWork anime=new MyModelListForDailyWork();
                                    anime.setName(jsonObject.getString("st_name"));
                                    anime.setMsg(jsonObject.getString("posts"));
                                    anime.setProfile_pic(jsonObject.getString("profile_pic"));
                                    anime.setPostid(jsonObject.getString("post_id"));
                                    anime.setSid(jsonObject.getString("sid"));
                                    anime.setTime(jsonObject.getString("time"));
                                    anime.setSubject(jsonObject.getString("subject"));
                                    anime.setImage_url(jsonObject.getString("post_pic"));
                                    list.add(anime);

                                }

                                setupRecycle(list);

                            } else {
                                noData.setVisibility(View.VISIBLE);
                                mShimmerViewContainer.stopShimmerAnimation();
                                mShimmerViewContainer.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Something went wrong..." + error, Toast.LENGTH_LONG).show();
                        // noData.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                // params.put("key", "9195A3CDB388F894B3EE3BD665DFD");
                params.put("school_id", sclId);
                params.put("cls", cls);
                params.put("sid", sid);
                return params;
            }
        };
        stringRequest.setShouldCache(true);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();


    }
}
