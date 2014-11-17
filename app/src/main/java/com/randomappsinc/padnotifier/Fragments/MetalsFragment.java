package com.randomappsinc.padnotifier.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.randomappsinc.padnotifier.Adapters.MetalsListAdapter;
import com.randomappsinc.padnotifier.Metals.MetalSchedule;
import com.randomappsinc.padnotifier.Misc.PreferencesManager;
import com.randomappsinc.padnotifier.R;

public class MetalsFragment extends Fragment
{
    private static View rootView;
    public static Context context;
    private static PreferencesManager m_prefs_manager;
    private static final String NO_METALS = "It looks like we are unable to fetch the metals information for today. "
                                            + "Please try again later.";
    private static final float METALS_MESSAGE_SIZE = 18;
    private static final String TAG = "MetalsFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        m_prefs_manager = new PreferencesManager(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_metals, container, false);
        return rootView;
    }

    public static void renderMetals()
    {
        if (MetalSchedule.timesIsEmpty(2 /* US Country Code*/, m_prefs_manager.getGroup()))
        {
            TextView metalsMessage = (TextView) rootView.findViewById(R.id.metalsMessage);
            metalsMessage.setText(NO_METALS);
            metalsMessage.setTextSize(METALS_MESSAGE_SIZE);
        }
        else
        {
            Log.d(TAG, "Rendering metals...");
            ListView metalsList = (ListView) rootView.findViewById(R.id.metalsList);
            MetalSchedule masterSchedule = MetalSchedule.getInstance();
            MetalsListAdapter metalsAdapter = new MetalsListAdapter(context,
                    masterSchedule.getTimeslots(2 /* US Country Code */, m_prefs_manager.getGroup()));
            metalsList.setAdapter(metalsAdapter);
        }
    }
}