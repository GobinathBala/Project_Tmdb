package com.example.vijaymacnn.tmdb.Interface;

import com.example.vijaymacnn.tmdb.Model.MoviesList.Example;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RetroInterface {
    @GET("discover/movie")
    Call<Example> getMoviesList(@QueryMap Map<String, Object> options);
    @GET("movie/{ID}")
    Call<com.example.vijaymacnn.tmdb.Model.MovieDetail.Example> getMovieDetail(@Path(value = "ID", encoded = true) String ID, @Query("api_key") String api_key);
}
