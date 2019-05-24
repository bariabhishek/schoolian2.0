package com.wikav.voulu.Adeptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wikav.voulu.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentOnDisAdeptor extends RecyclerView.Adapter<CommentOnDisAdeptor.ViewHolder> {
    Context context ;
    List<JobDisCommentData> jobDisCommentData;

    public CommentOnDisAdeptor(Context context, List <JobDisCommentData> jobDisCommentData) {
        this.context = context;
        this.jobDisCommentData = jobDisCommentData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from( context );
        View view = layoutInflater.inflate( R.layout.comment_on_job_dis_layout,parent,false );

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    holder.username.setText( jobDisCommentData.get( position ).getA() );
    holder.comment.setText( jobDisCommentData.get( position ).getB() );

    }

    @Override
    public int getItemCount() {
        return jobDisCommentData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imageView ;
        Button accept,reject;
        TextView comment, time , username;
        LinearLayout giverOptions;
        RelativeLayout comnetCard;
        public ViewHolder(@NonNull View itemView) {

            super( itemView );



            imageView = itemView.findViewById( R.id.commentProfile );
            comment = itemView.findViewById( R.id.MainCommentjob );
            username = itemView.findViewById( R.id.commentUsernamejob );
            time = itemView.findViewById( R.id.commentTime );
            giverOptions=itemView.findViewById(R.id.giverOptions);
            accept=itemView.findViewById(R.id.btnAccept);
            reject=itemView.findViewById(R.id.btnReject);
            comnetCard=itemView.findViewById(R.id.comnetCard);
        }
    }
}
