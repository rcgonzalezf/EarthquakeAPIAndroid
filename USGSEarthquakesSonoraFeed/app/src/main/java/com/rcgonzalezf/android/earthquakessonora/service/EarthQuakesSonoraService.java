package com.rcgonzalezf.android.earthquakessonora.service;

import com.rcgonzalezf.android.earthquakessonora.responses.EarthQuakesSonoraMessage;

import retrofit.http.GET;
import retrofit.http.Query;

public interface EarthQuakesSonoraService {

    @GET("/earthquakesJSON")
    EarthQuakesSonoraMessage earthQuakesList(@Query("north") String north, @Query("south") String south, @Query("east") String east,
                                         @Query("west") String west, @Query("username")  String username);

}
