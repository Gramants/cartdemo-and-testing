package com.my.android.shopcart.di.currencyprovider;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = CurrencyNetworkModule.class)
public interface CurrencyNetworkComponent {

    Retrofit retrofit();
}
