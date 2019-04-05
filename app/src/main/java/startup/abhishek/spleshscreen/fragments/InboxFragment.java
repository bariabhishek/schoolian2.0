package startup.abhishek.spleshscreen.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import java.util.ArrayList;

import startup.abhishek.spleshscreen.Adeptor.AdeptorJobSeeker;
import startup.abhishek.spleshscreen.Adeptor.JobSeeker;
import startup.abhishek.spleshscreen.Adeptor.ModelList;
import startup.abhishek.spleshscreen.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InboxFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<JobSeeker> arrayList;



    public InboxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_inbox, container, false );

        recyclerView = view.findViewById( R.id.recycleviewjobseeker );

        arrayList = new ArrayList <>(  );
        data();

        AdeptorJobSeeker adeptorJobSeeker = new AdeptorJobSeeker( getContext(),arrayList );

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

       // RecyclerView.LayoutManager layoutManager1 = layoutManager;

       // recyclerView.setLayoutManager( layoutManager1 );
        recyclerView.setHasFixedSize( true );
        recyclerView.setAdapter( adeptorJobSeeker );

        return  view;
    }

    private void data() {
        arrayList.add( new JobSeeker( R.drawable.logonewcolor,"workshop","500",R.drawable.ic_star_black_24dp) );
        arrayList.add( new JobSeeker( R.drawable.logonewcolor,"workshop","366",R.drawable.ic_star_black_24dp ) );
        arrayList.add( new JobSeeker( R.drawable.logonewcolor,"workshop","678",R.drawable.ic_star_black_24dp ) );
        arrayList.add( new JobSeeker( R.drawable.logonewcolor,"workshop","234",R.drawable.ic_star_black_24dp) );
    }

}
