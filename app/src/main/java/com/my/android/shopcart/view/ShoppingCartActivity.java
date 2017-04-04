package com.my.android.shopcart.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.my.android.shopcart.App;
import com.my.android.shopcart.adapter.ShoppingCartAdapter;
import com.my.android.shopcart.adapter.SpinnerCurrencyAdapter;
import com.my.android.shopcart.model.CartItem;
import com.my.android.shopcart.model.RateValue;
import com.my.android.shopcart.model.Rates;
import com.my.android.shopcart.presenter.ShoppingCartView;
import com.my.android.shopcart.presenter.impl.ShoppingCartPresenterImpl;
import com.my.android.shopcart.util.Constant;
import com.my.android.shopcart.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import javax.inject.Inject;
import butterknife.Bind;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ShoppingCartActivity extends AppCompatActivity implements ShoppingCartView {

    @Inject
    ShoppingCartPresenterImpl mShoppingCartPresenterImpl;


    @Bind(R.id.lvMessages)
    ListView listView;

    @Bind(R.id.total)
    TextView total;

    @Bind(R.id.footer_section)
    LinearLayout footer;

    @Bind(R.id.spinner_currencies)
    Spinner currencies;

    private MaterialDialog mProgress;
    private ArrayAdapter mAdapter;
    private SpinnerCurrencyAdapter spinnerAdapter;

    private ArrayList<RateValue> mCurrencyarray;
    private boolean userIsInteracting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((App) getApplication()).getAppComponent().plus(new ShoppingCartModule(this)).inject(this);
        init();
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_label));
    }

    private void init() {
    total.setText("0.0");
    }

    @Override
    public void onPause(){
        super.onPause();

        mShoppingCartPresenterImpl.cleanUp();

    }


    @Override
    public void onResume(){
        super.onResume();
        mShoppingCartPresenterImpl.getAvailableItems();
        mShoppingCartPresenterImpl.getCurrentRates();
        mAdapter.notifyDataSetChanged();
        
    }

    private void updateSpinnerRates(ArrayList rates) {
        spinnerAdapter = new SpinnerCurrencyAdapter(rates,this);

        currencies.setAdapter(spinnerAdapter);
        currencies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userIsInteracting) {
                RateValue item = (RateValue) mCurrencyarray.get(position);
                mShoppingCartPresenterImpl.calculateNewTotalAfterCurrencyChange(item.getValue());
                }
                    //Toast.makeText(parent.getContext(), "Currency conversion in " + item.getName(), Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        selectSpinnerValue(Constant.DEFAULT_CURRENCY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbarmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                Toast.makeText(this,getResources().getString(R.string.me),Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }



    @Override
    public void startLoadCurrencies() {
        footer.setVisibility(View.GONE);
        mProgress=new MaterialDialog.Builder(this)
                .title(getResources().getString(R.string.loading_label))
                .content(getResources().getString(R.string.wait_label))
                .progress(true, 0)
                .show();
    }
    
    public void finishLoadCurrencies() {
        mProgress.dismiss();
        footer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCurrencies(Rates currencies) {
    this.mCurrencyarray=currencies.getRateValueArray();
    updateSpinnerRates(mCurrencyarray);
    finishLoadCurrencies();
    }

    @Override
    public void showCartItems(ArrayList<CartItem> cartitems) {
        mAdapter = new ShoppingCartAdapter(cartitems,this);
        listView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateResultsAfterCurrencySelection(String totalcalculated) {
    total.setText(totalcalculated.substring(0,1).equals(",")?"0"+totalcalculated:totalcalculated);
    }

    @Override
    public void clearValue() {
        listView.invalidate();
        total.setText("0.0");
    }

    @Override
    public void addItemToBasket(int position) {
        mShoppingCartPresenterImpl.addItemToBasket(position);
        mShoppingCartPresenterImpl.calculateNewTotalAfterCurrencyChange(1);
        selectSpinnerValue(Constant.DEFAULT_CURRENCY);
    }

    @Override
    public void removeItemFromBasket(int position) {
        mShoppingCartPresenterImpl.removeItemFromBasket(position);
        mShoppingCartPresenterImpl.calculateNewTotalAfterCurrencyChange(1);
        selectSpinnerValue(Constant.DEFAULT_CURRENCY);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    private void selectSpinnerValue(String defaultcurrency)
    {

        for(int i = 0; i < mCurrencyarray.size(); i++){
            if( ((RateValue)mCurrencyarray.get(i)).getName().equals(defaultcurrency)){
                currencies.setSelection(i);
                break;
            }
        }
    }


    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userIsInteracting = true;
    }

}
