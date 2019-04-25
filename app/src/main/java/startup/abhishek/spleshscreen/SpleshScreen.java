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



           Handler  handler=new Handler();
             handler.postDelayed(new Runnable() {
                 @Override
                 public void run() {
                    // logopic.animate().alpha(1f).setDuration(3000);

                     Intent intent = new Intent(SpleshScreen.this, Login.class);
                     startActivity(intent);
                     finish();
                 }
             },3000);

            }
            else
            {
                Intent intent = new Intent(SpleshScreen.this, Home.class);
                startActivity(intent);
                finish();
            }



    }

    private void updateApp() {


    }
}
