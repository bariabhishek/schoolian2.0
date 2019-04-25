package startup.abhishek.spleshscreen;

import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserMobileNumber extends AppCompatActivity {

    TextInputLayout textInputLayout;
    EditText no;
    Button number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_user_mobile_number );
        textInputLayout = findViewById( R.id.etno );
        no = findViewById( R.id.editnumber );
        number = findViewById( R.id.nobtn );
        btnclick();
    }

    private void btnclick() {
        number.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(no.getText().toString().isEmpty()&&no.getText().toString().length()<10){
                    textInputLayout.setError( "Somthing Wrong" );
                }
                else {
                    Toast.makeText( getApplicationContext(),"succesful",Toast.LENGTH_LONG ).show();
                }
            }
        } );
    }
}
