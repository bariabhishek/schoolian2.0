package com.wikav.voulu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.wikav.voulu.SpleshScreen.PERMISSIONS_MULTIPLE_REQUEST;

public class Login extends AppCompatActivity {

    final String Url = "https://voulu.in/api/login2.php";
    SessionManger sessionManger;
    Config config;
    private TextInputLayout etEmail, etPass;
    private EditText email, pass;
    private Button login_btn;
    private ProgressBar progressBar;
    String newToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        config = new Config(this);
        getPermission();
        Log.i("LoginCall", "LoginCall");
        etEmail = findViewById(R.id.etEmail);
        progressBar = findViewById(R.id.progress);
        etPass = findViewById(R.id.etPass);
        email = findViewById(R.id.edittextemail);
        pass = findViewById(R.id.edittextpass);
        login_btn = findViewById(R.id.login_btn);
        sessionManger = new SessionManger(this);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEmail.setErrorEnabled(false);
            }
        });
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPass.setErrorEnabled(false);
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mail  = email.getText().toString();
                final String passw = pass.getText().toString();


                if (mail.isEmpty() && passw.isEmpty()) {
                    etEmail.setError("Please Enter Mobile Number and Password");
                } else {
                    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Login.this, new OnSuccessListener<InstanceIdResult>() {
                        @Override
                        public void onSuccess(InstanceIdResult instanceIdResult) {
                            newToken = instanceIdResult.getToken();
                            Log.e("newToken", newToken);
                           // checkLoin( mob,newToken) ;
                            onLogin(mail, passw,newToken);
                        }
                    });

                }
            }
        });

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                etEmail.setErrorEnabled(false);
            }
        });
        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                etPass.setErrorEnabled(false);
            }
        });

    }

    private void onLogin(final String mail, final String passw, final String newToken) {
        config.CheckConnection();
        login_btn.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response + " " + mail + " " + passw);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            // Toast.makeText(Login.this, mail+" "+passw+" "+ success, Toast.LENGTH_SHORT).show();
                            switch (success) {
                                case "1":

                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);
                                        String name = object.getString("name").trim();
                                        String email = object.getString("email").trim();
                                        String photo = object.getString("profile_pic").trim();
                                        String phone = object.getString("mobile").trim();
                                        String gender = object.getString("gender").trim();
                                        String verfied_status = object.getString("verfied_status").trim();
                                        String location = object.getString("address").trim();
                                        String dob = object.getString("bio").trim();
                                        String bio = object.getString("dob").trim();
                                        String quali = object.getString("quali").trim();
                                        sessionManger.createSession(name, email, photo, verfied_status, phone, gender, location, dob, quali, bio);
                                        Intent intent = new Intent(Login.this, Home.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    break;

                                case "0":
                                    etPass.setError("Invalid password");
                                    login_btn.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    break;

                                case "2":
                                    etEmail.setError("Invalid mobile number");
                                    login_btn.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    break;
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // email_hint.setError("Invalid Mobile no. or Password");
                            Toast.makeText(Login.this, "Error 1: " + e.toString(), Toast.LENGTH_LONG).show();
                            login_btn.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, "Error 2: " + error.toString(), Toast.LENGTH_LONG).show();
                        login_btn.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", mail);
                params.put("pass", passw);
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

    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(Login.this, Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                    .checkSelfPermission(Login.this, Manifest.permission.CAMERA) + ContextCompat
                    .checkSelfPermission(Login.this, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {


                requestPermissions(
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA,
                                Manifest.permission.INTERNET,
                                Manifest.permission.CALL_PHONE,
                                Manifest.permission.ACCESS_NETWORK_STATE},
                        PERMISSIONS_MULTIPLE_REQUEST);

            } else {
                // finish();
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_MULTIPLE_REQUEST:
                if (grantResults.length > 0) {
                    boolean cameraPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (cameraPermission && readExternalFile) {
                        // write your logic here
                    } else {

                    }
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        config.CheckConnection();
    }

    public void createOne(View view) {
        Intent views = new Intent(Login.this, Registration.class);
        startActivity(views);
    }
}



