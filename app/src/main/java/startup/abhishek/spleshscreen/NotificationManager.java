package startup.abhishek.spleshscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import startup.abhishek.spleshscreen.fragments.HomeFragment;
import startup.abhishek.spleshscreen.fragments.InboxFragment;
import startup.abhishek.spleshscreen.fragments.NotificationFragment;
import startup.abhishek.spleshscreen.fragments.ProfileFragment;

public class NotificationManager extends AppCompatActivity {
String notification_Option;
Fragment freg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_notification_manager);

        notification_Option =getIntent().getStringExtra("intent");
        Log.d("notii",notification_Option);

        switch(notification_Option){

            case "home":
               freg = new HomeFragment();
                fragmentCall(freg);
                break;

            case "comment":
                freg = new NotificationFragment();
                fragmentCall(freg);
                break;


            case "acceptedPost":
                Intent view = new Intent(this,AcceptedListActivity. class);
                startActivity(view);
                finish();
                break;
        }



    }

    private void fragmentCall(Fragment fragment){


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.notificationLayout,fragment);
        transaction.commit();

    }
}
