package com.wikav.schoolian.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.wikav.schoolian.AttendanceLayout;
import com.wikav.schoolian.ClassTimeTableSchoolian;
import com.wikav.schoolian.Holidays;
import com.wikav.schoolian.Notice;
import com.wikav.schoolian.R;
import com.wikav.schoolian.Results;
import com.wikav.schoolian.SchoolIDopt;
import com.wikav.schoolian.SchoolianWorld;
import com.wikav.schoolian.SessionManger;
import com.wikav.schoolian.ClassMates;
import com.wikav.schoolian.TeacherList;
import com.wikav.schoolian.Syllabus;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainGridFragment extends Fragment implements View.OnClickListener {

    Toolbar toolbar;
    SessionManger sessionManger;
    LinearLayout profile,exam,result,classRoutine,message, attendance,notice,event,holiday;
    ImageView feed;

    private String url="https://schoolian.website/android/newApi/sclName.php";
    public MainGridFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sessionManger=new SessionManger(getActivity());
        HashMap<String,String> getUser=sessionManger.getUserDetail();
        String SchlName=getUser.get(sessionManger.SCLNAME);
        String sclId=getUser.get(sessionManger.SCL_ID);
        View view = inflater.inflate( R.layout.activity_grid_home, container, false );
        toolbar=view.findViewById(R.id.homeToolbar);
        toolbar.setTitle(SchlName);
        toolbar.setTitleMarginStart(150);
        profile=view.findViewById(R.id.profileBtn);
        feed = view.findViewById( R.id.feedpost );
        exam=view.findViewById(R.id.examBtn);
        result=view.findViewById(R.id.resultBtn);
        classRoutine=view.findViewById(R.id.classRoutBtn);
        message=view.findViewById(R.id.classMateBtn );
        attendance=view.findViewById(R.id.attendanceBtn);
        notice=view.findViewById(R.id.noticBtn);
        holiday=view.findViewById(R.id.holidayBtn);
        event=view.findViewById(R.id.eventsBtn);
        api(sclId);
       setClick();
        return view;
    }

    private void setClick() {
        profile.setOnClickListener(this);
        exam.setOnClickListener(this);
        result.setOnClickListener(this);
        classRoutine.setOnClickListener(this);
        message.setOnClickListener(this);
        attendance.setOnClickListener(this);
        notice.setOnClickListener(this);
        holiday.setOnClickListener(this);
        event.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId())
        {
            case R.id.profileBtn:
                 intent = new Intent(getActivity(), Syllabus. class);
                startActivity(intent);

                break;

                    case R.id.examBtn:
                    intent = new Intent(getActivity(), TeacherList. class);
                    startActivity(intent);
                    break;

                    case R.id.resultBtn:
                        intent=new Intent(getActivity(), Results.class);
                    startActivity(intent);
                    break;

                    case R.id.classRoutBtn:
                    intent=new Intent(getActivity(), ClassTimeTableSchoolian.class);
                    startActivity(intent);
                    break;

                    case R.id.eventsBtn:
                    intent = new Intent(getActivity(), SchoolianWorld. class);
                    startActivity(intent);
                    break;

                    case R.id.holidayBtn:
                    intent=new Intent(getActivity(), Holidays.class);
                    startActivity(intent);
                    break;

                case R.id.attendanceBtn:
                    intent=new Intent(getActivity(), AttendanceLayout.class);
                    startActivity(intent);
                    break;

                case R.id.classMateBtn:
                    intent=new Intent(getActivity(), ClassMates.class);
                    startActivity(intent);
                    break;
                case R.id.noticBtn:
                    Toast.makeText(getActivity(), "No Notice Available", Toast.LENGTH_SHORT).show();
                    intent = new Intent( getActivity(), Notice.class );
                    startActivity( intent );
                    break;
        }

    }

    private void api(final String sclId) {

        StringRequest stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener <String>() {
            @Override
            public void onResponse(String response) {


                Log.e( "chack", response  );
                try {
                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("sclData");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        String banner = object.getString( "bnr" ).trim();
                        banner(banner);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    // email_hint.setError("Invalid Mobile no. or Password");
                    Toast.makeText(getContext(), "Error 1: " + e.toString(), Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText( getContext(), "Error 2: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        } ){  @Override
        protected Map <String, String> getParams() {
            Map<String, String> params = new HashMap<String, String>();

            params.put("id", sclId);
            return params;
        }};
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
    }

    private void banner(String banner) {
        Glide.with( getActivity() ).load( banner ).into( feed );
    }

}
