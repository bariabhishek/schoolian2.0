package com.wikav.schoolian;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AttendanceLayout extends AppCompatActivity {
    CalendarView cl;
    TextView abst, prst, lev, hol;
    String Url = "https://schoolian.in/android/newApi/getAttendance.php";
    SessionManger sessionManger;
    ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_layout);
        backbtn=findViewById( R.id.back );
        backbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );
        abst = findViewById(R.id.absent);
        prst = findViewById(R.id.present);
        lev = findViewById(R.id.leave);
        hol = findViewById(R.id.holiday);
        sessionManger = new SessionManger(this);
        HashMap<String, String> getUser = sessionManger.getUserDetail();
        String sclId = getUser.get(sessionManger.SCL_ID);
        String stId = getUser.get(sessionManger.SID);

        getData(sclId, stId);
    }

    private void getData(final String sclId, final String stId) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("MyRespo", response);
                Log.d("MyRespo", stId);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("Att");
                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String absent = object.getString("absent");
                            String present = object.getString("present");
                            String leave = object.getString("leave");
                            String holiday = object.getString("holiday");
                            setTextView(absent, present, leave, holiday);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("school_id", sclId);
                param.put("sid", stId);
                return param;
            }
        };
        stringRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void setTextView(String absent, String present, String leave, String holiday) {
        Log.d("MyRespo", absent+present);
        abst.setText(absent);
        prst.setText(present);
        lev.setText(leave);
        hol.setText(holiday);
    }


}

