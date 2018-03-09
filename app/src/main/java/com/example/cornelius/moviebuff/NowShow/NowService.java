package com.example.cornelius.moviebuff.NowShow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Cornelius on 24/02/2018.
 */

public interface NowService {
    @GET("now_playing?")
    Call<NowShowingModel> getNowMovie(@Query("page") int page, @Query("api_key") String api_key);
}
