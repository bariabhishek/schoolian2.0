package startup.abhishek.spleshscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import startup.abhishek.spleshscreen.Adeptor.AcceptedListAdaptor;
import startup.abhishek.spleshscreen.Adeptor.Adeptor;
import startup.abhishek.spleshscreen.Adeptor.ModelList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcceptedListActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<ModelList> list;
    TextView noData;
    private ShimmerFrameLayout mShimmerViewContainer;
    SessionManger sessionManger;
    String Url="https://voulu.in/api/getAcceptedJobPost.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number);
        toolbar=findViewById(R.id.toolbarLayout);
        sessionManger=new SessionManger(this);
        setSupportActionBar(toolbar);
        mShimmerViewContainer=findViewById(R.id.shimmer_view_container);
        getSupportActionBar().setTitle("Accepted List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.recycleviewAccepted);
        noData=findViewById(R.id.noDataYourPost);
        HashMap<String,String> user=sessionManger.getUserDetail();
        // String Ename = user.get(sessionManger.NAME);
        String mobile = user.get(sessionManger.MOBILE);
        list=new ArrayList<>();
        arraydata(mobile);


    }

    private void arraydata(final String mobile) {
        mShimmerViewContainer.startShimmerAnimation();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //    progressDialog.dismiss();
                        Log.i("TAG", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("allPost");
                            if (success.equals("1")){
                                Log.d("Response",response);
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String title = object.getString("title").trim();
                                    String mobile = object.getString("mobile").trim();
                                    String des = object.getString("des").trim();
                                    String rate = object.getString("rate").trim();
                                    String img = object.getString("img").trim();
                                    String img2 = object.getString("img2").trim();
                                    String img3 = object.getString("img3").trim();
                                    String id = object.getString("id").trim();
                                    String time = object.getString("time").trim();
                                    String profile = object.getString("profile").trim();
                                    String username = object.getString("username").trim();
                                    String like = object.getString("like").trim();
                                    String share = object.getString("status").trim();
                                    list.add( new ModelList(img,title,des,rate,id,time,mobile,like,profile,username,share,img2,img3) );

                                }
                                setupRecycle(list);

                            }
                            else
                            {
                                noData.setVisibility(View.VISIBLE);
                                mShimmerViewContainer.stopShimmerAnimation();
                                mShimmerViewContainer.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(getActivity(), "Something went wrong..."+e, Toast.LENGTH_LONG).show();
                            //  noData.setVisibility(View.VISIBLE);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        /// Toast.makeText(getActivity(), "Something went wrong..."+error, Toast.LENGTH_LONG).show();
                        // noData.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("key", "9195A3CDB388F894B3EE3BD665DFD");
                params.put("mobile", mobile);
                return params;
            }
        };

        stringRequest.setShouldCache(true);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
}

    public void setupRecycle(List <ModelList> list)
    {
        AcceptedListAdaptor a= new AcceptedListAdaptor( this,list );
        recyclerView.setHasFixedSize( true );
        recyclerView.setItemAnimator( new DefaultItemAnimator() );
        recyclerView.setLayoutManager(new LinearLayoutManager(this) );
        recyclerView.setAdapter( a );
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
