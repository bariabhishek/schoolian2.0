package com.wikav.schoolian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.wikav.schoolian.Adeptor.GallaryAdaptor;
import com.wikav.schoolian.DataClassSchoolian.GallarySetGet;

import java.util.ArrayList;
import java.util.List;

public class Gallary extends AppCompatActivity {

    RecyclerView recyclerView;
    List<GallarySetGet> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_gallary );

        recyclerView = findViewById( R.id.recyclerViewGallery );
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
