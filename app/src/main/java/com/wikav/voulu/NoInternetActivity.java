package com.wikav.voulu;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class NoInternetActivity extends AppCompatActivity {
    Config config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_intenet);
        config = new Config(this);

    }
    public void retry(View view) {
        if(config.haveNetworkConnection())
        {
            Intent intent = new Intent(NoInternetActivity.this, Home.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }
}
