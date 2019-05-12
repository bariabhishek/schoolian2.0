package startup.abhishek.spleshscreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import startup.abhishek.spleshscreen.fragments.FavoriteFragment;
import startup.abhishek.spleshscreen.fragments.HomeFragment;
import startup.abhishek.spleshscreen.fragments.InboxFragment;
import startup.abhishek.spleshscreen.fragments.NotificationFragment;
import startup.abhishek.spleshscreen.fragments.ProfileFragment;

public class NavigationDrawerActivity_ extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    HomeFragment homeFragment;
    InboxFragment inboxFragment ;
    NotificationFragment notificationFragment ;
    FavoriteFragment favoriteFragment;
    ProfileFragment profileFragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_navigation_drawer_ );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        homeFragment = new HomeFragment();
        inboxFragment = new InboxFragment();
        notificationFragment = new NotificationFragment();
        favoriteFragment = new FavoriteFragment();
        profileFragment = new ProfileFragment();


        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

    /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toggle.getDrawerArrowDrawable().setColor(getColor(R.color.colorPrimary));
        } else {
            toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimary));
        }
*/
        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
        navigationView.setItemIconTintList(null);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.navigation_drawer_activity_, menu );
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


         if (id == R.id.jobpost) {

            Intent intent = new Intent( this,UploadYourPost.class );
            startActivity( intent );

        } else if (id == R.id.yourpost) {

        } else if (id == R.id.commentedPost) {

        } else if (id == R.id.follow) {

        } else if (id == R.id.aboutus) {

        }
        else if (id == R.id.help) {

        }
        else if (id == R.id.logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

//    private void setFregment(Fragment ragment) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace( R.id.frame,ragment );
//        transaction.commit();
//    }
}
