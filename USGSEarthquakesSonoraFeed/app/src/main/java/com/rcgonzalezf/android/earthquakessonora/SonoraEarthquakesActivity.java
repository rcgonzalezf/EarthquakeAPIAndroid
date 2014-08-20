package com.rcgonzalezf.android.earthquakessonora;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.rcgonzalezf.android.earthquakessonora.responses.EarthQuakesSonoraMessage;
import com.rcgonzalezf.android.earthquakessonora.service.EarthQuakesSonoraRequest;

public class SonoraEarthquakesActivity extends BaseActivity {


    private EarthQuakesSonoraMessage message = null;
    private String TAG = SonoraEarthquakesActivity.class.getSimpleName();
    private TableLayout earthquakesTable;

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
                                                "31.329382","26.376271","-108.704123","-112.412008","sonoraEarthquakes");

        getSpiceManager().execute(request, new EarthQuakeRequestListener());
    }

    private void fillEarthQuakesTable(){

        for(EarthQuakesSonoraMessage.EarthQuakeInfo earthQuakeInfo : message.earthquakes )
        {
            TableRow earthquake = new TableRow(this);
            TextView magnitude = new TextView(this);
            TextView country = new TextView(this);
            Button showMap = new Button(this);

            magnitude.setText(String.valueOf( earthQuakeInfo.magnitude) );
            country.setText(earthQuakeInfo.address);
            showMap.setText("showMap");
            showMap.setOnClickListener(new ShowMapOnClickListener(earthQuakeInfo.lat, earthQuakeInfo.lng));

            earthquake.addView(magnitude);
            earthquake.addView(country);
            earthquake.addView(showMap);
            earthquakesTable.addView(earthquake);
        }

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
            fillEarthQuakesTable();
        }

    }

    private class ShowMapOnClickListener implements View.OnClickListener {

        private final double lng;
        private final double lat;

        public ShowMapOnClickListener(double lat, double lng){
            this.lat = lat;
            this.lng = lng;
        }
        @Override
        public void onClick(View v) {
            showMap(Uri.parse("geo:"+lat+","+lng+"?z=6") );
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
