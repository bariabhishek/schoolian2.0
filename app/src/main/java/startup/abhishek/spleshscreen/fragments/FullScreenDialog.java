package startup.abhishek.spleshscreen.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import startup.abhishek.spleshscreen.Adeptor.Adeptor;
import startup.abhishek.spleshscreen.Adeptor.CommentAdaptor;
import startup.abhishek.spleshscreen.Adeptor.CommentModel;
import startup.abhishek.spleshscreen.Adeptor.ModelList;
import startup.abhishek.spleshscreen.Home;
import startup.abhishek.spleshscreen.Login;
import startup.abhishek.spleshscreen.R;
import startup.abhishek.spleshscreen.SessionManger;
import startup.abhishek.spleshscreen.UploadYourPost;

public class FullScreenDialog extends DialogFragment {

    EditText commentBox;
    ImageView sendBtn;
    SessionManger sessionManger;
    final String Url="https://voulu.in/api/sendComment.php";
    final String Url2="https://voulu.in/api/getComments.php";
    String postId;
    List <CommentModel> list;
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
       postId=getArguments().getString("id");
       list=new ArrayList<>();
        getComment(postId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_full_screen_dialog, container, false);
        setToolbar(view);
        recyclerView = view.findViewById( R.id.recycleviewComment );
        sessionManger =new SessionManger(getActivity());
        sendBtn=view.findViewById(R.id.sedCommentButton);
        commentBox=view.findViewById(R.id.CommenBox);

        HashMap<String,String> user=sessionManger.getUserDetail();
        final String mobile = user.get(sessionManger.MOBILE);



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
                                commentBox.setText("");
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
                                   //(String comment_id, String comment, String username, String userpic, String time)
                                    list.add(new CommentModel(commentId,comment,username,userPic,time) );

                                }
                                setupRecycle(list);

                            }
                            else
                            {
                                Toast.makeText(getActivity(), "Something went wrong...", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Something went wrong..."+e, Toast.LENGTH_LONG).show();


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
                params.put("postId", post_id);
                params.put("key", "9195A3CDB388F894B3EE3BD665DFD");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void setupRecycle(List<CommentModel> list) {
        CommentAdaptor a= new CommentAdaptor( getContext(),list,postId );


        recyclerView.setHasFixedSize( true );
        recyclerView.setItemAnimator( new DefaultItemAnimator() );
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) );
        recyclerView.setAdapter( a );

    }
}
