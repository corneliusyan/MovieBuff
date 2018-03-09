package com.example.cornelius.moviebuff.ComSoon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Cornelius on 24/02/2018.
 */

public interface ComingService {
    @GET("upcoming?")
    Call<ComingSoonModel> getComingMovie(@Query("page") int page, @Query("api_key") String api_key);
}
