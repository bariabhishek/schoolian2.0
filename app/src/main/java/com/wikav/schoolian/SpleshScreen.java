package com.wikav.schoolian;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.wikav.schoolian.fragments.FullScreenDialogForNoInternet;
import com.wikav.schoolian.fragments.FullScreenDialogForUpdateApp;

import android.util.Log;
import android.view.View;
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
    BroadcastReceiver broadcastReceiver;

    boolean isUpdateAvailable =false;
    String Url="https://schoolian.website/android/getAppUpdate.php";
    public  static final int PERMISSIONS_MULTIPLE_REQUEST = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.spleshscreen );
        logopic = findViewById(R.id.logo);
        sessionManger = new SessionManger(this);
        progressBar = findViewById(R.id.progressBar);
      //  checkIntenet();
        getUpdate(versionCode);



    }

    private void getUpdate(final int versionCode) {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //    progressDialog.dismiss();
                        Log.i("TAG", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String link = jsonObject.getString("message");
                            if (success.equals("1")){
                               isUpdateAvailable=true ;
                                intents(isUpdateAvailable,link);
                                progressBar.setVisibility(View.INVISIBLE);

                            }
                            else if(success.equals("0"))
                            {
                                isUpdateAvailable=false;
                                intents(isUpdateAvailable,link);
                                progressBar.setVisibility(View.INVISIBLE);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            isUpdateAvailable=false ;
                            intents(isUpdateAvailable,"NA");
                            progressBar.setVisibility(View.INVISIBLE);

                            Toast.makeText(SpleshScreen.this, "Something went wrong"+e, Toast.LENGTH_SHORT).show();
                             }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        isUpdateAvailable=false ;
                        intents(isUpdateAvailable,"NA");
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(SpleshScreen.this, "Something went wrong"+error, Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("version_code", ""+versionCode);
                params.put("app", "student");
                return params;
            }
        };

        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();


    }
    public  void  intents(final boolean isUpdateAvailable,String link)
    {             //   Toast.makeText(this, "login check", Toast.LENGTH_SHORT).show();

        if (!sessionManger.isLoging()) {


            if(isUpdateAvailable)
            {
                FullScreenDialogForUpdateApp full=new FullScreenDialogForUpdateApp();
                Bundle b=new Bundle();
                b.putString("url",link);
                full.setArguments(b);
                full.show(getSupportFragmentManager(),"show");
            }
            else {
               // Toast.makeText(this, "ok he", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SpleshScreen.this, UserMobileNumber.class);
                startActivity(intent);
                finish();
            }
        }
        else
        {
            if(isUpdateAvailable)
            {
                FullScreenDialogForUpdateApp full=new FullScreenDialogForUpdateApp();
                Bundle b=new Bundle();
                b.putString("url",link);
                full.setArguments(b);
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

   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver!= null)
            unregisterReceiver(broadcastReceiver);
    }*/
}
