package com.dsheal.my_movies.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {
    private static MovieDataBase dataBase;
    private LiveData<List<Movie>> movies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        dataBase = MovieDataBase.getInstance(getApplication());
        movies = dataBase.movieDao().getAllMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    private Movie getMovieById(int id) {
        try {
            return new GetMovieTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void deleteAllMovies(){
        new DeleteAllMoviesTask().execute();
    }
    public void insertMovie(Movie movie){
        new InsertMovieTask().execute();
    }
    public void deleteMovie(Movie movie){
        new DeleteMovieTask().execute();
    }

    private static class GetMovieTask extends AsyncTask<Integer, Void, Movie> {
        @Override
        protected Movie doInBackground(Integer... integers) {
            if (integers != null && integers.length>0) {
                return dataBase.movieDao().getMovieById(integers[0]);
            }
            return null;
        }
    }
    private static class DeleteAllMoviesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... integers) {
                 dataBase.movieDao().deleteAllMovies();
            return null;
        }
    }
    private static class InsertMovieTask extends AsyncTask<Movie, Void, Void> {
        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length>0) {
                dataBase.movieDao().deleteMovie(movies[0]);
            }
            return null;
        }
    }
    private static class DeleteMovieTask extends AsyncTask<Movie, Void, Void> {
        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length>0) {
                dataBase.movieDao().insertMovie(movies[0]);
            }
            return null;
        }
    }
}
