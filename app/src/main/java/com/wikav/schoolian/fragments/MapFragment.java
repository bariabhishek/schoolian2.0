package com.wikav.schoolian.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.wikav.schoolian.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MapFragment extends DialogFragment implements OnMapReadyCallback {

GoogleMap mMap;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
        initMap();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_for_map_fragment, container, false);

        return view;
    }

    void initMap()
    {
        SupportMapFragment mapFragment= (SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapFragment.this);
    }


}
