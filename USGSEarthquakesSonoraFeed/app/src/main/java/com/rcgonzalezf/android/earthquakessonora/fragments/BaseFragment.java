package com.rcgonzalezf.android.earthquakessonora.fragments;


import android.app.Activity;
import android.support.v4.app.Fragment;

import com.octo.android.robospice.SpiceManager;
import com.rcgonzalezf.android.earthquakessonora.service.EarthquakesSonoraSpiceService;

public class BaseFragment extends Fragment {

    private static SpiceManager spiceManager = new SpiceManager(EarthquakesSonoraSpiceService.class);
    protected Activity hostActivity = getActivity();

    @Override
    public void onStart() {
        spiceManager.start(getActivity());
        super.onStart();
    }

    @Override
    public void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    protected static SpiceManager getSpiceManager() {
        return spiceManager;
    }
}
