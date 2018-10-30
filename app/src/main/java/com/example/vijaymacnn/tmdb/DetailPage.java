package com.example.vijaymacnn.tmdb;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.vijaymacnn.tmdb.Common.CommonFunctions;
import com.example.vijaymacnn.tmdb.Interface.RetroFunctions;
import com.example.vijaymacnn.tmdb.Model.MovieDetail.Example;
import com.example.vijaymacnn.tmdb.Model.MovieDetail.Genre;

import java.math.BigDecimal;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPage extends AppCompatActivity {

    AppCompatImageView img_poster;
    AppCompatTextView txt_descrition,txt_mins,txt_time,txt_rating,txt_movie_type,txt_language,txt_curr1,txt_curr2;
    ConstraintLayout movie_detail_parent;
    String id=null,title=null;
    Toolbar toolbar;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);
        getValues();
        declareToolbar();
        progress = new ProgressDialog(this);
        movie_detail_parent=findViewById(R.id.movie_detail_parent);
        img_poster=findViewById(R.id.Id_movDetail_img);
        txt_descrition=findViewById(R.id.Id_movDetail_txt_description);
        txt_mins=findViewById(R.id.Id_movDetail_txt_mins);
        txt_time=findViewById(R.id.Id_movDetail_txt_time);
        txt_rating=findViewById(R.id.Id_movDetail_txt_rating);
        txt_movie_type=findViewById(R.id.Id_movDetail_txt_movie_type);
        txt_language=findViewById(R.id.Id_movDetail_txt_language);
        txt_curr1=findViewById(R.id.Id_movDetail_txt_curr1);
        txt_curr2=findViewById(R.id.Id_movDetail_txt_curr2);
        getDetailCall();
    }
    private void declareToolbar() {
        toolbar=findViewById(R.id.toolbar);
        if (title!=null) {
            toolbar.setTitle(title);
        }
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }


    }

    private void getDetailCall() {
        if (id!=null){
            showload();
            RetroFunctions.RetroInstance().getMovieDetail(id,getResources().getString(R.string.api_key)).enqueue(new Callback<Example>() {
                @Override
                public void onResponse(@NonNull Call<Example> call, @NonNull Response<Example> response) {
                    if (response.isSuccessful()){
                        if(isloading()){
                            HideLoading();
                        }
                        Example objDetail=response.body();
                        if (objDetail!=null){
                            movie_detail_parent.setVisibility(View.VISIBLE);
                            if (objDetail.getOverview()!=null){
                                txt_descrition.setText(objDetail.getOverview());
                            }
                            if (objDetail.getTitle()!=null){
                                toolbar.setTitle(objDetail.getTitle());
                            }
                            if (objDetail.getPosterPath()!=null){
                                String imageUrl="https://image.tmdb.org/t/p/w500/"+objDetail.getPosterPath();
                                Glide.with(DetailPage.this).load(imageUrl).into(img_poster);
                            }
                            if (objDetail.getRuntime()!=null){
                                String runTime=objDetail.getRuntime().toString()+" Minutes";
                                txt_mins.setText(runTime);
                            }
                            if (objDetail.getReleaseDate()!=null){
                                txt_time.setText(CommonFunctions.convertDate(objDetail.getReleaseDate()));
                            }
                            if (objDetail.getVoteAverage()!=null){
                                String rating=objDetail.getVoteAverage().toString();
                                txt_rating.setText(rating);
                            }
                            if (objDetail.getGenres()!= null){
                                txt_movie_type.setText(getGeneres(objDetail.getGenres()));
                            }
                            if (objDetail.getOriginalLanguage()!=null){
                                txt_language.setText(objDetail.getOriginalLanguage());
                            }
                            if (objDetail.getBudget()!=null){
                                txt_curr1.setText(getMillion(objDetail.getBudget()));
                            }
                            if (objDetail.getBudget()!=null){
                                txt_curr2.setText(getMillion(objDetail.getRevenue()));
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
    }

    private String getMillion(BigDecimal decimal){
        return "$"+decimal.divide(BigDecimal.valueOf(1000000),0,1).toString()+" Millions";
    }

    private void getValues() {
        if (getIntent()!=null){
            if (getIntent().getExtras()!=null){
                id=getIntent().getExtras().getString("id");
                title=getIntent().getExtras().getString("title");
            }
        }
    }

    private String getGeneres(List<Genre> aryGeneres){
        if (aryGeneres!=null){
            if (aryGeneres.size()>0){
                StringBuilder objBuilder=new StringBuilder();
                for (int i=0;i<aryGeneres.size();i++){
                    if (i!=0){
                        objBuilder.append(", ");
                    }
                    objBuilder.append(aryGeneres.get(i).getName());
                }
                return objBuilder.toString();
            }
        }
        return "";
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
