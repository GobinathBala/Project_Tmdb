package com.example.vijaymacnn.tmdb.Interface;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFunctions {
    public static RetroInterface RetroInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RetroInterface.class);
    }

    public static Boolean isNetworkAvailable(Activity objActref) {
        ConnectivityManager conMgr =  (ConnectivityManager)objActref.getSystemService(Context.CONNECTIVITY_SERVICE);
        objActref.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr!=null){
            NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
            return netInfo != null;
        }
        return false;
    }
}
