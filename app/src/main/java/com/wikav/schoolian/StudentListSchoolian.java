package com.wikav.schoolian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.wikav.schoolian.DataClassSchoolian.StudentListSetGet;
import com.wikav.schoolian.schoolianAdeptor.StudentListAdaptor;

import java.util.ArrayList;
import java.util.List;

public class StudentListSchoolian extends AppCompatActivity {
    RecyclerView recyclerView ;
    List<StudentListSetGet> list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_student_list_schoolian );

        recyclerView = findViewById( R.id.recyclerViewStudentListSchoolian );

        list = new ArrayList <>(  );

        data();
        StudentListAdaptor studentListAdaptor = new StudentListAdaptor(getApplicationContext(),list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getApplicationContext() );
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL );
        recyclerView.setLayoutManager( linearLayoutManager );
        recyclerView.setAdapter( studentListAdaptor );

    }

    private void data() {

        for(int i = 0 ; i<=10 ; i++)
        list.add( new StudentListSetGet( "Abhishek",R.drawable.logo,"Maths" ) );
    }
}
