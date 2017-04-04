package com.my.android.shopcart.view;



import com.my.android.shopcart.net.CurrencyValuesListService;
import com.my.android.shopcart.presenter.ShoppingCartPresenter;
import com.my.android.shopcart.presenter.ShoppingCartView;
import com.my.android.shopcart.ActivityScope;
import com.my.android.shopcart.presenter.impl.ShoppingCartPresenterImpl;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;


@Module
public class ShoppingCartModule {

    public final ShoppingCartView view;

    public ShoppingCartModule(ShoppingCartView view) {
        this.view = view;
    }


    @Provides
    @ActivityScope
    CurrencyValuesListService provideChatMessageListService (Retrofit retrofit) {
        return retrofit.create(CurrencyValuesListService.class);
    }


    @Provides
    @ActivityScope
    ShoppingCartView provideMainView() {
        return this.view;
    }


    @Provides
    @ActivityScope
    ShoppingCartPresenter provideChatActivityPresenter(ShoppingCartPresenterImpl presenter) {
        return presenter;
    }

}
