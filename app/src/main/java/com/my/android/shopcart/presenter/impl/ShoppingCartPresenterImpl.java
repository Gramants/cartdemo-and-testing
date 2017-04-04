package com.my.android.shopcart.presenter.impl;


import com.my.android.shopcart.model.CartItem;
import com.my.android.shopcart.model.JsonRes;
import com.my.android.shopcart.net.CurrencyValuesListService;
import com.my.android.shopcart.presenter.ShoppingCartPresenter;
import com.my.android.shopcart.presenter.ShoppingCartView;
import com.my.android.shopcart.util.Constant;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import javax.inject.Inject;


public class ShoppingCartPresenterImpl implements ShoppingCartPresenter {


    private ShoppingCartView callback;
    private CurrencyValuesListService chatWebService;

    private Subscription subscriptionmessages;
    private HashMap<Integer,Integer> basket;
    ArrayList<CartItem> cartitems;
    private static DecimalFormat df2 = new DecimalFormat(".##");
    @Inject
    public ShoppingCartPresenterImpl(ShoppingCartView mChatView, CurrencyValuesListService chatWebService) {
        this.callback=mChatView;
        this.chatWebService=chatWebService;

    }


    @Override
    public void getCurrentRates()  {

        callback.startLoadCurrencies();

        if (subscriptionmessages != null) subscriptionmessages.unsubscribe();
        subscriptionmessages = chatWebService.fetchResults(Constant.DEFAULT_CURRENCY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<JsonRes>() {

                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable error) {}

                    @Override
                    public void onNext(JsonRes jsonres) {
                        callback.showCurrencies(jsonres.getRates());
                    }

                });
    }

    @Override
    public void getAvailableItems() {

        cartitems =new ArrayList();
        cartitems.add(new CartItem("Peas (per bag)",.95,Constant.DEFAULT_CURRENCY));
        cartitems.add(new CartItem("Eggs (per dozens)",2.1,Constant.DEFAULT_CURRENCY));
        cartitems.add(new CartItem("Milk (per bottle)",1.99,Constant.DEFAULT_CURRENCY));
        cartitems.add(new CartItem("Beans (per can)",.73,Constant.DEFAULT_CURRENCY));
        cartitems.add(new CartItem("Bread (per piece)",.90,Constant.DEFAULT_CURRENCY));
        cartitems.add(new CartItem("Fish (per fish)",2.5,Constant.DEFAULT_CURRENCY));
        cartitems.add(new CartItem("Tomatoes (per Kg)",1.7,Constant.DEFAULT_CURRENCY));
        cartitems.add(new CartItem("Potatoes (per Kg)",2.8,Constant.DEFAULT_CURRENCY));
        callback.showCartItems(cartitems);
    }

    @Override
    public void addItemToBasket(int itemid) {
        if (basket==null)
        {
            basket=new HashMap();
        }

        if (basket.get(itemid)!=null)
        {
            int iteminbasket=basket.get(itemid);
            iteminbasket++;
            basket.put(itemid,iteminbasket);
        }
        else
        {
            basket.put(itemid,1);
        }

    }

    @Override
    public void removeItemFromBasket (int itemid) {
        if (basket!=null)
        {

            if (basket.get(itemid)!=null)
            {
                int iteminbasket=basket.get(itemid);
                iteminbasket--;
                basket.put(itemid,iteminbasket<=-1?0:iteminbasket);
            }

        }

    }

    @Override
    public int getItemPerCatFromBasket(int itemid) {
        if (basket!=null)
        {

            if (basket.get(itemid)!=null)
            {
                return basket.get(itemid);

            }

        }
        return 0;
    }

    @Override
    public void calculateNewTotalAfterCurrencyChange(double multiplier) {
        double total=0.0;

        if (basket!=null)
        {
        for (Map.Entry<Integer, Integer> entry : basket.entrySet()) {
            Integer key = entry.getKey();
            if (basket.get(key)>0)
                total=total+((CartItem) cartitems.get(key)).getPrice()*basket.get(key);
        }
        }

        total=total*multiplier;


        callback.updateResultsAfterCurrencySelection(df2.format(total).equals(",0")?"0,0":df2.format(total));
    }

    @Override
    public void cleanUp() {
        if (basket!=null)
        {
        for (Map.Entry<Integer, Integer> entry : basket.entrySet()) {
            Integer key = entry.getKey();
            basket.put(key,0);
         }
        }
        callback.clearValue();
    }


}
