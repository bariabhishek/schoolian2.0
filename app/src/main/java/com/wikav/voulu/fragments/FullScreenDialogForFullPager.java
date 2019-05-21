package com.wikav.voulu.fragments;

import android.app.Dialog;
import android.os.Bundle;
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
import com.wikav.voulu.Adeptor.CommentAdaptor;
import com.wikav.voulu.Adeptor.CommentModel;
import com.wikav.voulu.Adeptor.CoustomSwipeAdeptor;
import com.wikav.voulu.Adeptor.CoustomSwipeAdeptorForFullImage;
import com.wikav.voulu.R;
import com.wikav.voulu.SessionManger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

public class FullScreenDialogForFullPager extends DialogFragment {

   ViewPager pager;
   List <String> imageArry;
    CoustomSwipeAdeptorForFullImage coustomSwipeAdeptor;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
        imageArry=new ArrayList<>();
        imageArry=getArguments().getStringArrayList("array");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_full_screen_for_image, container, false);
        pager=view.findViewById(R.id.viewpagerFull);
        coustomSwipeAdeptor = new CoustomSwipeAdeptorForFullImage( getActivity(),imageArry);
        pager.setAdapter( coustomSwipeAdeptor );
       return view;
    }


}
