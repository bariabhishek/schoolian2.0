package startup.abhishek.spleshscreen.Adeptor;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import startup.abhishek.spleshscreen.AcceptedActivity;
import startup.abhishek.spleshscreen.ExampleDialog;
import startup.abhishek.spleshscreen.JobDiscriptionForNotification;
import startup.abhishek.spleshscreen.R;

public class AdeptorNotification extends RecyclerView.Adapter<AdeptorNotification.Hold> {
    Context context;
    List<DataForNotification> list;
    String  Url="https://voulu.in/api/setNotificationStatus.php";


    public AdeptorNotification(FragmentActivity activity, List <DataForNotification> arrayList) {
        context = activity;
        list = arrayList;
    }

    @NonNull
    @Override
    public Hold onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from( context );
        View view =layoutInflater.inflate( R.layout.data_notification,viewGroup,false );
        return new Hold( view );
    }

    @Override
    public void onBindViewHolder(@NonNull final Hold hold, final int i) {

        Glide.with(context).load(list.get(i).getNotiImage()).into(hold.photo);
        if(list.get(i).getStatus().equals("not"))
        {
            hold.layout.setBackground(ContextCompat.getDrawable(context, R.drawable.background_rounded_for_giver));
            hold.notification.setTypeface(Typeface.DEFAULT_BOLD);
            hold.time.setTypeface(Typeface.DEFAULT_BOLD);
        }
        else {
            hold.layout.setBackground(ContextCompat.getDrawable(context, R.drawable.background_rounded));
        }
        hold.notification.setText( list.get( i ).getNotification() );
        hold.time.setText( list.get( i ).getTime() );
        hold.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRead(list.get(i).getNotId(),i);
            }
        });
       // Toast.makeText(context, ""+list.get(2).getUserProfile(), Toast.LENGTH_SHORT).show();
    }

    private void setRead(final String notId, final int i) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>()
                {

                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response",response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")){

                                if(list.get(i).getIntent().equals("accept")) {
                                    Intent view = new Intent(context, AcceptedActivity.class);
                                    view.putExtra("id",list.get(i).getPostId());
                                    context.startActivity(view);
                                     }
                                else
                                    {
                                        Toast.makeText(context, ""+list.get(i).getPostId(), Toast.LENGTH_SHORT).show();
                                    Intent view = new Intent(context, JobDiscriptionForNotification.class);
                                    view.putExtra("id",list.get(i).getPostId());
                                    context.startActivity(view);
                                }
                            }
                            else
                            {
                                Toast.makeText(context,"Something went wrong...", Toast.LENGTH_LONG).show();

                                Log.d("Response", response);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Something went wrong..."+e, Toast.LENGTH_LONG).show();
                            Log.d("Response", e.getMessage());

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error2: " + error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("Response", error.toString());



                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("notId", notId);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Hold extends RecyclerView.ViewHolder {
        CircleImageView photo;
        TextView notification, time;
        LinearLayout layout;
        public Hold(@NonNull View itemView) {
            super( itemView );

            photo = itemView.findViewById( R.id.notificationimg );
            layout = itemView.findViewById( R.id.notificationLayout );
            notification = itemView.findViewById( R.id.notificationtext );
            time = itemView.findViewById( R.id.notificationtime );
        }
    }
}
