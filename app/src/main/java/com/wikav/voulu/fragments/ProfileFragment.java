package com.wikav.voulu.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

import com.wikav.voulu.EditProfile;
import com.wikav.voulu.R;
import com.wikav.voulu.SessionManger;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    Bitmap bitmap;
    ImageView uploadProfile;
    int PICK_IMAGE_REQUEST = 0;
    String phone;
    Button editProfile;
    Button logout;
String addImageUrl="https://voulu.in/api/profileData.php";
    SessionManger sessionManger;
    TextView username,mobile,email,yourpost,youpost,nopost;
    ImageView imageView;

    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_profile, container, false );
        sessionManger=new SessionManger( getContext() );

        uploadProfile = view.findViewById( R.id.profileUpload );

        username = view.findViewById( R.id.name );
        nopost = view.findViewById( R.id.numPost );
        youpost = view.findViewById( R.id.youaccept );
        yourpost = view.findViewById( R.id.youraccept );
        mobile = view.findViewById( R.id.mobile );
        email = view.findViewById( R.id.email );
       // numberOfJob = view.findViewById( R.id.numberOfJob );
        editProfile = view.findViewById( R.id.editProfile );
        logout = view.findViewById( R.id.logot );
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage("Are you sure, you want to logout");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                sessionManger.logOut();
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        alertDialogBuilder.setCancelable(true);
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();;
            }
        });
editProfile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       /* FullScreenDialogForUpdateApp full=new FullScreenDialogForUpdateApp();
            full.show(getFragmentManager(),"TAG");*/
       Intent intent = new Intent(getActivity(), EditProfile.class);
       startActivity(intent);

    }
});

        HashMap<String,String> user=sessionManger.getUserDetail();
        String name = user.get(sessionManger.NAME);
         phone = user.get(sessionManger.MOBILE);
        String image = user.get( sessionManger.PROFILE_PIC );
        String email = user.get( sessionManger.EMAIL );

        username.setText( name );
        mobile.setText( phone );
        this.email.setText( email );

        Glide.with(getActivity())
                .load(image)
                .into(uploadProfile);


        profileData();




        return view;
    }

    private void profileData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, addImageUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("Post");
                            if (success.equals("1")) {
                                Log.d("Response", response);
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String noOfPost = object.getString("no_posts").trim();
                                    String yourAccepted = object.getString("job_seeker").trim();
                                    String youAccepted = object.getString("job_giver").trim();
                                    String donePosts = object.getString("job_done").trim();
                                    youpost.setText(youAccepted);
                                    nopost.setText(noOfPost);
                                    yourpost.setText(yourAccepted);

                                }
//
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(getActivity(), "Something went wrong..."+error, Toast.LENGTH_LONG).show();
                        // noData.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", "9195A3CDB388F894B3EE3BD665DFD");
                params.put("mobile", phone);
                return params;
            }
        };
        stringRequest.setShouldCache(true);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        // requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }


}
