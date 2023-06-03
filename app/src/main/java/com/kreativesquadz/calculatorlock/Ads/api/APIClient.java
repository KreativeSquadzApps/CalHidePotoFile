package com.kreativesquadz.calculatorlock.Ads.api;


import static com.kreativesquadz.calculatorlock.Ads.api.ConfigUrl.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static APIInterface service;

    private static APIClient apiManager;

    private APIClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(APIInterface.class);
    }

    public static APIClient getInstance() {
        if (apiManager == null) {
            apiManager = new APIClient();
        }
        return apiManager;
    }

}
