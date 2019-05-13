package startup.abhishek.spleshscreen.Adeptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import startup.abhishek.spleshscreen.JobDiscription;
import startup.abhishek.spleshscreen.R;
import startup.abhishek.spleshscreen.SessionManger;

public class CommentedPostAdaptor extends RecyclerView.Adapter<CommentedPostAdaptor.ViewHolder> {

    Context context;
    List <ModelList> list;
    SessionManger sessionManger;
    String job_giver_mobile;
   // String Url="https://voulu.in/api/addToFavorite.php";
    public CommentedPostAdaptor(Context context, List <ModelList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from( context );
        View view = layoutInflater.inflate( R.layout.commented_activity_data,parent,false );

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {




        if(list.get(i).getStatus().equals("2")){

            if(list.get(i).getImage().equals("NO"))
            {
                viewHolder.main_image_thumb.setVisibility(View.GONE);
            }
            else
            {
                Glide.with(context).load(list.get(i).getImage()).into(viewHolder.main_image_thumb);
            }
            viewHolder.main_card.setVisibility(View.GONE);
            viewHolder.accept_thumb.setVisibility(View.VISIBLE);
            Glide.with(context).load(list.get(i).getProfilePic()).into(viewHolder.profile_thumb);
            viewHolder.title_thumb.setText( list.get( i ).getTitle());
            viewHolder.dis_tumb.setText( list.get( i ).getDis() );
            viewHolder.rate_thumb.setText("₹ "+list.get( i ).getPese() );
            viewHolder.username_thumb.setText( list.get( i ).getUsername() );
            viewHolder.time_thumb.setText( list.get( i ).getTime() );
        }
        else {
            if(list.get(i).getImage().equals("NO"))
            {
                viewHolder.mainImage.setVisibility(View.GONE);

            }
            else {
                Glide.with(context).load(list.get(i).getImage()).into(viewHolder.mainImage);
            }

            viewHolder.main_card.setVisibility(View.VISIBLE);
            viewHolder.accept_thumb.setVisibility(View.GONE);
            Glide.with(context).load(list.get(i).getProfilePic()).into(viewHolder.profile);
            viewHolder.title.setText(list.get(i).getTitle());
            viewHolder.dis.setText(list.get(i).getDis());
            viewHolder.pese.setText("₹ " + list.get(i).getPese());
            viewHolder.username.setText(list.get(i).getUsername());
            viewHolder.time.setText(list.get(i).getTime());


            viewHolder.postCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent view = new Intent(context, JobDiscription. class);
                    view.putExtra("id",list.get( i ).getId());
                    view.putExtra("title",list.get( i ).getTitle());
                    view.putExtra("des",list.get( i ).getDis());
                    view.putExtra("username",list.get( i ).getUsername());
                    view.putExtra("profile",list.get( i ).getProfilePic());
                    view.putExtra("paise",list.get( i ).getPese());
                    view.putExtra("img",list.get( i ).getImage());
                    view.putExtra("img2",list.get( i ).getImg2());
                    view.putExtra("img3",list.get( i ).getImg3());
                    context.startActivity(view);
                }
            });

    }

}

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mainImage,share,like,main_image_thumb,option;
        TextView title,title_thumb, pese,rate_thumb ,deletePost, dis_tumb,dis,username_thumb,username,time_thumb,time;
        CardView postCard;
        CircleImageView profile,profile_thumb;
        RelativeLayout accept_thumb;
        LinearLayout main_card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            main_card = itemView.findViewById( R.id.main_card );
            accept_thumb = itemView.findViewById( R.id.accept_thubmnail );
            main_image_thumb = itemView.findViewById( R.id.main_image_thumb );
            profile_thumb = itemView.findViewById( R.id.profile_thumb );
            title_thumb = itemView.findViewById( R.id.title_thumb );
            rate_thumb = itemView.findViewById( R.id.rate_thumb );
            dis_tumb = itemView.findViewById( R.id.des_thumb );
            username_thumb=itemView.findViewById(R.id.username_thumb);
            time_thumb=itemView.findViewById(R.id.time_thumb);
            mainImage = itemView.findViewById(R.id.photoCard);
            profile = itemView.findViewById(R.id.userProfileCard);
            title = itemView.findViewById(R.id.titleCard);
            dis = itemView.findViewById(R.id.disCard);
            pese = itemView.findViewById(R.id.peseCard);
            postCard = itemView.findViewById(R.id.postCardMain);
            option = itemView.findViewById(R.id.option);
            share = itemView.findViewById(R.id.shareButton);
            like = itemView.findViewById(R.id.likeBtn);
            username = itemView.findViewById(R.id.userNameCard);
            time = itemView.findViewById(R.id.timeCard);
        }
    }
}
