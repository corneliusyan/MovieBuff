package com.example.cornelius.moviebuff.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;

import com.example.cornelius.moviebuff.ApiHelper;
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

/**
 * Created by Cornelius on 24/02/2018.
 */

public class NowShowingFragment extends Fragment implements NowShowingListener{
    private GridView gv_nowShowing;

    ArrayList<Movie> listPosterMovie;
    NowShowingAdapter adapter;
    int x= 0;
    int count=0;
    int y;

    private SpotsDialog progressDialog;

    public NowShowingFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_now_showing, container, false);
        count=0;

        listPosterMovie=new ArrayList<>();
        gv_nowShowing=(GridView)view.findViewById(R.id.grid_now_showing);
        progressDialog=new SpotsDialog(getContext(),"Loading Movies...");
        adapter=new NowShowingAdapter(getContext(),listPosterMovie,this);
        gv_nowShowing.setAdapter(adapter);

        callNowShowingApi();

        gv_nowShowing.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(count==1){
                    x=totalItemCount;
                }
                if (firstVisibleItem+visibleItemCount==totalItemCount&&totalItemCount!=0&&x*count<=totalItemCount){
                    callNowShowingApi();
                }
                if (x*count==totalItemCount){
                    progressDialog.dismiss();
                }
            }
        });
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
    private void callNowShowingApi(){
        progressDialog.show();

        count=count+1;
        NowService service = ApiHelper.client().create(NowService.class);
        Call<NowShowingModel> call = service.getNowMovie(count,"1a97cf4b57348648d4de896f96a2a950");

        call.enqueue(new Callback<NowShowingModel>() {
            @Override
            public void onResponse(Call<NowShowingModel> call, Response<NowShowingModel> response) {
                List<ResultNowShowing> nowList = response.body().getResults();
                for (ResultNowShowing rSult : nowList){
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

                    listPosterMovie.add(movie);
                }
                adapter.refreshDataM(listPosterMovie);
            }
            @Override
            public void onFailure(Call<NowShowingModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

}
