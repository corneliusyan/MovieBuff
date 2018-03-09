package com.example.cornelius.moviebuff;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Cornelius on 24/02/2018.
 */

@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {
    public static final String NAME = "MyDataBase";

    public static final int VERSION = 1;
}
