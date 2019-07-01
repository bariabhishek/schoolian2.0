package com.wikav.schoolian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wikav.schoolian.Adeptor.DataForNotification;
import com.wikav.schoolian.Adeptor.SyllabusList;
import com.wikav.schoolian.DataClassSchoolian.ClassTimeTableSetGet;
import com.wikav.schoolian.schoolianAdeptor.ClassTimeTableAdaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassTimeTableSchoolian extends AppCompatActivity {
    private final String JSON_URL = "https://schoolian.website/android/newApi/getSubTime.php";
    TextView sun, mon, tue, wed, thu, fri, sat;
    LinearLayout linearLayout;
    RecyclerView recyclerView;
    List<ClassTimeTableSetGet> list;
    SessionManger sessionManger;
    ImageView backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_time_table_schoolian);
//        toolbar=findViewById(R.id.toolbarForClassRoutine);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Class Routine");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initilisation();

        list = new ArrayList<>();
        sessionManger = new SessionManger(getApplicationContext());
        HashMap<String, String> user = sessionManger.getUserDetail();
        String Esclid = user.get(sessionManger.SCL_ID);
        String Ecls = user.get(sessionManger.CLAS);
        String Sid = user.get(sessionManger.SID);

        showMarks(Esclid, Ecls);


    }


    private void showMarks(final String sclid, final String clas) {

        // Toast.makeText(getActivity(),"method call",Toast.LENGTH_LONG).show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Myres", response);
                try {
                    //Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("subtimeTime");


                    if (success.equals("1")) {
                        // mobileArray= new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obbject = jsonArray.getJSONObject(i);
                            String subName = obbject.getString("subject");
                            String subtime = obbject.getString("subtime");
                            String firstName = obbject.getString("firstName");
                            String endtime = obbject.getString("endTime");

                            list.add(new ClassTimeTableSetGet(firstName, subtime, endtime, subName));
                        }
                        setUp(list);

                    }else
                    {
                        Toast.makeText(getApplicationContext(), "No data Available " , Toast.LENGTH_LONG).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "No data Available " , Toast.LENGTH_LONG).show();
//                    button.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "No data " , Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    private void setUp(List<ClassTimeTableSetGet> list) {
        ClassTimeTableAdaptor classTimeTableAdaptor = new ClassTimeTableAdaptor(getApplicationContext(), list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(classTimeTableAdaptor);
    }

    private void initilisation() {

        backbtn=findViewById( R.id.back );
        backbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );
        recyclerView = findViewById(R.id.recyclerViewTimeTable);
        linearLayout = findViewById(R.id.lltimetable);

        sun = findViewById(R.id.sun);
        mon = findViewById(R.id.Mon);
        tue = findViewById(R.id.tue);
        wed = findViewById(R.id.wed);
        thu = findViewById(R.id.thu);
        fri = findViewById(R.id.fri);
        sat = findViewById(R.id.sat);

        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                linearLayout.setBackgroundResource(R.drawable.background_time_table_cal);

                sun.setBackgroundResource(R.color.skyblue);
                mon.setBackgroundResource(R.color.colorWhite);
                tue.setBackgroundResource(R.color.colorWhite);
                wed.setBackgroundResource(R.color.colorWhite);
                thu.setBackgroundResource(R.color.colorWhite);
                fri.setBackgroundResource(R.color.colorWhite);
                sat.setBackgroundResource(R.color.colorWhite);

                sun.setTextColor(Color.WHITE);
                mon.setTextColor(Color.parseColor("#039BE5"));
                tue.setTextColor(Color.parseColor("#039BE5"));
                mon.setTextColor(Color.parseColor("#039BE5"));
                thu.setTextColor(Color.parseColor("#039BE5"));
                fri.setTextColor(Color.parseColor("#039BE5"));
                sat.setTextColor(Color.parseColor("#039BE5"));
                // mon.setTextColor(  R.color.colorWhite );
            }
        });
        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.setBackgroundResource(R.drawable.background_time_table_cal);

                mon.setBackgroundResource(R.color.skyblue);
                sun.setBackgroundResource(R.color.colorWhite);
                tue.setBackgroundResource(R.color.colorWhite);
                wed.setBackgroundResource(R.color.colorWhite);
                thu.setBackgroundResource(R.color.colorWhite);
                fri.setBackgroundResource(R.color.colorWhite);
                sat.setBackgroundResource(R.color.colorWhite);

                mon.setTextColor(Color.WHITE);
                sun.setTextColor(Color.parseColor("#039BE5"));
                tue.setTextColor(Color.parseColor("#039BE5"));
                wed.setTextColor(Color.parseColor("#039BE5"));
                thu.setTextColor(Color.parseColor("#039BE5"));
                fri.setTextColor(Color.parseColor("#039BE5"));
                sat.setTextColor(Color.parseColor("#039BE5"));

            }
        });
        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tue.setBackgroundResource(R.color.skyblue);
                sun.setBackgroundResource(R.color.colorWhite);
                mon.setBackgroundResource(R.color.colorWhite);
                wed.setBackgroundResource(R.color.colorWhite);
                thu.setBackgroundResource(R.color.colorWhite);
                fri.setBackgroundResource(R.color.colorWhite);
                sat.setBackgroundResource(R.color.colorWhite);

                tue.setTextColor(Color.WHITE);

                sun.setTextColor(Color.parseColor("#039BE5"));
                mon.setTextColor(Color.parseColor("#039BE5"));
                wed.setTextColor(Color.parseColor("#039BE5"));
                thu.setTextColor(Color.parseColor("#039BE5"));
                fri.setTextColor(Color.parseColor("#039BE5"));
                sat.setTextColor(Color.parseColor("#039BE5"));

            }
        });
        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wed.setBackgroundResource(R.color.skyblue);
                sun.setBackgroundResource(R.color.colorWhite);
                tue.setBackgroundResource(R.color.colorWhite);
                mon.setBackgroundResource(R.color.colorWhite);
                thu.setBackgroundResource(R.color.colorWhite);
                fri.setBackgroundResource(R.color.colorWhite);
                sat.setBackgroundResource(R.color.colorWhite);

               /* wed.setTextColor( Color.WHITE );
                sun.setTextColor( Color.parseColor( "#039BE5" ) );
                tue.setTextColor( Color.parseColor( "#039BE5" ) );
                mon.setTextColor( Color.parseColor( "#039BE5" ) );
                thu.setTextColor( Color.parseColor( "#039BE5" ) );
                fri.setTextColor( Color.parseColor( "#039BE5" ) );
                sat.setTextColor( Color.parseColor( "#039BE5" ) );*/
            }
        });
        thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                thu.setBackgroundResource(R.color.skyblue);
                sun.setBackgroundResource(R.color.colorWhite);
                tue.setBackgroundResource(R.color.colorWhite);
                mon.setBackgroundResource(R.color.colorWhite);
                wed.setBackgroundResource(R.color.colorWhite);
                fri.setBackgroundResource(R.color.colorWhite);
                sat.setBackgroundResource(R.color.colorWhite);

                thu.setTextColor(Color.WHITE);
                sun.setTextColor(Color.parseColor("#039BE5"));
                tue.setTextColor(Color.parseColor("#039BE5"));
                wed.setTextColor(Color.parseColor("#039BE5"));
                mon.setTextColor(Color.parseColor("#039BE5"));
                fri.setTextColor(Color.parseColor("#039BE5"));
                sat.setTextColor(Color.parseColor("#039BE5"));

            }
        });
        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fri.setBackgroundResource(R.color.skyblue);
                sun.setBackgroundResource(R.color.colorWhite);
                tue.setBackgroundResource(R.color.colorWhite);
                mon.setBackgroundResource(R.color.colorWhite);
                wed.setBackgroundResource(R.color.colorWhite);
                thu.setBackgroundResource(R.color.colorWhite);
                sat.setBackgroundResource(R.color.colorWhite);

                fri.setTextColor(Color.WHITE);
                sun.setTextColor(Color.parseColor("#039BE5"));
                tue.setTextColor(Color.parseColor("#039BE5"));
                wed.setTextColor(Color.parseColor("#039BE5"));
                thu.setTextColor(Color.parseColor("#039BE5"));
                mon.setTextColor(Color.parseColor("#039BE5"));
                sat.setTextColor(Color.parseColor("#039BE5"));

            }
        });

        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sat.setBackgroundResource(R.color.skyblue);
                sun.setBackgroundResource(R.color.colorWhite);
                tue.setBackgroundResource(R.color.colorWhite);
                mon.setBackgroundResource(R.color.colorWhite);
                wed.setBackgroundResource(R.color.colorWhite);
                fri.setBackgroundResource(R.color.colorWhite);
                thu.setBackgroundResource(R.color.colorWhite);

                sat.setTextColor(Color.WHITE);
                sun.setTextColor(Color.parseColor("#039BE5"));
                tue.setTextColor(Color.parseColor("#039BE5"));
                wed.setTextColor(Color.parseColor("#039BE5"));
                thu.setTextColor(Color.parseColor("#039BE5"));
                fri.setTextColor(Color.parseColor("#039BE5"));
                mon.setTextColor(Color.parseColor("#039BE5"));

            }
        });
    }
}
