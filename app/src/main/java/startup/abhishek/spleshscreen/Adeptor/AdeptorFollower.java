package startup.abhishek.spleshscreen.Adeptor;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import startup.abhishek.spleshscreen.R;

public class AdeptorFollower extends RecyclerView.Adapter<AdeptorFollower.Holder> {

    Context context;
    List<DataModelFollower> list;

    public AdeptorFollower(Context mcontext, ArrayList <DataModelFollower> arrayList) {
        context = mcontext;
        list = arrayList;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from( context );
        View view = inflater.inflate( R.layout.data_recycle_follower ,viewGroup,false);
        return new Holder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.name.setText( list.get( i ).getNamefollower() );
        holder.status.setText( list.get( i ).getFollowerstatus() );
        holder.follow.setText( list.get( i ).getFolloer() );
        holder.photo.setImageResource( list.get( i ).getPhotofollower());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView name,status,follow;
        ImageView photo;
        public Holder(@NonNull View itemView) {
            super( itemView );
            name = itemView.findViewById( R.id.namefollow );
            status = itemView.findViewById( R.id.status );
            follow = itemView.findViewById( R.id.folloerbtn );
            photo = itemView.findViewById( R.id.photofollow );
        }
    }
}
