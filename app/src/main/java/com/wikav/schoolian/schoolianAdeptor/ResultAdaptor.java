package com.wikav.schoolian.schoolianAdeptor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wikav.schoolian.DataClassSchoolian.ResultList;
import com.wikav.schoolian.R;
;

import java.util.List;

public class ResultAdaptor extends RecyclerView.Adapter<ResultAdaptor.ViewHolder> {
    Context context;
    List<ResultList> list;

    public ResultAdaptor(Context applicationContext, List <ResultList> list) {
        context = applicationContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from( context );
        View view = layoutInflater.inflate( R.layout.resullt_data,parent,false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.marks.setText( list.get( position ).getMarks() );
        holder.grads.setText( list.get( position ).getGrads() );
        holder.sub.setText( list.get( position ).getSubject() );
        if(list.get(position).getLength()%2==0)
        {
            holder.dataLinear.setBackgroundColor(Color.parseColor("#FDBDBD"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView sub,marks,grads;
       LinearLayout dataLinear;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            sub = itemView.findViewById( R.id.Subject );
            dataLinear = itemView.findViewById( R.id.dataLinear );
            grads = itemView.findViewById( R.id.grads );
            marks = itemView.findViewById( R.id.marks );

        }
    }
}
