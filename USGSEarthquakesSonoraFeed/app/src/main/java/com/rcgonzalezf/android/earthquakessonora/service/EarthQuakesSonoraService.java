package com.rcgonzalezf.android.earthquakessonora.service;

import com.rcgonzalezf.android.earthquakessonora.responses.EarthQuakesSonoraMessage;

import retrofit.http.GET;
import retrofit.http.Path;

public interface EarthQuakesSonoraService {

    @GET("?north={north}&south={south}&east={east}&west={west}&username={username}")
    EarthQuakesSonoraMessage empresaMovilInfo(@Path("north") String north, @Path("south") String south, @Path("east") String east,
                                         @Path("west") String west, @Path("username")  String username);

}
