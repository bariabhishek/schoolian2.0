package com.wikav.schoolian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wikav.schoolian.DataClassSchoolian.Evants_holidays_SetGet;
import com.wikav.schoolian.schoolianAdeptor.Evants_holidays_Adaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Holidays extends AppCompatActivity {

    RecyclerView recyclerView ;
    List<Evants_holidays_SetGet> list;
    String url="https://schoolian.website/android/newApi/getHoliday.php";
    SessionManger sessionManger;
    ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.holidays);

        backbtn=findViewById( R.id.back );
        backbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );

        recyclerView = findViewById( R.id.recyclerViewEvants_holidays );
        list = new ArrayList <>(  );
        sessionManger=new SessionManger(this);

        HashMap<String,String> user=sessionManger.getUserDetail();
        String sclId=user.get(sessionManger.SCL_ID);
        data(sclId);


    }

    private void setUp(List<Evants_holidays_SetGet> list) {
        Evants_holidays_Adaptor evants_holidays_adaptor = new Evants_holidays_Adaptor(getApplicationContext(),list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getApplicationContext() );
        linearLayoutManager.setOrientation( RecyclerView.VERTICAL );
        recyclerView.setLayoutManager( linearLayoutManager );
        recyclerView.setAdapter( evants_holidays_adaptor );
    }

    private void data(final String scl_id) {
        StringRequest stringRequest =new StringRequest(Request.Method.POST,url, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String success=jsonObject.getString("success");
                    JSONArray jsonArray=jsonObject.getJSONArray("holiday");
                    if(success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String occasion = jsonObject1.getString("occasion");
                            String from_date = jsonObject1.getString("from_date");
                            String to_date = jsonObject1.getString("to_date");

                            list.add(new Evants_holidays_SetGet("", occasion, from_date, to_date));
                        }
                        setUp(list);
                    }
                    else
                    {
                        Toast.makeText(Holidays.this, "No Data", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                  //  Toast.makeText(Holidays.this, "Error Hai", Toast.LENGTH_SHORT).show();

                }

            }
            },new Response.ErrorListener()
            {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Holidays.this, "Error", Toast.LENGTH_SHORT).show();

                }
            }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("school_id", scl_id);
                return param;
            }


        };
        stringRequest.setShouldCache(false);
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}
