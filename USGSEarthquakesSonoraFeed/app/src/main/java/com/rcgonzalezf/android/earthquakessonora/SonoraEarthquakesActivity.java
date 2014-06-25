package com.rcgonzalezf.android.earthquakessonora;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class SonoraEarthquakesActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonora_earthquakes);
        getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
