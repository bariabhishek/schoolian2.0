package startup.abhishek.spleshscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SpleshScreen extends AppCompatActivity {
    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;
    SessionManger sessionManger;
    ProgressBar progressBar;
    ImageView retry,logopic,sclfont;
    TextView tapto, pst;
    AlertDialog.Builder builder;
    Config    config;
    public  static final int PERMISSIONS_MULTIPLE_REQUEST = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.spleshscreen );
        logopic = findViewById(R.id.logo);
        sessionManger = new SessionManger(this);
        progressBar = findViewById(R.id.progressBar);

        config=new Config(this);

        config.CheckConnection();


    }



    @Override
    protected void onStart() {
        super.onStart();
         if (!sessionManger.isLoging()) {

                new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

                    @Override

                    public void run() {

                        logopic.animate().alpha(1f).setDuration(1000);

                        Intent intent = new Intent(SpleshScreen.this, Login.class);
                        startActivity(intent);
                        finish();

                    }

                }, 1*1000);

            }
            else
            {
                new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

                    @Override

                    public void run() {

                        logopic.animate().alpha(1f).setDuration(500);

                        Intent intent = new Intent(SpleshScreen.this, Home.class);
                        startActivity(intent);
                        finish();

                    }

                }, 1*500);



            }



    }

    private void updateApp() {


    }
}
