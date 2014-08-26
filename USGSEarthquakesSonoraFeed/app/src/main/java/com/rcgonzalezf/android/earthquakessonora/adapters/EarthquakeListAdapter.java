package com.rcgonzalezf.android.earthquakessonora.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rcgonzalezf.android.earthquakessonora.R;
import com.rcgonzalezf.android.earthquakessonora.responses.EarthQuakesSonoraMessage;

import java.util.Collection;
import java.util.List;

public class EarthquakeListAdapter extends ArrayAdapter<EarthQuakesSonoraMessage.EarthQuakeInfo> {

    private final Context hostActivity;
    private final List<EarthQuakesSonoraMessage.EarthQuakeInfo> earthquakes;

    public EarthquakeListAdapter(Activity hostActivity, List<EarthQuakesSonoraMessage.EarthQuakeInfo> earthquakes) {
        super(hostActivity, R.layout.earthquake_row,
                earthquakes);
        this.hostActivity = hostActivity;
        this.earthquakes = earthquakes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View earthquakeRowView = convertView;

        if( earthquakeRowView == null ) {

            LayoutInflater inflater = (LayoutInflater) hostActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            earthquakeRowView = inflater.inflate(R.layout.earthquake_row, parent, false);
            ViewHolder viewHolder = new ViewHolder();

            viewHolder.magnitude = (TextView) earthquakeRowView.findViewById(R.id.eq_row_magnitude);
            viewHolder.datetime = (TextView) earthquakeRowView.findViewById(R.id.eq_row_datetime);
            viewHolder.showMap =  (ImageButton) earthquakeRowView.findViewById(R.id.eq_row_show_map_button);

            earthquakeRowView.setTag(viewHolder);
        }

        EarthQuakesSonoraMessage.EarthQuakeInfo earthQuakeInfo =  getItem(position);
        if(  earthQuakeInfo != null  ) {

            ViewHolder viewHolder = (ViewHolder) earthquakeRowView.getTag();

            viewHolder.magnitude.setText(Double.toString(earthQuakeInfo.magnitude));
            viewHolder.datetime.setText(earthQuakeInfo.datetime);
            viewHolder.showMap
                    .setOnClickListener(
                            new ShowMapOnClickListener(earthQuakeInfo.lat, earthQuakeInfo.lng, earthQuakeInfo.magnitude));
        }

        return earthquakeRowView;
    }

    /**
     * Add all elements in the collection to the end of the adapter.
     * @param list to add all elements
     */
    @SuppressLint("NewApi")
    public void addAll(Collection<? extends EarthQuakesSonoraMessage.EarthQuakeInfo> list) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            super.addAll(list);
        } else {
            for (EarthQuakesSonoraMessage.EarthQuakeInfo earthQuakeInfo : list) {
                super.add(earthQuakeInfo);
            }
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
                hostActivity.startActivity(intent);
            }
        }
    }

    static class ViewHolder{
        TextView magnitude;
        TextView datetime;
        ImageButton showMap;
    }
}
