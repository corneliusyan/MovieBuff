package com.example.cornelius.moviebuff.ReviewPackage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Cornelius on 24/02/2018.
 */

public interface ReviewService {
    @GET("{movie_id}/reviews?")
    Call<ReviewModel> getReview(@Path("movie_id") int id, @Query("api_key") String api_key);
}
