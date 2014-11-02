package com.randomappsinc.padnotifier;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.randomappsinc.padnotifier.adapter.PDNListAdapter;

public class MetalsFragment extends Fragment
{
    private static View rootView;
    public static Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_metals, container, false);
        context = getActivity().getApplicationContext();
        return rootView;
    }

    public static void renderMetals()
    {
        ListView metalsList = (ListView) rootView.findViewById(R.id.metalsList);
        PDNListAdapter metalsAdapter = new PDNListAdapter(context, MetalSchedule.getTimes(MainActivity.getGroup()),
                                            MetalSchedule.getImageURLs(), "Metals");
        metalsList.setAdapter(metalsAdapter);
    }
}