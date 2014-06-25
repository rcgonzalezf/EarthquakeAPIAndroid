package com.rcgonzalezf.android.earthquakessonora.service;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.rcgonzalezf.android.earthquakessonora.responses.EarthQuakesSonoraMessage;

public class EarthQuakesSonoraRequest extends RetrofitSpiceRequest<EarthQuakesSonoraMessage, EarthQuakesSonoraService> {


    private String north;
    private String south;
    private String east;
    private String west;
    private String username;

    public EarthQuakesSonoraRequest(String north, String south, String east, String west, String username) {
        super(EarthQuakesSonoraMessage.class, EarthQuakesSonoraService.class);
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        this.username = username;
    }

    @Override
    public EarthQuakesSonoraMessage loadDataFromNetwork() {
        return getService().empresaMovilInfo(north,south,east,west,username);
    }

}
