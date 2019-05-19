package startup.abhishek.spleshscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import startup.abhishek.spleshscreen.fragments.FullScreenDialogForNoInternet;
import startup.abhishek.spleshscreen.fragments.ProfileFragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText name,email,phone,location;
    private TextView saveBtn;
    private ImageView editImage,imageUploadBtn;
    private String sessionName,sessionImage,sessionPhone,sessionEmail,sessionLocation;
    private SessionManger sessionManger;
    BroadcastReceiver broadcastReceiver;
    boolean isNewImageSet = false;
    Bitmap newImage;
    final String Url="https://voulu.in/api/updateProfile.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
       setContentView( R.layout.activity_edit_profile );
        sessionManger = new SessionManger(this);
        HashMap<String,String> user=sessionManger.getUserDetail();
        sessionName = user.get(sessionManger.NAME);
        sessionPhone = user.get(sessionManger.MOBILE);
        sessionImage = user.get( sessionManger.PROFILE_PIC );
        sessionEmail = user.get( sessionManger.EMAIL );
        sessionLocation = user.get( sessionManger.LOCATION );
        toolbar=findViewById(R.id.toolbarEdit);
        setToolbar();
        checkIntenet();
        name=findViewById(R.id.nameUser);
        email=findViewById(R.id.emailEdit);
        phone=findViewById(R.id.mobileNumber);
        phone.setEnabled(false);
        location=findViewById(R.id.location);
        saveBtn=findViewById(R.id.saveButton);
        editImage=findViewById(R.id.imageviewedit);
        imageUploadBtn=findViewById(R.id.imageUploadBtn);
        setAllFileds();
    }

    private void setAllFileds() {
        name.setText(sessionName);
        email.setText(sessionEmail);
        phone.setText(sessionPhone);
        location.setText(sessionLocation);
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
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(EditProfile.this);
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
        if(Name.equals("")|| Email.equals("")|| Phone.equals(""))
        {
            Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (isNewImageSet)
            {   isNewImageSet=false;
                uplaodData(Name,Email,Phone,Location,getStringImage(newImage));
                return;
            }
            else
            {
                uplaodData(Name,Email,Phone,Location,sessionImage);

            }

        }
    }



    private void uplaodData(final String name, final String email, final String phone, final String location, final String stringImage)
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
                                   // sessionManger.clerlast();
                                    sessionManger.updateSession(name, email, photo, phone,location);
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
    public void checkIntenet() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
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

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);

    }
}
