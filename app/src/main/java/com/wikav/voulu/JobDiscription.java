package com.wikav.voulu;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.jobdispatcher.Job;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.wikav.voulu.Adeptor.CommentAdaptor;
import com.wikav.voulu.Adeptor.CommentModel;
import com.wikav.voulu.Adeptor.CommentOnDisAdeptor;
import com.wikav.voulu.Adeptor.CommentedPostAdaptor;
import com.wikav.voulu.Adeptor.CoustomSwipeAdeptor;
import com.wikav.voulu.Adeptor.JobDisCommentData;
import com.wikav.voulu.Adeptor.ModelList;
import com.wikav.voulu.fragments.FullScreenDialogForComment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JobDiscription extends AppCompatActivity {
    final String Url = "http://voulu.in/api/sendComment.php";
    final String Url2 = "http://voulu.in/api/getComments.php";
    BroadcastReceiver broadcastReceiver;
    RecyclerView recyclerView;
    ViewPager viewPager;
    CoustomSwipeAdeptor coustomSwipeAdeptor;
    List<String> imageArry;
    Button showComment;
    CircleImageView profile;
    ImageView mainImage;
    TextView username, jobtTitle, jobdes, paise;
    Snackbar snackbar;
    String id, title, jobdis, usernames, image, profileImage, pese,status;
    EditText commentBox;
    TextView sendBtn;
    SessionManger sessionManger;
    List<CommentModel> list;
    ShimmerFrameLayout mShimmerViewContainer;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout bottomEdit;
    Toolbar toolbar;
    AppBarLayout appBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.for_job_description);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        profile = findViewById(R.id.postProfile);
        mainImage = findViewById(R.id.postMainImage);
        username = findViewById(R.id.postUsername);
        jobtTitle = findViewById(R.id.postJobTitle);
        jobdes = findViewById(R.id.postJobdes);
        paise = findViewById(R.id.paisePost);
        showComment = findViewById(R.id.showComment);
        imageArry = new ArrayList<>();
        viewPager = findViewById(R.id.viewpagerjob);
        recyclerView = findViewById(R.id.recycleview);
        mShimmerViewContainer =findViewById(R.id.shimmer_view_container);
        swipeRefreshLayout =findViewById(R.id.CommentSwipe);
        sessionManger =new SessionManger(this);
        HashMap<String,String> user=sessionManger.getUserDetail();
        final String mobile = user.get(sessionManger.MOBILE);
        sendBtn=findViewById(R.id.sedCommentButton);
        commentBox=findViewById(R.id.CommenBox);
        bottomEdit=findViewById(R.id.bottomEdit);
        appBarLayout=findViewById(R.id.app_barLaout);
        list = new ArrayList<>();
        getIntentData();
        checkInptenet();
        coustomSwipeAdeptor = new CoustomSwipeAdeptor(this, imageArry);
        viewPager.setAdapter(coustomSwipeAdeptor);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String commnet=commentBox.getText().toString().trim();
                if(!commnet.equals(""))
                {
                    sendComment(commnet,mobile,id);
                }
                else
                {
                    commentBox.setError("Please type");
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                getCommnetData(id);

            }
        });




    }


    public void click() {

        FullScreenDialogForComment dialog = new FullScreenDialogForComment();
        Bundle b = new Bundle();
        b.putString("id", id);
        b.putString("title", title);
        dialog.setArguments(b);
        dialog.show(getSupportFragmentManager(), "TAG");

    }

    public void checkInptenet() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
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

    public void getCommnetData(final String post_id) {
        mShimmerViewContainer.startShimmerAnimation();
        RequestQueue requestQueue = Volley.newRequestQueue(JobDiscription.this);
        requestQueue.getCache().clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url2,
                new Response.Listener<String>()
                {

                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("allPost");
                            if (success.equals("1")){
                                Log.d("Response",response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String comment = object.getString("comment").trim();
                                    String username = object.getString("username").trim();
                                    String commentId = object.getString("commentId").trim();
                                    String userPic = object.getString("userPic").trim();
                                    String time = object.getString("time").trim();
                                    String commenter_mobile = object.getString("mobile").trim();
                                    //(String comment_id, String comment, String username, String userpic, String time)
                                    list.add(new CommentModel(commentId,comment,username,userPic,time,commenter_mobile) );

                                }
                                setRecyclerView(list);

                            }
                            else
                            {
                                mShimmerViewContainer.stopShimmerAnimation();
                                mShimmerViewContainer.setVisibility(View.GONE);
                                if(swipeRefreshLayout.isRefreshing())
                                {
                                    swipeRefreshLayout.setRefreshing(false);
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(JobDiscription.this, "Something went wrong..."+e, Toast.LENGTH_LONG).show();
                            Log.d("Response", e.getMessage());
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
                        Toast.makeText(JobDiscription.this, "Error2: " + error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("Response", error.toString());

                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);
                        if(swipeRefreshLayout.isRefreshing())
                        {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("postId", post_id);
                params.put("key", "9195A3CDB388F894B3EE3BD665DFD");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void setRecyclerView(List<CommentModel> list) {
        CommentAdaptor a= new CommentAdaptor( this,list,id,title,status );
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

    public void getIntentData() {
        snackbar = Snackbar.make(this.findViewById(android.R.id.content), Html.fromHtml("<font color=\"#ffffff\">No Internet Connection</font>"), Snackbar.LENGTH_INDEFINITE);

        id = getIntent().getExtras().getString("id");
        jobdis = getIntent().getExtras().getString("des");
        usernames = getIntent().getExtras().getString("username");
        status = getIntent().getExtras().getString("status");
        profileImage = getIntent().getExtras().getString("profile");
        image = getIntent().getExtras().getString("img");
        String img2 = getIntent().getExtras().getString("img2"),
                img3 = getIntent().getExtras().getString("img3");
        pese = getIntent().getExtras().getString("paise");
        title = getIntent().getExtras().getString("title");
        if (!image.equals("NO")) {
            imageArry.add(image);
            if (!img2.equals("NO")) {
                imageArry.add(img2);
                if (!img3.equals("NO")) {
                    imageArry.add(img3);
                }
            }
        } else {
            viewPager.setVisibility(View.GONE);
        }
        if(status.equals("2"))
        {
            bottomEdit.setVisibility(View.GONE);
        }
        else
        {
            bottomEdit.setVisibility(View.VISIBLE);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setData();
        getCommnetData(id);
    }
    public void setData()
    {
        Glide.with(this).load(profileImage).into(profile);
        username.setText(usernames);
        jobtTitle.setText(title);
        jobdes.setText(jobdis);
        paise.setText("₹ " + pese);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
    public void sendComment(final String comment, final String user_id_as_mobile, final String post_id)
    {
        commentBox.setText(null);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                Toast.makeText(getApplicationContext(), "Successfully Sent!", Toast.LENGTH_SHORT).show();
                                list.clear();
                               getCommnetData(id);
                            }
                            else
                            {

                                Toast.makeText(getApplicationContext(), "Not Send", Toast.LENGTH_SHORT).show();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                            // email_hint.setError("Invalid Mobile no. or Password");
                            Toast.makeText(getApplicationContext(), "Error 1: " + e.toString(), Toast.LENGTH_LONG).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error2: " + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("comment", comment);
                params.put("mobile", user_id_as_mobile);
                params.put("postId", post_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver!= null)
            unregisterReceiver(broadcastReceiver);

    }
}
