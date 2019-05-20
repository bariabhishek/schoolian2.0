package com.wikav.voulu;

import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.wikav.voulu.fragments.FullScreenDialogForNoInternet;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.wikav.voulu.SpleshScreen.PERMISSIONS_MULTIPLE_REQUEST;

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
       // checkIntenet();
        no = findViewById( R.id.editnumber );
        number = findViewById( R.id.nobtn );
        no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            textInputLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        number.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnclick();

            }
        } );
    }

    private void btnclick() {

        if(no.getText().toString().isEmpty()){
            textInputLayout.setError( "Please enter mobile" );
        }
        else {
            if(no.getText().toString().length()>=10)
            {
                 String phoneNumber=no.getText().toString();
                int initialPart = Integer.parseInt(phoneNumber.substring(0,2));

                if(initialPart>=60)
                {
                    getPermission();
                }
                else
                {
                    textInputLayout.setError( "Mobile invalid" );
                }
            }
            else {
                textInputLayout.setError( "Mobile no. too short" );
            }

        }


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
                Intent i = new Intent(UserMobileNumber.this,NewOTP.class);
                i.putExtra("mobile",no.getText().toString());
                Log.d("Kya",no.getText().toString());
               // Toast.makeText(UserMobileNumber.this,no.getText().toString(),Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void checkItenet()
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

  /*  @Override
    protected void onPause() {
        super.onPause();
        if (broadcastReceiver!= null)
            unregisterReceiver(broadcastReceiver);

    }*/

    public void loginwithpass(View view) {
        Intent visew = new Intent(this,Login. class);
        startActivity(visew);
    }
}
