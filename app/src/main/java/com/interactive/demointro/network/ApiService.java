package com.interactive.demointro.network;


import com.google.gson.JsonObject;
import com.interactive.demointro.dto.MovieDTO;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @GET("marvel")
    Call<ArrayList<MovieDTO>> getHeroes();

    @Multipart
    @POST("upload_pan")
    Call<JsonObject> uploadPan(@Part("driver_id") RequestBody driverId,
                               @Part("pan_name") RequestBody panName,
                               @Part("pan_number") RequestBody panNumber,
                               @Part MultipartBody.Part part);
}