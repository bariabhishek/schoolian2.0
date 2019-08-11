package com.wikav.schoolian;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class NewOTP extends AppCompatActivity {
    final String Url = "https://schoolian.in/android/newApi/login.php";

    BroadcastReceiver broadcastReceiver;
    String mob;
    Button txtVerify;
    TextView timer;
    EditText editText;
    boolean verificationStatus = false;
    SessionManger sessionManger;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    String code;
    Random rnd;
    int i;
    String newToken,scl_id;
    CountDownTimer newTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        txtVerify = findViewById(R.id.verify);
        timer = findViewById(R.id.timer);
        mAuth = FirebaseAuth.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        editText = findViewById(R.id.editotp);
        sessionManger = new SessionManger(this);
        mob = getIntent().getStringExtra("mobile");
      //  scl_id = getIntent().getStringExtra("sclId");
        setTimer();
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 7)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
       // sendVerificationCode(mob);
        FirebaseAuthSettings firebaseAuthSettings = firebaseAuth.getFirebaseAuthSettings();
       // checkIntenet();
        rnd = new Random();
        i = rnd.nextInt(999999) + 10000;
       // Toast.makeText(this, ""+i, Toast.LENGTH_LONG).show();
        sendOtp(mob,i);
        txtVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().equals(""))
                {
                    if(editText.getText().toString().equals(""+i))
                    {


                        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(NewOTP.this, new OnSuccessListener<InstanceIdResult>() {
                            @Override
                            public void onSuccess(InstanceIdResult instanceIdResult) {
                                newToken = instanceIdResult.getToken();
                                Log.e("newToken", newToken);
                                checkLoin( mob,newToken) ;

                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(NewOTP.this, "Invalid OTP", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void sendOtp(String mob, int i) {
        String msg=i+" is your verification code for Schoolian App. Thankyou.";
    SendSms sendSms = new SendSms(mob,msg);
    sendSms.send();
    }


    void checkLoin(final String mail, final String newToken) {
        final ProgressDialog progressDialog = new ProgressDialog(NewOTP.this);
        progressDialog.setMessage("Checking your number...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        NewOTP.this.setFinishOnTouchOutside(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            // Toast.makeText(Login.this, mail+" "+passw+" "+ success, Toast.LENGTH_SHORT).show();
                            switch (success) {
                                case "1":
                                    progressDialog.dismiss();
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);
                                        String name = object.getString("name").trim();
                                        String email = object.getString("email").trim();
                                        String bio = object.getString("bio").trim();
                                        String sid = object.getString("sid").trim();
                                        String photo = object.getString("photo").trim();
                                        String scl_pic = object.getString("scl_pic").trim();
                                        String scl_id = object.getString("school_id").trim();
                                        String classs = object.getString("class").trim();
                                        String dob = object.getString("dob").trim();
                                        String sclname = object.getString("scl_name").trim();
                                        String mobile = object.getString("phone").trim();
                                        String pass = object.getString("pass").trim();
                                        String location = object.getString("location").trim();
                                        sessionManger.createSession(name, email, photo, mobile,location,dob, bio, sid, pass, scl_pic, scl_id,classs,sclname);
                                        Intent in = new Intent(getApplicationContext(), Home.class);
                                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(in);
                                        finish();
                                    }

                                    break;


                                case "2":
                                    progressDialog.dismiss();
                                    Intent in = new Intent(getApplicationContext(), SchoolIDopt.class);
                                    in.putExtra("mobile", mob);
                                   // in.putExtra("sclId", scl_id);
                                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(in);
                                    finish();
                                    break;
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // email_hint.setError("Invalid Mobile no. or Password");
                            progressDialog.dismiss();

                        //    Toast.makeText(NewOTP.this, "Error 1: " + e.toString(), Toast.LENGTH_LONG).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                     //   Toast.makeText(NewOTP.this, "Error 2: " + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", mail);
                params.put("token", newToken);

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();

    }

    void setTimer() {
         newTime= new CountDownTimer(29000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText("00:" + millisUntilFinished / 1000);
                timer.setClickable(false);
            }

            public void onFinish() {
                Snackbar.make(NewOTP.this.findViewById(android.R.id.content),
                        Html.fromHtml("<font color=\"#ffffff\">Mobile number invalid or not reachable at this time</font>"),
                        BaseTransientBottomBar.LENGTH_LONG).show();
                timer.setClickable(true);
                timer.setText("Check and Resend OTP");
            }
        }.start();


    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
