package com.vuki.bakingapp.network;

import com.vuki.bakingapp.models.ApiReceipt;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by mvukosav
 */
public interface ApiManagerService {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<ApiReceipt>> getReceipts();


}