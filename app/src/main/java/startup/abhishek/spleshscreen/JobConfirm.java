package startup.abhishek.spleshscreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

public class JobConfirm extends AppCompatActivity {

    String postId,comment_id,job_giver_mobile,job_seeker_mobile,job_giver_name,job_seeker_name,job_giver_profile,job_seeker_profile;
    SessionManger sessionManger;
    String Url="https://voulu.in/api/getJobSeekerDetail.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_job_confirm );
        postId=getIntent().getStringExtra("postId");
        comment_id=getIntent().getStringExtra("commnet_id");
        sessionManger=new SessionManger(this);
        HashMap<String,String> getUser=sessionManger.getUserDetail();
        job_giver_mobile=getUser.get(sessionManger.MOBILE);
        job_giver_profile=getUser.get(sessionManger.PROFILE_PIC);
        job_giver_name=getUser.get(sessionManger.NAME);
        getJobSeekerDetail(comment_id);
    }

    private void getJobSeekerDetail(final String comment_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("getmobile");
                            if (success.equals("1")){
                                Log.d("Respoo",response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    job_seeker_mobile = object.getString("mobile").trim();
                                    job_seeker_name = object.getString("username").trim();
                                    job_seeker_profile = object.getString("userPic").trim();
                                    //  final String profile = object.getString("profile").trim();

                                }

                            }
                            else
                            {
                                Toast.makeText(JobConfirm.this, "Something went wrong...", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(JobConfirm.this, "Something went wrong..."+e, Toast.LENGTH_LONG).show();


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(JobConfirm.this, "Error2: " + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("postId", comment_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(JobConfirm.this);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();

    }
}
