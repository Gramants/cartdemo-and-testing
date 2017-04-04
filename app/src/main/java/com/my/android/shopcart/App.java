package com.my.android.shopcart;

import android.app.Application;


import com.my.android.shopcart.di.contextprovider.AppModule;
import com.my.android.shopcart.di.currencyprovider.CurrencyNetworkModule;
import com.my.android.shopcart.util.Constant;
import com.my.android.shopcart.view.ShoppingCartComponent;
import com.my.android.shopcart.di.contextprovider.AppComponent;
import com.my.android.shopcart.di.contextprovider.DaggerAppComponent;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class App extends Application {

    private ShoppingCartComponent mShoppingCartComponent;
    private AppComponent mAppcomponent;

    @Override
    public void onCreate() {
        resolveDependency();
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/robotoregular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }


    private void resolveDependency() {



        mAppcomponent =DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .currencyNetworkModule(new CurrencyNetworkModule(Constant.CURRENCIES_LIST_BASE_URL))
                .build();
    }




    public AppComponent getAppComponent() {
        return mAppcomponent;
    }


}
