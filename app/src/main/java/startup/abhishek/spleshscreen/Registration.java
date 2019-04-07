package startup.abhishek.spleshscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Registration extends AppCompatActivity {

    CircleImageView circleImageView;
    Button signin;
    TextInputLayout username , userpassword;
    EditText name , password;
    RadioGroup radioGroup;
    RadioButton male,female;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState);
        setContentView( R.layout.activity_resistration );

        circleImageView = findViewById( R.id.civ );
        name = findViewById( R.id.edittextname );
        password = findViewById( R.id.edittextpassword1 );
        signin = findViewById( R.id.login_btn );
        username = findViewById( R.id.etname );
        userpassword = findViewById( R.id.etpassword );
        radioGroup = findViewById( R.id.redoigroup );
        male = findViewById( R.id.male );
        female = findViewById( R.id.female );
        signCondition();


        final String value = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId()))
                        .getText().toString();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Toast.makeText(getBaseContext(), value, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signCondition() {
        signin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.getText().toString().isEmpty() && password.getText().toString().isEmpty()){
                    username.setError( "not Valid" );

                }else {
                    if(password.length()>8){
                        Toast.makeText( getApplicationContext(),"minimum 8 character",Toast.LENGTH_LONG ).show();
                    }
                    else {
                        Toast.makeText( getApplicationContext(),"Succesful",Toast.LENGTH_LONG ).show();
                    }
                }

            }
        } );
    }
}
