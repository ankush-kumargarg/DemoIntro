package com.interactive.demointro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.interactive.demointro.R;
import com.interactive.demointro.dto.MovieDTO;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MovieDTO> list;

    public MoviesAdapter(Context context, ArrayList<MovieDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieDTO movieDTO = list.get(position);
        holder.tvMovieName.setText(movieDTO.getName());
        Glide.with(context)
                .load(movieDTO.getImageurl())
                .into(holder.ivMovieImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivMovieImage;
        private TextView tvMovieName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMovieImage = itemView.findViewById(R.id.iv_movie_image);
            tvMovieName = itemView.findViewById(R.id.tv_movie_name);
        }
    }
}
