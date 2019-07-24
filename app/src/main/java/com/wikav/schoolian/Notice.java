package com.wikav.schoolian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wikav.schoolian.DataClassSchoolian.NoticeDataClass;
import com.wikav.schoolian.schoolianAdeptor.NoticeAdaptor;

import java.util.ArrayList;
import java.util.List;

public class Notice extends AppCompatActivity {
    RecyclerView recyclerView;
    List<NoticeDataClass> list;
    ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_notice );
        backbtn=findViewById( R.id.back );

        backbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );

        recyclerView = findViewById( R.id.noticeRecycleView );

        list = new ArrayList <>(  );

        data();

        NoticeAdaptor noticeAdaptor = new NoticeAdaptor(getApplicationContext(),list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getApplicationContext() );
        linearLayoutManager.setOrientation( RecyclerView.VERTICAL);
        recyclerView.setLayoutManager( linearLayoutManager );
        recyclerView.setAdapter( noticeAdaptor );


    }

    private void data() {

        for (int i=0 ; i <=10; i++ ){
            list.add( new NoticeDataClass( "1/1/1111","kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk" +
                    "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk" +
                    "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                    "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq","Sports notice" ) );

            list.add( new NoticeDataClass("24/01/2086", "Material is the metaphorA material " +
                    "metaphor is the unifying theory of a rationalized space and a system of motion." +
                    "The material is grounded in tactile reality, inspired by the study of paper and ink, yet ", "Exam notice"));
    }}


}
