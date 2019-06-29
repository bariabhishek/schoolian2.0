package com.wikav.schoolian;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.wikav.schoolian.Adeptor.AdapterForComment;
import com.wikav.schoolian.Adeptor.CommentAnime;
import com.wikav.schoolian.Adeptor.MyModelList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimeActivity extends AppCompatActivity {
    private String name,posts,postPic,proPic,posId,star,sid,userId,Photo,nameCom,intent;

    TextView tv_name;
    TextView postss ;
    TextView tv_rating ;
    PhotoView img;
    CircleImageView prof;
    private RecyclerView.LayoutManager layoutManager;
    String  Url="https://schoolian.website/android/newApi/getSinglePost.php";
    private final String JSON_URL = "https://schoolian.website/android/getComments.php" ;
    private final String JSON_URL2 ="https://schoolian.website/android/comments.php" ;
    private final String Delete_URL ="https://schoolian.website/android/deletePost.php" ;
    private JsonArrayRequest request ;

    private List<CommentAnime> lstAnime ;
    private RecyclerView recyclerView ;
    EditText sendCom;
    SessionManger sessionManger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sendCom=findViewById(R.id.sentI);

        intent  = getIntent().getExtras().getString("intent");
        if(intent.equals("main"))
        {
            name  = getIntent().getExtras().getString("name");
            posts = getIntent().getExtras().getString("posts");
            postPic = getIntent().getExtras().getString("postPic") ;
            proPic = getIntent().getExtras().getString("proPic");
            posId = getIntent().getExtras().getString("posId") ;
            star=getIntent().getExtras().getString("star") ;
            sid=getIntent().getExtras().getString("sid");
            setValues();
        }
        else {
            Toast.makeText(this, intent, Toast.LENGTH_SHORT).show();
            getSingleData(intent);
            shocoments(intent);
        }

        tv_name = findViewById(R.id.anime_name_on);
       postss = findViewById(R.id.post_on);
        tv_rating  = findViewById(R.id.starGeti) ;
         img = findViewById(R.id.thumbnail_on);
         prof=findViewById(R.id.profile_pic_on);



        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewComment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        lstAnime = new ArrayList<>();

        sessionManger=new SessionManger(this);
        HashMap<String, String> user=sessionManger.getUserDetail();
        String Esid = user.get(sessionManger.SID);
        String Ename = user.get(sessionManger.NAME);
        String phot = user.get(sessionManger.PHOTO);

        userId=Esid;










    }

    private void getSingleData(final String intent) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject json = new JSONObject(response);
                            String success = json.getString("success");
                            JSONArray jsonArray = json.getJSONArray("userPost");
                            if (success.equals("1")) {


                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    name  = jsonObject.getString("st_name");
                                    posts = jsonObject.getString("posts");
                                    postPic = jsonObject.getString("post_pic") ;
                                    proPic = jsonObject.getString("profile_pic");
                                    posId = jsonObject.getString("post_id") ;
                                    star=jsonObject.getString("stars") ;
                                    sid=jsonObject.getString("student_id");

                                }
                                setValues();
                               }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Something went wrong..." + error, Toast.LENGTH_LONG).show();
                        // noData.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                // params.put("key", "9195A3CDB388F894B3EE3BD665DFD");
                params.put("post_id", intent);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
    }

    private void setValues() {
        tv_name.setText(name);
        postss.setText(posts);
        tv_rating.setText(star);

        if(postPic.equals("")||postPic.equals("NA"))
        {
            img.setMaxHeight(0);
            img.setVisibility(View.GONE);

        }
        else
        {
            img.setVisibility(View.VISIBLE);

            Glide.with(this).load(postPic).into(img);
        }
        Glide.with(this).load(proPic).into(prof);
    }

    private void shocoments(final String posId) {
        StringRequest stringRequest= new StringRequest(Request.Method.POST, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject1=new JSONObject(response);
                    String success = jsonObject1.getString("success");
                    JSONArray jsonArray= jsonObject1.getJSONArray("usercomments");

                    if(success.equals("1"))
                    {
                        for(int i=0 ; i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            CommentAnime anime = new CommentAnime() ;
                            anime.setNameCom(jsonObject.getString("userName"));
//                        anime.setDescription(jsonObject.getString("description"));

                            anime.setComment(jsonObject.getString("comment"));
                            anime.setIdCom(jsonObject.getString("com_id"));

                            anime.setImage_url(jsonObject.getString("profile_pic"));
                            lstAnime.add(anime);
                        }
                        setuprecyclerview(lstAnime);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(HomeMenuActivity.this,"Error 1: "+e.toString(),Toast.LENGTH_LONG).show();
//                    button.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(HomeMenuActivity.this,"Error 2: "+error.toString(),Toast.LENGTH_LONG).show();
//                        button.setVisibility(View.VISIBLE);
//                        progressBar.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("post_id",posId);

                return param;

            }
        };
        stringRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();

    }

    private void setuprecyclerview(List<CommentAnime> userPostsList) {
        AdapterForComment adaptorRecycler = new AdapterForComment(this,userPostsList) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptorRecycler);

    }







  /*  @Override
    public void onBackPressed() {

        Intent intent =new Intent(AnimeActivity.this, HomeMenuActivity.class);
        startActivity(intent);
    }*/

    public void deletEdit(View view) {

        final CharSequence[] options = { "Delete","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(AnimeActivity.this);

        builder.setTitle("Your Post!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Delete"))

                {

                    if (userId.equals(sid))
                    {

                        deletePost(posId);
                    }
                    else {
                        Toast.makeText(AnimeActivity.this, "Sorry!!! This is not your post "+sessionManger.SCL_ID+" "+sid, Toast.LENGTH_LONG).show();
                    }

                }

                else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();

    }

    public void sendComment(View view) {

        String anwser=sendCom.getText().toString().trim();
        if(anwser.isEmpty()||anwser.equals(""))
        {
            //// nothing
        }
        else
        {
            setSendCom(anwser,posId,userId);
          //  respose();
//            lstAnime.clear();
//            shocoments(posId);
        }





    }
    public void respose()
    {

        CommentAnime anime = new CommentAnime() ;
        anime.setNameCom(nameCom);
        anime.setComment(sendCom.getText().toString());

        anime.setIdCom(" ");
        anime.setImage_url(Photo);
        // Toast.makeText(this, Photo, Toast.LENGTH_LONG).show();

        lstAnime.add(anime);
        setuprecyclerview(lstAnime);
    }


    private void setSendCom(final String anwser, final String posId,final String userId) {
        sendCom.setText("");
        StringRequest stringRequest= new StringRequest(Request.Method.POST, JSON_URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject1=new JSONObject(response);
                    String success = jsonObject1.getString("mysuccess");
                    if(success.equals("1")) {

                        Toast.makeText(AnimeActivity.this, "Sending...", Toast.LENGTH_LONG).show();
                        lstAnime.clear();
                        shocoments(posId);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(AnimeActivity.this,"Error 1: "+e.toString(),Toast.LENGTH_LONG).show();
//                    button.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AnimeActivity.this,"Error 2: "+error.toString(),Toast.LENGTH_LONG).show();
//                        button.setVisibility(View.VISIBLE);
//                        progressBar.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("post_id",posId);
                param.put("student_id",userId);
                param.put("comment",anwser);
                param.put("senderId",sid);
                return param;

            }
        };
        stringRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
    }

    public void deletePost( final String posId)
    {
        StringRequest stringRequest= new StringRequest(Request.Method.POST, Delete_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if(success.equals("1"))
                    {
                        Intent in=new Intent(AnimeActivity.this, Home.class);
                        startActivity(in);
                        Toast.makeText(AnimeActivity.this, "Your post has Deleted !!!", Toast.LENGTH_SHORT).show();
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(HomeMenuActivity.this,"Error 1: "+e.toString(),Toast.LENGTH_LONG).show();
//                    button.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(HomeMenuActivity.this,"Error 2: "+error.toString(),Toast.LENGTH_LONG).show();
//                        button.setVisibility(View.VISIBLE);
//                        progressBar.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("post_id",posId);

                return param;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
