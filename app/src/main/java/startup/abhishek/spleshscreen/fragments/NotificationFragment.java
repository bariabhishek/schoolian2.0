package startup.abhishek.spleshscreen.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import startup.abhishek.spleshscreen.Adeptor.AdeptorNotification;
import startup.abhishek.spleshscreen.Adeptor.DataForNotification;
import startup.abhishek.spleshscreen.Adeptor.DataModelFollower;
import startup.abhishek.spleshscreen.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<DataForNotification> arrayList;


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_notification, container, false );

        recyclerView =view.findViewById( R.id.notificationrecycleview );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()) );

        arrayList = new ArrayList <>(  );
        data();

        AdeptorNotification adeptorNotification = new AdeptorNotification(getActivity(),arrayList);

        recyclerView.setAdapter( adeptorNotification );




        return view;
    }

    private void data() {
        arrayList.add( new DataForNotification( R.drawable.logonewcolor,"shivam","tattu") );
        arrayList.add( new DataForNotification( R.drawable.logo,"amit","jhandu" ) );
        arrayList.add( new DataForNotification( R.drawable.sch,"rajat","kallu" ) );
        arrayList.add( new DataForNotification( R.drawable.logonewcolor,"vijay","pappu") );
    }

}
