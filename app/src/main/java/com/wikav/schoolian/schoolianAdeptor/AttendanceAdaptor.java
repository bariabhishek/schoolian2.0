package com.wikav.schoolian.schoolianAdeptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wikav.schoolian.DataClassSchoolian.AttendanceSetGet;
import com.wikav.schoolian.R;
;

import java.util.List;

public class AttendanceAdaptor extends RecyclerView.Adapter<AttendanceAdaptor.ViewHolder> {
    Context context;
    List<AttendanceSetGet> list;

    public AttendanceAdaptor(Context applicationContext, List <AttendanceSetGet> list) {
        context = applicationContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from( context );
        View view = layoutInflater.inflate( R.layout.attendance_layout,parent,false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.marks.setText( list.get( position ).getMarks() );
        holder.grads.setText( list.get( position ).getGrads() );
        holder.sub.setText( list.get( position ).getSubject() );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView sub,marks,grads;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            sub = itemView.findViewById( R.id.Subject );
            grads = itemView.findViewById( R.id.grads );
            marks = itemView.findViewById( R.id.marks );

        }
    }
}
