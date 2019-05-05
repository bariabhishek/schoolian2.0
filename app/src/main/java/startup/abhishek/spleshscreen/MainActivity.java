package startup.abhishek.spleshscreen;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ExampleDialog.ExampleDilogeListner {

    TextView name,nmber;
    Button open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        name = findViewById( R.id.name );
        nmber = findViewById( R.id.nuber );
        open  = findViewById( R.id.opendiloag );


        open.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendilog();
            }
        } );
    }

    @Override
    public void applyTexts(String username, String number) {
        name.setText( username );
        nmber.setText( number );
    }

    private void opendilog() {

        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show( getSupportFragmentManager(),"exampleDiloge" );
    }

}

