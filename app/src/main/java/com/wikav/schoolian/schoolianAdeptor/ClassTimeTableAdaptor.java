package com.wikav.schoolian.schoolianAdeptor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.wikav.schoolian.DataClassSchoolian.ClassTimeTableSetGet;
import com.wikav.schoolian.R;
;

import java.util.List;
import java.util.Random;

public class ClassTimeTableAdaptor extends RecyclerView.Adapter<ClassTimeTableAdaptor.ViewHolder> {
    Context context ;
   List<ClassTimeTableSetGet> list;

    public ClassTimeTableAdaptor(Context applicationContext, List <ClassTimeTableSetGet> list) {
        context = applicationContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from( context );
        View view = layoutInflater.inflate( R.layout.schoolian_time_table_layout,parent,false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Random r = new Random();
        int red=r.nextInt(255 - 0 + 1)+0;
        int green=r.nextInt(255 - 0 + 1)+0;
        int blue=r.nextInt(255 - 0 + 1)+0;

        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.OVAL);
        draw.setColor(Color.rgb(red,green,blue));
        holder.teacherImage.setBackground(draw);
        holder.teacherImage.setText( list.get( position ).getImage() );
        holder.endTime.setText( list.get( position ).getEndTime() );
        holder.starttime.setText( list.get( position ).getStartTime() );
        holder.subjectName.setText( list .get( position ).getSubject());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView teacherImage;
        TextView starttime,subjectName,endTime;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            teacherImage = itemView.findViewById( R.id.subjectImage );
            starttime = itemView.findViewById( R.id.startTimeTextView );
            subjectName = itemView.findViewById( R.id.subjectTextView );
            endTime = itemView.findViewById( R.id.entTimeTextView );

        }
    }
}
