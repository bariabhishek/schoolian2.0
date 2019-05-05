package startup.abhishek.spleshscreen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import startup.abhishek.spleshscreen.Adeptor.CommentedAdaptor;
import startup.abhishek.spleshscreen.Adeptor.DataOFComment;

public class Commented extends AppCompatActivity {

    RecyclerView recyclerView;
    List <DataOFComment> commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_commented );

        recyclerView = findViewById( R.id.commentedRecycleview );

        commentList = new ArrayList <>(  );

        data();
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()) );
        CommentedAdaptor commentedAdaptor = new CommentedAdaptor( getApplicationContext(),commentList );


        recyclerView.setAdapter( commentedAdaptor );


    }

    private void data() {
        for (int i=0 ; i<=10 ; i++ )
        commentList.add( new DataOFComment( R.drawable.boy,"abhishek","ye Comment ki textting chal rhai h" ) );
    }
}
