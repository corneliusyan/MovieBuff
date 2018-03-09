package com.example.cornelius.moviebuff.fragment;

import com.example.cornelius.moviebuff.Movie;

import java.util.ArrayList;

/**
 * Created by Cornelius on 24/02/2018.
 */

public interface NowShowingListener {
    void onButtonDetailClick(int position, ArrayList<Movie> movie);
}
