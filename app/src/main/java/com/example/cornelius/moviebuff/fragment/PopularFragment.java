package com.example.cornelius.moviebuff.fragment;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;

import com.example.cornelius.moviebuff.ApiHelper;
import com.example.cornelius.moviebuff.DetailMovie;
import com.example.cornelius.moviebuff.Movie;
import com.example.cornelius.moviebuff.Popular.PopularModel;
import com.example.cornelius.moviebuff.Popular.PopularService;
import com.example.cornelius.moviebuff.Popular.ResultPopular;
import com.example.cornelius.moviebuff.R;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

/**
 * Created by Cornelius on 24/02/2018.
 */

public class PopularFragment extends Fragment implements NowShowingListener{
    private GridView gvPopular;
    NowShowingAdapter adapter;

    ArrayList<Movie> listPopular;
    int count = 0;
    int x;

    private SpotsDialog progressDialog;

    public PopularFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_popular, container, false);
        count=0;

        listPopular=new ArrayList<>();

        gvPopular = (GridView)view.findViewById(R.id.grid_popular);
        progressDialog=new SpotsDialog(getContext(),"Loading Movies...");
        adapter =new NowShowingAdapter(getContext(),listPopular,this);
        gvPopular.setAdapter(adapter);
        callPopularApi();

        gvPopular.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("scroll3",firstVisibleItem+" "+visibleItemCount+" "+totalItemCount+" "+count);
                if (count==1){
                    x=firstVisibleItem+visibleItemCount;
                }
                if (firstVisibleItem+visibleItemCount==totalItemCount&&totalItemCount!=0&&x*count<=totalItemCount){
                    callPopularApi();
                }
                if (x*count==totalItemCount){
                    progressDialog.dismiss();
                }
            }
        });

        return view;
    }




    private void callPopularApi() {
        progressDialog.show();
        count=count+1;
        Log.d("page",String.valueOf(count));
        PopularService service = ApiHelper.client().create(PopularService.class);

        Call<PopularModel> call = service.getPopularMovie(count,"1a97cf4b57348648d4de896f96a2a950");
        call.enqueue(new Callback<PopularModel>() {
            @Override
            public void onResponse(Call<PopularModel> call, Response<PopularModel> response) {
                List<ResultPopular> ratList = response.body().getResults();

                for (ResultPopular rSult : ratList){
                    String date = rSult.getReleaseDate();
                    String poster = rSult.getPosterPath();
                    String synopsis = rSult.getOverview();
                    String judul = rSult.getTitle();
                    double rating = rSult.getVoteAverage();
                    int id = rSult.getId();

                    Movie movie = new Movie();
                    movie.setId(id);
                    movie.setDate(date);
                    movie.setMovie(poster);
                    movie.setSynopsis(synopsis);
                    movie.setJudul(judul);
                    movie.setRating(rating);

                    listPopular.add(movie);

                }
                adapter.refreshDataM(listPopular);


            }

            @Override
            public void onFailure(Call<PopularModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void onButtonDetailClick(int position,ArrayList<Movie> listPosterComingSoon){
        Intent intent =new Intent(getActivity(), DetailMovie.class);
        intent.putExtra("poster",listPosterComingSoon.get(position).getMovie());
        intent.putExtra("judul",listPosterComingSoon.get(position).getJudul());
        intent.putExtra("date",listPosterComingSoon.get(position).getDate());
        intent.putExtra("synopsis",listPosterComingSoon.get(position).getSynopsis());
        intent.putExtra("rating",listPosterComingSoon.get(position).getRating());
        intent.putExtra("id",listPosterComingSoon.get(position).getId());
        startActivity(intent);
    }
}
