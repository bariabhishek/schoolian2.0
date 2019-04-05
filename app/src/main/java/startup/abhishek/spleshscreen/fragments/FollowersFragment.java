package startup.abhishek.spleshscreen.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import startup.abhishek.spleshscreen.Adeptor.AdeptorFollower;
import startup.abhishek.spleshscreen.Adeptor.DataModelFollower;
import startup.abhishek.spleshscreen.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class FollowersFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<DataModelFollower> arrayList;


    public FollowersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_followers, container, false );

        recyclerView = view.findViewById( R.id.recycleviewfollower );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
         arrayList =new ArrayList<>(  );
         data();


        AdeptorFollower follower = new AdeptorFollower(getContext(),arrayList);
        recyclerView.setAdapter( follower );

        return view;
    }

    private void data() {
        arrayList.add( new DataModelFollower( R.drawable.logonewcolor,"shivam","tattu","unfollow") );
        arrayList.add( new DataModelFollower( R.drawable.logo,"amit","jhandu","unFollow" ) );
        arrayList.add( new DataModelFollower( R.drawable.sch,"rajat","kallu","unfollow" ) );
        arrayList.add( new DataModelFollower( R.drawable.logonewcolor,"vijay","pappu","unfollow") );
    }

}
