package com.example.cornelius.moviebuff.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;

import com.example.cornelius.moviebuff.ApiHelper;
import com.example.cornelius.moviebuff.ComSoon.ComingService;
import com.example.cornelius.moviebuff.ComSoon.ComingSoonModel;
import com.example.cornelius.moviebuff.ComSoon.ResultComingSoon;
import com.example.cornelius.moviebuff.DetailMovie;
import com.example.cornelius.moviebuff.Movie;
import com.example.cornelius.moviebuff.NowShow.NowService;
import com.example.cornelius.moviebuff.NowShow.NowShowingModel;
import com.example.cornelius.moviebuff.NowShow.ResultNowShowing;
import com.example.cornelius.moviebuff.R;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComingSoonFragment extends Fragment implements NowShowingListener {
    private GridView gv_comingSoon;

    ArrayList<Movie> listPosterComingSoon;
    NowShowingAdapter adapter;

    int count=0;
    int x;

    private SpotsDialog progressDialog;

    public ComingSoonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coming_soon, container, false);
        count=0;

        listPosterComingSoon = new ArrayList<>();

        gv_comingSoon = (GridView)view.findViewById(R.id.grid_coming_soon);
        progressDialog=new SpotsDialog(getContext(),"Loading Movies...");
        adapter=new NowShowingAdapter(getContext(),listPosterComingSoon,this);
        gv_comingSoon.setAdapter(adapter);
        callComingSoonApi();

        gv_comingSoon.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("scroll2",firstVisibleItem+" "+visibleItemCount+" "+totalItemCount+" "+count);
                if (count==1){
                    x=firstVisibleItem+visibleItemCount;
                }
                if(firstVisibleItem+visibleItemCount==totalItemCount&&totalItemCount!=0&&x*count<=totalItemCount){
                    callComingSoonApi();
                }
                if (x*count==totalItemCount){
                    progressDialog.dismiss();
                }
            }
        });

        return view;
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

    private void callComingSoonApi() {
        progressDialog.show();
        count = count+1;
        ComingService service = ApiHelper.client().create(ComingService.class);

        Call<ComingSoonModel> call = service.getComingMovie(count,"1a97cf4b57348648d4de896f96a2a950");

        call.enqueue(new Callback<ComingSoonModel>() {
            @Override
            public void onResponse(Call<ComingSoonModel> call, Response<ComingSoonModel> response) {
                List<ResultComingSoon> comList = response.body().getResults();

                for (ResultComingSoon rSult : comList){
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

                    listPosterComingSoon.add(movie);

                }
                adapter.refreshDataM(listPosterComingSoon);
            }

            @Override
            public void onFailure(Call<ComingSoonModel> call, Throwable t) {


            }
        });

    }

}

