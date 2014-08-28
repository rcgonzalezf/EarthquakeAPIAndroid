package com.rcgonzalezf.android.earthquakessonora;

import android.os.Bundle;

import com.rcgonzalezf.android.earthquakessonora.fragments.EarthquakeListFragment;
import com.crittercism.app.Crittercism;
import com.rcgonzalezf.android.earthquakessonora.utils.Constants;

public class SonoraEarthquakesActivity extends BaseActivity {

    private String TAG = SonoraEarthquakesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crittercism.initialize(getApplicationContext(), Constants.CRITTERCISM_APP_ID);
        setContentView(R.layout.main_layout);

        if (getSupportFragmentManager().findFragmentById(R.id.earthquakes_list_placeholder) == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.earthquakes_list_placeholder, EarthquakeListFragment.newInstance())
                    .commit();
        }

        //getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
