package startup.abhishek.spleshscreen;

import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import startup.abhishek.spleshscreen.fragments.FullScreenDialogForNoInternet;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static startup.abhishek.spleshscreen.SpleshScreen.PERMISSIONS_MULTIPLE_REQUEST;

public class UserMobileNumber extends AppCompatActivity {
    BroadcastReceiver broadcastReceiver;

    TextInputLayout textInputLayout;
    EditText no;

    Button number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_user_mobile_number );
        textInputLayout = findViewById( R.id.etno );
        checkIntenet();
        no = findViewById( R.id.editnumber );
        number = findViewById( R.id.nobtn );
        btnclick();

    }

    private void btnclick() {

        number.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(no.getText().toString().isEmpty()&&no.getText().toString().length()<10){
                    textInputLayout.setError( "Somthing Wrong" );
                }
                else {
                   // Toast.makeText(UserMobileNumber.this,"succesful",Toast.LENGTH_LONG ).show();
                    getPermission();

                }
            }
        } );
    }
    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(UserMobileNumber.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {


                requestPermissions(
                        new String[]{
                                Manifest.permission.INTERNET,
                                Manifest.permission.ACCESS_NETWORK_STATE},
                        PERMISSIONS_MULTIPLE_REQUEST);



            }
            else {
                Intent i = new Intent(UserMobileNumber.this,OTP.class);
                i.putExtra("mobile",no.getText().toString());
                Log.d("Kya",no.getText().toString());
                Toast.makeText(UserMobileNumber.this,no.getText().toString(),Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        }



    }
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
                    FullScreenDialogForNoInternet full=new FullScreenDialogForNoInternet();
                    full.show(getSupportFragmentManager(),"show");
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
