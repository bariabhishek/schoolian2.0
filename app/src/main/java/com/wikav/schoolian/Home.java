package com.wikav.schoolian;

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
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
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


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.wikav.schoolian.fragments.FavoriteFragment;
import com.wikav.schoolian.fragments.FullScreenDialogForCheckJobDetail;
import com.wikav.schoolian.fragments.HomeFragment;
import com.wikav.schoolian.fragments.MainGridFragment;
import com.wikav.schoolian.fragments.NotificationFragment;
import com.wikav.schoolian.fragments.ProfileFragment;
import com.wikav.schoolian.fragments.UplodadPic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Home extends NavigationDrawerActivity_ {
    private static final String TAG = "AnimeActivity";
    private final int INTERVEL = 10 * 1000;
    BottomNavigationView bottomNavigationView;
    BottomAppBar bottomAppBar;
    FrameLayout frameLayout;
    BroadcastReceiver broadcastReceiver;
    HomeFragment homeFragment;
    //InboxFragment inboxFragment;
    NotificationFragment notificationFragment;
    FavoriteFragment favoriteFragment;
    ProfileFragment profileFragment;
    MainGridFragment mainGridFragment;
    SessionManger sessionManger;
    IntentFilter intentFilter;
    Snackbar snackbar = null;
    String Mainresponse;
    LinearLayout tasknote;
    CircleImageView closeTaskNote;
    long currentVisiblePosition;
    int JOB_ID = 101;
   private static final int DIALOG_REQUEST_ERROR=9001;
    JobScheduler mJobScheduler;
    FloatingActionButton uploadPost;
    TextView goToCheck;
    String Url2 = "https://voulu.in/api/pendingTask.php";
Toolbar toolbar;
    private FirebaseJobDispatcher jobDispatcher;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /////////initilize/////////
        sessionManger = new SessionManger(this);
        homeFragment = new HomeFragment();

      //  inboxFragment = new InboxFragment();
        notificationFragment = new NotificationFragment();
        favoriteFragment = new FavoriteFragment();
        profileFragment = new ProfileFragment();
        mainGridFragment = new MainGridFragment();
        Driver driver = new GooglePlayDriver(this);
        jobDispatcher = new FirebaseJobDispatcher(driver);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        SharedPreferences sharedPref = getSharedPreferences("MyJobWork", Context.MODE_PRIVATE);
        final String jobId = sharedPref.getString("taskId", "");
        final boolean showNote = sharedPref.getBoolean("showNote", false);
        snackbar = Snackbar.make(Home.this.findViewById(android.R.id.content), Html.fromHtml("<font color=\"#ffffff\">No Internet Connection</font>"), BaseTransientBottomBar.LENGTH_INDEFINITE);
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_home, null, false);
        drawer.addView(contentView, 0);
        bottomNavigationView = findViewById(R.id.navigation);
        frameLayout = findViewById(R.id.frame);
        uploadPost = findViewById(R.id.uploadPost);
        goToCheck = findViewById(R.id.goToCheck);
        bottomAppBar = findViewById( R.id.navBar );
        HashMap<String, String> user = sessionManger.getUserDetail();
        String phone = user.get(sessionManger.MOBILE);
///////////////////////set methods/////////////////
        checkPendingTask(phone);
       // service();
      //  arraydata();
        checkIntenet();
        timeConverter();
        setFragment(mainGridFragment);

///////////////// click listners///////////////////////
        tasknote = findViewById(R.id.slideUp);
        if (showNote) {
            tasknote.setVisibility(View.VISIBLE);
        } else {
            tasknote.setVisibility(View.GONE);
        }
        tasknote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullScreenDialogForCheckJobDetail jobDetail = new FullScreenDialogForCheckJobDetail();
                Bundle b = new Bundle();
                b.putString("id", jobId);
                jobDetail.setArguments(b);
                jobDetail.show(getSupportFragmentManager(), "show");
            }
        });

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
        uploadPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UplodadPic uplodadPic= new UplodadPic();
                uplodadPic.show(getSupportFragmentManager(),"show");
                /*Intent vi = new Intent(Home.this, UploadYourPost.class);
                startActivity(vi);*/
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        setFragment(mainGridFragment);
                        return true;

                    case R.id.notification:
                        setFragment(notificationFragment);
                        return true;

                    case R.id.Fevrate:
                        setFragment(homeFragment);
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

    public void setFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame, fragment);
        ft.commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        switch (item.getItemId()) {
            case R.id.jobpost:
                // Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();

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
        mJobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = mJobScheduler.schedule(info);
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

    private void checkPendingTask(final String phone) {

        SharedPreferences sharedpreferences = getSharedPreferences("MyJobWork", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences.edit();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("JOB_Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("task");
                            if (success.equals("1")) {
                                Log.d("JOB_Response", response);

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String taskId = object.getString("complete_id").trim();
                                    editor.putString("taskId", taskId);
                                    editor.putBoolean("showNote", true);
                                    editor.apply();
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
                        // noData.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", phone);
                params.put("key", "9195A3CDB388F894B3EE3BD665DFD");
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
        // requestQueue.getCache().clear();

    }

}
