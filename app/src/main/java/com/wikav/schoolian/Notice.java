package com.wikav.schoolian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wikav.schoolian.DataClassSchoolian.NoticeDataClass;
import com.wikav.schoolian.schoolianAdeptor.NoticeAdaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notice extends AppCompatActivity {
    RecyclerView recyclerView;
    List<NoticeDataClass> list;
    ImageView backbtn;
    SessionManger sessionManger;
    String Url = "https://schoolian.website/android/newApi/getNotice.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_notice );
        backbtn=findViewById( R.id.back );

        sessionManger = new SessionManger(this);
        HashMap<String, String> getUser = sessionManger.getUserDetail();
        String sclId = getUser.get(sessionManger.SCL_ID);
        String stClass = getUser.get(sessionManger.CLAS);

        backbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );

        recyclerView = findViewById( R.id.noticeRecycleView );

        list = new ArrayList <>(  );



        getData(sclId,stClass);




    }



        private void getData( final String sclId, final String getclass) {
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST, Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("noti");
                        if (success.equals("1")) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String date = object.getString("date");
                                String msg = object.getString("msg");

                                list.add( new NoticeDataClass( date,msg,"" ) );
                            }
                            setData(list);
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
                protected Map <String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap <>();
                    param.put("school_id", sclId);
                    param.put("studentClass", getclass);
                    return param;
                }
            };
            stringRequest.setShouldCache(false);
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

    }


void setData(List<NoticeDataClass> list)
{
    NoticeAdaptor noticeAdaptor = new NoticeAdaptor(getApplicationContext(),list);

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getApplicationContext() );
    linearLayoutManager.setOrientation( RecyclerView.VERTICAL);
    recyclerView.setLayoutManager( linearLayoutManager );
    recyclerView.setAdapter( noticeAdaptor );
}

}
