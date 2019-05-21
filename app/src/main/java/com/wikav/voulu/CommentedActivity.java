package com.wikav.voulu;

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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.android.material.snackbar.Snackbar;
import com.wikav.voulu.Adeptor.CommentedPostAdaptor;
import com.wikav.voulu.Adeptor.ModelList;
import com.wikav.voulu.fragments.FullScreenDialogForNoInternet;

public class CommentedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List <ModelList> commentList;
    SessionManger sessionManger;
    Toolbar toolbar;
    ShimmerFrameLayout mShimmer;
    TextView noDataCommentedPost;
    String Url="https://voulu.in/api/getJobPostCommented.php";
    BroadcastReceiver broadcastReceiver;
    Snackbar snackbar;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_commented );
        toolbar=findViewById(R.id.toolbar_CommentedActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Your Commented Posts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById( R.id.commentedRecycleview );
        mShimmer = findViewById( R.id.shimmer_view_container );
        swipeRefreshLayout = findViewById( R.id.commentedSwipe );
        noDataCommentedPost = findViewById( R.id.noDataCommentedPost );
        commentList = new ArrayList <>(  );
        snackbar=  Snackbar.make(this.findViewById(android.R.id.content), Html.fromHtml("<font color=\"#ffffff\">No Internet Connection</font>"), Snackbar.LENGTH_INDEFINITE);
        sessionManger=new SessionManger(this);
        HashMap<String,String> getUser=sessionManger.getUserDetail();
        final String  userMobile=getUser.get(sessionManger.MOBILE);
        checkInptenet();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                commentList.clear();
                data(userMobile);

            }
        });


        data(userMobile);


    }

    private void setUpRecycler(List<ModelList> commentList) {
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()) );
        CommentedPostAdaptor commentedAdaptor = new CommentedPostAdaptor( getApplicationContext(), commentList);
        recyclerView.setAdapter( commentedAdaptor );
        mShimmer.stopShimmerAnimation();
        mShimmer.setVisibility(View.GONE);
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

    private void data(final String userMobile) {
        mShimmer.startShimmerAnimation();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //    progressDialog.dismiss();
                        Log.i("RESPO", response.toString());
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
                                    commentList.add( new ModelList(img,title,des,rate,id,time,mobile,like,profile,username,share,img2,img3) );
                                }
                                setUpRecycler(commentList);

                            }
                            else

                            {
                                recyclerView.setVisibility(View.GONE);
                                noDataCommentedPost.setVisibility(View.VISIBLE);
                                mShimmer.stopShimmerAnimation();
                                mShimmer.setVisibility(View.GONE);
                                if(swipeRefreshLayout.isRefreshing())
                                {
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //  Toast.makeText(getActivity(), "Something went wrong..."+e, Toast.LENGTH_LONG).show();

                            recyclerView.setVisibility(View.GONE);
                            noDataCommentedPost.setVisibility(View.VISIBLE);
                            mShimmer.stopShimmerAnimation();
                            mShimmer.setVisibility(View.GONE);
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
                        ///    Toast.makeText(getActivity(), "Something went wrong..."+error, Toast.LENGTH_LONG).show();
                        recyclerView.setVisibility(View.GONE);
                        noDataCommentedPost.setVisibility(View.VISIBLE);
                        mShimmer.stopShimmerAnimation();
                        mShimmer.setVisibility(View.GONE);
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
                params.put("mobile",userMobile);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();




    }
    public void checkInptenet() {
        IntentFilter  intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
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

}
