package com.dsheal.my_movies.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Movie.class, version = 1, exportSchema = false)
public abstract class MovieDataBase extends RoomDatabase {
    private static MovieDataBase database;
    private static final String DB_NAME="movies.db";
    private static final Object LOCK = new Object();

    public static MovieDataBase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, MovieDataBase.class, DB_NAME).build();
            }
            return database;
        }
    }

    public abstract MovieDao movieDao();

}
