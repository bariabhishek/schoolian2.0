package startup.abhishek.spleshscreen;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


import java.util.ArrayList;

import startup.abhishek.spleshscreen.Adeptor.Adeptor;
import startup.abhishek.spleshscreen.Adeptor.ModelList;
import startup.abhishek.spleshscreen.fragments.FollowersFragment;
import startup.abhishek.spleshscreen.fragments.HomeFragment;
import startup.abhishek.spleshscreen.fragments.InboxFragment;
import startup.abhishek.spleshscreen.fragments.NotificationFragment;
import startup.abhishek.spleshscreen.fragments.ProfileFragment;


public class Home extends NavigationDrawerActivity_ {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    HomeFragment homeFragment;
    InboxFragment inboxFragment;
    NotificationFragment notificationFragment;
    FollowersFragment followersFragment;
    ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
     //   setContentView( R.layout.activity_home );

        final LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_home, null, false);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.addView(contentView, 0);

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

                    case R.id.inbox :
                        setFragment( inboxFragment );
                        return true;

                    case R.id.notification :
                        setFragment( notificationFragment );
                        return true;

                    case R.id.follower :
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


}
