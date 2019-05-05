package startup.abhishek.spleshscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MobileNumber extends AppCompatActivity {
EditText editText ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number);

    }

    public void  sendToOtp(View view){

        Intent i = new Intent(this,OTP.class);
        i.putExtra("mobile","8952896895");
        startActivity(i);
    }
}
