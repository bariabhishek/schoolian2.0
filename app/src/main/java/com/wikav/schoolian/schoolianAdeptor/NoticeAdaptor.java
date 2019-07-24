package com.wikav.schoolian.schoolianAdeptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wikav.schoolian.DataClassSchoolian.NoticeDataClass;
import com.wikav.schoolian.Notice;
import com.wikav.schoolian.R;

import java.util.List;

public class NoticeAdaptor extends RecyclerView.Adapter<NoticeAdaptor.ViewHolder> {

    Context context;
    List<NoticeDataClass> list;

    public NoticeAdaptor(Context applicationContext, List <NoticeDataClass> list) {
        context = applicationContext;
        this.list= list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from( context );
        View view = layoutInflater.inflate( R.layout.layout_notice,parent,false );
        return new ViewHolder( view );

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.notice.setText( list.get( position ).getNotice() );
        holder.date.setText("( " +list.get( position ).getDate()+ " )" );
        holder.noticeTitle.setText( list.get( position ).getNoticeTitle() );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date,notice,noticeTitle;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            date = itemView.findViewById( R.id.date );
            notice = itemView.findViewById( R.id.notice );
            noticeTitle = itemView.findViewById( R.id.noticeTitle );

        }
    }
}
