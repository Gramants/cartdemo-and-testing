package com.my.android.shopcart.cucumber.test;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;

import com.my.android.shopcart.R;
import com.my.android.shopcart.cucumber.test.util.CustomMatcher;
import com.my.android.shopcart.model.RateValue;
import com.my.android.shopcart.util.Constant;
import com.my.android.shopcart.view.ShoppingCartActivity;

import org.hamcrest.Matcher;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.internal.util.Checks.checkNotNull;
import static com.my.android.shopcart.cucumber.test.util.CustomMatcher.getText;
import static com.my.android.shopcart.cucumber.test.util.CustomMatcher.withTheCurrency;
import static com.my.android.shopcart.cucumber.test.util.GlobalActions.closeAllActivities;
import static com.my.android.shopcart.cucumber.test.util.GlobalActions.waitInMillisecondsForAppToBeIdle;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;


import cucumber.api.CucumberOptions;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


@CucumberOptions(features = "features",tags ="@go")

public class ShoppingCart_BDDSteps extends ActivityInstrumentationTestCase2<ShoppingCartActivity> {

    private Activity mActivity;
    private Context mInstrumentationContext;
    private Context mAppContext;

    public ShoppingCart_BDDSteps() {
        super(ShoppingCartActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        mInstrumentationContext = getInstrumentation().getContext();
        mAppContext = getInstrumentation().getTargetContext();
        mActivity = getActivity();
        assertNotNull(mActivity);
    }

    @After
    public void tearDown() throws Exception {
        //ActivityFinisher.finishOpenActivities();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.edit().clear().commit();
        closeAllActivities((Instrumentation) getInstrumentation());
        super.tearDown();
    }



    @Given("^I am on the main page$")
    public void I_have_a_login_page() {
        assertNotNull(getActivity());
        waitInMillisecondsForAppToBeIdle(onView(withId(R.id.lvMessages)), matches(isDisplayed()), 3000);
    }

    @And("^I tap the plus button on item n.\"([^\"]*)\"$")
    public void i_tap_plus_on_item_with(String match) {
        int pos=Integer.parseInt(match);
        pos=pos-1;
        onData(anything()).inAdapterView(withId(R.id.lvMessages)).atPosition(pos).onChildView(withId(R.id.itemadd)).perform(click());
   }



    @And("^I tap the minus button on item n.\"([^\"]*)\"$")
    public void i_tap_the_minus_button_on_item_n(String match) throws Throwable {
        int pos=Integer.parseInt(match);
        pos=pos-1;
        onData(anything()).inAdapterView(withId(R.id.lvMessages)).atPosition(pos).onChildView(withId(R.id.itemremove)).perform(click());

    }

    @Then("^I see the message list$")
    public void I_see_the_message_list() {
        onView(withId(R.id.lvMessages)).check(matches(isDisplayed()));
    }


    @Then("^I should see the result \"([^\"]*)\"$")
    public void i_should_see_the_result(String result) throws Throwable {
        String newtotal = getText(withId(R.id.total));
        assertTrue(newtotal.equals(result));
    }
    @When("^I cfffselect the currency \"([^\"]*)\"$")
    public void i_select_a_currency(int currencyindex) throws Throwable {

        onData(anything())
                .inAdapterView(withId(R.id.spinner_currencies))
                .atPosition(currencyindex)
                .perform(click());
        onView(withId(R.id.total)).perform(click());
    }


    @Then("^I select the currency at the position (\\d+)$")
    public void i_select_the_currency_at_the_position(int idx) throws Throwable {


        onData(anything())
                .inAdapterView(withId(R.id.spinner_currencies))
                .atPosition(idx)
                .perform(click());


    }





}
