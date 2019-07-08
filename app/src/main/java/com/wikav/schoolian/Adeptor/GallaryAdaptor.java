package com.wikav.schoolian.Adeptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wikav.schoolian.DataClassSchoolian.GallarySetGet;
import com.wikav.schoolian.R;

import java.util.List;

public class GallaryAdaptor extends RecyclerView.Adapter<GallaryAdaptor.ViewHolder> {
    Context context;
    List<GallarySetGet> list;

    public GallaryAdaptor(Context applicationContext, List <GallarySetGet> list) {
        context = applicationContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from( context );
        View view = layoutInflater.inflate( R.layout.gallery_layout , parent , false);
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.imageView.setImageResource( list.get( position ).getImg());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            imageView = itemView.findViewById( R.id.imageviewGallery );
        }
    }
}
