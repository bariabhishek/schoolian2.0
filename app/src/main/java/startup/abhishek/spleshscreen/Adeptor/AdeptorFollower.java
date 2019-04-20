package startup.abhishek.spleshscreen.Adeptor;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import startup.abhishek.spleshscreen.JobDiscription;
import startup.abhishek.spleshscreen.R;

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
        viewHolder.postCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent view = new Intent(context, JobDiscription. class);
                view.putExtra("id",list.get( i ).getId());
                view.putExtra("title",list.get( i ).getTitle());
                context.startActivity(view);
            }
        });

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
