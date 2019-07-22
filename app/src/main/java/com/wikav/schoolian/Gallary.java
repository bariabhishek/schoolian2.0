package com.wikav.schoolian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wikav.schoolian.Adeptor.GallaryAdaptor;
import com.wikav.schoolian.DataClassSchoolian.GallarySetGet;

import java.util.ArrayList;
import java.util.List;

public class Gallary extends AppCompatActivity {

    RecyclerView recyclerView;
    List<GallarySetGet> list;
    ImageView backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_gallary );

        backbtn=findViewById( R.id.back );
        recyclerView = findViewById( R.id.recyclerViewGallery );

        backbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );

        list = new ArrayList <>(  );

        GallaryAdaptor adaptor = new GallaryAdaptor(getApplicationContext(),list);

        data();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter( adaptor);


    }

    private void data() {
        for (int i = 0 ; i< 11 ; i ++)
            list.add(new GallarySetGet( R.drawable.sch) );
    }
}
