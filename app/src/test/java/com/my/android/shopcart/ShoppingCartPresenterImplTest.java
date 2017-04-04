package com.my.android.shopcart;


import android.util.LruCache;

import com.my.android.shopcart.model.CartItem;
import com.my.android.shopcart.model.JsonRes;
import com.my.android.shopcart.model.Rates;
import com.my.android.shopcart.net.CurrencyValuesListService;
import com.my.android.shopcart.presenter.ShoppingCartPresenter;
import com.my.android.shopcart.presenter.impl.ShoppingCartPresenterImpl;
import com.my.android.shopcart.presenter.ShoppingCartView;
import com.my.android.shopcart.util.Constant;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ShoppingCartPresenterImplTest {

    @Mock
    private ShoppingCartView callback;
    @Mock
    private CurrencyValuesListService chatWebService;

    @InjectMocks
    protected ShoppingCartPresenter presenter;




    @SuppressWarnings("unchecked")
    @Before public void setUp() {

        presenter = new ShoppingCartPresenterImpl(callback,chatWebService);
        MockitoAnnotations.initMocks(this);
    }



    @Test public void presenter_getCurrentRates() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.CURRENCIES_LIST_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TestSubscriber<Rates> subscriber = new TestSubscriber<>();

        retrofit.create(CurrencyValuesListService.class).fetchResults("GBP")
                .flatMap(new Func1<JsonRes, Observable<Rates>>() {
                    @Override public Observable<Rates> call(JsonRes result) {
                        return Observable.from(new Rates[]{result.getRates()});
                    }
                })
                .first()
                .toBlocking() // the main difference
                .subscribe(subscriber);

        subscriber.assertValueCount(1);
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
    }

    @Test public void presenter_getAvailableItems() {
        presenter.getAvailableItems();
        ArgumentCaptor<ArrayList> cartitems =ArgumentCaptor.forClass(ArrayList.class);
        verify(callback).showCartItems(cartitems.capture());
        assertEquals(cartitems.getValue().size(), 8);
    }

    @Test public void presenter_addItemToBasket() {
        presenter.addItemToBasket(1);
        assertEquals(presenter.getItemPerCatFromBasket(1),1);

    }

    @Test public void presenter_removeItemFromBasket() {
        presenter.addItemToBasket(1);
        presenter.addItemToBasket(1);
        presenter.removeItemFromBasket(1);
        assertEquals(presenter.getItemPerCatFromBasket(1),1);

    }

    @Test public void presenter_calculateNewTotalAfterCurrencyChange() {
        presenter.addItemToBasket(1);

        ArgumentCaptor<String> res =ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ArrayList> cartitems =ArgumentCaptor.forClass(ArrayList.class);
        presenter.getAvailableItems();
        verify(callback).showCartItems(cartitems.capture());
        presenter.calculateNewTotalAfterCurrencyChange(2.0);
        verify(callback).updateResultsAfterCurrencySelection(res.capture());
        assertEquals(res.getValue(), "4,2");
    }

}