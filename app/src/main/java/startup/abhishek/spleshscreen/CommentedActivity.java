package startup.abhishek.spleshscreen;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import startup.abhishek.spleshscreen.Adeptor.Adeptor;
import startup.abhishek.spleshscreen.Adeptor.CommentAdaptor;
import startup.abhishek.spleshscreen.Adeptor.CommentedPostAdaptor;
import startup.abhishek.spleshscreen.Adeptor.ModelList;

public class CommentedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List <ModelList> commentList;
    SessionManger sessionManger;
    Toolbar toolbar;
    ShimmerFrameLayout mShimmer;
    TextView noDataCommentedPost;
    String Url="https://voulu.in/api/getJobPostCommented.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_commented );
        toolbar=findViewById(R.id.toolbar_CommentedActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Your Commented Posts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById( R.id.commentedRecycleview );
        mShimmer = findViewById( R.id.shimmer_view_container );
        noDataCommentedPost = findViewById( R.id.noDataCommentedPost );
        commentList = new ArrayList <>(  );
        sessionManger=new SessionManger(this);
        HashMap<String,String> getUser=sessionManger.getUserDetail();
        String  userMobile=getUser.get(sessionManger.MOBILE);

        data(userMobile);



    }

    private void setUpRecycler(List<ModelList> commentList) {
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()) );
        CommentedPostAdaptor commentedAdaptor = new CommentedPostAdaptor( getApplicationContext(), commentList);
        recyclerView.setAdapter( commentedAdaptor );
        mShimmer.stopShimmerAnimation();
        mShimmer.setVisibility(View.GONE);
    }

    private void data(final String userMobile) {
        mShimmer.startShimmerAnimation();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //    progressDialog.dismiss();
                        Log.i("RESPO", response.toString());
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
                                    String share = object.getString("share").trim();
                                    commentList.add( new ModelList(img,title,des,rate,id,time,mobile,like,profile,username,share,img2,img3) );
                                }
                                setUpRecycler(commentList);

                            }
                            else

                            {
                                recyclerView.setVisibility(View.GONE);
                                noDataCommentedPost.setVisibility(View.VISIBLE);
                                mShimmer.stopShimmerAnimation();
                                mShimmer.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //  Toast.makeText(getActivity(), "Something went wrong..."+e, Toast.LENGTH_LONG).show();


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ///    Toast.makeText(getActivity(), "Something went wrong..."+error, Toast.LENGTH_LONG).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("key", "9195A3CDB388F894B3EE3BD665DFD");
                params.put("mobile",userMobile);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();




    }
}
