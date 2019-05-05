package startup.abhishek.spleshscreen.Adeptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import startup.abhishek.spleshscreen.R;

public class CommentedAdaptor extends RecyclerView.Adapter<CommentedAdaptor.ViewHolder> {

    Context context;
    List <DataOFComment> list;

    public CommentedAdaptor(Context context, List <DataOFComment> list) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImage()).into(holder.personimageView);
        holder.name.setText( list.get( position ).getName() );
        holder.comment.setText( list.get( position ).getComment() );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView personimageView;
        TextView name,comment;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            personimageView = itemView.findViewById( R.id.commted_image_view );
            name =itemView.findViewById( R.id.comment_person_name );
            comment = itemView.findViewById( R.id.commented_text );
        }
    }
}
