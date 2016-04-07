package com.mobiquity.androidunittests.net.services;

import com.mobiquity.androidunittests.net.models.WolframResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WolframService {

    @GET("query")
    Call<WolframResponse> query(@Query("input") String input);
}
