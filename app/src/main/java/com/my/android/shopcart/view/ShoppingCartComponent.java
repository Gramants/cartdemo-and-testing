package com.my.android.shopcart.view;


import com.my.android.shopcart.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent (modules = {ShoppingCartModule.class})
public interface ShoppingCartComponent {

    void inject(ShoppingCartActivity chatactivity);
}
