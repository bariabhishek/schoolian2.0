package com.wikav.schoolian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.wikav.schoolian.DataClassSchoolian.Evants_holidays_SetGet;
import com.wikav.schoolian.schoolianAdeptor.Evants_holidays_Adaptor;

import java.util.ArrayList;
import java.util.List;

public class Events_Holidays extends AppCompatActivity {

    RecyclerView recyclerView ;
    List<Evants_holidays_SetGet> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_events__holidays );

        recyclerView = findViewById( R.id.recyclerViewEvants_holidays );
        list = new ArrayList <>(  );
        data();

        Evants_holidays_Adaptor evants_holidays_adaptor = new Evants_holidays_Adaptor(getApplicationContext(),list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getApplicationContext() );
        linearLayoutManager.setOrientation( RecyclerView.VERTICAL );
        recyclerView.setLayoutManager( linearLayoutManager );
        recyclerView.setAdapter( evants_holidays_adaptor );
    }

    private void data() {

        list.add( new Evants_holidays_SetGet( "","DIWALI","15OCT to 20 OCT" ) );
        list.add( new Evants_holidays_SetGet( "","HOLI","15OCT to 20 OCT" ) );
    }
}
