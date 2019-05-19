package com.wikav.voulu.Adeptor;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.wikav.voulu.R;

public class AdeptorJobSeeker extends RecyclerView.Adapter<AdeptorJobSeeker.ViewHoleder2> {

    Context context;
    List<JobSeeker> list;

    public AdeptorJobSeeker(Context context, List <JobSeeker> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHoleder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from( context );
        View view=layoutInflater.inflate( R.layout.data_forrecyclejob_seeker,viewGroup,false );

        return new ViewHoleder2( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoleder2 viewHoleder2, int i) {

        viewHoleder2.name.setText( list.get( i ).getName() );
        viewHoleder2.pese.setText( list.get( i ).getRupee() );
        viewHoleder2.photo.setImageResource( list.get( i ).getImage() );
        viewHoleder2.star.setImageResource( list.get( i ).getStar() );

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoleder2 extends RecyclerView.ViewHolder {

        ImageView star , photo;
        TextView pese , name;

        public ViewHoleder2(@NonNull View itemView) {
            super( itemView );

            star = itemView.findViewById( R.id.star );
            photo = itemView.findViewById( R.id.photo );
            pese = itemView.findViewById( R.id.pese );
            name = itemView.findViewById( R.id.title );
        }
    }
}
