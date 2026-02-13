package com.astememe.openani.API_Manager;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_Interface {
    @GET("nyaa")
    Call<Data> getAnimebyCategory(@Query("category") String categoria);

    @GET("nyaa")
    Call<Data> getCoctailsByName(@Query("s") String name);
}
