package com.my.android.shopcart.net;


import com.my.android.shopcart.model.JsonRes;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface CurrencyValuesListService {
    @GET("/latest")
   Observable<JsonRes> fetchResults(@Query("base") String currencydefault);

}