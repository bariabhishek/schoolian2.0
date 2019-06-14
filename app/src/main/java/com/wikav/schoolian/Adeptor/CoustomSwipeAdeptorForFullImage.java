package com.wikav.schoolian.Adeptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.wikav.schoolian.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;

public class CoustomSwipeAdeptorForFullImage extends PagerAdapter {


    private Context context;
    List <String> imageArry;
    LayoutInflater layoutInflater;



    public CoustomSwipeAdeptorForFullImage(FragmentActivity activity, List <String> imageArry) {
        this.context = activity;
        this.imageArry = imageArry;
        layoutInflater=LayoutInflater.from( activity );
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
        View itemView = layoutInflater.inflate( R.layout.view_pager_layout_for_full,container,false );
        PhotoView imageView = itemView.findViewById( R.id.imageViewForFull );
        Glide.with(context).load(imageArry.get(position)).into(imageView);
        container.addView( itemView );
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView( (LinearLayout) object);

    }
}
