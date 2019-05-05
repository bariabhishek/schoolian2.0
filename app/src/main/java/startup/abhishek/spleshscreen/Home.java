package startup.abhishek.spleshscreen;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;


import startup.abhishek.spleshscreen.fragments.FollowersFragment;
import startup.abhishek.spleshscreen.fragments.HomeFragment;
import startup.abhishek.spleshscreen.fragments.InboxFragment;
import startup.abhishek.spleshscreen.fragments.NotificationFragment;
import startup.abhishek.spleshscreen.fragments.ProfileFragment;

import static startup.abhishek.spleshscreen.SpleshScreen.PERMISSIONS_MULTIPLE_REQUEST;


public class Home extends NavigationDrawerActivity_ {
    //abhi push kiaa h
// dusra update agya
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    BroadcastReceiver broadcastReceiver;
    HomeFragment homeFragment;
    InboxFragment inboxFragment;
    NotificationFragment notificationFragment;
    FollowersFragment followersFragment;
    ProfileFragment profileFragment;
    SessionManger sessionManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
     //   setContentView( R.layout.activity_home );
        getPermission();
        checkIntenet();
        sessionManger=new SessionManger(this);

        final LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_home, null, false);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.addView(contentView, 0);



        bottomNavigationView = findViewById( R.id.navigation );
        frameLayout = findViewById( R.id.frame );

        homeFragment = new HomeFragment();
        inboxFragment = new InboxFragment();
        notificationFragment =new  NotificationFragment();
        followersFragment = new FollowersFragment();
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
                        setFragment( followersFragment );
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

    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(Home.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                    .checkSelfPermission(Home.this,
                            Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale
                        (Home.this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                        ActivityCompat.shouldShowRequestPermissionRationale
                                (Home.this, Manifest.permission.CAMERA)) {

                    Snackbar.make(Home.this.findViewById(android.R.id.content),
                            "Please Grant Permissions to upload profile photo",
                            Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                            new View.OnClickListener() {
                                @TargetApi(Build.VERSION_CODES.M)
                                @Override
                                public void onClick(View v) {
                                    requestPermissions(
                                            new String[]{Manifest.permission
                                                    .READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                            PERMISSIONS_MULTIPLE_REQUEST);
                                }
                            }).show();








                } else {
                    requestPermissions(
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.MEDIA_CONTENT_CONTROL,
                                    Manifest.permission.INTERNET,
                                    Manifest.permission.ACCESS_NETWORK_STATE},
                            PERMISSIONS_MULTIPLE_REQUEST);
                }
            } else {
                // write your logic code if permission already granted
                // Toast.makeText(this, "Permission Granted...", Toast.LENGTH_SHORT).show();
            }
        }



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_MULTIPLE_REQUEST:
                if (grantResults.length > 0) {
                    boolean cameraPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (cameraPermission && readExternalFile) {
                        // write your logic here
                    } else {
                        Snackbar.make(Home.this.findViewById(android.R.id.content),
                                "Please Grant Permissions to upload profile photo",
                                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(
                                                    new String[]{Manifest.permission
                                                            .READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                                    PERMISSIONS_MULTIPLE_REQUEST);
                                        }
                                    }
                                }).show();
                    }
                }
                break;
        }
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
            case R.id.wallet:
                break;
            case R.id.follow:
                setFragment( followersFragment );
                break;
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
    IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int [] type={ ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI};
            if(ConnectivityReceiver.isNetworkAvailable(context,type))
            {
                return;
            }
            else {
                Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
            }
        }
    };
    registerReceiver(broadcastReceiver,intentFilter);
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
