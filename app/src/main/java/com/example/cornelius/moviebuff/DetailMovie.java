package com.example.cornelius.moviebuff;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cornelius.moviebuff.ReviewPackage.ResultReview;
import com.example.cornelius.moviebuff.ReviewPackage.ReviewModel;
import com.example.cornelius.moviebuff.ReviewPackage.ReviewService;
import com.example.cornelius.moviebuff.Video.VideoModel;
import com.example.cornelius.moviebuff.Video.VideoService;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Cornelius on 24/02/2018.
 */

public class DetailMovie extends AppCompatActivity{
    private ImageView imgPoster,favorite;
    private TextView txtJudul,txtDate,txtRating,txtSynopsis;
    private Button btnTrailer,btnReview;
    private String poster,judul,date,synopsis;
    private double rating;
    private int id=0;
    private ListView rvReview;
    private LinearLayout layoutReview;
    ArrayList<String> listAuthor,listContent;
    CustomList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        imgPoster=(ImageView)findViewById(R.id.imgMovie_detail);
        txtJudul=(TextView)findViewById(R.id.txtMovie_judul);
        txtDate=(TextView)findViewById(R.id.txtMovie_release);
        txtRating=(TextView)findViewById(R.id.txtMovie_Rating);
        txtSynopsis=(TextView)findViewById(R.id.txtMovie_synopsis);
        btnReview=(Button)findViewById(R.id.btn_review);
        btnTrailer=(Button)findViewById(R.id.trailer);
        rvReview = (ListView)findViewById(R.id.rv_review);
        layoutReview = (LinearLayout)findViewById(R.id.layout_review);
        favorite=(ImageView)findViewById(R.id.favorite);

        Intent intent = getIntent();

        poster = intent.getStringExtra("poster");
        judul = intent.getStringExtra("judul");
        date = intent.getStringExtra("date");
        synopsis = intent.getStringExtra("synopsis");
        rating = intent.getDoubleExtra("rating",0);
        id = intent.getIntExtra("id",0);

        List<MovieFavourite> movieSearch = new Select()
                .from(MovieFavourite.class)
                .where(MovieFavourite_Table.id.is(id))
                .queryList();
        if (movieSearch.size() == 0){
            favorite.setImageResource(R.drawable.star_white);
        } else {
            favorite.setImageResource(R.drawable.star_yellow);
        }

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<MovieFavourite> movieSearch = new Select()
                        .from(MovieFavourite.class)
                        .where(MovieFavourite_Table.id.is(id))
                        .queryList();
                if (movieSearch.size() == 0){
                    MovieFavourite movieFav = new MovieFavourite();
                    movieFav.setId(id);
                    movieFav.setJudul(judul);
                    movieFav.setMovie(poster);
                    movieFav.setRating(rating);
                    movieFav.setSynopsis(synopsis);
                    movieFav.setDate(date);
                    movieFav.save();
                    favorite.setImageResource(R.drawable.star_yellow);
                    Toast.makeText(getApplicationContext(), "Added to Collection",Toast.LENGTH_SHORT).show();
                } else {
                    MovieFavourite movieFav = movieSearch.get(0);
                    movieFav.delete();
                    favorite.setImageResource(R.drawable.star_white);
                    Toast.makeText(getApplicationContext(), "Removed to Collection",Toast.LENGTH_SHORT).show();
                }
            }
        });
        listAuthor=new ArrayList<>();
        listContent = new ArrayList<>();

        adapter = new CustomList(DetailMovie.this,listAuthor,listContent,R.layout.list_item,R.id.text_news,R.id.text_subNews);
        rvReview.setAdapter(adapter);

        callReviewApi();

        layoutReview.setVisibility(View.GONE);

        rvReview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailNews.class);
                intent.putExtra("judulDetail",listAuthor.get(position));
                intent.putExtra("isiDetail",listContent.get(position));
                startActivity(intent);
            }
        });

        rvReview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        getSupportActionBar().setTitle(judul);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d("Image URL : ",poster);
        Picasso.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500"+poster).into(imgPoster);

        txtJudul.setText(judul);
        txtDate.setText(date);
        txtSynopsis.setText(synopsis);
        txtRating.setText(String.valueOf((int) rating));

        btnTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoService service = ApiHelper.client().create(VideoService.class);

                Call<VideoModel> call = service.getVideo(id,"1a97cf4b57348648d4de896f96a2a950");
                call.enqueue(new Callback<VideoModel>() {
                    @Override
                    public void onResponse(Call<VideoModel> call, Response<VideoModel> response) {
                        String videoKey = response.body().getResults().get(0).getKey();
                        String url = "http://www.youtube.com/watch?v="+videoKey;
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    }

                    @Override
                    public void onFailure(Call<VideoModel> call, Throwable t) {

                    }
                });

            }
        });

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnReview.setVisibility(View.GONE);
                layoutReview.setVisibility(View.VISIBLE);
            }
        });


    }

    private void callReviewApi() {
        //final ArrayList<String> listAuthor = new ArrayList<>();
        //final ArrayList<String> listContent = new ArrayList<>();


        ReviewService service = ApiHelper.client().create(ReviewService.class);

        Call<ReviewModel> call = service.getReview(id,"1a97cf4b57348648d4de896f96a2a950");
        call.enqueue(new Callback<ReviewModel>() {
            @Override
            public void onResponse(Call<ReviewModel> call, Response<ReviewModel> response) {
                List<ResultReview> listRevie = response.body().getResults();
                for (ResultReview rSult : listRevie){

                    String author = rSult.getAuthor();
                    String content = rSult.getContent();

                    listAuthor.add(author);
                    listContent.add(content);

                }
                adapter.refreshData(listAuthor,listContent);

            }

            @Override
            public void onFailure(Call<ReviewModel> call, Throwable t) {
                listAuthor.add("Not Found");
                listContent.add("Review Not Found");
                adapter.refreshData(listAuthor,listContent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);

    }
}

