package startup.abhishek.spleshscreen.Adeptor;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import startup.abhishek.spleshscreen.R;

public class CoustomSwipeAdeptor extends PagerAdapter {

    private int[] image ={R.drawable.logo,R.drawable.boy};
    private Context context;
    List <String> imageArry;
    LayoutInflater layoutInflater;

    public CoustomSwipeAdeptor(Context context,List <String> imageArry) {
        this.context = context;
        this.imageArry = imageArry;
    }

    @Override
    public int getCount() {
        return imageArry.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view==(LinearLayout)o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View itemView = layoutInflater.inflate( R.layout.view_pager_layout_data,container,false );

        ImageView imageView = itemView.findViewById( R.id.imageViewdisp );
        TextView textView = itemView.findViewById( R.id.tvdisp );

        Glide.with(context).load(imageArry.get(position)).into(imageView);
        textView.setText("← or →" );
        container.addView( itemView );


        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView( (LinearLayout) object);

    }
}
