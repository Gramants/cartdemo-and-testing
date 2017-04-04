package com.my.android.shopcart.presenter;

import java.util.ArrayList;

public interface ShoppingCartPresenter {
    void getCurrentRates();
    void getAvailableItems();
    void addItemToBasket(int cartid);
    void removeItemFromBasket(int cartid);
    void calculateNewTotalAfterCurrencyChange(double multiplier);


    void cleanUp();
    int getItemPerCatFromBasket(int itemid);

}
