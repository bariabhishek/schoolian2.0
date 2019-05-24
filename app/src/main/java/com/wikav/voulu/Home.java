package com.wikav.voulu;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;
import com.wikav.voulu.fragments.FavoriteFragment;
import com.wikav.voulu.fragments.FullScreenDialogForNoInternet;
import com.wikav.voulu.fragments.HomeFragment;
import com.wikav.voulu.fragments.InboxFragment;
import com.wikav.voulu.fragments.NotificationFragment;
import com.wikav.voulu.fragments.ProfileFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Home extends NavigationDrawerActivity_ {
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    BroadcastReceiver broadcastReceiver;
    HomeFragment homeFragment;
    InboxFragment inboxFragment;
    NotificationFragment notificationFragment;
    FavoriteFragment favoriteFragment;
    ProfileFragment profileFragment;
    SessionManger sessionManger;
    IntentFilter intentFilter;
    Snackbar snackbar = null;
    DatabaseHelper mydb;
    long currentVisiblePosition;
    int JOB_ID = 101;
    JobInfo jobInfo;
    private  final int INTERVEL=10*1000;
    JobScheduler mJobScheduler;
    String Url = "https://voulu.in/api/getJobPost.php";
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Toast.makeText(this, "kya he", Toast.LENGTH_SHORT).show();
        sessionManger = new SessionManger(this);
        mydb=new DatabaseHelper(this);
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_home, null, false);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.addView(contentView, 0);

        ComponentName componentName = new ComponentName(this, JobSchedulerService.class);
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID,componentName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setPeriodic(15*60*1000);
        } else {
            builder.setPeriodic(INTERVEL);
        }

        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        //builder.setPersisted(true);
        jobInfo=builder.build();
        mJobScheduler = (JobScheduler)getSystemService(Context.JOB_SCHEDULER_SERVICE);
        mJobScheduler.schedule(jobInfo);

        arraydata();



        snackbar = Snackbar.make(Home.this.findViewById(android.R.id.content),
                Html.fromHtml("<font color=\"#ffffff\">No Internet Connection</font>"),
                Snackbar.LENGTH_INDEFINITE);
        checkIntenet();


        bottomNavigationView = findViewById(R.id.navigation);
        frameLayout = findViewById(R.id.frame);

        homeFragment = new HomeFragment();
        inboxFragment = new InboxFragment();
        notificationFragment = new NotificationFragment();
        favoriteFragment = new FavoriteFragment();
        profileFragment = new ProfileFragment();
        setFragment(homeFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        setFragment(homeFragment);
                        return true;

//                    case R.id.inbox :
//                        setFragment( inboxFragment );
//                        return true;

                    case R.id.notification:
                        setFragment(notificationFragment);
                        return true;

                    case R.id.Fevrate:
                        setFragment(favoriteFragment);
                        return true;

                    case R.id.profile:
                        setFragment(profileFragment);
                        return true;

                    default:

                        return false;
                }


            }
        });


    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        switch (item.getItemId()) {
            case R.id.jobpost:
                // Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
                intent = new Intent(Home.this, UploadYourPost.class);
                startActivity(intent);
                break;
            case R.id.yourpost:
                intent = new Intent(this, YourPost.class);
                startActivity(intent);
                break;
            case R.id.commentedPost:
                intent = new Intent(this, CommentedActivity.class);
                startActivity(intent);
                break;
            case R.id.acceptedComment:
                intent = new Intent(this, AcceptedListActivity.class);
                startActivity(intent);
                break;
            /*case R.id.follow:
                setFragment(favoriteFragment);
                break;*/
            case R.id.aboutus:
                intent = new Intent(this, AboutUs.class);
                startActivity(intent);
                break;
            case R.id.help:
//                intent = new Intent( this,Help.class );
//                startActivity( intent );
                break;
            case R.id.logout:
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Home.this);
                alertDialogBuilder.setMessage("Are you sure, you want to logout");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                sessionManger.logOut();
                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        alertDialogBuilder.setCancelable(true);
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                ;

                break;

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
///////////////////Navigation Drawer finish //////////

    public void checkIntenet() {
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
//                FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
//                FullScreenDialogForNoInternet full=new FullScreenDialogForNoInternet();
//                full.show(ft,"show");

                    snackbar.show();
//

                }
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       /* mJobScheduler.cancel(JOB_ID);*/
        if (broadcastReceiver != null)
            unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*if (broadcastReceiver!= null)
            unregisterwwwwwwwwwwwwwwwwww2Receiver(broadcastReceiver);*/
        //unregisterReceiver(broadcastReceiver);
    }

    private void arraydata() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("allPost");
                            if (success.equals("1")) {

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
                                    boolean db= mydb.insertData(username,profile,title,des,time,id,img,like,img2,img3,status,mobile,rate);
                                    // list.add(new ModelList(img, title, des, rate, id, time, mobile, like, profile, username, status, img2, img3));
                                    if(db)
                                    {
                                        Log.i("data",""+i);
                                    }
                                    else {
                                        Log.i("No",""+i);

                                    }
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
                        Toast.makeText(Home.this, "Something went wrong..." + error, Toast.LENGTH_LONG).show();
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

      RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();

    }
}
