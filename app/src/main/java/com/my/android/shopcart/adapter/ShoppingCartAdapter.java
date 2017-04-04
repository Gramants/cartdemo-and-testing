package com.my.android.shopcart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.my.android.shopcart.R;
import com.my.android.shopcart.model.CartItem;
import com.my.android.shopcart.view.ShoppingCartActivity;

import java.util.ArrayList;

public class ShoppingCartAdapter extends ArrayAdapter {
private Context context;
    public ShoppingCartAdapter(final ArrayList<CartItem> items, final Context context) {
        super(context, 0, items);
        this.context=context;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        final CartItem item = (CartItem) getItem(position);

            final LayoutInflater layoutInflater = LayoutInflater.from(getContext());

            convertView = layoutInflater.inflate(R.layout.item_cart, null);

            viewHolder = new ViewHolder();
            viewHolder.item_desc = (TextView) convertView.findViewById(R.id.item_desc);
            viewHolder.item_price = (TextView) convertView.findViewById(R.id.item_price);
            viewHolder.add = (ImageView) convertView.findViewById(R.id.itemadd);
            viewHolder.remove = (ImageView) convertView.findViewById(R.id.itemremove);
            convertView.setTag(viewHolder);

        viewHolder.item_desc.setText(item.getName());
        viewHolder.item_price.setText("£ "+String.valueOf(item.getPrice()));

        viewHolder.add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(context instanceof ShoppingCartActivity){

                    ((ShoppingCartActivity)context).addItemToBasket(position);


                }
            }
        });


        viewHolder.remove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(context instanceof ShoppingCartActivity){

                        ((ShoppingCartActivity)context).removeItemFromBasket(position);


                }
            }
        });

        return convertView;
    }



    private class ViewHolder {
        private ImageView add;
        private ImageView remove;
        private TextView item_price;
        private TextView item_desc;

    }

}
