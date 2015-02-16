package com.randomappsinc.padbuddy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.randomappsinc.padbuddy.R;

/**
 * Created by Alex on 11/16/2014.
 */

public class StarterColorSpinnerAdapter extends ArrayAdapter<String>
{
    private Context context;

    private String[] colors = {"Fire","Water", "Grass"};
    private int color_icons[] ={ R.drawable.fire_orb, R.drawable.water_orb, R.drawable.grass_orb};

        public StarterColorSpinnerAdapter(Context context, int textViewResourceId, String[] objects)
        {
            super(context, textViewResourceId, objects);
            this.context = context;
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent)
        {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View row = inflater.inflate(R.layout.starter_color_spinner_item, parent, false);
            TextView color = (TextView) row.findViewById(R.id.color);
            color.setText(colors[position]);

            ImageView icon = (ImageView) row.findViewById(R.id.color_icon);
            icon.setImageResource(color_icons[position]);

            return row;
        }
}
