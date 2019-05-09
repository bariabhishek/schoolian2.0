package startup.abhishek.spleshscreen.Adeptor;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import startup.abhishek.spleshscreen.JobConfirm;
import startup.abhishek.spleshscreen.JobDiscription;
import startup.abhishek.spleshscreen.R;
import startup.abhishek.spleshscreen.SessionManger;
import startup.abhishek.spleshscreen.fragments.BottomSheetFragmentui;

public class Adeptor extends RecyclerView.Adapter<Adeptor.ViewHolder> {
    Context context;
    List<ModelList> list ;
    SessionManger sessionManger;
    String job_giver_mobile;
    String Url="https://voulu.in/api/addToFavorite.php";
    String UrlDelete="https://voulu.in/api/deleteJobPost.php";
    public Adeptor(Context context, List <ModelList> list) {
        this.context = context;
        this.list = list;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        sessionManger=new SessionManger(context);
        HashMap<String,String> getUser=sessionManger.getUserDetail();
        job_giver_mobile=getUser.get(sessionManger.MOBILE);
        LayoutInflater inflater = LayoutInflater.from( context );
        View view= inflater.inflate( R.layout.data_forrecycle,viewGroup,false );
       ViewHolder viewHolder = new ViewHolder( view );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        if(job_giver_mobile.equals(list.get(i).getMobile()))
        {
            viewHolder.deletePost.setVisibility(View.VISIBLE);
            viewHolder.deletePost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  deletePOST(list.get(i).getId(),i) ;
                }
            });

        }
        if(list.get(i).getImage().equals("NO"))
        {
            viewHolder.mainImage.setVisibility(View.GONE);
        }
        else
        {
            Glide.with(context).load(list.get(i).getImage()).into(viewHolder.mainImage);
        }

        Glide.with(context).load(list.get(i).getProfilePic()).into(viewHolder.profile);
        viewHolder.title.setText( list.get( i ).getTitle());
        viewHolder.dis.setText( list.get( i ).getDis() );
        viewHolder.pese.setText("₹ "+list.get( i ).getPese() );
        viewHolder.username.setText( list.get( i ).getUsername() );
        viewHolder.time.setText( list.get( i ).getTime() );

            likebutton(viewHolder,list.get(i).getId());

        viewHolder.postCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent view = new Intent(context, JobDiscription. class);
                view.putExtra("id",list.get( i ).getId());
                view.putExtra("title",list.get( i ).getTitle());
                view.putExtra("des",list.get( i ).getDis());
                view.putExtra("username",list.get( i ).getUsername());
                view.putExtra("profile",list.get( i ).getProfilePic());
                view.putExtra("paise",list.get( i ).getPese());
                view.putExtra("img",list.get( i ).getImage());
                view.putExtra("img2",list.get( i ).getImg2());
                view.putExtra("img3",list.get( i ).getImg3());
                context.startActivity(view);
            }
        });

        viewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), BitmapFactory.decodeResource(context.getResources(), R.drawable.logonewcolor), null, null));
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey, i am adding a task on VOULU APP, you install this app and complete that task and get money instantly. Its Amazing i love it. Voulu.in");
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                sendIntent.setType("image/jpeg");
                sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(sendIntent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mainImage,share,like;
        TextView title, pese ,deletePost, dis,username,time;
        CardView postCard;
        CircleImageView profile;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            mainImage = itemView.findViewById( R.id.photoCard );
            profile = itemView.findViewById( R.id.userProfileCard );
            title = itemView.findViewById( R.id.titleCard );
            dis = itemView.findViewById( R.id.disCard );
            pese = itemView.findViewById( R.id.peseCard );
            postCard=itemView.findViewById(R.id.postCardMain);
            deletePost=itemView.findViewById(R.id.deletePost);
            share=itemView.findViewById(R.id.shareButton);
            like=itemView.findViewById(R.id.likeBtn);
            username=itemView.findViewById(R.id.userNameCard);
            time=itemView.findViewById(R.id.timeCard);
        }
    }

    public  void likebutton(ViewHolder viewHolder, final String id)
    {
        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              addToFavorite(id,job_giver_mobile);
            }
        });
    }

    private void addToFavorite(final String id, final String job_giver_mobile) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1"))
                            {
                                Toast.makeText(context, "Added to favorites", Toast.LENGTH_LONG).show();

                            }
                            else if(success.equals("2"))
                            {
                                Toast.makeText(context, "Already added", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(context, "Something went wrong...", Toast.LENGTH_LONG).show();
                                Log.d("OtpRes","lastCondirion"+id+"  =  "+job_giver_mobile);
                            }

                        } catch (JSONException e) {
                            Log.d("OtpRes",e.getMessage());
                            e.printStackTrace();
                            Toast.makeText(context, "Something went wrong..."+e, Toast.LENGTH_LONG).show();


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("OtpRes",error.toString());
                        Toast.makeText(context, "Error2: " + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("postId",id);
                params.put("mobile",job_giver_mobile);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
    }
    private void deletePOST(final String id,final int i) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlDelete,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1"))
                            {
                                list.remove(i);
                                notifyItemRemoved(i);
                                notifyItemRangeChanged(i,list.size());
                                Toast.makeText(context, "Deleted Your post", Toast.LENGTH_LONG).show();

                            }
                            else {
                                Toast.makeText(context, "Something went wrong...", Toast.LENGTH_LONG).show();
                                Log.d("OtpRes","lastCondirion"+id+"  =  "+job_giver_mobile);
                            }

                        } catch (JSONException e) {
                            Log.d("OtpRes",e.getMessage());
                            e.printStackTrace();
                            Toast.makeText(context, "Something went wrong..."+e, Toast.LENGTH_LONG).show();


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("OtpRes",error.toString());
                        Toast.makeText(context, "Error2: " + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("postId",id);
                params.put("mobile",job_giver_mobile);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
    }

}
