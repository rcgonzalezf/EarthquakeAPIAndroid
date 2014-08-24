package com.rcgonzalezf.android.earthquakessonora.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.rcgonzalezf.android.earthquakessonora.R;
import com.rcgonzalezf.android.earthquakessonora.responses.EarthQuakesSonoraMessage;
import com.rcgonzalezf.android.earthquakessonora.service.EarthQuakesSonoraRequest;

public class EarthquakeListFragment extends BaseFragment {


    private String TAG = EarthquakeListFragment.class.getSimpleName();
    private String lastRequestCacheKey;
    private EarthQuakesSonoraMessage message = null;
    private TableLayout earthquakesTable;

    public static EarthquakeListFragment newInstance() {
        EarthquakeListFragment fragment = new EarthquakeListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        hostActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View containerView = inflater.inflate(R.layout.activity_sonora_earthquakes, container, false);

        earthquakesTable = (TableLayout) containerView.findViewById(R.id.earthquakes_table_layout);

        return containerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        // TODO RCGF Double check with a map if the coordinates are correct
        EarthQuakesSonoraRequest request = new EarthQuakesSonoraRequest(hostActivity.getApplicationContext(),
                "31.329382", "26.376271", "-108.704123", "-112.412008", "sonoraEarthquakes", "20");
        lastRequestCacheKey = request.createCacheKey();
        getSpiceManager().execute(request, lastRequestCacheKey, 5 * DurationInMillis.ONE_MINUTE, new EarthQuakeRequestListener());

    }


    private void fillEarthQuakesTable() {

        for (EarthQuakesSonoraMessage.EarthQuakeInfo earthQuakeInfo : message.earthquakes) {
            TableRow earthquake = new TableRow(hostActivity);
            TextView magnitude = new TextView(hostActivity);
            TextView date = new TextView(hostActivity);
            TextView country = new TextView(hostActivity);
            ImageButton showMap = new ImageButton(hostActivity);

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
            if (intent.resolveActivity(hostActivity.getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

}
