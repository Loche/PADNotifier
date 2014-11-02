package com.randomappsinc.padnotifier.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.randomappsinc.padnotifier.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Alex on 11/1/2014.
 */
public class PDNListAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<String> times;
    private ArrayList<String> imageURLs;
    private String mode;

    private static final float GODFEST_TEXT_SIZE = 24;

    // Creates the "Question 1, Question 2, etc..." list
    public PDNListAdapter(Context context, ArrayList<String> times, ArrayList<String> imageURLS, String mode)
    {
        this.context = context;
        this.times = times;
        this.imageURLs = imageURLS;
        this.mode = mode;
    }

    public int getCount()
    {
        return imageURLs.size();
    }
    public Object getItem(int position)
    {
        return imageURLs.get(position);
    }
    public long getItemId(int position)
    {
        return position;
    }

    public static class ViewHolder
    {
        public ImageView item1;
        public TextView item2;
    }

    // Renders the ListView item that the user has scrolled to or is about to scroll to
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        final ViewHolder holder;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.item1 = (ImageView) v.findViewById(R.id.list_icon);
            holder.item2 = (TextView) v.findViewById(R.id.list_text);
            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)v.getTag();
        }

        Picasso.with(context).load(imageURLs.get(position)).into(holder.item1);
        holder.item2.setText(times.get(position));

        if (mode.equals("Godfest"))
        {
            holder.item2.setTextSize(GODFEST_TEXT_SIZE);
        }

        return v;
    }
}
