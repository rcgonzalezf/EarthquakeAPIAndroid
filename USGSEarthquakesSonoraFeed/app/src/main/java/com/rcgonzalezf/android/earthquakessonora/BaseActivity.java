package com.rcgonzalezf.android.earthquakessonora;

import android.support.v7.app.ActionBarActivity;

import com.octo.android.robospice.SpiceManager;
import com.rcgonzalezf.android.earthquakessonora.service.EarthquakesSonoraSpiceService;

public class BaseActivity extends ActionBarActivity {

    private static SpiceManager spiceManager = new SpiceManager(EarthquakesSonoraSpiceService.class);

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    protected static SpiceManager getSpiceManager() {
        return spiceManager;
    }
}
