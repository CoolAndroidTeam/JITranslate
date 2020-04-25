package com.coolcode.jittranslate.ui.shop;


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface BookQuery {


    @GET
    Call<JsonObject> getBooks(@Url String url) ;



}
