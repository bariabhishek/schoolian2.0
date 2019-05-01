package startup.abhishek.spleshscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SpleshScreen extends AppCompatActivity {
    int versionCode = BuildConfig.VERSION_CODE;
    SessionManger sessionManger;
    ProgressBar progressBar;
    ImageView logopic;
    Config    config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.spleshscreen );
        logopic = findViewById(R.id.logo);
        sessionManger = new SessionManger(this);
        progressBar = findViewById(R.id.progressBar);

    }



    @Override
    protected void onStart() {
        super.onStart();
         if (!sessionManger.isLoging()) {



           Handler  handler=new Handler();
             handler.postDelayed(new Runnable() {
                 @Override
                 public void run() {
                    // logopic.animate().alpha(1f).setDuration(3000);
                     getUpdate(versionCode);
                     Intent intent = new Intent(SpleshScreen.this, Login.class);
                     startActivity(intent);
                     finish();
                 }
             },4000);

            }
            else
            {
                Intent intent = new Intent(SpleshScreen.this, Home.class);
                startActivity(intent);
                finish();
            }



    }

    private void getUpdate(int versionCode) {


    }

}
