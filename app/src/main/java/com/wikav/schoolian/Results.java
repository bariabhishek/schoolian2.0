package com.wikav.schoolian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.wikav.schoolian.DataClassSchoolian.ResultList;
import com.wikav.schoolian.schoolianAdeptor.ResultAdaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Results extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RecyclerView recyclerView ;
    Spinner spinner;
    TextView mTotal,mObtain,mPercentage,mPassFail;
    List<ResultList> list;
    ProgressBar progressBar;
    LinearLayout marksDetail;

    ArrayAdapter<String> dataAdapter;
    List<String> examName;
    String sclid,Sid,cls;
    String selectedSubject =null;
    ImageView backbtn;
    private final String JSON_URL_EXAM = "https://schoolian.website/android/newApi/getExamName.php" ;
    SessionManger sessionManger;
    private final String JSON_URL = "https://schoolian.website/android/Marks.php" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_results);

        backbtn=findViewById( R.id.back );
        backbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );
//        toolbar=findViewById(R.id.toobar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Results");
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById( R.id.recyclerViewAttendance );
        mTotal = findViewById( R.id.totalMarks );
        mObtain = findViewById( R.id.obtainMarks );
        mPassFail = findViewById( R.id.passFail );
        mPercentage = findViewById( R.id.percentage );
        progressBar = findViewById( R.id.progressBarRes );
        marksDetail = findViewById( R.id.marksDetail );
        spinner=findViewById(R.id.spinnerClass);
        spinner.setOnItemSelectedListener(this);
        sessionManger=new SessionManger(this);
        HashMap<String, String> user=sessionManger.getUserDetail();
         sclid = user.get(sessionManger.SCL_ID);
      Sid= user.get(sessionManger.SID);

        cls= user.get(sessionManger.CLAS);
      //setupRecycl(list);
        getExamName( sclid);
        examName=new ArrayList<>();
        list=new ArrayList<>();





    }

    private void setupRecycl(List<ResultList> list) {
        ResultAdaptor attendanceAdaptor = new ResultAdaptor(getApplicationContext(), list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getApplicationContext() );

        linearLayoutManager.setOrientation( RecyclerView.VERTICAL );
        recyclerView.setLayoutManager( linearLayoutManager );
        recyclerView.setAdapter( attendanceAdaptor );
    }

    private void showMarks(final String sclid, final String Sid, final String selectedSubject) {

        // Toast.makeText(getActivity(),"method call",Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest= new StringRequest(Request.Method.POST, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                int getTotalmarks=0 ;
                int obtMarks=0  ;
                try {
                    progressBar.setVisibility(View.GONE);
                    JSONObject jsonObject1=new JSONObject(response);
                    String success = jsonObject1.getString("success");
                    JSONArray jsonArray= jsonObject1.getJSONArray("marks");

                    if(success.equals("1"))
                    {

                        if(list!=null)
                        {
                            list.clear();
                        }
                        recyclerView.setVisibility(View.VISIBLE);

                        for(int i=0 ; i<jsonArray.length();i++)
                        {

                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                String name= jsonObject.getString("name");
                                String subject= jsonObject.getString("subject");
                                String obmarks= jsonObject.getString("obmarks");
                                String examname= jsonObject.getString("examname");
                                String totalmarks= jsonObject.getString("totalmarks");
                                String grade = null;

                                    obtMarks = obtMarks + Integer.parseInt(obmarks);

                                    getTotalmarks = getTotalmarks + Integer.parseInt(totalmarks);
                                    Log.d("Myres", getTotalmarks +" ---- "+ obtMarks);
                                    if (Integer.parseInt(obmarks) > 75) {
                                        grade = "A+";
                                    } else if (Integer.parseInt(obmarks) < 75 && Integer.parseInt(obmarks) > 60) {
                                        grade = "A";
                                    } else if (Integer.parseInt(obmarks) < 60 && Integer.parseInt(obmarks) > 45) {
                                        grade = "B";
                                    } else {
                                        grade = "C";
                                    }


                            list.add( new ResultList( subject,obmarks,grade,i ) );
                        }
                        setMarksDetail(getTotalmarks, obtMarks);
                        setupRecycl(list);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "No Exam Available of this Subject", Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                   // Toast.makeText(getApplicationContext(),"Error 1: "+e.toString(),Toast.LENGTH_LONG).show();
//                    button.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     //   Toast.makeText(getApplicationContext(),"Error 2: "+error.toString(),Toast.LENGTH_LONG).show();
//                        button.setVisibility(View.VISIBLE);
//                        progressBar.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("school_id",sclid);
                param.put("sid",Sid);
                param.put("exam",selectedSubject);
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

    void setMarksDetail(int Totalmarks,int obtMarks)
    {

        marksDetail.setVisibility(View.VISIBLE);
        float val=(obtMarks*100)/Totalmarks;
        mTotal.setText(""+Totalmarks);
        mObtain.setText(""+obtMarks);
        mPercentage.setText(""+val+"%");

        if(val>33){
            mPassFail.setText("PASS");
        }else {
            mPassFail.setText("FAIL");
        }



    }



    private void getExamName(final String sclid)
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL_EXAM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject1 = new JSONObject(response);
                    String success = jsonObject1.getString("success");
                    JSONArray jsonArray = jsonObject1.getJSONArray("examName");

                    if (success.equals("1")) {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String classes = jsonObject.getString("examName");

                            examName.add(classes);


                        }
                        setAdapterMethod(examName);
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

                return param;

            }
        };
        stringRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
    private void setAdapterMethod(List<String> clsases) {

        dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, clsases);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedSubject = parent.getItemAtPosition(position).toString();
        // if (!selectedSubject.equals("Select Subject")) {
        //   showMarks(sclid, selectedSubject);
        showMarks(sclid,Sid,selectedSubject);


        //  }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

