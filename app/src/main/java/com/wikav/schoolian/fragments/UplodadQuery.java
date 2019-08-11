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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class UplodadQuery extends DialogFragment {

    private final String JSON_URL_EXAM = "https://schoolian.in/android/newApi/subjectName.php";
    public String getId, scl_Id, Clas;
    ImageButton imageButton;
    SessionManger sessionManger;
    Bitmap newImage;
    Spinner spinner;
    String selectedSubject = null;
    int i = 0;
    ArrayAdapter<String> dataAdapter;
    List<String> subName;
    String uplod = "https://schoolian.in/android/newApi/work_upload.php";
    private ImageView viewImage;
    private Button imageSelect, uploadBtn;
    private EditText postText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.uplodpic_query, container, false);
        viewImage = (ImageView) view.findViewById(R.id.postImage2);
        uploadBtn = view.findViewById(R.id.postUp);
        imageSelect = view.findViewById(R.id.choosIm);
        spinner = view.findViewById(R.id.spinnerCls);

        postText = view.findViewById(R.id.postText);
        sessionManger = new SessionManger(getActivity());
        HashMap<String, String> user = sessionManger.getUserDetail();
        String getid = user.get(sessionManger.SID);
        String scl_id = user.get(sessionManger.SCL_ID);
        String cls = user.get(sessionManger.CLAS);
        getId = getid;
        scl_Id = scl_id;
        Clas = cls;

        //  Toast.makeText(getActivity(), ""+scl_id, Toast.LENGTH_SHORT).show();
        imageButton = view.findViewById(R.id.imageButton);
        subName = new ArrayList<>();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewImage.setImageBitmap(null);
                viewImage.setVisibility(View.GONE);
                imageSelect.setVisibility(View.VISIBLE);
                imageButton.setVisibility(View.VISIBLE);
                i = 0;
            }
        });


        uploadBtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                final String et = postText.getText().toString().trim();

                if (et.isEmpty() && i == 0) {
                    postText.setError("Please Enter Query Or Select Image");
                } else {

                    if (i == 0) {
                        //
                        UploadOnlyWrite(getId, et,selectedSubject,Clas);

                    } else if (et.isEmpty() && i == 1) {
                        // Toast.makeText(feedUpload.this, "et worrk", Toast.LENGTH_SHORT).show();
                        UploadOnlyPicture(getId, getStringImage(newImage),selectedSubject,Clas);
                    } else {
                        //Toast.makeText(feedUpload.this, "waorklslsls", Toast.LENGTH_SHORT).show();
                        UploadWithPicture(getId, et, getStringImage(newImage),selectedSubject,Clas);
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedSubject = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getExamName(scl_Id, Clas);
        return view;
    }


    private void UploadWithPicture(final String id, final String et, final String stringImage, final String subject, final String cls) {

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

                            if (success.equals("1")) {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), Home.class);
                                intent.putExtra("intent", "value");
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Try Again!" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Try Again!" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("student_id", id);
                params.put("post_pic", stringImage);
                params.put("posts", et);
                params.put("subject", subject);
                params.put("class", cls);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();

    }

/////////////////////////////////////////////////////////////// UPLOAD ONLY TEXT//////////////////////////////////////////////////////////////////////////////////

    private void UploadOnlyWrite(final String id, final String et, final String subject, final String cls) {
        final String BLANK = "NA";
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        getActivity().setFinishOnTouchOutside(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uplod,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("uploadTag",response);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), Home.class);
                                intent.putExtra("intent", "value");
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Try Again!" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Try Again!" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("student_id", id);
                params.put("posts", et);
                params.put("post_pic", BLANK);
                params.put("subject", subject);
                params.put("class", cls);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();

    }

    /////////////////////////////////////////////////////////////// UPLOAD PICTURE ONLY//////////////////////////////////////////////////////////////////////////////////
    private void UploadOnlyPicture(final String id, final String photo, final String subject, final String cls) {
        final String BLANK = "";
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

                            if (success.equals("1")) {
                                Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getActivity(), Home.class);
                                intent.putExtra("intent", "value");
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
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("student_id", id);
                params.put("post_pic", photo);
                params.put("posts", BLANK);
                params.put("subject", subject);
                params.put("class", cls);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();

    }

    public String getStringImage(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

        return encodedImage;
    }

    public void chooseImage() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(getContext(), this);

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
                    i = 1;
                    imageButton.setVisibility(View.VISIBLE);
                    imageSelect.setVisibility(View.GONE);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.i("Photo", error.toString());
            }
        }
    }

    private void getExamName(final String sclid, final String clas) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL_EXAM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAGRespos", response + clas + sclid);
                try {

                    JSONObject jsonObject1 = new JSONObject(response);
                    String success = jsonObject1.getString("success");
                    JSONArray jsonArray = jsonObject1.getJSONArray("examName");

                    if (success.equals("1")) {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String classes = jsonObject.getString("subject");

                            subName.add(classes);


                        }
                        setAdapterMethod(subName);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    //   Toast.makeText(getApplicationContext(), "Error 1: " + e.toString(), Toast.LENGTH_LONG).show();
//                    button.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(getApplicationContext(), "Error 2: " + error.toString(), Toast.LENGTH_LONG).show();
//                        button.setVisibility(View.VISIBLE);
//                        progressBar.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("school_id", sclid);
                param.put("class", clas);

                return param;

            }
        };
        stringRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void setAdapterMethod(List<String> clsases) {

        dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, clsases);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }

}
