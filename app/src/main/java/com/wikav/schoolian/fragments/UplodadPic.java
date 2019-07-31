package com.wikav.schoolian.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wikav.schoolian.Home;
import com.wikav.schoolian.R;
import com.wikav.schoolian.SessionManger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import static android.app.Activity.RESULT_OK;

public class UplodadPic extends DialogFragment {

    private ImageView viewImage;
    private  Button imageSelect,uploadBtn;
    ImageButton imageButton;
    private EditText postText;
    SessionManger sessionManger;
    public String getId,scl_Id;
    Bitmap newImage;
    int i=0;
    String uplod="https://schoolian.website/android/post_upload.php";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.uplodpic, container, false);
        viewImage=(ImageView)view.findViewById(R.id.postImage2);
        uploadBtn= view.findViewById(R.id.postUp);
        imageSelect=view.findViewById(R.id.choosIm);

        postText=view.findViewById(R.id.postText);
        sessionManger = new SessionManger(getActivity());
        HashMap<String, String> user=sessionManger.getUserDetail();
        String getid = user.get(sessionManger.SID);
        String scl_id = user.get(sessionManger.SCL_ID);
        getId=getid;
        scl_Id=scl_id;
      //  Toast.makeText(getActivity(), ""+scl_id, Toast.LENGTH_SHORT).show();
        imageButton=view.findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewImage.setImageBitmap(null);
                viewImage.setVisibility(View.GONE);
                imageSelect.setVisibility(View.VISIBLE);
                imageButton.setVisibility(View.VISIBLE);
                i=0;
            }
        });


        uploadBtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                final String et=postText.getText().toString().trim();

                if(et.isEmpty()&&i==0)
                {
                    postText.setError("Please Enter Query Or Select Image");
                }
                else {

                    if (i == 0) {
                        //
                        UploadOnlyWrite(getId, et);

                    } else if (et.isEmpty() && i == 1) {
                        // Toast.makeText(feedUpload.this, "et worrk", Toast.LENGTH_SHORT).show();
                        UploadOnlyPicture(getId, getStringImage(newImage));
                    } else {
                        //Toast.makeText(feedUpload.this, "waorklslsls", Toast.LENGTH_SHORT).show();
                        UploadWithPicture(getId, et, getStringImage(newImage));
                    }

                }
            }

        });

        imageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        return view;
    }


    private void UploadWithPicture(final String id, final String et, final String stringImage) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        getActivity().setFinishOnTouchOutside(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, uplod,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        progressDialog.dismiss();
                        Log.i("TAG", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                               Intent intent=new Intent(getActivity(),Home.class);
                                intent.putExtra("intent","value");
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Try Again!"+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Try Again!" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("student_id",id);
                params.put("post_pic",stringImage);
                params.put("posts",et);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
      RequestQueue requestQueue=Volley.newRequestQueue(getActivity());
      requestQueue.add(stringRequest);
      requestQueue.getCache().clear();

    }

/////////////////////////////////////////////////////////////// UPLOAD ONLY TEXT//////////////////////////////////////////////////////////////////////////////////

    private void UploadOnlyWrite(final String id, final String et) {
        final String BLANK="NA";
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
      getActivity().setFinishOnTouchOutside(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uplod,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(),Home.class);
                                intent.putExtra("intent","value");
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Try Again!"+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Try Again!" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("student_id",id);
                params.put("posts",et);
                params.put("post_pic",BLANK);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue=Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();

    }

    public void chooseImage() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(getContext(),this);

    }


    /////////////////////////////////////////////////////////////// UPLOAD PICTURE ONLY//////////////////////////////////////////////////////////////////////////////////
    private void UploadOnlyPicture(final String id, final String photo) {
        final String BLANK="";
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        getActivity().setFinishOnTouchOutside(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, uplod,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i("TAG", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getActivity(),Home.class);
                                intent.putExtra("intent","value");
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                          //  Toast.makeText(getActivity(), "Try Again!"+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                     //   Toast.makeText(getActivity(), "Try Again!" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("student_id", id);
                params.put("post_pic", photo);
                params.put("posts", BLANK);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue=Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();

    }

    public String getStringImage(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

        return encodedImage;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();
            //    Toast.makeText(getActivity(), "Okk", Toast.LENGTH_SHORT).show();

                try {
                    newImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                    viewImage.setVisibility(View.VISIBLE);
                    viewImage.setImageBitmap(newImage);
                    i=1;
                    imageButton.setVisibility(View.VISIBLE);
                    imageSelect.setVisibility(View.GONE);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.i("Photo",error.toString());
            }
        }
    }
}
