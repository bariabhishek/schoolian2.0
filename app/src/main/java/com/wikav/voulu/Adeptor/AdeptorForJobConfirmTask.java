package com.wikav.voulu.Adeptor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.wikav.voulu.R;
import com.wikav.voulu.SessionManger;

public class AdeptorForJobConfirmTask extends RecyclerView.Adapter<AdeptorForJobConfirmTask.ViewHolder> {
    Context context;
    List<ModelList> list;
    SessionManger sessionManger;
    String job_giver_mobile;
    String Url = "https://voulu.in/api/verifyOtp.php";
    String UrlDelete = "https://voulu.in/api/deleteOtpPost.php";
    String UrlDone = "https://voulu.in/api/doneJob.php";

    public AdeptorForJobConfirmTask(Context context, List<ModelList> list) {
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
        View view = inflater.inflate(R.layout.insert_otp_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        if(!list.get(i).getStatus().equals("Done")) {
            if (list.get(i).getStatus().equals("accepted")) {
                viewHolder.otpEdit.setVisibility(View.VISIBLE);
                viewHolder.accept.setVisibility(View.VISIBLE);
                viewHolder.decline.setVisibility(View.VISIBLE);

            Glide.with(context).load(list.get(i).getProfilePic()).into(viewHolder.profile);
            viewHolder.title.setText(list.get(i).getTitle());
            viewHolder.username.setText(list.get(i).getUsername());
            viewHolder.time.setText(list.get(i).getTime());

            viewHolder.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callToJobGiver(list.get(i).getMobile());
                }
            });



            viewHolder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!viewHolder.otpEdit.getText().toString().equals("")) {
                        // Toast.makeText(context, viewHolder.otpEdit.getText().toString(), Toast.LENGTH_SHORT).show();
                        completeAcceptTask(viewHolder.otpEdit.getText().toString(), list.get(i).getId(), i, viewHolder);
                    }
                }
            });

            viewHolder.decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    declineTask(list.get(i).getId(), i);
                }
            });

            viewHolder.jobdone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doneTask(viewHolder.otpEdit.getText().toString(), list.get(i).getId(), i, viewHolder);
                }
            });
            }
        }
        else
        {
            list.remove(i);
        }
    }

    private void doneTask(String toString, final String id, final int i, final ViewHolder viewHolder) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Verifying...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlDone,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //    progressDialog.dismiss();
                        Log.i("TAG", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")){
                                progressDialog.dismiss();
                               list.remove(i);
                               notifyDataSetChanged();
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(context, "invalid OTP", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.setCancelable(true);

                            // Toast.makeText(getActivity(), "Something went wrong..."+e, Toast.LENGTH_LONG).show();
                            //  noData.setVisibility(View.VISIBLE);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.setCancelable(true);

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("postId", id);
                return params;
            }
        };

        stringRequest.setShouldCache(true);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
    }

    private void declineTask(final String id, final int i) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Declining");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlDelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //    progressDialog.dismiss();
                        Log.i("TAG", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")){
                                progressDialog.dismiss();
                                list.remove(i);
                                notifyItemRemoved(i);
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(context, "invalid OTP", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.setCancelable(true);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.setCancelable(true);

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("postId", id);
                return params;
            }
        };

        stringRequest.setShouldCache(true);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();

    }

    private void completeAcceptTask(final String otp, final String id, final int i, final ViewHolder viewHolder) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Verifying...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //    progressDialog.dismiss();
                        Log.i("TAG", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")){
                                viewHolder.etOtp.setVisibility(View.GONE);
                                viewHolder.accept.setVisibility(View.GONE);
                                viewHolder.decline.setVisibility(View.GONE);
                                viewHolder.jobdone.setVisibility(View.VISIBLE);

                                progressDialog.dismiss();

                            }
                            else
                            {
                               progressDialog.dismiss();
                                Toast.makeText(context, "invalid OTP", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.setCancelable(true);

                            // Toast.makeText(getActivity(), "Something went wrong..."+e, Toast.LENGTH_LONG).show();
                            //  noData.setVisibility(View.VISIBLE);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.setCancelable(true);

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("postId", id);
                params.put("otp", otp);
                return params;
            }
        };

        stringRequest.setShouldCache(true);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();
    }

    private void callToJobGiver(String mobile) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile));
        context.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, accept, decline, call, username, time,jobdone;
        CircleImageView profile;
        EditText otpEdit;
        TextInputEditText etOtp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.userProfileCard);
            accept = itemView.findViewById(R.id.accept);
            etOtp = itemView.findViewById(R.id.etEmailForotp);
            decline = itemView.findViewById(R.id.decline);
            call = itemView.findViewById(R.id.deletePost);
            title = itemView.findViewById(R.id.titleCard);
            otpEdit = itemView.findViewById(R.id.edittextforotp);
            username = itemView.findViewById(R.id.userNameCard);
            time = itemView.findViewById(R.id.timeCard);
        }
    }


}
