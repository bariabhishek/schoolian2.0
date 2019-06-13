package com.wikav.voulu;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import androidx.core.content.ContextCompat;
import de.hdodenhof.circleimageview.CircleImageView;
import com.wikav.voulu.fragments.FullScreenDialogForNoInternet;

import static com.wikav.voulu.SpleshScreen.PERMISSIONS_MULTIPLE_REQUEST;

public class Registration extends AppCompatActivity {
    BroadcastReceiver broadcastReceiver;
    CircleImageView circleImageView;
    Button signin;
    TextInputLayout username, userpassword, usermobile;
    EditText name, password, mobile;
    RadioGroup radioGroup;
    RadioButton male, female;
    ProgressBar progressBar;
    Bitmap profileImage = null;
    boolean isImageset = false;
    String Gender = "male";
    String simage = Gender;
    String newToken, mob;
    CheckBox chkIos;
    Spinner spinner;
    SessionManger sessionManger;
    String Url = "https://voulu.in/api/register.php";
    Uri resultUri;

    IntentFilter intentFilter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_resistration);
        sessionManger = new SessionManger(this);
        circleImageView = findViewById(R.id.civ);
        progressBar = findViewById(R.id.progressBarReg);
        name = findViewById(R.id.edittextname);
        mobile = findViewById(R.id.edittextMobile);
       // spinner = (Spinner) findViewById(R.id.spinnert);
       // password = findViewById(R.id.edittextpassword1);
        signin = findViewById(R.id.login_btn);
        signin.setEnabled(false);
        addListenerOnChkIos();
        username = findViewById(R.id.etname);
        userpassword = findViewById(R.id.etEmail);
        usermobile = findViewById(R.id.etMobile);
        radioGroup = findViewById(R.id.redoigroup);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        checkIntenet();
        mob = getIntent().getStringExtra("mobile");
        mobile.setText(mob);
        mobile.setEnabled(false);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermission();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.male) {
                    final String gender = "male";
                    if (!isImageset) {
                       // circleImageView.setImageResource(R.drawable.boy);

                        Gender = gender;
                      //  Toast.makeText(Registration.this, "" + Gender, Toast.LENGTH_SHORT).show();
                    } else {
                        Gender = gender;
                    }

                } else if (checkedId == R.id.female) {
                    final String gender = "female";
                    if (!isImageset) {
                      //  circleImageView.setImageResource(R.drawable.girl);
                        Gender = gender;
                     //   Toast.makeText(Registration.this, "" + Gender, Toast.LENGTH_SHORT).show();
                    } else {
                      Gender = gender;
                    }
                }

            }

        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signCondition();
            }
        });
    }

    private void signCondition() {


        final String sname = name.getText().toString();
        final String spassword = password.getText().toString();
        final String smobile = mobile.getText().toString();


        if (sname.isEmpty() && spassword.isEmpty() && smobile.isEmpty()) {
            username.setError("not Valid");

        } else {
            if (password.getText().toString().length() <= 8) {
                userpassword.setError("minimum 8 character requierd");
                Toast.makeText(getApplicationContext(), "minimum 8 character", Toast.LENGTH_LONG).show();
            } else {
                if (isImageset) {
                    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
                        @Override
                        public void onSuccess(InstanceIdResult instanceIdResult) {
                            newToken = instanceIdResult.getToken();
                            Log.e("newToken", newToken);
                            uploadDataWithImage(sname, smobile, spassword, Gender, getStringImage(profileImage), newToken);

                        }
                    });

                } else {
                    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
                        @Override
                        public void onSuccess(InstanceIdResult instanceIdResult) {
                            newToken = instanceIdResult.getToken();
                            Log.e("newToken", newToken);
                            uploadDataWithOutImage(sname, smobile, spassword, Gender, simage, newToken);

                        }
                    });

                }
            }
        }


    }

    private void uploadDataWithImage(final String sname, final String smobile, final String spassword, final String gender, final String stringimage, final String newToken) {
        progressBar.setVisibility(View.VISIBLE);
        signin.setVisibility(View.GONE);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Log.i("profileImage", profileImage.toString());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //    progressDialog.dismiss();
                Log.i("TAG", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("register");
                    if (success.equals("1")) {
                        Log.d("Response", response + " " + gender);
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
                            Intent intent = new Intent(Registration.this, Home.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        signin.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Registration.this, "error:1- " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    signin.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Registration.this, "error:1- " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        signin.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("name", sname);
                params.put("mobile", smobile);
                params.put("gender", gender);
                params.put("email", "NA");
                params.put("verfiedStatus", "");
                params.put("usertype", "");
                params.put("password", spassword);
                params.put("profile_pic", stringimage);
                params.put("without", "image");
                params.put("token", newToken);


                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);

        requestQueue.add(stringRequest);

    }

    public String getStringImage(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);
        Toast.makeText(this, "Call", Toast.LENGTH_SHORT).show();
        return encodedImage;
    }

    private void uploadDataWithOutImage(final String sname, final String smobile, final String spassword, final String gender, final String simage, final String newToken) {
        progressBar.setVisibility(View.VISIBLE);
        signin.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //    progressDialog.dismiss();
                        Log.i("TAG", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("register");
                            if (success.equals("1")) {
                                Log.d("Response", response + " " + gender);
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
                                    Intent intent = new Intent(Registration.this, Home.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                progressBar.setVisibility(View.GONE);
                                signin.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("name", sname);
                params.put("mobile", smobile);
                params.put("gender", gender);
                params.put("email", "NA");
                params.put("verfiedStatus", "");
                params.put("usertype", "");
                params.put("password", spassword);
                params.put("profile_pic", simage);
                params.put("without", "noImage");
                params.put("token", newToken);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                resultUri = result.getUri();
                try {
                    profileImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    circleImageView.setImageBitmap(profileImage);
                    isImageset = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.i("Photo", error.toString());
            }
        }
    }

    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(Registration.this, Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                    .checkSelfPermission(Registration.this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {


                requestPermissions(
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA},
                        PERMISSIONS_MULTIPLE_REQUEST);


            } else {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(Registration.this);
            }
        }


    }

    public void checkIntenet() {
         intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int[] type = {ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI};
                if (ConnectivityReceiver.isNetworkAvailable(context, type)) {
                    return;
                } else {
                    FullScreenDialogForNoInternet full = new FullScreenDialogForNoInternet();
                    full.show(getSupportFragmentManager(), "show");
                }
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
    }

  /*  @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, intentFilter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (broadcastReceiver!= null)
            unregisterReceiver(broadcastReceiver);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver!= null)
            unregisterReceiver(broadcastReceiver);
    }*/

    public void trems(View view) {
        String url = "http://voulu.in/terms.php";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    public void addListenerOnChkIos() {

        chkIos = (CheckBox) findViewById(R.id.checkOne);

        chkIos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                //    Toast.makeText(Registration.this, "ehch", Toast.LENGTH_SHORT).show();
                    signin.setEnabled(true);
                }
                else
                {
                    signin.setEnabled(false);
                }

            }
        });

    }
}
