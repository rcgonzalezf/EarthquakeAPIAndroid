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
import com.rcgonzalezf.android.earthquakessonora.responses.EarthQuakesSonoraMessage;
import com.rcgonzalezf.android.earthquakessonora.service.EarthQuakesSonoraRequest;

public class SonoraEarthquakesActivity extends BaseActivity {


    private EarthQuakesSonoraMessage message = null;
    private String TAG = SonoraEarthquakesActivity.class.getSimpleName();
    private TableLayout earthquakesTable;
    private String lastRequestCacheKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonora_earthquakes);

        earthquakesTable = (TableLayout) findViewById(R.id.earthquakes_table_layout);
        //getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO RCGF Double check with a map if the coordinates are correct
        EarthQuakesSonoraRequest request = new EarthQuakesSonoraRequest(getApplicationContext(),
                "31.329382", "26.376271", "-108.704123", "-112.412008", "sonoraEarthquakes", "20");
        lastRequestCacheKey = request.createCacheKey();
        getSpiceManager().execute(request, lastRequestCacheKey, 5 * DurationInMillis.ONE_MINUTE, new EarthQuakeRequestListener());

    }

    private void fillEarthQuakesTable() {

        for (EarthQuakesSonoraMessage.EarthQuakeInfo earthQuakeInfo : message.earthquakes) {
            TableRow earthquake = new TableRow(this);
            TextView magnitude = new TextView(this);
            TextView date = new TextView(this);
            TextView country = new TextView(this);
            ImageButton showMap = new ImageButton(this);

            magnitude.setText(String.valueOf(earthQuakeInfo.magnitude));
            date.setText(earthQuakeInfo.datetime);
            country.setText(earthQuakeInfo.address);
            showMap.setImageResource(R.drawable.ic_action_locate);
            showMap.setOnClickListener(new ShowMapOnClickListener(earthQuakeInfo.lat, earthQuakeInfo.lng, earthQuakeInfo.magnitude));

            earthquake.setGravity(Gravity.CENTER_VERTICAL);
            earthquake.addView(magnitude);
            earthquake.addView(date);
            //earthquake.addView(country);
            earthquake.addView(showMap);
            earthquakesTable.addView(earthquake);
        }

    }

    private class EarthQuakeRequestListener implements RequestListener<EarthQuakesSonoraMessage> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.d(TAG, "Earthquakes Request Failure", spiceException);
        }

        @Override
        public void onRequestSuccess(EarthQuakesSonoraMessage earthQuakesSonoraMessage) {
            message = earthQuakesSonoraMessage;
            fillEarthQuakesTable();
        }

    }

    private class ShowMapOnClickListener implements View.OnClickListener {

        private final double lng;
        private final double lat;
        private final double magnitude;

        public ShowMapOnClickListener(double lat, double lng, Double magnitude) {
            this.lat = lat;
            this.lng = lng;
            this.magnitude = magnitude;
        }

        @Override
        public void onClick(View v) {
            showMap(Uri.parse("geo:" + lat + "," + lng + "?q=" + lat + "," + lng + "(Magnitude: " + magnitude + ")&z=6"));
        }

        public void showMap(Uri geoLocation) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(geoLocation);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
}
