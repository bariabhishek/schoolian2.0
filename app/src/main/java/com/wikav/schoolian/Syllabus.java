package com.wikav.schoolian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wikav.schoolian.Adeptor.AdapterForSyllabus;
import com.wikav.schoolian.Adeptor.SyllabusList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Syllabus extends AppCompatActivity {
    SessionManger sessionManger;
    String sclid,clas;
    private List<SyllabusList> lstAnime ;
    private RecyclerView recyclerView ;
    ProgressBar progressBar;
    ImageView backbtn;
    private final String JSON_URL = "https://schoolian.website/android/sallybus.php" ;
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

        backbtn=findViewById( R.id.back );
        backbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );
        //toolbar=findViewById(R.id.toolbarForSyllabus);
       // setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Syllabus");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sessionManger=new SessionManger(getApplicationContext());
        HashMap<String, String> user=sessionManger.getUserDetail();
        String Esclid = user.get(sessionManger.SCL_ID);
        String Ecls = user.get(sessionManger.CLAS);
        String Sid= user.get(sessionManger.SID);
        sclid=Esclid;
        recyclerView=findViewById(R.id.recyclerviewSyllbus);
        progressBar=findViewById(R.id.progressSyllabus);
        lstAnime=new ArrayList<>();
        //sidi=Sid;
        clas=Ecls;
        showMarks(sclid,clas);
    }

    private void showMarks(final String sclid, final String clas) {

        // Toast.makeText(getActivity(),"method call",Toast.LENGTH_LONG).show();

        StringRequest stringRequest= new StringRequest(Request.Method.POST, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Myres",response);
                try {
                    //Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                    JSONObject jsonObject1=new JSONObject(response);
                    String success = jsonObject1.getString("success");
                    JSONArray jsonArray= jsonObject1.getJSONArray("syllabus");

                    if(success.equals("1"))
                    {
                        // mobileArray= new String[jsonArray.length()];
                        for(int i=0 ; i<jsonArray.length();i++)
                        {

                            SyllabusList syllabus=new SyllabusList();
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            // mobileArray[i]=jsonObject.get("subject").toString();
                            syllabus.setSubject(jsonObject.getString("subject"));
                            syllabus.setSyllabusComplete(String.valueOf(jsonObject.getInt("per")));
                            syllabus.setChapter(jsonObject.getString("syllabus")+" Chapters done");
                            lstAnime.add(syllabus);
                        }
                        setuprecyclerview(lstAnime);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error 1: "+e.toString(),Toast.LENGTH_LONG).show();
//                    button.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error 2: "+error.toString(),Toast.LENGTH_LONG).show();
//                        button.setVisibility(View.VISIBLE);
//                        progressBar.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("school_id",sclid);
                param.put("clas",clas);
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
    private void setuprecyclerview(List<SyllabusList> userPostsList) {


        AdapterForSyllabus adaptorRecycler = new AdapterForSyllabus(Syllabus.this,userPostsList) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adaptorRecycler);

    }
}
