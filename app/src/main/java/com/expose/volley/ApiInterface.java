package com.expose.volley;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by vineesh.expose on 30-10-2017.
 */

public interface ApiInterface {
    @GET("glide.json")
    Call<List<Model>> getDetails();
}
