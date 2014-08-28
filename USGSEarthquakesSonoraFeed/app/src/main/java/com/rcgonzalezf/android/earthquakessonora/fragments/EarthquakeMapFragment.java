package com.rcgonzalezf.android.earthquakessonora.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;
import com.rcgonzalezf.android.earthquakessonora.R;

public class EarthquakeMapFragment extends SupportMapFragment {

    private static final String LAT = "LAT";
    private static final String LNG = "LNG";
    private static final String MAGNITUDE = "MAGNITUDE";

    public EarthquakeMapFragment newInstance(double lat, double lng, double magnitude)
    {
        EarthquakeMapFragment earthquakeMapFragment = new EarthquakeMapFragment();
        Bundle args = new Bundle();
        args.putDouble(LAT, lat);
        args.putDouble(LNG, lng);
        args.putDouble(MAGNITUDE, magnitude);
        earthquakeMapFragment.setArguments(args);

        return earthquakeMapFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.map_fragment_layout, container, false);
    }


}
