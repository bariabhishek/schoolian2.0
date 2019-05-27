package com.wikav.voulu.coustomDilogeClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wikav.voulu.R;

public class CoustomDilogeConfirmActivity extends AppCompatActivity {


    Button textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_coustom_diloge_confirm );


        textView =findViewById( R.id.tv );
        textView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDiloge();
            }
        } );
    }

    private void openDiloge() {

        CouatomDilogeLastConfirm c = new CouatomDilogeLastConfirm();
        c.show( getSupportFragmentManager(),"" );

    }
}
