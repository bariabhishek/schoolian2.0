package startup.abhishek.spleshscreen.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import startup.abhishek.spleshscreen.Adeptor.AdeptorNotification;
import startup.abhishek.spleshscreen.Adeptor.CommentModel;
import startup.abhishek.spleshscreen.Adeptor.DataForNotification;
import startup.abhishek.spleshscreen.R;
import startup.abhishek.spleshscreen.SessionManger;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    RecyclerView recyclerView;
    String  Url="https://voulu.in/api/getNotification.php",mobile;
    SessionManger sessionManger;

    List<DataForNotification> arrayList;


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_notification, container, false );
        arrayList = new ArrayList <>(  );
        sessionManger=new SessionManger(getActivity());
        HashMap<String,String> getUser=sessionManger.getUserDetail();
            mobile=getUser.get(sessionManger.MOBILE);
        data(mobile);

        recyclerView =view.findViewById( R.id.notificationrecycleview );




        return view;
    }

    private void setUpRecyclerview(List<DataForNotification> arrayList) {
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()) );


        AdeptorNotification adeptorNotification = new AdeptorNotification(getActivity(),arrayList);

        recyclerView.setAdapter( adeptorNotification );

    }

    private void data(final String mobile) {

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.getCache().clear();

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
                            JSONArray jsonArray = jsonObject.getJSONArray("noti");
                            if (success.equals("1")){
                                Log.d("Response",response);
                                for (int i = 0; i < jsonArray.length(); i++) {


                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String notification = object.getString("metter").trim();
                                    String senderImage = object.getString("sender_image").trim();
                                    String intent = object.getString("intent").trim();
                                    String time = object.getString("time").trim();
                                    String status = object.getString("status").trim();
                                    String notId = object.getString("not_id").trim();
                                    String postId = object.getString("post_id").trim();
                                   arrayList.add(new DataForNotification(senderImage,notification,intent,time,status,notId,postId));
                                    }


                                setUpRecyclerview(arrayList);

                            }
                            else
                            {
                                Toast.makeText(getActivity(), "Something went wrong...", Toast.LENGTH_LONG).show();

                                Log.d("Response", response);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Something went wrong..."+e, Toast.LENGTH_LONG).show();
                            Log.d("Response", e.getMessage());

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error2: " + error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("Response", error.toString());



                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("mobile", mobile);
                return params;
            }
        };

        requestQueue.add(stringRequest);


    }

}
