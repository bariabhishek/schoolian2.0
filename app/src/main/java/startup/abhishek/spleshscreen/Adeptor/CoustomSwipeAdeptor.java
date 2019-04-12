package startup.abhishek.spleshscreen.Adeptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import startup.abhishek.spleshscreen.R;

public class CoustomSwipeAdeptor extends PagerAdapter {

    private int[] image ;
    private Context context;
    LayoutInflater layoutInflater;

    public CoustomSwipeAdeptor(Context context,int[] image) {
        this.context = context;
        this.image = image;
    }

    @Override
    public int getCount() {
        return image.length;
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

        imageView.setImageResource( image[position] );
        textView.setText("← or →" );
        container.addView( itemView );


        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView( (LinearLayout) object);

    }
}
