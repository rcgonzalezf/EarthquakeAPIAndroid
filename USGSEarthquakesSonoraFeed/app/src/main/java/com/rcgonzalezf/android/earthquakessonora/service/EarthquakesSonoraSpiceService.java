package com.rcgonzalezf.android.earthquakessonora.service;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

public class EarthquakesSonoraSpiceService extends RetrofitGsonSpiceService {

    private final static String BASE_URL = "http://api.geonames.org";

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(EarthQuakesSonoraService.class);
    }

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }
}
