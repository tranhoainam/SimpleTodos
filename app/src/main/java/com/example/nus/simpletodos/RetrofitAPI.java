package com.example.nus.simpletodos;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by nus on 10/11/16.
 */

public interface RetrofitAPI {
    @GET("utc/now")
    Call<ResponseBody> getInfo();
}
