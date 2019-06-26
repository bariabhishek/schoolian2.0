package com.wikav.schoolian.Adeptor;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.wikav.schoolian.R;


import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wikav-pc on 9/1/2018.
 */

public class AdapterForSyllabus extends RecyclerView.Adapter<AdapterForSyllabus.MyViewHolder> {
    private Context mContext ;
    private List<SyllabusList> myData ;
    RequestOptions option;
    String ComId;


    public AdapterForSyllabus(Context mContext, List<SyllabusList> mData) {
        this.mContext = mContext;
        this.myData = mData;


        // Request option for Glide
       // option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }

    @Override
    public AdapterForSyllabus.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.syllabsfeed,parent,false) ;
        final AdapterForSyllabus.MyViewHolder viewHolder = new AdapterForSyllabus.MyViewHolder(view) ;
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(AdapterForSyllabus.MyViewHolder holder, int position) {
        holder.subject_name.setText(myData.get(position).getSubject());
        holder.sylbus.setText(myData.get(position).getSyllabusComplete());
        holder.chapter.setText(myData.get(position).getChapter());

        holder.seekBar.setProgress(Integer.parseInt(myData.get(position).getSyllabusComplete()));
        holder.seekBar.setEnabled(false);
        holder.seekBar.setClickable(false);
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView subject_name,sylbus,chapter;
        SeekBar seekBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            seekBar=itemView.findViewById(R.id.seekBarSyll);
            subject_name = itemView.findViewById(R.id.st_SubName);
            sylbus = itemView.findViewById(R.id.syllabusCompl);
            chapter = itemView.findViewById(R.id.chapter);

        }
    }

}
