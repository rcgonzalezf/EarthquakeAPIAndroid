package com.rcgonzalezf.android.earthquakessonora.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.rcgonzalezf.android.earthquakessonora.R;
import com.rcgonzalezf.android.earthquakessonora.adapters.EarthquakeListAdapter;
import com.rcgonzalezf.android.earthquakessonora.responses.EarthQuakesSonoraMessage;
import com.rcgonzalezf.android.earthquakessonora.service.EarthQuakesSonoraRequest;
import com.rcgonzalezf.android.earthquakessonora.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeListFragment extends BaseFragment {


    private String TAG = EarthquakeListFragment.class.getSimpleName();
    private String lastRequestCacheKey;
    private EarthQuakesSonoraMessage message = null;
    private ListView earthquakesListView;
    private List<EarthQuakesSonoraMessage.EarthQuakeInfo> earthquakes;
    private EarthquakeListAdapter earthquakeListAdapter;

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

        earthquakesListView = (ListView) containerView.findViewById(R.id.earthquakes_list_view);

        if (earthquakes == null) {
            earthquakes = new ArrayList<EarthQuakesSonoraMessage.EarthQuakeInfo>(Constants.DEFAULT_EARTHQUAKE_MAX_ROWS);
        }
        earthquakeListAdapter = new EarthquakeListAdapter(hostActivity, earthquakes);

        earthquakesListView.setAdapter(earthquakeListAdapter);

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
                "31.329382", "26.376271", "-108.704123", "-112.412008", "sonoraEarthquakes",
                Short.toString(Constants.DEFAULT_EARTHQUAKE_MAX_ROWS));
        lastRequestCacheKey = request.createCacheKey();
        getSpiceManager().execute(request, lastRequestCacheKey, Constants.APP_CACHE_DURATION, new EarthQuakeRequestListener());

    }


    private void fillEarthQuakesAdapter() {

        // For now I'm just filling the last 20 rows, later I will start controlling the
        // rows and doing a better job with this
        earthquakeListAdapter.clear();
        earthquakeListAdapter.addAll(message.earthquakes);
        earthquakeListAdapter.notifyDataSetChanged();

    }

    private class EarthQuakeRequestListener implements RequestListener<EarthQuakesSonoraMessage> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.d(TAG, "Earthquakes Request Failure", spiceException);
        }

        @Override
        public void onRequestSuccess(EarthQuakesSonoraMessage earthQuakesSonoraMessage) {
            message = earthQuakesSonoraMessage;
            fillEarthQuakesAdapter();
        }

    }

}
