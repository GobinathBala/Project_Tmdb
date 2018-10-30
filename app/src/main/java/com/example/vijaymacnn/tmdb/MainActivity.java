package com.example.vijaymacnn.tmdb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.vijaymacnn.tmdb.Adapter.Adapter_MovieList;
import com.example.vijaymacnn.tmdb.Interface.Interface_Adap;
import com.example.vijaymacnn.tmdb.Interface.RetroFunctions;
import com.example.vijaymacnn.tmdb.Model.MoviesList.Example;
import com.example.vijaymacnn.tmdb.Model.MoviesList.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Interface_Adap{

    public Boolean isLoading = false;
    Adapter_MovieList objAdapt_MovieList;
    List<Result> objAryMovieList;
    RecyclerView objRecyler_movieList;
    ProgressDialog progress;
    private int lastVisibleItem, totalItemCount,totalcount,page=1;
    private LinearLayoutManager objLinearManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = new ProgressDialog(this);
        declareToolbar();
        setAdapterContent();
        getMoviesList();
    }

    private void setAdapterContent() {
        objAryMovieList=new ArrayList<>();
        objAdapt_MovieList=new Adapter_MovieList(this,objAryMovieList,this);
        objRecyler_movieList=findViewById(R.id.Id_movie_list_recyler);
        objLinearManager=new LinearLayoutManager(this);
        objRecyler_movieList.setLayoutManager(objLinearManager);
        objRecyler_movieList.setAdapter(objAdapt_MovieList);
        objRecyler_movieList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (objLinearManager != null) {
                    totalItemCount = objLinearManager.getItemCount();
                    lastVisibleItem = objLinearManager.findLastVisibleItemPosition();
                    if (!isLoading && totalItemCount == (lastVisibleItem + 1) && totalItemCount >= 2) {
                        if (totalItemCount < totalcount && RetroFunctions.isNetworkAvailable(MainActivity.this)) {
                            isLoading = true;
                            page++;
                            getMoviesList();
                        }
                    }
                    }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private void declareToolbar() {
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    private void getMoviesList() {
        Map<String,Object> objParams=new HashMap<>();
        objParams.put("api_key",getResources().getString(R.string.api_key));
        objParams.put("sort_by","popularity.desc");
        objParams.put("page",page);
        showload();
        RetroFunctions.RetroInstance().getMoviesList(objParams).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(@NonNull Call<Example> call, @NonNull Response<Example> response) {
                if (response.isSuccessful()){
                    if(isloading()){
                        HideLoading();
                    }
                    Example objBody=response.body();
                    if (objBody!=null){
                        if (objBody.getResult()!=null){
                            if (objBody.getResult().size()>0){
                                objAryMovieList.addAll(objBody.getResult());
                                objAdapt_MovieList.notifyDataSetChanged();
                                totalcount=objBody.getTotalResults();
                                isLoading=false;
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Example> call, @NonNull Throwable t) {
                if(isloading()){
                    HideLoading();
                }
            }
        });
    }

    @Override
    public void onClicked(int position) {
        if (objAryMovieList.get(position)!=null){
            String ID=objAryMovieList.get(position).getId().toString();
            Intent objIntent=new Intent(this,DetailPage.class);
            objIntent.putExtra("id",ID);
            objIntent.putExtra("title",objAryMovieList.get(position).getTitle());
            startActivity(objIntent);
        }
    }

    public void showload() {
        progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progress.setCanceledOnTouchOutside(false);
        progress.setIndeterminate(true);
        progress.show();
        progress.setContentView(R.layout.progress);
    }
    public void HideLoading() {
        if (progress != null) {
            progress.dismiss();
        }
    }
    public Boolean isloading() {
        if (progress != null) {
            return progress.isShowing();
        }
        return false;
    }
}
