package com.wikav.schoolian.schoolianAdeptor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wikav.schoolian.DataClassSchoolian.ClassTimeTableSetGet;
import com.wikav.schoolian.DataClassSchoolian.TeacherSetGet;
import com.wikav.schoolian.R;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

;

public class TeachersApdaptor extends RecyclerView.Adapter<TeachersApdaptor.ViewHolder> {
    Context context ;
    List <TeacherSetGet> list;

    public TeachersApdaptor(Context applicationContext, List <TeacherSetGet> list) {
        context = applicationContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from( context );
        View view = layoutInflater.inflate( R.layout.teacher_list,parent,false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.teacheNmae.setText(list.get(position).getTeacherName());
            holder.subjectName.setText(list.get(position).getSubject());
       if( !list.get(position).getImage().equals("noImage")){
           Glide.with(context).load(list.get(position).getImage()).into(holder.teacherImage);
       }else
       {
           Random r = new Random();
           int red=r.nextInt(255 - 0 + 1)+0;
           int green=r.nextInt(255 - 0 + 1)+0;
           int blue=r.nextInt(255 - 0 + 1)+0;
           GradientDrawable draw = new GradientDrawable();
           draw.setShape(GradientDrawable.OVAL);
           draw.setColor(Color.rgb(red,green,blue));
           holder.teacherImage.setBackground(draw);
       }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView teacherImage;
        TextView subjectName,teacheNmae;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            teacherImage = itemView.findViewById( R.id.teacherImage );
            subjectName = itemView.findViewById( R.id.subjectName );
            teacheNmae = itemView.findViewById( R.id.teacherName );

        }
    }
}
