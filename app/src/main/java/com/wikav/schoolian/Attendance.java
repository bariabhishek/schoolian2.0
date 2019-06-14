package com.wikav.schoolian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.wikav.schoolian.DataClassSchoolian.AttendanceSetGet;
import com.wikav.schoolian.schoolianAdeptor.AttendanceAdaptor;

import java.util.ArrayList;
import java.util.List;

public class Attendance extends AppCompatActivity {

    RecyclerView recyclerView ;
    List<AttendanceSetGet> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_attendance );
        recyclerView = findViewById( R.id.recyclerViewAttendance );

        list = new ArrayList <>(  );

        data();

        AttendanceAdaptor attendanceAdaptor = new AttendanceAdaptor(getApplicationContext(),list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getApplicationContext() );

        linearLayoutManager.setOrientation( RecyclerView.VERTICAL );
        recyclerView.setLayoutManager( linearLayoutManager );
        recyclerView.setAdapter( attendanceAdaptor );




    }

    private void data() {
        for(int i = 0 ; i < 10 ; i++ ){
            list.add( new AttendanceSetGet( "maths","20","A+" ) );
        list.add( new AttendanceSetGet( "Scienceevvv","200","A+" ) );
    }}
}

