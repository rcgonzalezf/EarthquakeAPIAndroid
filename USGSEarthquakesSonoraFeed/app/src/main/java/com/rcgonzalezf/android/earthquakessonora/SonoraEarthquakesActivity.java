package com.rcgonzalezf.android.earthquakessonora;

import android.os.Bundle;

public class SonoraEarthquakesActivity extends BaseActivity {

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
