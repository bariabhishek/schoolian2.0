package com.wikav.voulu;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.StrictMode;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.wikav.voulu.fragments.FullScreenDialogForNoInternet;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.ImageQuality;
import com.wikav.voulu.fragments.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static com.wikav.voulu.SpleshScreen.PERMISSIONS_MULTIPLE_REQUEST;

public class UploadYourPost extends  AppCompatActivity {

    AlertDialog alertDialog;
    LinearLayout uploadImageBtn,linearLayoutAmount,linearLayoutTitle,imageViewTwo,imageViewThree;
    RelativeLayout amountrl,titlerl;
    EditText title,dis,pese;
    TextView doneAmount,doneTitle;
    ImageView imageViewOne,imageViewTwoA,imageViewTwoB,imageViewThreeA,imageViewThreeB,imageViewThreeC;
    String mobilenumber;
    String encodedImage;

    public String photo;
    SessionManger sessionManger;
    Bitmap bt1,bt2,bt3;
    int imageCount=0;
    Toolbar toolbar;
    String Url="https://voulu.in/api/jobpost.php";
    ArrayList<String> returnValue = new ArrayList<>();
    final String NO_IMAGE="no_image";
    TextView post;
    BroadcastReceiver broadcastReceiver;
    ImageView backpress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_upload_your_post );
        toolbar=findViewById(R.id.toolbar_uplod);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle( "Upload Your Job" );
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkIntenet();

        sessionManger = new SessionManger( this );
        HashMap <String,String> hashMap = sessionManger.getUserDetail();
        mobilenumber= hashMap.get( SessionManger.MOBILE );
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        StrictMode.VmPolicy.Builder builder= new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        initilisation();
        condition();


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void condition() {
        post.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(title.getText().toString().isEmpty()){
            title.setError( "fill blanck" );
        }else if (dis.getText().toString().isEmpty()){
            dis.setError( "fill blanck"   );
        }else if(pese.getText().toString().isEmpty()){
            pese.setError(  "fill blanck"  );
        }else {
                String titles=title.getText().toString();
                String diss=dis.getText().toString();
                String paise=pese.getText().toString();

                switch (imageCount)
                 {
                     case 0:
                         volleyWithOutImage(mobilenumber,titles,diss,paise,NO_IMAGE);
                         break;
                     case 1:
                         volleyWithOneImage(mobilenumber,titles,diss,paise,getStringImage(bt1));
                        break;
                     case 2:
                         volleyWithTwoImage(mobilenumber,titles,diss,paise,getStringImage(bt1),getStringImage(bt2));
                         break;
                     case 3:
                         volleyWithThreeImage(mobilenumber,titles,diss,paise,getStringImage(bt1),getStringImage(bt2),getStringImage(bt3));
                         break;

                 }
        }
            }
        } );

    }

    private void volleyWithThreeImage(final String mobilenumber, final String titles, final String diss, final String paise, final String stringImage, final String stringImage1, final String stringImage2) {
        Log.i("Method","Three");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        UploadYourPost.this.setFinishOnTouchOutside(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            Log.d("uploadchak", response);

                            if (success.equals("1")){
                                Toast.makeText(UploadYourPost.this, "Success!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Intent views = new Intent(UploadYourPost.this,Home. class);
                                startActivity(views);
                                finish();

                            }
                            else
                            {
                                Toast.makeText(UploadYourPost.this, "Not upload", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(UploadYourPost.this, "Try Again!"+e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadYourPost.this, "Try Again!"+error, Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", titles);
                params.put("mobile", mobilenumber);
                params.put("des", diss);
                params.put("pic1", stringImage);
                params.put("pic2", stringImage1);
                params.put("pic3", stringImage2);
                params.put("rate", paise);
                params.put("image_status","3");

                return params;

            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue( this );
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        stringRequest.setShouldCache(false);
      //  requestQueue.getCache().clear();
        requestQueue.add( stringRequest );
    }

    private void volleyWithTwoImage(final String mobilenumber, final String titles, final String diss, final String paise, final String stringImage, final String stringImage1) {
        Log.i("Method","two");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        UploadYourPost.this.setFinishOnTouchOutside(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            Log.d("uploadchak", response);

                            if (success.equals("1")){
                                Toast.makeText(UploadYourPost.this, "Success!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Intent views = new Intent(UploadYourPost.this,Home. class);
                                startActivity(views);
                                finish();

                            }
                            else
                            {
                                Toast.makeText(UploadYourPost.this, "Not upload", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(UploadYourPost.this, "Try Again!"+e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadYourPost.this, "Try Again!"+error, Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", titles);
                params.put("mobile", mobilenumber);
                params.put("des", diss);
                params.put("pic1", stringImage);
                params.put("pic2", stringImage1);
                params.put("pic3", "No");
                params.put("rate", paise);
                params.put("image_status", "2");
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue( this );
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
       // requestQueue.getCache().clear();
        requestQueue.add( stringRequest );
    }

    private void volleyWithOneImage(final String mobilenumber, final String titles, final String diss, final String paise, final String stringImage) {
        Log.i("Method","one");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        UploadYourPost.this.setFinishOnTouchOutside(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            Log.d("uploadchak", response);

                            if (success.equals("1")){
                                Toast.makeText(UploadYourPost.this, "Success!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Intent views = new Intent(UploadYourPost.this,Home. class);
                                startActivity(views);
                                finish();

                            }
                            else
                            {
                                Toast.makeText(UploadYourPost.this, "Not upload", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(UploadYourPost.this, "Try Again!"+e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadYourPost.this, "Try Again!"+error, Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", titles);
                params.put("mobile", mobilenumber);
                params.put("des", diss);
                params.put("pic1", stringImage);
                params.put("pic2", "No");
                params.put("pic3", "No");
                params.put("rate", paise);
                params.put("image_status", "1");

                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue( this );
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
     //   requestQueue.getCache().clear();
        requestQueue.add( stringRequest );
    }

    private void volleyWithOutImage(final String mobilenumber, final String titles, final String diss, final String paise, final String stringImage) {
        Log.i("Method","zero");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        UploadYourPost.this.setFinishOnTouchOutside(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            Log.d("uploadchak", response);

                            if (success.equals("1")){
                                Toast.makeText(UploadYourPost.this, "Success!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Intent views = new Intent(UploadYourPost.this,Home. class);
                                startActivity(new Intent(UploadYourPost.this,Home. class));
                                finish();



                            }
                            else
                            {
                                Toast.makeText(UploadYourPost.this, "Not upload", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(UploadYourPost.this, "Try Again!"+e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadYourPost.this, "Try Again!"+error, Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", titles);
                params.put("mobile", mobilenumber);
                params.put("des", diss);
                params.put("pic1", "No");
                params.put("pic2", "No");
                params.put("pic3", "No");
                params.put("rate", paise);
                params.put("image_status", "0");

                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue( this );
        stringRequest.setShouldCache(false);
        requestQueue.getCache().clear();
        requestQueue.add( stringRequest );
    }


    private void initilisation() {

        doneAmount = findViewById( R.id.doneamount );
        doneTitle = findViewById( R.id.donetitle );
        post = findViewById( R.id.post );
        title = findViewById( R.id.title );
        dis = findViewById( R.id.discription );
        pese = findViewById( R.id.dealpese );
        imageViewOne= findViewById( R.id.imageOne);
        imageViewTwoA= findViewById( R.id.imageTwoA);
        imageViewTwoB= findViewById( R.id.imageTwoB);
        imageViewTwo= findViewById( R.id.imageTwo);
        imageViewThree= findViewById( R.id.imageThree);
        imageViewThreeA= findViewById( R.id.imageThreeA);
        imageViewThreeB= findViewById( R.id.imageThreeB);
        imageViewThreeC= findViewById( R.id.imageThreeC);

        uploadImageBtn= findViewById( R.id.uplodImageBtn );
        linearLayoutTitle = findViewById( R.id.linear_layout_title );
        linearLayoutAmount = findViewById( R.id.linear_layout_amount );
        amountrl = findViewById( R.id.RLamount );
        titlerl = findViewById( R.id.RLtitle );
        backpress = findViewById( R.id.backpress );
        linearLayoutAmount.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amountrl.setVisibility( View.VISIBLE );
                titlerl.setVisibility( View.GONE  );
            }
        } );
        linearLayoutTitle.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titlerl.setVisibility( View.VISIBLE );
                amountrl.setVisibility( View.GONE );
            }
        } );
        backpress.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        } );
        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermission();
            }
        });
    }

    public String getStringImage(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.e("val", "requestCode ->  " + requestCode+"  resultCode "+resultCode);
        switch (requestCode) {
            case (100): {
                if (resultCode == Activity.RESULT_OK) {
                    returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);

                    Log.e("val", " ->  " + returnValue.get(0));
                    int i = returnValue.size();
                    File f1,f2,f3;
                    switch (i) {
                        case 1 :
                            f1 = new File(returnValue.get(0));

                            imageViewOne.setVisibility(View.VISIBLE);
                            imageViewTwo.setVisibility(View.GONE);
                            imageViewThree.setVisibility(View.GONE);
                            bt1  = new BitmapDrawable(this.getResources(), f1.getAbsolutePath()).getBitmap();
                             bt1=com.fxn.utility.Utility.getScaledBitmap(512, bt1);
                            imageViewOne.setImageBitmap(bt1);
                             imageCount=1;
                        break;


                        case 2 :
                            imageViewTwo.setVisibility(View.VISIBLE);
                            imageViewThree.setVisibility(View.GONE);
                            imageViewOne.setVisibility(View.GONE);
                             f1 = new File(returnValue.get(0));

                            bt1  = new BitmapDrawable(this.getResources(), f1.getAbsolutePath()).getBitmap();
                            bt1=com.fxn.utility.Utility.getScaledBitmap(512, bt1);
                            imageViewTwoA.setImageBitmap(bt1);

                            imageCount=2;

                            f2 = new File(returnValue.get(1));
                            bt2  = new BitmapDrawable(this.getResources(), f2.getAbsolutePath()).getBitmap();
                            bt2=com.fxn.utility.Utility.getScaledBitmap(512, bt2);
                            imageViewTwoB.setImageBitmap(bt2);
                            break;



                        case 3:
                            imageViewThree.setVisibility(View.VISIBLE);
                            imageViewTwo.setVisibility(View.GONE);
                            imageViewOne.setVisibility(View.GONE);
                            f1 = new File(returnValue.get(0));
                            bt1  = new BitmapDrawable(this.getResources(), f1.getAbsolutePath()).getBitmap();                        bt1=com.fxn.utility.Utility.getScaledBitmap(512, bt1);
                            bt1=com.fxn.utility.Utility.getScaledBitmap(512, bt1);
                            imageViewThreeA.setImageBitmap(bt1);

                            imageCount=3;

                            f2 = new File(returnValue.get(1));
                            bt2  = new BitmapDrawable(this.getResources(), f2.getAbsolutePath()).getBitmap();
                            bt2=com.fxn.utility.Utility.getScaledBitmap(512, bt2);
                            imageViewThreeB.setImageBitmap(bt2);

                            f3 = new File(returnValue.get(2));
                            bt3  = new BitmapDrawable(this.getResources(), f3.getAbsolutePath()).getBitmap();
                            bt3=com.fxn.utility.Utility.getScaledBitmap(512, bt3);
                            imageViewThreeC.setImageBitmap(bt3);
                            break;

                    }

                }
            }
            break;
        }
    }
    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(UploadYourPost.this, Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                    .checkSelfPermission(UploadYourPost.this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {


                requestPermissions(
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA},
                        PERMISSIONS_MULTIPLE_REQUEST);



            }
            else {
                Options options = Options.init()
                        .setRequestCode(100)                                                 //Request code for activity results
                        .setCount(3)                                                         //Number of images to restict selection count
                        .setFrontfacing(true)                                                //Front Facing camera on start
                        .setImageQuality(ImageQuality.HIGH)                                  //Image Quality
                        .setImageResolution(1024, 800)                                       //Custom Resolution
                        .setPreSelectedUrls(returnValue)                                     //Pre selected Image Urls
                        .setScreenOrientation(Options.SCREEN_ORIENTATION_REVERSE_PORTRAIT)   //Orientaion
                        .setPath("/pix/images");                                             //Custom Path For Image Storage

                Pix.start(UploadYourPost.this, options);
            }
        }



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


}
