package com.wikav.schoolian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SchoolIDopt extends AppCompatActivity {
    RelativeLayout relativeLayout,school_name_Rlayout;
    EditText schoolOtpET;
    Button schoolOtpButton,schoolNamebutton;
    TextView scl_name;
    ImageView imageView;
    String mobile;
    private String url="https://schoolian.website/android/newApi/getSchoolName.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_school_idopt );
        mobile  = getIntent().getStringExtra( "mobile" );

        relativeLayout = findViewById( R.id.school_otp_Rlayout );
        schoolOtpET = findViewById(R.id.editTextSchoolVerificatioCode );
        schoolOtpButton = findViewById( R.id.SchoolOtpButton );
        school_name_Rlayout = findViewById( R.id.school_name_Rlayout );
        scl_name = findViewById( R.id.school_name );
        schoolNamebutton = findViewById( R.id.btnSchoolName );
        imageView = findViewById( R.id.school_logo );


        schoolOtpButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(schoolOtpET.getText().toString().isEmpty()){
                    schoolOtpET.setError( "Somthing Wrong" );
                }else {

                    getSchoolData(schoolOtpET.getText().toString());

                //    Toast.makeText( getApplicationContext(),"working",Toast.LENGTH_LONG ).show();
                }
            }
        } );

    }

    private void layoutVisible(final String sclId, String sclname , String imag,String banner) {
        scl_name.setText("Welcome to "+sclname);
        Glide.with( getApplicationContext() ).load( imag ).into( imageView );
    relativeLayout.setVisibility( View.INVISIBLE );
        school_name_Rlayout.setVisibility( View.VISIBLE );
        schoolNamebutton .setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(),Registration.class );
                intent.putExtra("scl_id",sclId);
                intent.putExtra( "mobile", mobile);
                startActivity( intent );
                finish();
            }
        } );

    }

 private void getSchoolData(final String sclId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("sclData");
                            // Toast.makeText(Login.this, mail+" "+passw+" "+ success, Toast.LENGTH_SHORT).show();
                            switch (success) {
                                case "1":

                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);
                                        String sclname = object.getString("sclName").trim();
                                        String sclId = object.getString("sclId").trim();
                                        String pic = object.getString( "logo" ).trim();
                                        String banner = object.getString( "bnr" ).trim();
                                        layoutVisible(sclId,sclname,pic,banner);
                                    }

                                    break;

                               case "0":
                                    /*etPass.setError("Invalid password");
                                    login_btn.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);*/
                                   schoolOtpET.setError("Invalid Id");
                                    break;


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // email_hint.setError("Invalid Mobile no. or Password");
                        //    Toast.makeText(SchoolIDopt.this, "Error 1: " + e.toString(), Toast.LENGTH_LONG).show();
                           /* login_btn.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);*/
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    //    Toast.makeText(SchoolIDopt.this, "Error 2: " + error.toString(), Toast.LENGTH_LONG).show();
                       /* login_btn.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);*/
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", sclId);
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

}
