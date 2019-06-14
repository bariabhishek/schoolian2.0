package com.wikav.schoolian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wikav.schoolian.DataClassSchoolian.ClassTimeTableSetGet;
import com.wikav.schoolian.schoolianAdeptor.ClassTimeTableAdaptor;

import java.util.ArrayList;
import java.util.List;

public class ClassTimeTableSchoolian extends AppCompatActivity {
    TextView sun,mon,tue,wed,thu,fri,sat;
    LinearLayout linearLayout;
    RecyclerView recyclerView ;
    List<ClassTimeTableSetGet> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_class_time_table_schoolian );

        initilisation();

        list = new ArrayList <>(  );

        data();
        ClassTimeTableAdaptor classTimeTableAdaptor = new ClassTimeTableAdaptor(getApplicationContext(),list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getApplicationContext() );
        linearLayoutManager.setOrientation( RecyclerView.VERTICAL );
        recyclerView.setLayoutManager( linearLayoutManager );
        recyclerView.setAdapter( classTimeTableAdaptor );


    }

    private void data() {

        for(int i = 0 ; i< 10 ; i++)
            list.add( new ClassTimeTableSetGet( R.drawable.logo,"10:00","11:00","English" ) );
    }

    private void initilisation() {

        recyclerView = findViewById( R.id.recyclerViewTimeTable );
        linearLayout = findViewById( R.id.lltimetable );

        sun = findViewById( R.id.sun );
        mon = findViewById( R.id.Mon );
        tue = findViewById( R.id.tue );
        wed = findViewById( R.id.wed );
        thu = findViewById( R.id.thu );
        fri = findViewById( R.id.fri );
        sat = findViewById( R.id.sat );

        sun.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                linearLayout.setBackgroundResource( R.drawable.background_time_table_cal );

                sun.setBackgroundResource( R.color.skyblue );
                mon.setBackgroundResource( R.color.colorWhite );
                tue.setBackgroundResource( R.color.colorWhite );
                wed.setBackgroundResource(  R.color.colorWhite );
                thu.setBackgroundResource(  R.color.colorWhite );
                fri.setBackgroundResource(  R.color.colorWhite );
                sat.setBackgroundResource(  R.color.colorWhite );

                sun.setTextColor( Color.WHITE );
                mon.setTextColor( Color.parseColor( "#039BE5" ) );
                tue.setTextColor( Color.parseColor( "#039BE5" ) );
                mon.setTextColor( Color.parseColor( "#039BE5" ) );
                thu.setTextColor( Color.parseColor( "#039BE5" ) );
                fri.setTextColor( Color.parseColor( "#039BE5" ) );
                sat.setTextColor( Color.parseColor( "#039BE5" ) );
               // mon.setTextColor(  R.color.colorWhite );
            }
        } );
        mon.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.setBackgroundResource( R.drawable.background_time_table_cal );

                mon.setBackgroundResource( R.color.skyblue );
                sun.setBackgroundResource(  R.color.colorWhite );
                tue.setBackgroundResource( R.color.colorWhite );
                wed.setBackgroundResource(  R.color.colorWhite );
                thu.setBackgroundResource(  R.color.colorWhite );
                fri.setBackgroundResource(  R.color.colorWhite );
                sat.setBackgroundResource( R.color.colorWhite );

                mon.setTextColor( Color.WHITE );
                sun.setTextColor( Color.parseColor( "#039BE5" ) );
                tue.setTextColor( Color.parseColor( "#039BE5" ) );
                wed.setTextColor( Color.parseColor( "#039BE5" ) );
                thu.setTextColor( Color.parseColor( "#039BE5" ) );
                fri.setTextColor( Color.parseColor( "#039BE5" ) );
                sat.setTextColor( Color.parseColor( "#039BE5" ) );

            }
        } );
        tue.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tue.setBackgroundResource( R.color.skyblue );
                sun.setBackgroundResource( R.color.colorWhite );
                mon.setBackgroundResource( R.color.colorWhite );
                wed.setBackgroundResource( R.color.colorWhite );
                thu.setBackgroundResource( R.color.colorWhite );
                fri.setBackgroundResource( R.color.colorWhite );
                sat.setBackgroundResource( R.color.colorWhite );

                tue.setTextColor( Color.WHITE );

                sun.setTextColor( Color.parseColor( "#039BE5" ) );
                mon.setTextColor( Color.parseColor( "#039BE5" ) );
                wed.setTextColor( Color.parseColor( "#039BE5" ) );
                thu.setTextColor( Color.parseColor( "#039BE5" ) );
                fri.setTextColor( Color.parseColor( "#039BE5" ) );
                sat.setTextColor( Color.parseColor( "#039BE5" ) );

            }
        } );
        wed.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wed.setBackgroundResource( R.color.skyblue );
                sun.setBackgroundResource( R.color.colorWhite );
                tue.setBackgroundResource( R.color.colorWhite);
                mon.setBackgroundResource( R.color.colorWhite );
                thu.setBackgroundResource(R.color.colorWhite );
                fri.setBackgroundResource( R.color.colorWhite );
                sat.setBackgroundResource(R.color.colorWhite );

                wed.setTextColor( Color.WHITE );
                sun.setTextColor( Color.parseColor( "#039BE5" ) );
                tue.setTextColor( Color.parseColor( "#039BE5" ) );
                mon.setTextColor( Color.parseColor( "#039BE5" ) );
                thu.setTextColor( Color.parseColor( "#039BE5" ) );
                fri.setTextColor( Color.parseColor( "#039BE5" ) );
                sat.setTextColor( Color.parseColor( "#039BE5" ) );
            }
        } );
        thu.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                thu.setBackgroundResource( R.color.skyblue );
                sun.setBackgroundResource( R.color.colorWhite );
                tue.setBackgroundResource( R.color.colorWhite );
                mon.setBackgroundResource( R.color.colorWhite );
                wed.setBackgroundResource( R.color.colorWhite );
                fri.setBackgroundResource( R.color.colorWhite );
                sat.setBackgroundResource( R.color.colorWhite);

                thu.setTextColor( Color.WHITE );
                sun.setTextColor( Color.parseColor( "#039BE5" ) );
                tue.setTextColor( Color.parseColor( "#039BE5" ) );
                wed.setTextColor( Color.parseColor( "#039BE5" ) );
                mon.setTextColor( Color.parseColor( "#039BE5" ) );
                fri.setTextColor( Color.parseColor( "#039BE5" ) );
                sat.setTextColor( Color.parseColor( "#039BE5" ) );

            }
        } );
        fri.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fri.setBackgroundResource( R.color.skyblue );
                sun.setBackgroundResource( R.color.colorWhite );
                tue.setBackgroundResource( R.color.colorWhite );
                mon.setBackgroundResource(R.color.colorWhite );
                wed.setBackgroundResource( R.color.colorWhite);
                thu.setBackgroundResource( R.color.colorWhite );
                sat.setBackgroundResource( R.color.colorWhite );

                fri.setTextColor( Color.WHITE );
                sun.setTextColor( Color.parseColor( "#039BE5" ) );
                tue.setTextColor( Color.parseColor( "#039BE5" ) );
                wed.setTextColor( Color.parseColor( "#039BE5" ) );
                thu.setTextColor( Color.parseColor( "#039BE5" ) );
                mon.setTextColor( Color.parseColor( "#039BE5" ) );
                sat.setTextColor( Color.parseColor( "#039BE5" ) );

            }
        } );

        sat.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sat.setBackgroundResource(R.color.skyblue );
                sun.setBackgroundResource( R.color.colorWhite );
                tue.setBackgroundResource( R.color.colorWhite );
                mon.setBackgroundResource( R.color.colorWhite );
                wed.setBackgroundResource( R.color.colorWhite );
                fri.setBackgroundResource( R.color.colorWhite );
                thu.setBackgroundResource(R.color.colorWhite );

                sat.setTextColor( Color.WHITE );
                sun.setTextColor( Color.parseColor( "#039BE5" ) );
                tue.setTextColor( Color.parseColor( "#039BE5" ) );
                wed.setTextColor( Color.parseColor( "#039BE5" ) );
                thu.setTextColor( Color.parseColor( "#039BE5" ) );
                fri.setTextColor( Color.parseColor( "#039BE5" ) );
                mon.setTextColor( Color.parseColor( "#039BE5" ) );

            }
        } );
    }
}
