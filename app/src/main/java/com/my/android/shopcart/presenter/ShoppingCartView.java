package com.my.android.shopcart.presenter;


import com.my.android.shopcart.model.CartItem;
import com.my.android.shopcart.model.Rates;

import java.util.ArrayList;

public interface ShoppingCartView {
    void startLoadCurrencies();
    void showCurrencies(Rates currencies);
    void showCartItems(ArrayList<CartItem> cartitems);
    void updateResultsAfterCurrencySelection(String total);
    void clearValue();
    // listview interface
    void addItemToBasket(int position);
    void removeItemFromBasket(int position);


}
