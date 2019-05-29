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
import android.content.SharedPreferences;
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
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import de.hdodenhof.circleimageview.CircleImageView;


import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.wikav.voulu.fragments.FavoriteFragment;
import com.wikav.voulu.fragments.FullScreenDialogForCheckJobDetail;
import com.wikav.voulu.fragments.FullScreenDialogForNoInternet;
import com.wikav.voulu.fragments.HomeFragment;
import com.wikav.voulu.fragments.InboxFragment;
import com.wikav.voulu.fragments.NotificationFragment;
import com.wikav.voulu.fragments.ProfileFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Home extends NavigationDrawerActivity_ {
    private static final String TAG = "MainActivity";
    private final int INTERVEL = 10 * 1000;
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
    LinearLayout tasknote;
    CircleImageView closeTaskNote;
    long currentVisiblePosition;
    int JOB_ID = 101;
    JobInfo jobInfo;
    JobScheduler mJobScheduler;
    TextView goToCheck;
    String Url = "https://voulu.in/api/getJobPost.php";
    private FirebaseJobDispatcher jobDispatcher;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Toast.makeText(this, "kya he", Toast.LENGTH_SHORT).show();
        sessionManger = new SessionManger(this);
        mydb = new DatabaseHelper(this);

        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_home, null, false);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.addView(contentView, 0);
        Driver driver = new GooglePlayDriver(this);
        jobDispatcher = new FirebaseJobDispatcher(driver);

        service();
/*
            ComponentName componentName = new ComponentName(this, JobSchedulerService.class);
            JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, componentName);
            builder.setPeriodic(INTERVEL);


            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
            //builder.setPersisted(true);
            jobInfo = builder.build();
            mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            mJobScheduler.schedule(jobInfo);*/

        // job();

        arraydata();

        SharedPreferences sharedPref = getSharedPreferences("MyJobWork", Context.MODE_PRIVATE);
        final String jobId = sharedPref.getString("taskId", "");

        Log.d("MyToast","my"+jobId);

        boolean showNote = sharedPref.getBoolean("showNote", false);

        checkIntenet();

        timeConverter();
        bottomNavigationView = findViewById(R.id.navigation);
        frameLayout = findViewById(R.id.frame);
        tasknote = findViewById(R.id.slideUp);
        goToCheck = findViewById(R.id.goToCheck);
        homeFragment = new HomeFragment();
        inboxFragment = new InboxFragment();
        notificationFragment = new NotificationFragment();
        favoriteFragment = new FavoriteFragment();
        profileFragment = new ProfileFragment();
        setFragment(homeFragment);
        if (showNote) {
            tasknote.setVisibility(View.VISIBLE);
        } else {
            tasknote.setVisibility(View.GONE);
        }

        goToCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullScreenDialogForCheckJobDetail jobDetail = new FullScreenDialogForCheckJobDetail();
                Bundle b = new Bundle();
                b.putString("id", jobId);
                jobDetail.setArguments(b);
                jobDetail.show(getSupportFragmentManager(), "show");
            }
        });
        /*closeTaskNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasknote.setVisibility(View.GONE);
            }
        });*/

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
        snackbar = Snackbar.make(Home.this.findViewById(android.R.id.content),
                Html.fromHtml("<font color=\"#ffffff\">No Internet Connection</font>"), BaseTransientBottomBar.LENGTH_INDEFINITE);


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
        jobDispatcher.cancel("MyJobTag");
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
                                    boolean db = mydb.insertData(username, profile, title, des, time, id, img, like, img2, img3, status, mobile, rate);
                                    // list.add(new ModelList(img, title, des, rate, id, time, mobile, like, profile, username, status, img2, img3));
                                    if (db) {
                                        Log.i("data", "" + i);
                                    } else {
                                        Log.i("No", "" + i);

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

    private void job() {

       /* Job myJob = jobDispatcher.newJobBuilder()
                // the JobService that will be called
                .setService(MyService.class)
                // uniquely identifies the job
                // one-off job
                .setRecurring(false)
                // don't persist past a device reboot
                .setLifetime(Lifetime.FOREVER)
                .setTag(JOB_ID)
                // start between 0 and 60 seconds from now
                .setTrigger(Trigger.executionWindow(10,15)).
                setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL).
                setReplaceCurrent(false).
                setConstraints(Constraint.ON_ANY_NETWORK)
                .build();*/
        Job myJob = jobDispatcher.newJobBuilder()
                .setService(MyService.class) // the JobService that will be called
                .setTag("MyTag")        // uniquely identifies the job
                .build();

        jobDispatcher.mustSchedule(myJob);


        //jobDispatcher.mustSchedule(myJob);
        Toast.makeText(this, "calll", Toast.LENGTH_SHORT).show();
    }

    private void service() {
        ComponentName componentName = new ComponentName(this, JobSchedulerService.class);
        JobInfo info = new JobInfo.Builder(JOB_ID, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }


    public void timeConverter() {
        try {

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.setTime(sdf.parse("2019-05-21 05:28:36"));

            Calendar calendar = Calendar.getInstance();
            long now = calendar.getTimeInMillis();
            long time = cal.getTimeInMillis();

            long diff = now - time;

            int seconds = (int) (diff / 1000) % 60;
            int minutes = (int) ((diff / (1000 * 60)) % 60);
            int hours = (int) ((diff / (1000 * 60 * 60)) % 24);
            int days = (int) (diff / (1000 * 60 * 60 * 24));

            Log.i("myTime", time + " " + now);
            Log.i("myTime", hours + " hours ago");
            Log.i("myTime", minutes + " minutes ago");
            Log.i("myTime", seconds + " seconds ago");
            Log.i("myTime", days + " days ago");


        } catch (ParseException e) {
            System.out.println(e.toString());
        }
    }
}
