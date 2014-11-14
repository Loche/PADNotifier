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
import com.randomappsinc.padnotifier.Timeslot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Alex on 11/1/2014.
 */
public class MetalsListAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<Timeslot> timeslots;
    private static final String DUNGEON_NOT_FOUND = "Unknown dungeon.";

    // Creates the "Question 1, Question 2, etc..." list
    public MetalsListAdapter(Context context, ArrayList<Timeslot> timeslots)
    {
        this.context = context;
        this.timeslots = timeslots;
    }

    public int getCount()
    {
        return timeslots.size();
    }
    public Object getItem(int position)
    {
        return timeslots.get(position);
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
        Integer metalDrawableID = DungeonMapper.getDungeonMapper().getDrawableResourceID(timeslots.get(position).getTitle());
        if (metalDrawableID != null)
        {
            holder.item1.setImageDrawable(resources.getDrawable
                    (DungeonMapper.getDungeonMapper().getDrawableResourceID(timeslots.get(position).getTitle())));
        }
        else
        {
            Picasso.with(context).load(DungeonMapper.getImageURL(timeslots.get(position).getTitle())).into(holder.item1);
        }

        String metalTitle = timeslots.get(position).getTitle();
        if (metalTitle != null)
        {
            holder.item2.setText(timeslots.get(position).getTitle());
        }
        else
        {
            holder.item2.setText(DUNGEON_NOT_FOUND);
        }
        holder.item3.setText(timeslots.get(position).getStarts_at().get(Calendar.HOUR_OF_DAY)
                + ":" + timeslots.get(position).getStarts_at().get(Calendar.MINUTE));

        return v;
    }
}
