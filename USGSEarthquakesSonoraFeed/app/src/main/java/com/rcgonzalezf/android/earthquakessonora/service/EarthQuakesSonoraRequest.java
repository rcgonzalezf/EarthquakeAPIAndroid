package com.rcgonzalezf.android.earthquakessonora.service;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.rcgonzalezf.android.earthquakessonora.responses.EarthQuakesSonoraMessage;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class EarthQuakesSonoraRequest extends RetrofitSpiceRequest<EarthQuakesSonoraMessage, EarthQuakesSonoraService> {


    private final Context context;
    private String north;
    private String south;
    private String east;
    private String west;
    private String username;
    private String maxRows;

    public EarthQuakesSonoraRequest(Context applicationContext, String north, String south, String east, String west, String username, String maxRows) {
        super(EarthQuakesSonoraMessage.class, EarthQuakesSonoraService.class);
        this.context = applicationContext;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        this.username = username;
        this.maxRows = maxRows;
    }

    @Override
    public EarthQuakesSonoraMessage loadDataFromNetwork() {
        EarthQuakesSonoraMessage message = getService().earthQuakesList(north, south, east, west, username, maxRows);

        Geocoder geocoder
                = new Geocoder(context, Locale.getDefault());

        for (EarthQuakesSonoraMessage.EarthQuakeInfo earthQuakeInfo : message.earthquakes) {
            try {

                List<Address> addresses = geocoder.getFromLocation(earthQuakeInfo.lat, earthQuakeInfo.lng, 1);

                if (addresses.size() > 0) {
                    earthQuakeInfo.address =
                            geocoder.getFromLocation(earthQuakeInfo.lat, earthQuakeInfo.lng, 2).get(0).getCountryName();
                }

            } catch (IOException e) {
                Log.e(EarthQuakesSonoraRequest.class.getSimpleName(), "Problem retrieving address", e);
            }
        }

        return message;
    }

}
