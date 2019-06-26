package com.wikav.schoolian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.wikav.schoolian.SpleshScreen.PERMISSIONS_MULTIPLE_REQUEST;

public class EditProfile extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText name,email,phone,location,dob,quali,about;
    private TextView saveBtn,age;
    private ImageView editImage,imageUploadBtn;
    private String sessionName,sessionImage,sessionPhone,sessionEmail,sessionLocation,sessionAbout,sessionQuali,sessionDob;
    private SessionManger sessionManger;
    BroadcastReceiver broadcastReceiver;
    RadioButton genderradioButton;
    RadioGroup radioGroup;
    boolean isNewImageSet = false;
    Bitmap newImage;
    private static final int DIALOG_REQUEST_ERROR=9001;
    Snackbar snackbar;
    private int mYear, mMonth, mDay, mHour, mMinute;

    final String Url="https://voulu.in/api/updateProfile.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
       setContentView( R.layout.activity_edit_profile );
        sessionManger = new SessionManger(this);
        snackbar=  Snackbar.make(this.findViewById(android.R.id.content),
                Html.fromHtml("<font color=\"#ffffff\">No Internet Connection</font>"),
                BaseTransientBottomBar.LENGTH_INDEFINITE);

        HashMap<String,String> user=sessionManger.getUserDetail();
        sessionName = user.get(sessionManger.NAME);
        sessionPhone = user.get(sessionManger.MOBILE);
        sessionImage = user.get( sessionManger.PROFILE_PIC );
        sessionEmail = user.get( sessionManger.EMAIL );
        sessionLocation = user.get( sessionManger.LOCATION );
        sessionDob = user.get( sessionManger.DOB );
        sessionQuali = user.get( sessionManger.QUALI );
        sessionAbout = user.get( sessionManger.BIO);

        toolbar=findViewById(R.id.toolbarEdit);
        setToolbar();
        checkInptenet();
        name=findViewById(R.id.nameUser);
        email=findViewById(R.id.emailEdit);
        phone=findViewById(R.id.mobileNumber);
        quali=findViewById(R.id.quali);
        dob=findViewById(R.id.dob);
        age=findViewById(R.id.ageTv);
        about=findViewById(R.id.aboutSelf);
        location=findViewById(R.id.location);
        saveBtn=findViewById(R.id.saveButton);
        editImage=findViewById(R.id.imageviewedit);
        imageUploadBtn=findViewById(R.id.imageUploadBtn);
        //radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        if(serviceCheck())
        {
           // Toast.makeText(this, "ho rha he sb kuch ok", Toast.LENGTH_SHORT).show();

            location.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus)
                {
                    getPermission();
                }
            });
        }
        setAllFileds();
    }

    private void setAllFileds() {
        name.setText(sessionName);
        email.setText(sessionEmail);
        phone.setText(sessionPhone);
        location.setText(sessionLocation);
        quali.setText(sessionQuali);
        about.setText(sessionAbout);
        dob.setText(sessionDob);
        Glide.with(this).load(sessionImage).into(editImage);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckData();
            }
        });

        imageUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCameraPermission();
            }
        });

        dob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                final Date date=new Date();


                DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfile.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                int current_year=date.getYear()+1900;
                                int a=current_year-year;
                                age.setText(a+"+");
                                Toast.makeText(EditProfile.this, ""+a, Toast.LENGTH_SHORT).show();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "calll", Toast.LENGTH_SHORT).show();
                Uri resultUri = result.getUri();
                try {

                    newImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    editImage.setImageBitmap(newImage);
                    isNewImageSet=true;
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.i("Photo",error.toString());
            }
        }
    }
    private void CheckData() {
        String Name= name.getText().toString();
        String Email= email.getText().toString();
        String Phone= phone.getText().toString();
        String Location= location.getText().toString();
        String Bio= about.getText().toString();
        String Quali= quali.getText().toString();
        String Dob= dob.getText().toString();
        if(Name.equals("")|| Email.equals("")|| Phone.equals(""))
        {
            Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (isNewImageSet)
            {   isNewImageSet=false;
                uplaodData(Name,Email,Phone,Location,getStringImage(newImage),Dob,Bio,Quali);
                return;
            }
            else
            {
                uplaodData(Name,Email,Phone,Location,sessionImage, Dob, Bio, Quali);

            }

        }
    }



    private void uplaodData(final String name, final String email, final String phone, final String location, final String stringImage, final String dob, final String bio, final String quali)
    {
        Log.i("profileImage",stringImage);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        EditProfile.this.setFinishOnTouchOutside(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //    progressDialog.dismiss();
                        Log.i("TAG", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("update");
                            if (success.equals("1")){
                                //updateSession(String name, String email, String photo, String mobile,String location)
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String name = object.getString("name").trim();
                                    String email = object.getString("email").trim();
                                    String photo = object.getString("profile_pic").trim();
                                    String phone = object.getString("mobile").trim();
                                    String location = object.getString("address").trim();
                                    String bio = object.getString("bio").trim();
                                    String quali = object.getString("quali").trim();
                                    String dob = object.getString("dob").trim();
                                   // sessionManger.clerlast();
                                  //  sessionManger.updateSession(name, email, photo, phone,location, bio, quali, dob);
                                    progressDialog.dismiss();
                                    finish();

                                }
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(EditProfile.this, "Not Updated", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(EditProfile.this, "Not Updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(EditProfile.this, "Not Updated", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("name", name);
                params.put("mobile", phone);
                params.put("email", email);
                params.put("profile_pic", stringImage);
                params.put("address", location);
                params.put("quali", quali);
                params.put("dob", dob);
                params.put("bio", bio);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }

    private void setToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        toolbar.setTitle("EditProfile");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public String getStringImage(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);
        return encodedImage;
    }

    public void onclickbuttonMethod(View v){
        int selectedId = radioGroup.getCheckedRadioButtonId();
        genderradioButton = (RadioButton) findViewById(selectedId);
        if(selectedId==-1){
            Toast.makeText(getApplicationContext(),"Nothing selected", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),genderradioButton.getText(), Toast.LENGTH_SHORT).show();
        }

    }

    public void checkInptenet() {
        IntentFilter  intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int[] type = {ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI};


                if (ConnectivityReceiver.isNetworkAvailable(getApplicationContext(), type)) {
                    if (snackbar.isShown())
                        snackbar.dismiss();
                } else {
                    //Toast.makeText(context, "Toast", Toast.LENGTH_SHORT).show();
                /*FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                FullScreenDialogForNoInternet full=new FullScreenDialogForNoInternet();
                full.show(ft,"show");*/

                    snackbar.show();


                }

            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
    }
    private boolean serviceCheck()
    {
        int result= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(EditProfile.this);
        if(result== ConnectionResult.SUCCESS)
        {
            Log.d("ConnectOk","ok hai connection");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(result))
        {
            Log.d("ConnectOk","error he but thik hoga");
            Dialog dialog=GoogleApiAvailability.getInstance().getErrorDialog(EditProfile.this,result,DIALOG_REQUEST_ERROR);
            dialog.show();
        }
        else
        {
            Toast.makeText(this, "kuch nhi kar skte", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(EditProfile.this, Manifest.permission.ACCESS_FINE_LOCATION) + ContextCompat
                    .checkSelfPermission(EditProfile.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {


                requestPermissions(
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSIONS_MULTIPLE_REQUEST);


            } else {

            }
        }


    }
    private void getCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(EditProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                    .checkSelfPermission(EditProfile.this, Manifest.permission.CAMERA)
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
                        .start(EditProfile.this);
            }
        }


    }
}
