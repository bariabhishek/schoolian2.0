package com.wikav.schoolian.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.wikav.schoolian.EditProfile;
import com.wikav.schoolian.R;
import com.wikav.schoolian.SessionManger;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    Bitmap bitmap;
    CircleImageView uploadProfile;
    int PICK_IMAGE_REQUEST = 0;
    String phone;
    Date date;
    TextView classKonsi;
    Button logout;
    String addImageUrl = "https://voulu.in/api/profileData.php";
    SessionManger sessionManger;
    TextView username, mobile, email, achivment, qualiTv, bioTv, dobTv,age,stream,section,parentsRelation,parentsName ;
    TextView editProfileBtn;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        sessionManger = new SessionManger(getContext());
        uploadProfile = view.findViewById(R.id.profileUpload);
        qualiTv = view.findViewById(R.id.studentClass);
        dobTv = view.findViewById(R.id.dobTv);
        age = view.findViewById(R.id.agtb);
        classKonsi = view.findViewById( R.id.clasesKonsi );
        bioTv = view.findViewById(R.id.hobbies);
//        uploadProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent view = new Intent(getActivity(), EditProfile.class);
//                startActivity(view);
//            }
//        });

        editProfileBtn = view.findViewById(R.id.edit1);
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tadddd", "click work");
                Toast.makeText(getActivity(), "jhhhh", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent( getActivity(),EditProfile.class );
                startActivity( intent );
            }
        });

        username = view.findViewById(R.id.name);
       /* nopost = view.findViewById(R.id.numPost);
        youpost = view.findViewById(R.id.youaccept);*/
        achivment = view.findViewById(R.id.achivement);
        mobile = view.findViewById(R.id.mobile);
        email = view.findViewById(R.id.rollnumber);


        stream = view.findViewById( R.id.stream );
        section = view.findViewById( R.id.section );
        ImageView parentsImage = view.findViewById( R.id.parentsimage );
        parentsRelation = view.findViewById( R.id.parentsRelation );
        parentsName = view.findViewById( R.id.parentsName );


        profileData();


 date=new Date();
        HashMap<String, String> user = sessionManger.getUserDetail();
        String name = user.get(sessionManger.NAME);
        phone = user.get(sessionManger.MOBILE);
        String image = user.get(sessionManger.PROFILE_PIC);
        String email = user.get(sessionManger.EMAIL);
        String sessionDob = user.get(sessionManger.DOB);
        String sessionQuali = user.get(sessionManger.QUALI);
        String sessionAbout = user.get(sessionManger.BIO);
        //Log.d("myArry", "my dob"+sessionDob);

               /* if(!sessionDob.equals("")) {
                    String[] spl = sessionDob.split("/");
                    String num = spl[2].trim();
                    int aa = Integer.parseInt(num);
                    int current_year = date.getYear() + 1900;
                    int a = current_year - aa;
                    age.setText(a+"+");
                    Log.d("myArry", spl[2]);
                }
                else
                {
                    age.setText(18+"+");
                }*/


        username.setText(name);
        mobile.setText(phone);
        qualiTv.setText(sessionQuali);
        bioTv.setText(sessionAbout);
        dobTv.setText(sessionDob);
        this.email.setText(email);

        Glide.with(getActivity())
                .load(image)
                .into(uploadProfile);


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
                                   /* youpost.setText(youAccepted);
                                    nopost.setText(noOfPost);
                                    yourpost.setText(yourAccepted);*/

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

    @Override
    public void onResume() {
        super.onResume();
        HashMap<String, String> user = sessionManger.getUserDetail();
        String name = user.get(sessionManger.NAME);
        phone = user.get(sessionManger.MOBILE);
        String image = user.get(sessionManger.PROFILE_PIC);
        String email = user.get(sessionManger.EMAIL);
        String sessionDob = user.get(sessionManger.DOB);
        String sessionQuali = user.get(sessionManger.QUALI);
        String sessionAbout = user.get(sessionManger.BIO);
        String sessionCls = user.get( sessionManger.CLAS );
        /*if(!sessionDob.equals("")) {
            String[] spl = sessionDob.split("/");
            String num = spl[2].trim();
            int aa = Integer.parseInt(num);
            int current_year = date.getYear() + 1900;
            int a = current_year - aa;
            age.setText(a+"+");
            Log.d("myArry", spl[2]);
        }
        else
        {
            age.setText(18+"+");
        }*/
        username.setText(name);
        mobile.setText(phone);
        qualiTv.setText(sessionQuali);
        bioTv.setText(sessionAbout);
        dobTv.setText(sessionDob);
        classKonsi.setText( sessionCls );
        this.email.setText(email);

        Glide.with(getActivity())
                .load(image)
                .into(uploadProfile);
    }
}
