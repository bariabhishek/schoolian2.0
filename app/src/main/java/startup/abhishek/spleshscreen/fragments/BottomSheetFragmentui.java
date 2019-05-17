package startup.abhishek.spleshscreen.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import startup.abhishek.spleshscreen.R;
import startup.abhishek.spleshscreen.SendSms;
import startup.abhishek.spleshscreen.SessionManger;

public class BottomSheetFragmentui extends BottomSheetDialogFragment {
    ImageView call,message ;
    String Otp,name,number,my_number,my_name;
    TextView OTP,tvName;
    SessionManger sessionManger;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.bottom_sheet_layout,container,false );
sessionManger=new SessionManger(getActivity());
HashMap<String,String> get=sessionManger.getUserDetail();
        my_number=get.get(sessionManger.MOBILE);
        my_name=get.get(sessionManger.NAME);
        call = view.findViewById( R.id.civcall );
        tvName = view.findViewById( R.id.jobSeekerBottomsheet );
        OTP = view.findViewById( R.id.OtpBottom);
        message = view.findViewById( R.id.civmessage );
        Otp=getArguments().getString("otp");
        name=getArguments().getString("seekerName");
        number=getArguments().getString("seekerMobile");
        tvName.setText("Contact with "+name);
        OTP.setText(Otp);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                startActivity(intent);
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
            }
        });
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 7)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
        return view;
    }

    private void sendSms() {
        String msg="Hello "+name+", I'm happy to accepting your deal and I'm very glad to you for interest to complete this task. contact detail- "+my_name+", "+my_number;
        SendSms sendSms=new SendSms(number,msg);
        sendSms.send();
        dismiss();
    }


}

