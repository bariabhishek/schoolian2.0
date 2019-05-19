package com.wikav.voulu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;


import com.wikav.voulu.fragments.FavoriteFragment;
import com.wikav.voulu.fragments.FullScreenDialogForNoInternet;
import com.wikav.voulu.fragments.HomeFragment;
import com.wikav.voulu.fragments.InboxFragment;
import com.wikav.voulu.fragments.NotificationFragment;
import com.wikav.voulu.fragments.ProfileFragment;


public class Home extends NavigationDrawerActivity_ {
    //abhi push kiaa h
// dusra update agya
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
       // Toast.makeText(this, "kya he", Toast.LENGTH_SHORT).show();
        sessionManger=new SessionManger(this);

        final LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_home, null, false);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.addView(contentView, 0);
       checkIntenet();


        bottomNavigationView = findViewById( R.id.navigation );
        frameLayout = findViewById( R.id.frame );

        homeFragment = new HomeFragment();
        inboxFragment = new InboxFragment();
        notificationFragment =new  NotificationFragment();
        favoriteFragment = new FavoriteFragment();
        profileFragment = new ProfileFragment();
        setFragment( homeFragment );

        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.navigation_home :
                        setFragment(homeFragment);
                        return true;

//                    case R.id.inbox :
//                        setFragment( inboxFragment );
//                        return true;

                    case R.id.notification :
                        setFragment( notificationFragment );
                        return true;

                    case R.id.Fevrate :
                        setFragment(favoriteFragment);
                       return true;

                    case R.id.profile :
                        setFragment( profileFragment );
                        return true;

                        default:

                            return false;
                }


            }
        } );


    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace( R.id.frame,fragment );
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        switch (item.getItemId()) {
            case R.id.jobpost:
                // Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
                intent = new Intent( Home.this,UploadYourPost.class );
                startActivity( intent );
                break;
            case R.id.yourpost:
                intent = new Intent( this,YourPost.class );
                startActivity( intent );
                break;
            case R.id.commentedPost:
                intent = new Intent( this,CommentedActivity.class );
                startActivity( intent );
                break;
                case R.id.acceptedComment:
                intent = new Intent( this,AcceptedListActivity.class );
                startActivity( intent );
                break;
            /*case R.id.follow:
                setFragment(favoriteFragment);
                break;*/
            case  R.id.aboutus:
                intent = new Intent( this,AboutUs.class );
                startActivity( intent );
                break;
            case  R.id.help:
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

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        alertDialogBuilder.setCancelable(true);
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();;

                break;

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
///////////////////Navigation Drawer finish //////////

public void checkIntenet()
{
     intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int [] type={ ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI};
            if(ConnectivityReceiver.isNetworkAvailable(getApplicationContext(),type))
            {
                return;
            }
            else {
                //Toast.makeText(context, "Toast", Toast.LENGTH_SHORT).show();
                FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                FullScreenDialogForNoInternet full=new FullScreenDialogForNoInternet();
                full.show(ft,"show");

            }
        }
    };
    registerReceiver(broadcastReceiver,intentFilter);
}
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver,intentFilter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver!= null)
            unregisterReceiver(broadcastReceiver);
    }
 @Override
    protected void onPause() {
        super.onPause();
        if (broadcastReceiver!= null)
            unregisterReceiver(broadcastReceiver);
        //unregisterReceiver(broadcastReceiver);
    }
}
