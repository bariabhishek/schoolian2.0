package com.wikav.schoolian.fragments;


import android.os.Bundle;

import com.facebook.shimmer.ShimmerFrameLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.wikav.schoolian.Adeptor.CoustomSwipeAdeptorForHome;
import com.wikav.schoolian.Adeptor.MyAdeptor;
import com.wikav.schoolian.Adeptor.MyModelList;
import com.wikav.schoolian.DatabaseHelper;
import com.wikav.schoolian.R;
import com.wikav.schoolian.SessionManger;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    final long DELAY_MS = 4000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000;
    ViewPager viewPager;
    DatabaseHelper mydb;
    CoustomSwipeAdeptorForHome coustomSwipeAdeptorForHome;
    // int [] imageArry ={R.drawable.logo,R.drawable.boy};
    int currentPage = 0;
    boolean isScrolling = false;
    Timer timer;
    SessionManger sessionManger;
    Parcelable state=null;
    int currentItems, totalItem, scrolledOutItem;
    RecyclerView recyclerView;
    List<MyModelList> list;
    List<String> imageArry;
    ImageButton imageButton;
    String Url = "https://schoolian.website/android/newApi/getPostData.php", response;
    String addImageUrl = "https://voulu.in/api/upoladAddImage.php";
    View view;
    TextView noData, adtext;

    Map<String, String> params;
    ProgressBar progressBar;
    private TextView mTextMessage;
    private RequestQueue requestQueue;
    private ShimmerFrameLayout mShimmerViewContainer;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int requestCount = 1;
    private LinearLayoutManager layoutManager;

    public HomeFragment() {
        // Required empty public constructor
    }

    private void arraydata(final String  sclId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            String success = json.getString("success");
                            JSONArray jsonArray = json.getJSONArray("userPost");
                            if (success.equals("1")) {

                                Log.d("MAINResponse", response);
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    MyModelList anime=new MyModelList();
                                    anime.setName(jsonObject.getString("st_name"));
                                    anime.setImage_url(jsonObject.getString("post_pic"));
                                    anime.setDescription(jsonObject.getString("posts"));
                                    anime.setPostId(jsonObject.getString("post_id"));
                                    anime.setProfile_pic(jsonObject.getString("profile_pic"));
                                    anime.setSid(jsonObject.getString("sid"));
                                    anime.setTime(jsonObject.getString("time"));
                                    anime.setImage_url(jsonObject.getString("post_pic"));
                                    list.add(anime);

                                }
                                if (swipeRefreshLayout.isRefreshing()) {
                                    swipeRefreshLayout.setRefreshing(false);
                                }

                                setupRecycle(list);

                            } else {
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
                        Toast.makeText(getActivity(), "Something went wrong..." + error, Toast.LENGTH_LONG).show();
                        // noData.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                // params.put("key", "9195A3CDB388F894B3EE3BD665DFD");
                params.put("school_id", sclId);
                return params;
            }
        };
        stringRequest.setShouldCache(true);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();


    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                         getAdImage();
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mydb=new DatabaseHelper(getActivity());
        imageButton = view.findViewById(R.id.uplodButton);
        noData = view.findViewById(R.id.noData);
        adtext = view.findViewById(R.id.adtext);
        swipeRefreshLayout = view.findViewById(R.id.homeSwipe);
        progressBar = view.findViewById(R.id.homeProgess);
        layoutManager = new LinearLayoutManager(getActivity());
        //   imageArry=new ArrayList<>();
        imageArry = new ArrayList<>();

      //  Toast.makeText(getActivity(), my, Toast.LENGTH_SHORT).show();
        viewPager = view.findViewById(R.id.viewPagerHome);
        recyclerView = view.findViewById(R.id.recycleview);


        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        list = new ArrayList<>();


        sessionManger=new SessionManger(getActivity());


        HashMap<String, String> user = sessionManger.getUserDetail();
        final String sclId = user.get(sessionManger.SCL_ID);
        arraydata(sclId);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                arraydata(sclId);
               /* setupRecycle(mydb.getdata());*/

            }
        });

        if(state != null) {
            layoutManager.onRestoreInstanceState(state);
            mShimmerViewContainer.setVisibility(View.GONE);
        }
        else
        {
            mShimmerViewContainer.startShimmerAnimation();

        }

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        state = layoutManager.onSaveInstanceState();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(state != null) {
            layoutManager.onRestoreInstanceState(state);

        }
    }



    private void getAdImage() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, addImageUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("ad");
                            if (success.equals("1")) {
                                Log.d("Response", response);
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String img = object.getString("image").trim();

                                    imageArry.add(img);

                                }
//                                Toast.makeText(getActivity(), ""+imageArry.size(), Toast.LENGTH_SHORT).show();
                                coustomSwipeAdeptorForHome = new CoustomSwipeAdeptorForHome(getActivity(), imageArry);
                                viewPager.setAdapter(coustomSwipeAdeptorForHome);
                                adtext.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(getActivity(), "Something went wrong..."+error, Toast.LENGTH_LONG).show();
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
        stringRequest.setShouldCache(true);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == imageArry.size()) {
                    currentPage = 0;
                } else {
                    currentPage++;
                }
                viewPager.setCurrentItem(currentPage, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    public void setupRecycle(List<MyModelList> list) {
        mShimmerViewContainer.startShimmerAnimation();
        MyAdeptor a = new MyAdeptor(getContext(), list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(a);
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
