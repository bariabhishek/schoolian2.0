package com.wikav.voulu.fragments;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import com.wikav.voulu.Adeptor.CommentAdaptor;
import com.wikav.voulu.Adeptor.CommentModel;
import com.wikav.voulu.R;
import com.wikav.voulu.SessionManger;

public class FullScreenDialogForComment extends DialogFragment {

    EditText commentBox;
    TextView sendBtn;
    SessionManger sessionManger;
    final String Url="http://voulu.in/api/sendComment.php";
    final String Url2="http://voulu.in/api/getComments.php";
    String postId,title;
    List <CommentModel> list;
    RecyclerView recyclerView;
ShimmerFrameLayout mShimmerViewContainer;
SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
       postId=getArguments().getString("id");
       title=getArguments().getString("title");
       list=new ArrayList<>();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_full_screen_dialog, container, false);
        setToolbar(view);
        mShimmerViewContainer =view.findViewById(R.id.shimmer_view_container_for_Commnet);
        swipeRefreshLayout =view.findViewById(R.id.commentSwipe);
        recyclerView = view.findViewById( R.id.recycleviewComment );
        sessionManger =new SessionManger(getActivity());
        sendBtn=view.findViewById(R.id.sedCommentButton);
        commentBox=view.findViewById(R.id.CommenBox);

        HashMap<String,String> user=sessionManger.getUserDetail();
        final String mobile = user.get(sessionManger.MOBILE);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                getComment(postId);

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String commnet=commentBox.getText().toString().trim();
                if(!commnet.equals(""))
                {
                    sendComment(commnet,mobile,postId);
                }
                else
                {
                    commentBox.setError("Please type");
                }
            }
        });
        getComment(postId);
        return view;
    }

    private void setToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        toolbar.setTitle("Comments");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    public void sendComment(final String comment, final String user_id_as_mobile, final String post_id)
    {
        commentBox.setText(null);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                Toast.makeText(getActivity(), "Successfully Sent!", Toast.LENGTH_SHORT).show();
                                list.clear();
                                getComment(post_id );
                            }
                            else
                            {

                                Toast.makeText(getActivity(), "Not Send", Toast.LENGTH_SHORT).show();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                            // email_hint.setError("Invalid Mobile no. or Password");
                            Toast.makeText(getActivity(), "Error 1: " + e.toString(), Toast.LENGTH_LONG).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error2: " + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("comment", comment);
                params.put("mobile", user_id_as_mobile);
                params.put("postId", post_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
    }
    public void getComment(final String post_id){
        mShimmerViewContainer.startShimmerAnimation();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.getCache().clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url2,
                new Response.Listener<String>()
                {

                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("allPost");
                            if (success.equals("1")){
                                Log.d("Response",response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String comment = object.getString("comment").trim();
                                    String username = object.getString("username").trim();
                                    String commentId = object.getString("commentId").trim();
                                    String userPic = object.getString("userPic").trim();
                                    String time = object.getString("time").trim();
                                    String commenter_mobile = object.getString("mobile").trim();
                                   //(String comment_id, String comment, String username, String userpic, String time)
                                    list.add(new CommentModel(commentId,comment,username,userPic,time,commenter_mobile) );

                                }


                                setupRecycle(list);

                            }
                            else
                            {
                                mShimmerViewContainer.stopShimmerAnimation();
                                mShimmerViewContainer.setVisibility(View.GONE);
                                if(swipeRefreshLayout.isRefreshing())
                                {
                                    swipeRefreshLayout.setRefreshing(false);
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Something went wrong..."+e, Toast.LENGTH_LONG).show();
                            Log.d("Response", e.getMessage());
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            if(swipeRefreshLayout.isRefreshing())
                            {
                                swipeRefreshLayout.setRefreshing(false);
                            }

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error2: " + error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("Response", error.toString());

                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);
                        if(swipeRefreshLayout.isRefreshing())
                        {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("postId", post_id);
                params.put("key", "9195A3CDB388F894B3EE3BD665DFD");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void setupRecycle(List<CommentModel> list) {
        CommentAdaptor a= new CommentAdaptor( getContext(),list,postId,title );


        recyclerView.setHasFixedSize( true );
        recyclerView.setItemAnimator( new DefaultItemAnimator() );
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) );
        recyclerView.setAdapter( a );
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);
        if(swipeRefreshLayout.isRefreshing())
        {
            swipeRefreshLayout.setRefreshing(false);
        }

    }
}
