package com.randomappsinc.padnotifier.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.randomappsinc.padnotifier.Models.God;
import com.randomappsinc.padnotifier.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Alex on 11/1/2014.
 */
public class GodfestListAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<God> featuredGods;

    // Creates the "Question 1, Question 2, etc..." list
    public GodfestListAdapter(Context context, ArrayList<God> featuredGods)
    {
        this.context = context;
        this.featuredGods = featuredGods;
    }

    public int getCount()
    {
        return featuredGods.size();
    }
    public Object getItem(int position)
    {
        return featuredGods.get(position);
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
            v = vi.inflate(R.layout.godfest_list_item, null);
            holder = new ViewHolder();
            holder.item1 = (ImageView) v.findViewById(R.id.god_icon);
            holder.item2 = (TextView) v.findViewById(R.id.god_title);
            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)v.getTag();
        }

        Picasso.with(context).load(featuredGods.get(position).getImageUrl()).into(holder.item1);
        holder.item2.setText(featuredGods.get(position).getGodName());

        return v;
    }
}
