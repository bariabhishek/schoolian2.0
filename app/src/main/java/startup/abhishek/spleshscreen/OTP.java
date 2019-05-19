package startup.abhishek.spleshscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {
    final String Url = "https://voulu.in/api/login.php";
    String mob;
    TextView txtVerify, timer;
    EditText editText;
    boolean verificationStatus = false;
    SessionManger sessionManger;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    String code;
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
        setTimer();
        sendVerificationCode(mob);
        FirebaseAuthSettings firebaseAuthSettings = firebaseAuth.getFirebaseAuthSettings();


        txtVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificationStatus) {
                    checkLoin(mob);
                }
                else
                {
                    verifyVerificationCode(editText.getText().toString());
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

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
             code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {

                editText.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.d("Kya", e.getMessage());

            Toast.makeText(OTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };



    private void sendVerificationCode(String mob) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mob,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OTP.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            verificationStatus = true;
                            newTime.cancel();
                           // Toast.makeText(getApplicationContext(), "Verifyed", Toast.LENGTH_SHORT).show();
                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                            Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();
                        }
                    }
                });
    }

    void checkLoin(final String mail) {
        final ProgressDialog progressDialog = new ProgressDialog(OTP.this);
        progressDialog.setMessage("Checking your number...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        OTP.this.setFinishOnTouchOutside(false);
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
                                        String photo = object.getString("profile_pic").trim();
                                        String phone = object.getString("mobile").trim();
                                        String gender = object.getString("gender").trim();
                                        String verfied_status = object.getString("verfied_status").trim();
                                        String location = object.getString("address").trim();
                                        Toast.makeText(OTP.this, "Logged In", Toast.LENGTH_LONG).show();
                                        sessionManger.createSession(name, email, photo, verfied_status, phone, gender, location);
                                        Intent in = new Intent(getApplicationContext(), Home.class);
                                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(in);
                                        finish();
                                    }

                                    break;


                                case "2":
                                    progressDialog.dismiss();

                                    Intent in = new Intent(getApplicationContext(), Registration.class);
                                    in.putExtra("mobile", mob);
                                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(in);
                                    finish();
                                    break;
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // email_hint.setError("Invalid Mobile no. or Password");
                            progressDialog.dismiss();

                            Toast.makeText(OTP.this, "Error 1: " + e.toString(), Toast.LENGTH_LONG).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(OTP.this, "Error 2: " + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", mail);

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
         newTime= new CountDownTimer(59000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText("00:" + millisUntilFinished / 1000);
                timer.setClickable(false);
            }

            public void onFinish() {
                Snackbar.make(OTP.this.findViewById(android.R.id.content),
                        "Mobile number invalid or not reachable at this time",
                        Snackbar.LENGTH_LONG).show();
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
