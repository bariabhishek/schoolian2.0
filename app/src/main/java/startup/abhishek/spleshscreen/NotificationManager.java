package startup.abhishek.spleshscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import startup.abhishek.spleshscreen.fragments.HomeFragment;
import startup.abhishek.spleshscreen.fragments.InboxFragment;
import startup.abhishek.spleshscreen.fragments.NotificationFragment;
import startup.abhishek.spleshscreen.fragments.ProfileFragment;

public class NotificationManager extends AppCompatActivity {
String notification_Option ="";
Fragment freg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_manager);

        notification_Option ="2";

        switch(notification_Option){

            case "1": /** Start a new Activity MyCards.java */
               freg = new HomeFragment();
                fragmentCall(freg);
                break;

            case  "2": /** AlerDialog when click on Exit */
                freg = new InboxFragment();
                fragmentCall(freg);
                break;

            case "3": /** AlerDialog when click on Exit */
                freg = new NotificationFragment();
                fragmentCall(freg);
                break;


            case "4": /** AlerDialog when click on Exit */
                freg = new ProfileFragment();
                fragmentCall(freg);
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
