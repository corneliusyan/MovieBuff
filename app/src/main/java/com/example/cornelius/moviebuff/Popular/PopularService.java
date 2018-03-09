package com.example.cornelius.moviebuff.Popular;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Cornelius on 24/02/2018.
 */

public interface PopularService {
    @GET("popular?")
    Call<PopularModel> getPopularMovie(@Query("page") int page, @Query("api_key") String api_key);
}

