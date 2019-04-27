package startup.abhishek.spleshscreen;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import startup.abhishek.spleshscreen.Adeptor.CoustomSwipeAdeptor;
import startup.abhishek.spleshscreen.fragments.FullScreenDialog;

public class JobDiscription extends AppCompatActivity  {

    Button showComment;
    CircleImageView profile;
    ImageView mainImage;
    TextView username,jobtTitle,jobdes,paise;
    String id,title,jobdis,usernames,image,profileImage,pese;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.new_job_dec );

           id=getIntent().getExtras().getString("id");
           jobdis=getIntent().getExtras().getString("des");
           usernames=getIntent().getExtras().getString("username");
           profileImage=getIntent().getExtras().getString("profile");
           image=getIntent().getExtras().getString("img");
           pese=getIntent().getExtras().getString("paise");
         title=getIntent().getExtras().getString("title");

       profile = findViewById( R.id.postProfile );
       mainImage = findViewById( R.id.postMainImage );
       username = findViewById( R.id.postUsername );
       jobtTitle = findViewById( R.id.postJobTitle );
       showComment = findViewById( R.id.showComment );
       jobdes = findViewById( R.id.postJobdes );
       paise = findViewById( R.id.paisePost );
       showComment = findViewById( R.id.showComment );

       Glide.with(this).load(profileImage).into(profile);
      if (!image.equals("NO")) {
          Glide.with(this).load(image).into(mainImage);
      }
      username.setText(usernames);
      jobtTitle.setText(title);
      jobdes.setText(jobdis);
      paise.setText("₹ "+pese);

       getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        showComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click();
            }
        });

    }

    public void click() {

        FullScreenDialog dialog =new FullScreenDialog();
        Bundle b=new Bundle();
        b.putString("id",id);
        b.putString("title",title);
        dialog.setArguments(b);
        dialog.show(getSupportFragmentManager(),"TAG");

    }



}
