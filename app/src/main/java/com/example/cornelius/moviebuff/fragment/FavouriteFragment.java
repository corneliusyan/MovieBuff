package com.example.cornelius.moviebuff.fragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.cornelius.moviebuff.DetailMovie;
import com.example.cornelius.moviebuff.Movie;
import com.example.cornelius.moviebuff.MovieFavourite;
import com.example.cornelius.moviebuff.R;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.List;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

/**
 * Created by Cornelius on 24/02/2018.
 */

public class FavouriteFragment extends Fragment implements NowShowingListener{
    private GridView gvFavourite;
    private ArrayList<Movie> listFavourite;
    NowShowingAdapter adapter;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        gvFavourite=(GridView)view.findViewById(R.id.grid_favourite);

        listFavourite=new ArrayList<>();

        adapter = new NowShowingAdapter(getContext(),listFavourite,this);

        gvFavourite.setAdapter(adapter);

        loadDatabase();


        return view;
    }

    public void onButtonDetailClick(int position,ArrayList<Movie> movie){
        Intent intent =new Intent(getActivity(), DetailMovie.class);
        intent.putExtra("poster",movie.get(position).getMovie());
        intent.putExtra("judul",movie.get(position).getJudul());
        intent.putExtra("date",movie.get(position).getDate());
        intent.putExtra("synopsis",movie.get(position).getSynopsis());
        intent.putExtra("rating",movie.get(position).getRating());
        intent.putExtra("id",movie.get(position).getId());
        startActivity(intent);
    }

    private void loadDatabase() {
        List<MovieFavourite> movieSearch = new Select()
                .from(MovieFavourite.class)
                .queryList();
        Integer x = movieSearch.size();
        for (MovieFavourite movieFav : movieSearch) {
            Movie movie = new Movie();
            movie.setId(movieFav.getId());
            movie.setRating(movieFav.getRating());
            movie.setDate(movieFav.getDate());
            movie.setJudul(movieFav.getJudul());
            movie.setSynopsis(movieFav.getSynopsis());
            movie.setMovie(movieFav.getMovie());
            listFavourite.add(movie);
        }
        adapter.refreshDataM(listFavourite);

    }


}
