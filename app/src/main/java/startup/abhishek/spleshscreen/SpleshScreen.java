package startup.abhishek.spleshscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import startup.abhishek.spleshscreen.fragments.FullScreenDialogForUpdateApp;

import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SpleshScreen extends AppCompatActivity {
    int versionCode = BuildConfig.VERSION_CODE;
    SessionManger sessionManger;
    ProgressBar progressBar;
    ImageView logopic;
    Config    config;
    boolean isUpdateAvailable =false;
    String Url="https://voulu.in/api/getAppUpdate.php";
    public  static final int PERMISSIONS_MULTIPLE_REQUEST = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.spleshscreen );
        logopic = findViewById(R.id.logo);
        sessionManger = new SessionManger(this);
        progressBar = findViewById(R.id.progressBar);


        getUpdate(versionCode);
    }

    private void getUpdate(final int versionCode) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //    progressDialog.dismiss();
                        Log.i("TAG", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")){
                               isUpdateAvailable=true ;
                                intents(isUpdateAvailable);
                            }
                            else
                            {
                                isUpdateAvailable=false;
                                intents(isUpdateAvailable);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            isUpdateAvailable=false ;
                            intents(isUpdateAvailable);
                            Toast.makeText(SpleshScreen.this, "Something went wrong"+e, Toast.LENGTH_SHORT).show();
                             }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        isUpdateAvailable=false ;
                        intents(isUpdateAvailable);
                        Toast.makeText(SpleshScreen.this, "Something went wrong"+error, Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("version_code", ""+versionCode);
                return params;
            }
        };

        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();


    }
    public  void  intents(final boolean isUpdateAvailable)
    {
        if (!sessionManger.isLoging()) {



           /* Handler  handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            },2000);*/

            if(isUpdateAvailable)
            {
                FullScreenDialogForUpdateApp full=new FullScreenDialogForUpdateApp();
                full.show(getSupportFragmentManager(),"show");
            }
            else {
                Intent intent = new Intent(SpleshScreen.this, Login.class);
                startActivity(intent);
                finish();
            }
        }
        else
        {
            if(isUpdateAvailable)
            {
                FullScreenDialogForUpdateApp full=new FullScreenDialogForUpdateApp();
                full.show(getSupportFragmentManager(),"show");
            }
            else {
                Intent intent = new Intent(SpleshScreen.this, Home.class);
                startActivity(intent);
                finish();
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
