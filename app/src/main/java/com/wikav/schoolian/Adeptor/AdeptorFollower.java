package com.wikav.schoolian.Adeptor;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.wikav.schoolian.R;

public class AdeptorFollower extends RecyclerView.Adapter<AdeptorFollower.Holder> {

    Context context;
    List<DataModelFollower> list;

    public AdeptorFollower(Context mcontext, List <DataModelFollower> arrayList) {
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
    public void onBindViewHolder(@NonNull Holder viewHolder, final int i) {
        Glide.with(context).load(list.get(i).getImage()).into(viewHolder.imageView);
        //viewHolder.imageView.setImageResource( list.get( i ).getImage() );
        viewHolder.title.setText( list.get( i ).getTitle());
        viewHolder.dis.setText( list.get( i ).getDis() );
        viewHolder.pese.setText( list.get( i ).getPese() );


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageView ;
        TextView title, pese , dis;
        LinearLayout postCard;

        public Holder(@NonNull View itemView) {
            super( itemView );

            imageView = itemView.findViewById( R.id.photo );
            title = itemView.findViewById( R.id.title );
            dis = itemView.findViewById( R.id.dis );
            pese = itemView.findViewById( R.id.pese );
            postCard=itemView.findViewById(R.id.postCard);
        }
    }
}
