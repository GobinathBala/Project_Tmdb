package com.example.vijaymacnn.tmdb.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.example.vijaymacnn.tmdb.Common.CommonFunctions;
import com.example.vijaymacnn.tmdb.Interface.Interface_Adap;
import com.example.vijaymacnn.tmdb.Model.MoviesList.Result;
import com.example.vijaymacnn.tmdb.R;
import java.util.List;

public class Adapter_MovieList extends RecyclerView.Adapter<Adapter_MovieList.ViewHolder>{

    private Activity objActivityRef;
    private List<Result> data;
    private Interface_Adap objInterAdap;

    public Adapter_MovieList(Activity objActivityRef, List<Result> data,Interface_Adap objInterAdap){
        this.objActivityRef=objActivityRef;
        this.data=data;
        this.objInterAdap=objInterAdap;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.move_list_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (data.get(position)!=null){
            if (data.get(position).getPosterPath()!=null){
                String imageUrl="https://image.tmdb.org/t/p/w500/"+data.get(position).getPosterPath();
                Glide.with(objActivityRef).load(imageUrl).into(holder.img_poster);
            }
            if (data.get(position).getTitle()!=null){
                holder.txt_title.setText(data.get(position).getTitle());
            }
            if (data.get(position).getOverview()!=null){
                holder.txt_descriptiom.setText(data.get(position).getOverview());
            }
            if (data.get(position).getVoteAverage()!=null){
                String average=data.get(position).getVoteAverage().toString();
                holder.txt_rating.setText(average);
            }
            if (data.get(position).getReleaseDate()!=null){
                holder.txt_date.setText(CommonFunctions.convertDate(data.get(position).getReleaseDate()));
            }
            if (data.get(position).getOriginalLanguage()!=null){
                holder.txt_language.setText(data.get(position).getOriginalLanguage());
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        AppCompatImageView img_poster;
        AppCompatTextView txt_title,txt_descriptiom,txt_rating,txt_date,txt_language;
        ConstraintLayout objParentConstraint;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_poster=itemView.findViewById(R.id.Id_movie_list_Img);
            txt_title=itemView.findViewById(R.id.Id_movie_list_txt_title);
            txt_descriptiom=itemView.findViewById(R.id.Id_movie_list_txt_description);
            txt_rating=itemView.findViewById(R.id.Id_movie_list_txt_rating);
            txt_date=itemView.findViewById(R.id.Id_movie_list_txt_date);
            txt_language=itemView.findViewById(R.id.Id_movie_list_txt_language);
            objParentConstraint=itemView.findViewById(R.id.Id_parent_constraint);
            objParentConstraint.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.Id_parent_constraint:
                    objInterAdap.onClicked(getAdapterPosition());
                    break;
            }
        }
    }
}
