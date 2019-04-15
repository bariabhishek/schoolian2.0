package startup.abhishek.spleshscreen.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
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
import startup.abhishek.spleshscreen.Adeptor.ModelList;
import startup.abhishek.spleshscreen.Home;
import startup.abhishek.spleshscreen.R;
import startup.abhishek.spleshscreen.Registration;
import startup.abhishek.spleshscreen.UploadYourPost;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView recyclerView ;
    List<ModelList> list;
    ImageButton imageButton;
    View view;
    private TextView mTextMessage;

    String Url="https://voulu.in/api/getJobPost.php";

    public HomeFragment() {
        // Required empty public constructor
    }
  /*  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //   mTextMessage.setText( R.string.title_home );
                    return true;
                case R.id.inbox:
                    //   mTextMessage.setText("demo" );
                    return true;
                case R.id.profile:
                    //    mTextMessage.setText( R.string.title_notifications );
                    return true;
                case R.id.notification:
                    //    mTextMessage.setText( R.string.title_notifications );
                    return true;
                case R.id.follower:
                    //    mTextMessage.setText( R.string.title_notifications );
                    return true;
            }
            return false;
        }
    };*/
    private void arraydata() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //    progressDialog.dismiss();
                        Log.i("TAG", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("allPost");
                            if (success.equals("1")){
                                Log.d("Response",response);
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String title = object.getString("title").trim();
                                    String mobile = object.getString("mobile").trim();
                                    String des = object.getString("des").trim();
                                    String rate = object.getString("rate").trim();
                                    String img = object.getString("img").trim();
                                    String id = object.getString("id").trim();
                                    String time = object.getString("time").trim();
                                    Toast.makeText(getActivity(), ""+title+mobile+des, Toast.LENGTH_SHORT).show();
                                    list.add( new ModelList(img,title,des,rate,id,time,mobile ) );

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
                        Toast.makeText(getActivity(), "Something went wrong..."+error, Toast.LENGTH_LONG).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("key", "9195A3CDB388F894B3EE3BD665DFD");
                 return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate( R.layout.fragment_home, container, false );
            imageButton=view.findViewById(R.id.uplodButton);
        list = new ArrayList <>(  );
        arraydata();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vi = new Intent(getActivity(), UploadYourPost. class);
                startActivity(vi);
            }
        } );





        return view;
    }

    public void setupRecycle(List <ModelList> list)
    {
        Adeptor a= new Adeptor( getContext(),list );
        recyclerView = view.findViewById( R.id.recycleview );

        recyclerView.setHasFixedSize( true );
        recyclerView.setItemAnimator( new DefaultItemAnimator() );
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2) );
        recyclerView.setAdapter( a );
    }
}
