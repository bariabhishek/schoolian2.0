package com.wikav.schoolian.Adeptor;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import com.wikav.schoolian.R;
import com.wikav.schoolian.SessionManger;

public class CommentAdaptor extends RecyclerView.Adapter<CommentAdaptor.ViewHolder> {

    private Context context;
    private List<CommentModel> list ;
    private String postId,jobTitle;
    public String mobile;
    private String Url="http://voulu.in/api/getMobileUsingPostId.php";
    private String UrlDelet="http://voulu.in/api/deletCommentPostId.php";
    private SessionManger sessionManger;
    private String userMobile;
    private String status;

    public CommentAdaptor(Context context, List<CommentModel> list, String postId, String jobTitle, String status) {
        this.context = context;
        this.list = list;
        this.postId=postId;
        this.jobTitle=jobTitle;
        this.status=status;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from( context );
        View view= inflater.inflate( R.layout.comment_layout,viewGroup,false );
       ViewHolder viewHolder = new ViewHolder( view );
       sessionManger=new SessionManger(context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        HashMap<String,String> getUser=sessionManger.getUserDetail();
         userMobile=getUser.get(sessionManger.MOBILE);

        Glide.with(context).load(list.get(i).getUserpic()).into(viewHolder.imageView);
        viewHolder.username.setText( list.get( i ).getUsername());
        viewHolder.comment.setText( list.get( i ).getComment() );
        viewHolder.time.setText( list.get( i ).getTime() );
        checkPostId(postId,viewHolder,i,userMobile);
      //  Toast.makeText(context, "userMobile =>"+userMobile+" mobile=>"+mobile, Toast.LENGTH_SHORT).show();

        viewHolder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletComment(list.get(i).getComment_id(),i);
            }
        });
        if(list.get(i).getCommenter_mobile().equals(userMobile))
        {
            viewHolder.giverOptions.setVisibility(View.GONE);
        }




    }

    private void deletComment(final String postId, final int i) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlDelet,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")){
                                Log.d("Response",response);
                                list.remove(i);
                                notifyItemRemoved(i);
                                notifyItemRangeChanged(i,list.size());
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(context, "Something went wrong...", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Something went wrong..."+e, Toast.LENGTH_LONG).show();


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error2: " + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("postId", postId);

                params.put("key", "9195A3CDB388F894B3EE3BD665DFD");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();

    }

    private void checkPostId(final String post_id, final ViewHolder viewHolder, final int position, final String userMobile) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                              //  JSONArray jsonArray = jsonObject.getJSONArray("getmobile");
                                if (success.equals("1")){

                                    if(!list.get(position).getCommenter_mobile().equals(userMobile)&&!status.equals("2")) {
                                        viewHolder.giverOptions.setVisibility(View.VISIBLE);
                                        viewHolder.accept.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                confirmTask(postId, list.get(position).getComment_id(), jobTitle);
                                            }
                                        });
                                    }
                                   else
                                    {
                                        viewHolder.giverOptions.setVisibility(View.GONE);
                                        viewHolder.comnetCard.setBackground(ContextCompat.getDrawable(context, R.drawable.background_rounded_for_giver));

                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(context, "Something went wrong..."+e, Toast.LENGTH_LONG).show();


                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "Error2: " + error.toString(), Toast.LENGTH_LONG).show();

                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("postId", post_id);
                    params.put("mobile", userMobile);
                    return params;
                }
            };

        requestQueue.add(stringRequest);
        }

    private void confirmTask(String postId, String comment_id, String jobTitle) {

       /* Intent view = new Intent(context, JobConfirm. class);
        view.putExtra("postId",postId);
        view.putExtra("title",jobTitle);
        view.putExtra("commnet_id",comment_id);*//*
        context.startActivity(view);*/

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imageView ;
        Button accept,reject;
        TextView comment, time , username;
        LinearLayout giverOptions;
        RelativeLayout comnetCard;


        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            imageView = itemView.findViewById( R.id.commentProfile );
            comment = itemView.findViewById( R.id.MainComment );
            username = itemView.findViewById( R.id.commentUsername );
            time = itemView.findViewById( R.id.commentTime );
            giverOptions=itemView.findViewById(R.id.giverOptions);
            accept=itemView.findViewById(R.id.btnAccept);
            reject=itemView.findViewById(R.id.btnReject);
            comnetCard=itemView.findViewById(R.id.comnetCard);

        }
    }


}
