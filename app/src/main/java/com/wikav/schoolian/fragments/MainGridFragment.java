package com.wikav.schoolian.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wikav.schoolian.R;
import com.wikav.schoolian.Results;
import com.wikav.schoolian.SessionManger;

import java.util.HashMap;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


public class MainGridFragment extends Fragment implements View.OnClickListener {

    Toolbar toolbar;
    SessionManger sessionManger;
    LinearLayout profile,exam,result,classRoutine,message, attendance,notice,event,holiday;
    public MainGridFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sessionManger=new SessionManger(getActivity());
        HashMap<String,String> getUser=sessionManger.getUserDetail();
        String SchlName=getUser.get(sessionManger.SCLNAME);
        View view = inflater.inflate( R.layout.activity_grid_home, container, false );
        toolbar=view.findViewById(R.id.homeToolbar);
        toolbar.setTitle(SchlName);
        toolbar.setTitleMarginStart(150);
        profile=view.findViewById(R.id.profileBtn);
        exam=view.findViewById(R.id.examBtn);
        result=view.findViewById(R.id.resultBtn);
        classRoutine=view.findViewById(R.id.classRoutBtn);
        message=view.findViewById(R.id.messageBtn);
        attendance=view.findViewById(R.id.attendanceBtn);
        notice=view.findViewById(R.id.noticBtn);
        holiday=view.findViewById(R.id.holidayBtn);
        event=view.findViewById(R.id.eventsBtn);
        profile.setOnClickListener(this);
        exam.setOnClickListener(this);
        result.setOnClickListener(this);
        classRoutine.setOnClickListener(this);
        message.setOnClickListener(this);
        attendance.setOnClickListener(this);
        notice.setOnClickListener(this);
        holiday.setOnClickListener(this);
        event.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.profileBtn:
                Toast.makeText(getActivity(), "profile", Toast.LENGTH_SHORT).show();

                break;
                case R.id.examBtn:
                    Toast.makeText(getActivity(), "exam", Toast.LENGTH_SHORT).show();;

                    break;
                case R.id.resultBtn:
                    Toast.makeText(getActivity(), "result", Toast.LENGTH_SHORT).show();;
                    Intent intent=new Intent(getActivity(), Results.class);
                    startActivity(intent);
                    break;
                case R.id.classRoutBtn:
                    Toast.makeText(getActivity(), "classRoutine", Toast.LENGTH_SHORT).show();;

                    break;
                case R.id.eventsBtn:
                    Toast.makeText(getActivity(), "events", Toast.LENGTH_SHORT).show();;

                    break;
                case R.id.holidayBtn:
                    Toast.makeText(getActivity(), "holiday", Toast.LENGTH_SHORT).show();;

                    ;
                break;
                case R.id.attendanceBtn:
                    Toast.makeText(getActivity(), "Attendence", Toast.LENGTH_SHORT).show();;

                break;
                case R.id.messageBtn:
                    Toast.makeText(getActivity(), "message", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.noticBtn:
                    Toast.makeText(getActivity(), "notice", Toast.LENGTH_SHORT).show();;

                    break;
        }

    }
}
