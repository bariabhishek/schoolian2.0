package com.wikav.schoolian.schoolianAdeptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wikav.schoolian.DataClassSchoolian.Evants_holidays_SetGet;
import com.wikav.schoolian.R;

import java.util.List;

public class Evants_holidays_Adaptor extends RecyclerView.Adapter<Evants_holidays_Adaptor.ViewHolder> {
    Context context ;
    List<Evants_holidays_SetGet> list;

    public Evants_holidays_Adaptor(Context applicationContext, List <Evants_holidays_SetGet> list) {
        context = applicationContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from( context );
        View view = layoutInflater.inflate( R.layout.events_holidays_layout,parent,false );

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

      //  holder.textcolor.setText( list.get( position ).getColor() );
        holder.holiday_name.setText( list.get( position ).getHolidayname() );
        if(!list.get( position ).getToDate().equals("NA")) {
            holder.Holiday_date.setText(list.get(position).getFromDate() + " To " + list.get(position).getToDate());
        }
        else
        {
            holder.Holiday_date.setText(list.get(position).getFromDate());
        }




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView holiday_name,Holiday_date,textcolor;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            holiday_name = itemView.findViewById( R.id.holidayname);
            Holiday_date = itemView.findViewById( R.id.holidaydate );
            textcolor = itemView.findViewById( R.id.textColor ) ;
        }
    }
}
