package com.example.cornelius.moviebuff.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.cornelius.moviebuff.Movie;
import com.example.cornelius.moviebuff.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Cornelius on 24/02/2018.
 */

public class NowShowingAdapter extends BaseAdapter{
    Context context;
    ArrayList<Movie> movie;
    NowShowingListener nowShowingListener;

    public NowShowingAdapter(Context context, ArrayList<Movie> movie, NowShowingListener nowShowingListener){
        this.context=context;
        this.movie=movie;
        this.nowShowingListener=nowShowingListener;
    }

    @Override
    public int getCount(){
        return movie.size();
    }

    @Override
    public Object getItem(int position){
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        View view = convertView;
        ViewHolder holder = null;

        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.now_showing_item, null);
            holder.imgItem = (ImageView)view.findViewById(R.id.image_film);
            holder.btnDetail = (Button)view.findViewById(R.id.btn_detail);

            view.setTag(holder);
        }else{
            holder=(ViewHolder)view.getTag();
        }

        Picasso.with(context).load("https://image.tmdb.org/t/p/w500"+movie.get(position).getMovie()).into(holder.imgItem);
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowShowingListener.onButtonDetailClick(position,movie);
            }
        });
        return view;
    }

    static class ViewHolder{
        ImageView imgItem;
        Button btnDetail;
        LinearLayout linearLayout;
    }

    public void refreshDataM(ArrayList<Movie> listTopMovie) {
        this.movie=listTopMovie;
        notifyDataSetChanged();
    }

}
