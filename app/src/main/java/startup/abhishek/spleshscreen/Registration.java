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

import de.hdodenhof.circleimageview.CircleImageView;

public class Registration extends AppCompatActivity {

    CircleImageView circleImageView;
    Button signin;
    TextInputLayout username , userpassword,usermobile;
    EditText name , password,mobile;
    RadioGroup radioGroup;
    RadioButton male,female;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState);
        setContentView( R.layout.activity_resistration );

        circleImageView = findViewById( R.id.civ );
        name = findViewById( R.id.edittextname );
        name = findViewById( R.id.edittextMobile );
        password = findViewById( R.id.edittextpassword1 );
        signin = findViewById( R.id.login_btn);
        username = findViewById( R.id.etname );
        userpassword = findViewById( R.id.etEmail);
        usermobile = findViewById( R.id.etMobile);
        radioGroup = findViewById( R.id.redoigroup );
        male = findViewById( R.id.male );
        female = findViewById( R.id.female );
        signCondition();


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.male) {
                    circleImageView.setImageResource(R.drawable.boy);
                    Toast.makeText(getApplicationContext(), "choice: Male",
                            Toast.LENGTH_SHORT).show();
                } else if(checkedId == R.id.female) {
                    circleImageView.setImageResource(R.drawable.girl);
                    Toast.makeText(getApplicationContext(), "choice: Female",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void signCondition() {
        signin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.getText().toString().isEmpty() && password.getText().toString().isEmpty()&&mobile.getText().toString().isEmpty()){
                    username.setError( "not Valid" );

                }else {
                    if(password.length()>8){
                        userpassword.setError("minimum 8 character");
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
