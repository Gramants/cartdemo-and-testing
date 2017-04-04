package com.my.android.shopcart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.my.android.shopcart.model.RateValue;
import com.my.android.shopcart.R;

import java.util.ArrayList;

public class SpinnerCurrencyAdapter extends BaseAdapter {
private Context context;
private ArrayList<RateValue> items;
    public SpinnerCurrencyAdapter(final ArrayList<RateValue> items, final Context context) {
        this.context=context;
        this.items=items;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        final RateValue item = (RateValue) items.get(position);

            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_currency, null);

            viewHolder = new ViewHolder();
            viewHolder.spinner_label = (TextView) convertView.findViewById(R.id.spinner_label);
            convertView.setTag(viewHolder);

        viewHolder.spinner_label.setText(item.getName());

        return convertView;
    }



    private class ViewHolder {
        private TextView spinner_label;

    }

}
