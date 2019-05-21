package com.wikav.voulu.Adeptor;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import com.wikav.voulu.R;
import com.wikav.voulu.fragments.FullScreenDialogForFullPager;
import com.wikav.voulu.fragments.FullScreenDialogForNoInternet;

public class CoustomSwipeAdeptor extends PagerAdapter {

    private int[] image ={R.drawable.logo,R.drawable.boy};
    private Context context;
    List <String> imageArry;
    LayoutInflater layoutInflater;

    public CoustomSwipeAdeptor(Context context,List <String> imageArry) {
        this.context = context;
        this.imageArry = imageArry;
    }

    @Override
    public int getCount() {
        return imageArry.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view==(LinearLayout)o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View itemView = layoutInflater.inflate( R.layout.view_pager_layout_data,container,false );

        ImageView imageView = itemView.findViewById( R.id.imageViewdisp );
        TextView textView = itemView.findViewById( R.id.tvdisp );

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                FullScreenDialogForFullPager full=new FullScreenDialogForFullPager();
                Bundle b=new Bundle();
                b.putStringArrayList("array", (ArrayList<String>) imageArry);
                full.setArguments(b);
                full.show(ft,"show");
            }
        });
        Glide.with(context).load(imageArry.get(position)).into(imageView);
        textView.setText("← or →" );
        container.addView( itemView );


        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView( (LinearLayout) object);

    }
}
