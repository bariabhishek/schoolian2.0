package com.wikav.voulu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SchoolIDopt extends AppCompatActivity {
    RelativeLayout relativeLayout,school_name_Rlayout;
    EditText schoolOtpET;
    Button schoolOtpButton,schoolNamebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_school_idopt );

        relativeLayout = findViewById( R.id.school_otp_Rlayout );
        schoolOtpET = findViewById(R.id.editTextSchoolVerificatioCode );
        schoolOtpButton = findViewById( R.id.SchoolOtpButton );
        school_name_Rlayout = findViewById( R.id.school_name_Rlayout );
        schoolNamebutton = findViewById( R.id.btnSchoolName );


        schoolOtpButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(schoolOtpET.getText().toString().isEmpty()){
                    schoolOtpET.setError( "Somthing Wrong" );
                }else {
                    layoutVisible();
                    Toast.makeText( getApplicationContext(),"working",Toast.LENGTH_LONG ).show();
                }
            }
        } );
        schoolNamebutton .setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(),UserMobileNumber.class );
                startActivity( intent );
            }
        } );
    }

    private void layoutVisible() {

        relativeLayout.setVisibility( View.INVISIBLE );
        school_name_Rlayout.setVisibility( View.VISIBLE );

    }
}
