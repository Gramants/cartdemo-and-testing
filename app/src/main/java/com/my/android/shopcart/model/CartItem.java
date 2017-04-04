package com.my.android.shopcart.model;


public class CartItem {


    public CartItem(String name, Double price,String currency) {
        this.name = name;
        this.price = price;
        this.currency = currency;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }




    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    private String currency;
    private String name;
    private Double price;
}
