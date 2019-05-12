package startup.abhishek.spleshscreen.Adeptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;

import java.util.List;

import startup.abhishek.spleshscreen.R;

public class CoustomSwipeAdeptorForHome extends PagerAdapter {

    private int[] image2 ;
    private Context context;
    int [] imageArry;
    LayoutInflater layoutInflater;



    public CoustomSwipeAdeptorForHome(FragmentActivity activity, int[] imageArry) {
        this.context = activity;
        this.imageArry = imageArry;
        layoutInflater=LayoutInflater.from( activity );
    }

    @Override
    public int getCount() {

//        Log.e( "chack", String.valueOf( image2.length ) );

        return imageArry.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view==(LinearLayout)o);
    }

    @NonNull
    @Override

    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View itemView = layoutInflater.inflate( R.layout.view_pager_layout_data_for_home,container,false );

        ImageView imageView = itemView.findViewById( R.id.imageViewdisphome );
        TextView textView = itemView.findViewById( R.id.tvdisphome );

        imageView.setImageResource( imageArry[position] );
        textView.setText("← or →" );
       container.addView( itemView );


        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView( (LinearLayout) object);

    }
}
