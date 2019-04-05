package startup.abhishek.spleshscreen.Adeptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import startup.abhishek.spleshscreen.R;

public class Adeptor extends RecyclerView.Adapter<Adeptor.ViewHolder> {
    public Adeptor(Context context, ArrayList <ModelList> list) {
        this.context = context;
        this.list = list;
    }

    Context context;
    ArrayList<ModelList> list ;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from( context );
        View view= inflater.inflate( R.layout.data_recycle_grid_layout,viewGroup,false );
       ViewHolder viewHolder = new ViewHolder( view );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.imageView.setImageResource( list.get( i ).getImage() );
        viewHolder.title.setText( list.get( i ).getTitle());
        viewHolder.dis.setText( list.get( i ).getDis() );
        viewHolder.pese.setText( list.get( i ).getPese() );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView ;
        TextView title, pese , dis;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            imageView = itemView.findViewById( R.id.photo );
            title = itemView.findViewById( R.id.title );
            dis = itemView.findViewById( R.id.dis );
            pese = itemView.findViewById( R.id.pese );
        }
    }


}
