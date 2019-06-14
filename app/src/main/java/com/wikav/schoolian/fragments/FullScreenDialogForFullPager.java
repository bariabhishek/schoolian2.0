package com.wikav.schoolian.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wikav.schoolian.Adeptor.CoustomSwipeAdeptorForFullImage;
import com.wikav.schoolian.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
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
