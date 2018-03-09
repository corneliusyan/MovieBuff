package com.example.cornelius.moviebuff;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Cornelius on 24/02/2018.
 */

public class CustomList extends ArrayAdapter<String> {
    private final Activity activity;
    private ArrayList<String> judul=new ArrayList<>();
    private ArrayList<String> subJudul=new ArrayList<>();
    private final int layout;
    private final int txtNews;
    private final int txtSubNews;

    public CustomList(Activity activity,ArrayList<String> judulX,ArrayList<String> subJudulX,int layout,int txtNews,int txtSubNews){
        super(activity,R.layout.list_item,judulX);
        this.activity=activity;
        this.judul=judulX;
        this.subJudul=subJudulX;
        this.layout=layout;
        this.txtNews=txtNews;
        this.txtSubNews=txtSubNews;
    }

    public CustomList(Activity activity,String[] judulX,String[] subJudulX,int layout,int txtNews,int txtSubNews){
        super(activity,R.layout.list_item,judulX);
        this.activity=activity;
        for (int i=0;i<judulX.length;i++){
            this.judul.add(judulX[i]);
        }
        //this.subJudul=subJudul;
        for (int j=0;j<subJudulX.length;j++){
            this.subJudul.add(subJudulX[j]);
        }
        this.layout=layout;
        this.txtNews=txtNews;
        this.txtSubNews=txtSubNews;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater=activity.getLayoutInflater();
        View rowView = inflater.inflate(layout,null,false);
        TextView textJudul=(TextView)rowView.findViewById(txtNews);
        TextView textIsi=(TextView)rowView.findViewById(txtSubNews);

        textJudul.setText(judul.get(position));

        if(subJudul.get(position).length()>80){
            String potong="";
            for(int i=0; i<80;i++){
                potong=potong+subJudul.get(position).charAt(i);
            }
            potong=potong+"...";
            textIsi.setText(potong);
        }else{
            textIsi.setText(subJudul.get(position));
        }

        return rowView;

    }

    public void refreshData(ArrayList<String> listAuthor,ArrayList<String> listContent) {
        this.judul=listAuthor;
        this.subJudul=listContent;
        notifyDataSetChanged();
    }
}

