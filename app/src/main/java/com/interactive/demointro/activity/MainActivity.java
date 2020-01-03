package com.interactive.demointro.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.LinearLayout;

import com.interactive.demointro.R;
import com.interactive.demointro.adapter.MoviesAdapter;
import com.interactive.demointro.dto.MovieDTO;
import com.interactive.demointro.network.ApiManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));

        ApiManager.getInstance().getApiService().getHeroes().enqueue(new Callback<ArrayList<MovieDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<MovieDTO>> call, Response<ArrayList<MovieDTO>> response) {
                if(response.body()!=null)
                {
                    adapter=new MoviesAdapter(MainActivity.this,response.body());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MovieDTO>> call, Throwable t) {

            }
        });
    }
}
