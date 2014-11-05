package com.randomappsinc.padnotifier.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.randomappsinc.padnotifier.DungeonMapper;
import com.randomappsinc.padnotifier.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Alex on 11/1/2014.
 */
public class MetalsListAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<String> times;
    private ArrayList<String> imageURLs;
    private static final String DUNGEON_NOT_FOUND = "Unknown dungeon.";

    // Creates the "Question 1, Question 2, etc..." list
    public MetalsListAdapter(Context context, ArrayList<String> times, ArrayList<String> imageURLS)
    {
        this.context = context;
        this.times = times;
        this.imageURLs = imageURLS;
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
        public TextView item3;
    }

    // Renders the ListView item that the user has scrolled to or is about to scroll to
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        final ViewHolder holder;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.metal_list_item, null);
            holder = new ViewHolder();
            holder.item1 = (ImageView) v.findViewById(R.id.metal_icon);
            holder.item2 = (TextView) v.findViewById(R.id.metal_title);
            holder.item3 = (TextView) v.findViewById(R.id.metal_time);
            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)v.getTag();
        }

        Resources resources = context.getResources();
        Integer metalDrawableID = DungeonMapper.getDungeonMapper().getDrawableResourceID(imageURLs.get(position));
        if (metalDrawableID != null)
        {
            holder.item1.setImageDrawable(resources.getDrawable
                    (DungeonMapper.getDungeonMapper().getDrawableResourceID(imageURLs.get(position))));
        }
        else
        {
            Picasso.with(context).load(imageURLs.get(position)).into(holder.item1);
        }

        String metalTitle = DungeonMapper.getDungeonMapper().getDungeonName(imageURLs.get(position));
        if (metalTitle != null)
        {
            holder.item2.setText(DungeonMapper.getDungeonMapper().getDungeonName(imageURLs.get(position)));
        }
        else
        {
            holder.item2.setText(DUNGEON_NOT_FOUND);
        }
        holder.item3.setText(times.get(position));

        return v;
    }
}
