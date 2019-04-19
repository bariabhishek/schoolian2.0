package startup.abhishek.spleshscreen;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import startup.abhishek.spleshscreen.Adeptor.CoustomSwipeAdeptor;
import startup.abhishek.spleshscreen.fragments.BottomSheetFragmentForCooments;
import startup.abhishek.spleshscreen.fragments.BottomSheetFragmentui;
import startup.abhishek.spleshscreen.fragments.FullScreenDialog;

public class JobDiscription extends AppCompatActivity  {

    Button accept,reject,showComment;

    int[] image = {R.drawable.sch,R.drawable.sch};
    ViewPager viewPager ;
    CoustomSwipeAdeptor coustomSwipeAdeptor;
    String id,title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.new_job_dec );
        id=getIntent().getExtras().getString("id");
        title=getIntent().getExtras().getString("title");
        Toast.makeText(this, ""+id, Toast.LENGTH_LONG).show();
        /*viewPager = findViewById( R.id.viewPager );
        coustomSwipeAdeptor = new CoustomSwipeAdeptor( this,image );
        viewPager.setAdapter( coustomSwipeAdeptor );*/

       showComment = findViewById( R.id.showComment );
//
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
