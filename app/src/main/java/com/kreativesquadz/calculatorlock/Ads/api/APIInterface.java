package com.kreativesquadz.calculatorlock.Ads.api;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("getappstatus.php")
    Call<JsonArray> GetAppstatus(@Query("app_name") String app_name);


}
