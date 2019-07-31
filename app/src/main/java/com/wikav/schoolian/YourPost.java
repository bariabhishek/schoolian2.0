package com.wikav.schoolian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.wikav.schoolian.Adeptor.ModelList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
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
import com.facebook.shimmer.ShimmerFrameLayout;
import com.wikav.schoolian.Adeptor.MyAdeptor;
import com.wikav.schoolian.Adeptor.MyModelList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YourPost extends AppCompatActivity {
Toolbar toolbar;
    ImageView backbtn;
    BroadcastReceiver broadcastReceiver;
    IntentFilter intentFilter;
RecyclerView recyclerView;
    List<MyModelList> list;
    TextView noData;
    String sid;
    private ShimmerFrameLayout mShimmerViewContainer;
    SessionManger sessionManger;
    SwipeRefreshLayout swipeRefreshLayout;
    Snackbar snackbar;
    String Url="https://schoolian.website/android/getUserPost.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_post);
        snackbar=  Snackbar.make(YourPost.this.findViewById(android.R.id.content),
                Html.fromHtml("<font color=\"#ffffff\">No Internet Connection</font>"),
                BaseTransientBottomBar.LENGTH_INDEFINITE);

        toolbar=findViewById(R.id.toolbarLayout);
        backbtn = findViewById( R.id.backuploadpost );
        sessionManger=new SessionManger(this);
        setSupportActionBar(toolbar);
        mShimmerViewContainer=findViewById(R.id.shimmer_view_container);
        swipeRefreshLayout=findViewById(R.id.youPostSwipe);
//        getSupportActionBar().setTitle("Your Posts");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
        recyclerView=findViewById(R.id.recycleviewYourPost);
        noData=findViewById(R.id.noDataYourPost);
        HashMap<String,String>user=sessionManger.getUserDetail();
       // String Ename = user.get(sessionManger.NAME);
        String mobile = user.get(sessionManger.SCL_ID);
         sid = user.get(sessionManger.SID);
        list=new ArrayList<>();
        arraydata(mobile,sid);
        checkInptenet();
      // checkIntenet();


        backbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );

    }
    private void arraydata(final String  sclId, final String sid) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("MAINResponse", response+sid+sclId);
                        try {
                            JSONObject json = new JSONObject(response);
                            String success = json.getString("success");
                            JSONArray jsonArray = json.getJSONArray("userPost");
                            if (success.equals("1")) {


                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    MyModelList anime=new MyModelList();
                                    anime.setName(jsonObject.getString("st_name"));
                                    anime.setImage_url(jsonObject.getString("post_pic"));
                                    anime.setDescription(jsonObject.getString("posts"));
                                    anime.setPostId(jsonObject.getString("post_id"));
                                    anime.setProfile_pic(jsonObject.getString("profile_pic"));
                                    anime.setSid(sid);
                                    anime.setTime(jsonObject.getString("time"));
                                    anime.setImage_url(jsonObject.getString("post_pic"));
                                    list.add(anime);

                                }
                                if (swipeRefreshLayout.isRefreshing()) {
                                    swipeRefreshLayout.setRefreshing(false);
                                }

                                setupRecycle(list);

                            } else if(success.equals("0")) {

                                noData.setVisibility(View.VISIBLE);
                                mShimmerViewContainer.stopShimmerAnimation();
                                mShimmerViewContainer.setVisibility(View.GONE);
                                if (swipeRefreshLayout.isRefreshing()) {
                                    swipeRefreshLayout.setRefreshing(false);
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
                      //  Toast.makeText(getApplicationContext(), "Something went wrong..." + error, Toast.LENGTH_LONG).show();
                        // noData.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                // params.put("key", "9195A3CDB388F894B3EE3BD665DFD");
                params.put("school_id", sclId);
                params.put("sid", sid);
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
        requestQueue.getCache().clear();


    }
   /* private void arraydata(final String mobile) {
        mShimmerViewContainer.startShimmerAnimation();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //    progressDialog.dismiss();
                        Log.i("TAG", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("allPost");
                            if (success.equals("1")){
                                Log.d("Response",response);
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
                                    String share = object.getString("status").trim();
                                    list.add( new ModelList(img,title,des,rate,id,time,mobile,like,profile,username,share,img2,img3) );

                                }
                                setupRecycle(list);

                            }
                            else
                            {
                                noData.setVisibility(View.VISIBLE);
                                mShimmerViewContainer.stopShimmerAnimation();
                                mShimmerViewContainer.setVisibility(View.GONE);
                                if(swipeRefreshLayout.isRefreshing())
                                {
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(getActivity(), "Something went wrong..."+e, Toast.LENGTH_LONG).show();
                            //  noData.setVisibility(View.VISIBLE);
                            noData.setVisibility(View.VISIBLE);
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            if(swipeRefreshLayout.isRefreshing())
                            {
                                swipeRefreshLayout.setRefreshing(false);
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        /// Toast.makeText(getActivity(), "Something went wrong..."+error, Toast.LENGTH_LONG).show();
                        // noData.setVisibility(View.VISIBLE);
                        noData.setVisibility(View.VISIBLE);
                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);
                        if(swipeRefreshLayout.isRefreshing())
                        {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("key", "9195A3CDB388F894B3EE3BD665DFD");
                params.put("mobile", mobile);
                return params;
            }
        };

        stringRequest.setShouldCache(true);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();




    }*/
    public void setupRecycle(List <MyModelList> list)
    {
        MyAdeptor a = new MyAdeptor(getApplicationContext(), list,sid);
        recyclerView.setHasFixedSize( true );
        recyclerView.setItemAnimator( new DefaultItemAnimator() );
        recyclerView.setLayoutManager(new LinearLayoutManager(this) );
        recyclerView.setAdapter( a );
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);
        if(swipeRefreshLayout.isRefreshing())
        {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
    public void checkInptenet() {
        intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int[] type = {ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI};


                if (ConnectivityReceiver.isNetworkAvailable(getApplicationContext(), type)) {
                    if (snackbar.isShown())
                        snackbar.dismiss();
                } else {
                    //Toast.makeText(context, "Toast", Toast.LENGTH_SHORT).show();
                /*FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                FullScreenDialogForNoInternet full=new FullScreenDialogForNoInternet();
                full.show(ft,"show");*/

                    snackbar.show();


                }

            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver!= null)
           unregisterReceiver(broadcastReceiver);

    }

    @Override
    protected void onResume() {
        super.onResume();
      //  registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //if (broadcastReceiver!= null)
        //    unregisterReceiver(broadcastReceiver);

    }

   /* @Override
    protected void onDestroy() {
        super.onDestroy();
    }*/
}
