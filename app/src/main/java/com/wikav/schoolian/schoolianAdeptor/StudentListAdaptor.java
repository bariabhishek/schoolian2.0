package com.wikav.schoolian.schoolianAdeptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import com.bumptech.glide.Glide;
import com.wikav.schoolian.DataClassSchoolian.StudentListSetGet;
import com.wikav.schoolian.R;
;

import java.util.List;

public class StudentListAdaptor extends RecyclerView.Adapter<StudentListAdaptor.ViewHolder> {
    Context context ;
    List<StudentListSetGet> listSetGets;

    public StudentListAdaptor(Context applicationContext, List <StudentListSetGet> list) {
        context = applicationContext;
        listSetGets = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from( context );
        View view = layoutInflater.inflate( R.layout.studentlist_schoolian_layout,parent,false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.studentRoll.setText( listSetGets.get( position ).getRoll() );
        holder.studentName.setText( listSetGets.get( position ).getName() );
        holder.studentMobile.setText( listSetGets.get( position ).getMobile() );

        if(!listSetGets.get( position ).getImage().equals("noImage")) {
            Glide.with(context).load(listSetGets.get(position).getImage()).into(holder.teacherImage);
        }
    }

    @Override
    public int getItemCount() {
        return listSetGets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView teacherImage;
        TextView studentName,studentRoll,studentMobile;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            teacherImage = itemView.findViewById( R.id.profileImage );
            studentName = itemView.findViewById( R.id.studentName );
            studentRoll = itemView.findViewById( R.id.roll );
            studentMobile = itemView.findViewById( R.id.studentMobile );
        }
    }
}
