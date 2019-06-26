package com.wikav.schoolian.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wikav.schoolian.AttendanceLayout;
import com.wikav.schoolian.ClassTimeTableSchoolian;
import com.wikav.schoolian.Holidays;
import com.wikav.schoolian.R;
import com.wikav.schoolian.Results;
import com.wikav.schoolian.SchoolianWorld;
import com.wikav.schoolian.SessionManger;
import com.wikav.schoolian.StudentListSchoolian;
import com.wikav.schoolian.TeacherList;
import com.wikav.schoolian.Syllabus;

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
        Intent intent;
        switch (v.getId())
        {
            case R.id.profileBtn:
                 intent = new Intent(getActivity(), Syllabus. class);
                startActivity(intent);

                break;
                case R.id.examBtn:
                    intent = new Intent(getActivity(), TeacherList. class);
                    startActivity(intent);

                    break;
                case R.id.resultBtn:
                     intent=new Intent(getActivity(), Results.class);
                    startActivity(intent);
                    break;
                case R.id.classRoutBtn:

                    intent=new Intent(getActivity(), ClassTimeTableSchoolian.class);
                    startActivity(intent);
                    break;
                case R.id.eventsBtn:
                    intent = new Intent(getActivity(), SchoolianWorld. class);
                    startActivity(intent);
                    break;
                case R.id.holidayBtn:

                    intent=new Intent(getActivity(), Holidays.class);
                    startActivity(intent);
                    ;
                break;
                case R.id.attendanceBtn:

                    intent=new Intent(getActivity(), AttendanceLayout.class);
                    startActivity(intent);
                break;
                case R.id.messageBtn:
                    intent=new Intent(getActivity(), StudentListSchoolian.class);
                    startActivity(intent);
                    break;
                case R.id.noticBtn:


                    break;
        }

    }
}
