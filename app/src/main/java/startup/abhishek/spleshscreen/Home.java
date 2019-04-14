package startup.abhishek.spleshscreen;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import startup.abhishek.spleshscreen.Adeptor.Adeptor;
import startup.abhishek.spleshscreen.Adeptor.ModelList;
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

//                    case R.id.follower :
//                        setFragment( followersFragment );
//                        return true;

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
        switch (item.getItemId()) {
            case R.id.jobpost:
                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent( this,UploadYourPost.class );
                startActivity( intent );
                break;
            case R.id.yourpost:
                break;
            case R.id.wallet:
                break;
            case R.id.follow:
                break;
            case  R.id.aboutus:
                break;
            case  R.id.help:
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


}
