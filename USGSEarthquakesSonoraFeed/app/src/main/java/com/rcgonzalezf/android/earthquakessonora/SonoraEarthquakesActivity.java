package com.rcgonzalezf.android.earthquakessonora;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.rcgonzalezf.android.earthquakessonora.fragments.EarthquakeListFragment;
import com.rcgonzalezf.android.earthquakessonora.responses.EarthQuakesSonoraMessage;
import com.rcgonzalezf.android.earthquakessonora.service.EarthQuakesSonoraRequest;

public class SonoraEarthquakesActivity extends BaseActivity {

    private String TAG = SonoraEarthquakesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        if( getSupportFragmentManager().findFragmentById(R.id.earthquakes_list_placeholder) == null )
        {
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
