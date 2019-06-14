package com.wikav.schoolian.schoolianAdeptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

        holder.subjectName.setText( listSetGets.get( position ).getSubject() );
        holder.techerName.setText( listSetGets.get( position ).getName() );
        holder.teacherImage.setImageResource(( listSetGets.get( position ).getImage() ));
    }

    @Override
    public int getItemCount() {
        return listSetGets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView teacherImage;
        TextView techerName,subjectName;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            teacherImage = itemView.findViewById( R.id.subjectImage );
            techerName = itemView.findViewById( R.id.teacherNameTextView );
            subjectName = itemView.findViewById( R.id.subjectTextView );
        }
    }
}
