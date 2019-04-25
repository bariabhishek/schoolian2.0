package startup.abhishek.spleshscreen.Adeptor;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import startup.abhishek.spleshscreen.R;

public class AdeptorNotification extends RecyclerView.Adapter<AdeptorNotification.Hold> {
    Context context;
    List<DataForNotification> list;

    public AdeptorNotification(FragmentActivity activity, ArrayList <DataForNotification> arrayList) {
        context = activity;
        list = arrayList;
    }

    @NonNull
    @Override
    public Hold onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from( context );
        View view =layoutInflater.inflate( R.layout.data_notification,viewGroup,false );
        return new Hold( view );
    }

    @Override
    public void onBindViewHolder(@NonNull Hold hold, int i) {

        hold.photo.setImageResource( list.get( i ).getNotiImage() );
        hold.notification.setText( list.get( i ).getNotification() );
        hold.time.setText( list.get( i ).getTime() );

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Hold extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView notification, time;
        public Hold(@NonNull View itemView) {
            super( itemView );

            photo = itemView.findViewById( R.id.notificationimg );
            notification = itemView.findViewById( R.id.notificationtext );
            time = itemView.findViewById( R.id.notificationtime );
        }
    }
}
