package startup.abhishek.spleshscreen.Adeptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import startup.abhishek.spleshscreen.R;
import startup.abhishek.spleshscreen.SessionManger;

public class CommentAdaptor extends RecyclerView.Adapter<CommentAdaptor.ViewHolder> {

    private Context context;
    private List<CommentModel> list ;
    private String postId;
    public String mobile;
    private String Url="https://voulu.in/api/getMobileUsingPostId.php";
    private SessionManger sessionManger;
    private String userMobile;

    public CommentAdaptor(Context context, List <CommentModel> list, String postId) {
        this.context = context;
        this.list = list;
        this.postId=postId;
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
        checkPostId(postId,viewHolder);
      //  Toast.makeText(context, "userMobile =>"+userMobile+" mobile=>"+mobile, Toast.LENGTH_SHORT).show();





    }

    private void checkPostId(final String post_id, final ViewHolder viewHolder) {
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
                                    Log.d("Response",response);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                       mobile = object.getString("user_mobile").trim();
                                        if (userMobile.equals(mobile))
                                        {
                                            viewHolder.giverOptions.setVisibility(View.VISIBLE);
                                        }
                                    }

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
                    params.put("postId", post_id);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
            requestQueue.getCache().clear();

        }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imageView ;
        TextView comment, time , username;
        LinearLayout giverOptions;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            imageView = itemView.findViewById( R.id.commentProfile );
            comment = itemView.findViewById( R.id.MainComment );
            username = itemView.findViewById( R.id.commentUsername );
            time = itemView.findViewById( R.id.commentTime );
            giverOptions=itemView.findViewById(R.id.giverOptions);
        }
    }


}
