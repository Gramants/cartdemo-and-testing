package com.my.android.shopcart.di.contextprovider;


import android.content.Context;

import dagger.Module;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }


}