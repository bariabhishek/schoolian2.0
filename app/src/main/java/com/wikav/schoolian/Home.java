package com.wikav.schoolian;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.wikav.schoolian.fragments.FavoriteFragment;
import com.wikav.schoolian.fragments.HomeFragment;
import com.wikav.schoolian.fragments.MainGridFragment;
import com.wikav.schoolian.fragments.NotificationFragment;
import com.wikav.schoolian.fragments.ProfileFragment;
import com.wikav.schoolian.fragments.UplodadPic;


import java.util.HashMap;


public class Home extends NavigationDrawerActivity_ {
    BottomNavigationView bottomNavigationView;
    BottomAppBar bottomAppBar;
    FrameLayout frameLayout;
    BroadcastReceiver broadcastReceiver;
    HomeFragment homeFragment;
    NotificationFragment notificationFragment;
    FavoriteFragment favoriteFragment;
    ProfileFragment profileFragment;
    MainGridFragment mainGridFragment;
    SessionManger sessionManger;
    IntentFilter intentFilter;
    Snackbar snackbar = null;
    FloatingActionButton uploadPost;
    TextView goToCheck;
    Toolbar toolbar;



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManger = new SessionManger(this);
        homeFragment = new HomeFragment();
        notificationFragment = new NotificationFragment();
        favoriteFragment = new FavoriteFragment();
        profileFragment = new ProfileFragment();
        mainGridFragment = new MainGridFragment();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        snackbar = Snackbar.make(Home.this.findViewById(android.R.id.content), Html.fromHtml("<font color=\"#ffffff\">No Internet Connection</font>"), BaseTransientBottomBar.LENGTH_INDEFINITE);
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_home, null, false);
        drawer.addView(contentView, 0);
        bottomNavigationView = findViewById(R.id.navigation);
        frameLayout = findViewById(R.id.frame);
        uploadPost = findViewById(R.id.uploadPost);
        goToCheck = findViewById(R.id.goToCheck);
        bottomAppBar = findViewById(R.id.navBar);
        HashMap<String, String> user = sessionManger.getUserDetail();
        String phone = user.get(sessionManger.MOBILE);
///////////////////////set methods/////////////////

        checkIntenet();
        String in=getIntent().getStringExtra("intent");
      //  Toast.makeText(this, ""+in, Toast.LENGTH_LONG).show();
        if(in!=null)
        {
            setFragment(homeFragment);
        } else {

            setFragment(mainGridFragment);
        }
        uploadPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UplodadPic uplodadPic = new UplodadPic();
                uplodadPic.show(getSupportFragmentManager(), "show");
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
                UplodadPic uplodadPic = new UplodadPic();
                uplodadPic.show(getSupportFragmentManager(), "show");

                break;
            case R.id.yourpost:
                intent = new Intent(this, YourPost.class);
                startActivity(intent);
                break;
            case R.id.gallery:
                intent = new Intent( getApplicationContext(),Gallary.class );
                startActivity( intent );
                break;
            case R.id.getclassmate:
                 intent = new Intent( getApplicationContext(),ClassMates.class );
                startActivity( intent );
                break;
            case R.id.getTeacher:
                 intent = new Intent( getApplicationContext(),TeacherList.class );
                startActivity( intent );
                break;
            case R.id.schoolianBtn:
                intent = new Intent( getApplicationContext(),SchoolianWorld.class );
                startActivity( intent );
                break;
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

                    snackbar.show();

                }
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    }


}
