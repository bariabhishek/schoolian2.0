package com.wikav.schoolian.Adeptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wikav.schoolian.AnimeActivity;
import com.wikav.schoolian.R;
import com.wikav.schoolian.SessionManger;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdeptor extends RecyclerView.Adapter<MyAdeptor.ViewHolder> {
    Context context;
    List<MyModelList>mData;
    SessionManger sessionManger;
    String job_giver_mobile;
    RequestOptions option,option2;
    String Url = "https://voulu.in/api/addToFavorite.php";
    String UrlDelete = "https://voulu.in/api/deleteJobPost.php";

    public MyAdeptor(Context context, List<MyModelList> list) {
        this.context = context;
        this.mData = list;;
        option2 = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.man).error(R.drawable.man);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.data_feed, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position ) {


        final MyModelList anime = mData.get(position);
        if (!anime.getImage_url().equals("NA")) {
//            imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();
//            imageLoader.get(anime.getImage_url(),ImageLoader.getImageListener(holder.img_thumbnail,0,0));
//            holder.img_thumbnail.setImageUrl(anime.getImage_url(),imageLoader);
            holder.img_thumbnail.setVisibility(View.VISIBLE);
            Glide.with(context).load(mData.get(position).getImage_url()).into(holder.img_thumbnail);
        } else {
            holder.img_thumbnail.setVisibility(View.GONE);
        }

        holder.tv_name.setText(mData.get(position).getName());
        holder.ans.setText(mData.get(position).getComment());
               //  holder.tv_studio.setText(mData.get(position).getProfilePic());
        holder.posts.setText(mData.get(position).getDescription());
        holder.tv_time.setText(mData.get(position).getTime());


        // Load Image from the internet and set it into Imageview using Glide





        Glide.with(context).load(mData.get(position).getProfile_pic()).apply(option2).into(holder.tv_pro1);







        holder.answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(context, AnimeActivity.class);
                i.putExtra("name", mData.get(position).getName());
               //i.putExtra("anime_description",mData.get(holder.getAdapterPosition()).getDescription());
                i.putExtra("proPic", mData.get(position).getProfile_pic());
                i.putExtra("posts", mData.get(position).getDescription());
                i.putExtra("posId", mData.get(position).getPostId());

                i.putExtra("sid", mData.get(position).getSid());
                i.putExtra("postPic", mData.get(position).getImage_url());
                    context.startActivity(i);

            }
        });

        holder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, AnimeActivity.class);
                i.putExtra("name", mData.get(position).getName());
                //i.putExtra("anime_description",mData.get(holder.getAdapterPosition()).getDescription());
                i.putExtra("proPic", mData.get(position).getProfile_pic());
                i.putExtra("posts", mData.get(position).getDescription());
                i.putExtra("posId", mData.get(position).getPostId());
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
        TextView tv_rating;
        TextView tv_time;
        TextView posts, ans;
        ImageView img_thumbnail;
        ImageView star, staron;
        CircleImageView tv_pro1;
        LinearLayout view_container, answer;
        LinearLayout getStar;


        public ViewHolder(View itemView) {
            super(itemView);
            view_container = itemView.findViewById(R.id.containeritem);
            answer = itemView.findViewById(R.id.answers);
            getStar = itemView.findViewById(R.id.setStar5);
            tv_name = itemView.findViewById(R.id.anime_name);
            ans = itemView.findViewById(R.id.answersText);
            tv_time = itemView.findViewById(R.id.timestamp);
            posts = itemView.findViewById(R.id.postss);
            tv_rating = itemView.findViewById(R.id.starGetss);
            tv_pro1 = itemView.findViewById(R.id.profile_pic);
            star = itemView.findViewById(R.id.starIMG2);
            // staron=itemView.findViewById(R.id.starIMG25);
            img_thumbnail = itemView.findViewById(R.id.thumbnail);

        }
    }

}
