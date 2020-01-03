package com.interactive.demointro.network;


import com.interactive.demointro.dto.MovieDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("marvel")
    Call<ArrayList<MovieDTO>> getHeroes();
}