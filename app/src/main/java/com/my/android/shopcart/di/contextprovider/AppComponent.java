package com.my.android.shopcart.di.contextprovider;


import com.my.android.shopcart.di.currencyprovider.CurrencyNetworkModule;
import com.my.android.shopcart.view.ShoppingCartComponent;
import com.my.android.shopcart.view.ShoppingCartModule;


import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, CurrencyNetworkModule.class})
public interface AppComponent {
    ShoppingCartComponent plus(ShoppingCartModule module);
}