package com.example.movietickets;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.TextView;
import android.app.Activity;
import java.util.ArrayList;

/* Class to dynamically populate the ListView */
public class PricesAdapter extends ArrayAdapter<Prices> {


    public PricesAdapter(Activity context, ArrayList<Prices> priceList) {
        super(context, 0, priceList);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.price_list, parent,false);
        }

        Prices currentPrice = getItem(position);

        /* Shows values in demography variable of Prices object */
        TextView demoText = listItemView.findViewById(R.id.demography);
        demoText.setText(currentPrice.getDemography());

        /* Shows values in price variable of Prices object */
        TextView priceText = listItemView.findViewById(R.id.price);
        priceText.setText(currentPrice.getPrice());

        return listItemView;

    }
}

