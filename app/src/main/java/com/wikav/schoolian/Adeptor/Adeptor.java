package com.wikav.schoolian.Adeptor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import com.wikav.schoolian.DatabaseHelper;
import com.wikav.schoolian.JobDiscription;
import com.wikav.schoolian.R;
import com.wikav.schoolian.SessionManger;

public class Adeptor extends RecyclerView.Adapter<Adeptor.ViewHolder> {
    Context context;
    List<ModelList> list;
    SessionManger sessionManger;
    String job_giver_mobile;
    DatabaseHelper myDB;
    String Url = "https://voulu.in/api/addToFavorite.php";
    String UrlDelete = "https://voulu.in/api/deleteJobPost.php";

    public Adeptor(Context context, List<ModelList> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        sessionManger = new SessionManger(context);
        HashMap<String, String> getUser = sessionManger.getUserDetail();
        job_giver_mobile = getUser.get(sessionManger.MOBILE);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.new_home_card, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
       // myDB = new DatabaseHelper(context);
        if(!list.get(i).getStatus().equals("3"))
        {
            if (list.get(i).getStatus().equals("2")) {

            if (list.get(i).getImage().equals("NO")) {
                viewHolder.main_image_thumb.setVisibility(View.GONE);
            } else {
                Glide.with(context).load(list.get(i).getImage()).into(viewHolder.main_image_thumb);
            }
            viewHolder.main_card.setVisibility(View.GONE);
            viewHolder.accept_thumb.setVisibility(View.VISIBLE);
            Glide.with(context).load(list.get(i).getProfilePic()).into(viewHolder.profile_thumb);
            viewHolder.title_thumb.setText(list.get(i).getTitle());
            viewHolder.dis_tumb.setText(list.get(i).getDis());
            viewHolder.rate_thumb.setText("₹ " + list.get(i).getPese());
            viewHolder.username_thumb.setText(list.get(i).getUsername());
            viewHolder.time_thumb.setText(list.get(i).getTime());
            viewHolder.postCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent view = new Intent(context, JobDiscription.class);
                    view.putExtra("id", list.get(i).getId());
                    view.putExtra("title", list.get(i).getTitle());
                    view.putExtra("des", list.get(i).getDis());
                    view.putExtra("username", list.get(i).getUsername());
                    view.putExtra("profile", list.get(i).getProfilePic());
                    view.putExtra("paise", list.get(i).getPese());
                    view.putExtra("img", list.get(i).getImage());
                    view.putExtra("img2", list.get(i).getImg2());
                    view.putExtra("img3", list.get(i).getImg3());
                    view.putExtra("status", list.get(i).getStatus());
                    context.startActivity(view);
                }
            });
            } else if (list.get(i).getStatus().equals("0")) {

            if (list.get(i).getImage().equals("NO")) {
                viewHolder.mainImage.setVisibility(View.GONE);

            } else {
                Glide.with(context).load(list.get(i).getImage()).into(viewHolder.mainImage);
            }
            if (job_giver_mobile.equals(list.get(i).getMobile())) {
                viewHolder.deletePost.setVisibility(View.VISIBLE);
                viewHolder.deletePost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setMessage("Are you sure, you want to delete this post");
                        alertDialogBuilder.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        deletePOST(list.get(i).getId(), i);
                                    }
                                });

                        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                alertDialogBuilder.setCancelable(true);
                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }
                });

            }
            viewHolder.main_card.setVisibility(View.VISIBLE);
            viewHolder.accept_thumb.setVisibility(View.GONE);
            Glide.with(context).load(list.get(i).getProfilePic()).into(viewHolder.profile);
            viewHolder.title.setText(list.get(i).getTitle());
            viewHolder.dis.setText(list.get(i).getDis());
            viewHolder.pese.setText("₹ " + list.get(i).getPese());
            viewHolder.username.setText(list.get(i).getUsername());
            viewHolder.time.setText(list.get(i).getTime());
            likebutton(viewHolder, list.get(i).getId());

            viewHolder.postCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent view = new Intent(context, JobDiscription.class);
                    view.putExtra("id", list.get(i).getId());
                    view.putExtra("title", list.get(i).getTitle());
                    view.putExtra("des", list.get(i).getDis());
                    view.putExtra("username", list.get(i).getUsername());
                    view.putExtra("profile", list.get(i).getProfilePic());
                    view.putExtra("paise", list.get(i).getPese());
                    view.putExtra("img", list.get(i).getImage());
                    view.putExtra("img2", list.get(i).getImg2());
                    view.putExtra("img3", list.get(i).getImg3());
                    view.putExtra("status", list.get(i).getStatus());
                    context.startActivity(view);
                }
            });
            viewHolder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "https://play.google.com/store/apps/details?id=com.wikav.voulu";
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey, i am adding a task on VOULU APP, you install this app and complete that task and get money instantly. Its Amazing i love it. " + url);
                    sendIntent.setType("text/plain");
                    context.startActivity(sendIntent);
                }
            });
            }


        }
        else
        {
            list.remove(i);
        }


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void likebutton(ViewHolder viewHolder, final String id) {
        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavorite(id, job_giver_mobile);
            }
        });
    }

    private void addToFavorite(final String id, final String job_giver_mobile) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {   //Snackbar  snackbar =
                                    /*Snackbar.make(findViewById(android.R.id.content),
                                            Html.fromHtml("<font color=\"#ffffff\">No Internet Connection</font>"),
                                            BaseTransientBottomBar.LENGTH_INDEFINITE);
snackbar.show();
*/
                                Toast.makeText(context, "Added to favorites", Toast.LENGTH_LONG).show();
                            } else if (success.equals("2")) {
                                Toast.makeText(context, "Already added", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Something went wrong...", Toast.LENGTH_LONG).show();
                                Log.d("OtpRes", "lastCondirion" + id + "  =  " + job_giver_mobile);
                            }

                        } catch (JSONException e) {
                            Log.d("OtpRes", e.getMessage());
                            e.printStackTrace();
                            Toast.makeText(context, "Something went wrong..." + e, Toast.LENGTH_LONG).show();


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("OtpRes", error.toString());
                        Toast.makeText(context, "Error2: " + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("postId", id);
                params.put("mobile", job_giver_mobile);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
    }

    private void deletePOST(final String id, final int i) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Deleting...");
        progressDialog.setCancelable(false);
        progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlDelete,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                if (success.equals("1")) {
                                   /* int row = myDB.deleteData(id);
                                    if(row>0) {

                                    }*/
                                    progressDialog.dismiss();
                                    list.remove(i);
                                    notifyItemRemoved(i);
                                    notifyItemRangeChanged(i, list.size());
                                    Toast.makeText(context, "Deleted Your post", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, "Something went wrong...", Toast.LENGTH_LONG).show();
                                    Log.d("OtpRes", "lastCondirion" + id + "  =  " + job_giver_mobile);
                                }

                            } catch (JSONException e) {
                                Log.d("OtpRes", e.getMessage());
                                e.printStackTrace();
                                Toast.makeText(context, "Something went wrong..." + e, Toast.LENGTH_LONG).show();


                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("OtpRes", error.toString());
                            Toast.makeText(context, "Error2: " + error.toString(), Toast.LENGTH_LONG).show();

                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("postId", id);
                    params.put("mobile", job_giver_mobile);
                    return params;
                }
            };
            stringRequest.setShouldCache(false);
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
            requestQueue.getCache().clear();
        }



    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView mainImage, share, like, deletePost, main_image_thumb;
        TextView title, title_thumb, pese, rate_thumb, dis_tumb, dis, username_thumb, username, time_thumb, time;
        CardView postCard;
        CircleImageView profile, profile_thumb;
        RelativeLayout accept_thumb;
        LinearLayout main_card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            main_card = itemView.findViewById(R.id.main_card);
            mainImage = itemView.findViewById(R.id.photoCard);
            accept_thumb = itemView.findViewById(R.id.accept_thubmnail);
            main_image_thumb = itemView.findViewById(R.id.main_image_thumb);
            profile_thumb = itemView.findViewById(R.id.profile_thumb);
            title_thumb = itemView.findViewById(R.id.title_thumb);
            rate_thumb = itemView.findViewById(R.id.rate_thumb);
            dis_tumb = itemView.findViewById(R.id.des_thumb);
            username_thumb = itemView.findViewById(R.id.username_thumb);
            time_thumb = itemView.findViewById(R.id.time_thumb);
            profile = itemView.findViewById(R.id.userProfileCard);
            deletePost = itemView.findViewById(R.id.deletePost);
            title = itemView.findViewById(R.id.titleCard);
            dis = itemView.findViewById(R.id.disCard);
            pese = itemView.findViewById(R.id.peseCard);
            postCard = itemView.findViewById(R.id.postCardMain);
            share = itemView.findViewById(R.id.shareButton);
            like = itemView.findViewById(R.id.likeBtn);
            username = itemView.findViewById(R.id.userNameCard);
            time = itemView.findViewById(R.id.timeCard);
        }
    }

}
