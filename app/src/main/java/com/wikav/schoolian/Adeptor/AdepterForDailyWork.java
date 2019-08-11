package com.wikav.schoolian.Adeptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wikav.schoolian.AnimeActivity;
import com.wikav.schoolian.DailyWorkCommentActivity;
import com.wikav.schoolian.R;
import com.wikav.schoolian.SessionManger;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdepterForDailyWork extends RecyclerView.Adapter<AdepterForDailyWork.ViewHolder> {
    Context context;
    List<MyModelListForDailyWork>mData;
    RequestOptions option2;


    public AdepterForDailyWork(Context context, List <MyModelListForDailyWork> list, String sid) {
        this.context = context;
        this.mData = list;;
        option2 = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.man).error(R.drawable.man);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_feed, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position ) {


        final MyModelListForDailyWork anime = mData.get(position);
        if (!anime.getImage_url().equals("NA")) {
            holder.img_thumbnail.setVisibility(View.VISIBLE);
            Glide.with(context).load(mData.get(position).getImage_url()).into(holder.img_thumbnail);
        } else {
            holder.img_thumbnail.setVisibility(View.GONE);
        }
        holder.tv_name.setText(mData.get(position).getName());
        holder.posts.setText(mData.get(position).getMsg());
        holder.tv_time.setText(mData.get(position).getTime());
        holder.subject.setText(mData.get(position).getSubject());

        Glide.with(context).load(mData.get(position).getProfile_pic()).apply(option2).into(holder.tv_pro1);



        holder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DailyWorkCommentActivity.class);
                i.putExtra("name", mData.get(position).getName());
                i.putExtra("proPic", mData.get(position).getProfile_pic());
                i.putExtra("posts", mData.get(position).getMsg());
                i.putExtra("posId", mData.get(position).getPostid());
                i.putExtra("sid", mData.get(position).getSid());
                i.putExtra("postPic", mData.get(position).getImage_url());
                context.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_name;
        TextView tv_time;
        TextView posts, subject;
        ImageView img_thumbnail;
        CircleImageView tv_pro1;
        CardView view_container;


        public ViewHolder(View itemView) {
            super(itemView);
            view_container = itemView.findViewById(R.id.containerItem);
            tv_name = itemView.findViewById(R.id.anime_name);
            subject = itemView.findViewById(R.id.feedsubject);
            tv_time = itemView.findViewById(R.id.timestamp);
            posts = itemView.findViewById(R.id.postss);
            tv_pro1 = itemView.findViewById(R.id.profile_pic);
            img_thumbnail = itemView.findViewById(R.id.thumbnail_image);

        }
    }

}
