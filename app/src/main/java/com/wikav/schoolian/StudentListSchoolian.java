package com.wikav.schoolian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wikav.schoolian.DataClassSchoolian.Evants_holidays_SetGet;
import com.wikav.schoolian.DataClassSchoolian.StudentListSetGet;
import com.wikav.schoolian.schoolianAdeptor.StudentListAdaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentListSchoolian extends AppCompatActivity {
    RecyclerView recyclerView ;
    List<StudentListSetGet> list ;
SessionManger sessionManger;
String url="https://schoolian.website/android/newApi/getClassmate.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_student_list_schoolian );
        sessionManger=new SessionManger(this);

        HashMap<String,String> user=sessionManger.getUserDetail();
        String sclId=user.get(sessionManger.SCL_ID);
        String clas=user.get(sessionManger.CLAS);
        recyclerView = findViewById( R.id.recyclerViewStudentListSchoolian );

        list = new ArrayList <>(  );

        data(sclId,clas);


    }

    private void data(final String sclId, final String clas) {

        StringRequest stringRequest =new StringRequest(Request.Method.POST,url, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                Log.d("Myresponse",response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String success=jsonObject.getString("success");
                    JSONArray jsonArray=jsonObject.getJSONArray("classmate");
                    if(success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String name = jsonObject1.getString("name");
                            String profile = jsonObject1.getString("profile");
                            String mobile = jsonObject1.getString("mobile");
                            String roll = jsonObject1.getString("roll");

                            list.add(new StudentListSetGet(name,profile,roll,mobile));
                        }
                        setUp(list);
                    }
                    else
                    {
                        Toast.makeText(StudentListSchoolian.this, "No Data", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(StudentListSchoolian.this, "Error Hai", Toast.LENGTH_SHORT).show();

                }

            }
        },new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StudentListSchoolian.this, "Error", Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("school_id", sclId);
                param.put("cls", clas);
                return param;
            }


        };
        stringRequest.setShouldCache(false);
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void setUp(List<StudentListSetGet> list) {
        StudentListAdaptor studentListAdaptor = new StudentListAdaptor(getApplicationContext(),list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getApplicationContext() );
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL );
        recyclerView.setLayoutManager( linearLayoutManager );
        recyclerView.setAdapter( studentListAdaptor );
    }
}
