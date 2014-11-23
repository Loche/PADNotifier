package com.randomappsinc.padnotifier.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.randomappsinc.padnotifier.Metals.DungeonMapper;
import com.randomappsinc.padnotifier.Misc.PreferencesManager;
import com.randomappsinc.padnotifier.Misc.Util;
import com.randomappsinc.padnotifier.R;

import java.util.ArrayList;


public class SelectivePushAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<String> dungeonList;
    private PreferencesManager prefsManager;
    private DungeonMapper dungeonMapper;

    public SelectivePushAdapter(Context context, String criteria)
    {
        this.context = context;
        dungeonMapper = DungeonMapper.getDungeonMapper();
        if (criteria.isEmpty())
        {
            dungeonList = dungeonMapper.getDungeonNamesList();
        }
        else
        {
            dungeonList = Util.getSearchResults(dungeonMapper.getDungeonNamesList(), criteria);
        }
        prefsManager = new PreferencesManager(context);
    }

    public int getCount()
    {
        return dungeonList.size();
    }

    public Object getItem(int position)
    {
        return dungeonList.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public static class ViewHolder
    {
        public ImageView dungeonIcon;
        public TextView dungeonName;
        public ImageView alarmClock;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        final ViewHolder holder;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.selective_push_item, null);
            holder = new ViewHolder();
            holder.dungeonIcon = (ImageView) v.findViewById(R.id.dungeon_icon);
            holder.dungeonName = (TextView) v.findViewById(R.id.dungeon_name);
            holder.alarmClock = (ImageView) v.findViewById(R.id.alarm_clock);
            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)v.getTag();
        }

        if (position >= dungeonList.size())
        {
            return v;
        }
        final String dungeonName = dungeonList.get(position);
        if (dungeonName != null)
        {
            boolean isAllowed = prefsManager.isDungeonAllowed(dungeonName);
            if (isAllowed)
            {
                holder.alarmClock.setImageResource(R.drawable.green_alarm_clock);
            }
            else
            {
                holder.alarmClock.setImageResource(R.drawable.red_alarm_clock);
            }
            holder.alarmClock.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                boolean isAllowed = prefsManager.isDungeonAllowed(dungeonName);
                if (isAllowed)
                {
                    holder.alarmClock.setImageResource(R.drawable.red_alarm_clock);
                    prefsManager.setAllowedDungeon(dungeonName, false);
                }
                else
                {
                    holder.alarmClock.setImageResource(R.drawable.green_alarm_clock);
                    prefsManager.setAllowedDungeon(dungeonName, true);
                }
                }
            });
            holder.dungeonName.setText(dungeonName);
            holder.dungeonIcon.setImageResource(dungeonMapper.getDrawableIdFromName(dungeonName));
        }
        return v;
    }
}
