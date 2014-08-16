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

        // TODO RCGF Double check with a map if the coordinates are correct
        EarthQuakesSonoraRequest request = new EarthQuakesSonoraRequest("31.329382","26.376271","-108.704123","-112.412008","sonoraEarthquakes");

        getSpiceManager().execute(request, new EarthQuakeRequestListener());
    }


    private class EarthQuakeRequestListener implements RequestListener<EarthQuakesSonoraMessage>
    {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.d(TAG, "Earthquakes Request Failure", spiceException);

        }

        @Override
        public void onRequestSuccess(EarthQuakesSonoraMessage earthQuakesSonoraMessage) {
            message = earthQuakesSonoraMessage;
        }

    }

}
