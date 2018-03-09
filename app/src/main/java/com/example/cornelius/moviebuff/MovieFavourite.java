package com.example.cornelius.moviebuff;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Cornelius on 24/02/2018.
 */

@Table( database = MyDatabase.class)
public class MovieFavourite extends BaseModel {

    @Column
    @PrimaryKey
    int id;
    @Column
    String movie;
    @Column
    String judul;
    @Column
    String synopsis;
    @Column
    String date;
    @Column
    Double rating;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getMovie(){
        return movie;
    }

    public void setMovie(String movie){
        this.movie=movie;
    }

    public String getJudul(){
        return judul;
    }

    public void setJudul(String judul){
        this.judul=judul;
    }

    public String getSynopsis(){
        return synopsis;
    }

    public void setSynopsis(String synopsis){
        this.synopsis=synopsis;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date=date;
    }

    public Double getRating(){
        return rating;
    }

    public void setRating(double rating){
        this.rating=rating;
    }

}

