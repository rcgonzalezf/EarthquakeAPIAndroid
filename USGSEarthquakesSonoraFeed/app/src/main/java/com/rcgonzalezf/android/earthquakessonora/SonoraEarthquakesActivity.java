package com.rcgonzalezf.android.earthquakessonora;

import android.os.Bundle;
import android.util.Log;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.rcgonzalezf.android.earthquakessonora.responses.EarthQuakesSonoraMessage;
import com.rcgonzalezf.android.earthquakessonora.service.EarthQuakesSonoraRequest;

public class SonoraEarthquakesActivity extends BaseActivity {


    private EarthQuakesSonoraMessage message = null;
    private String TAG = SonoraEarthquakesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonora_earthquakes);
       //getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    @Override
    protected void onResume() {
        super.onResume();
        EarthQuakesSonoraRequest request = new EarthQuakesSonoraRequest("44.1","-9.9","-22.4","55.2","demo");


        getSpiceManager().execute(request, new RequestListener<EarthQuakesSonoraMessage>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                Log.d(TAG, "Earthquakes Request Failure", spiceException);

            }

            @Override
            public void onRequestSuccess(EarthQuakesSonoraMessage earthQuakesSonoraMessage) {
                message = earthQuakesSonoraMessage;
            }
        });
    }

}
