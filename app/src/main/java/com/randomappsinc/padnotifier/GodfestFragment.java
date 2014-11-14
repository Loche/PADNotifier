package com.randomappsinc.padnotifier;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.randomappsinc.padnotifier.adapter.GodfestListAdapter;

public class GodfestFragment extends Fragment
{
    private static View rootView;
    public static Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_godfest, container, false);
        context = getActivity().getApplicationContext();
        return rootView;
    }

    public static void renderGods()
    {
        Log.d("GodfestFragment", "Rendering Godfest...");
        TextView godfestMessage = (TextView) rootView.findViewById(R.id.godfestMessage);
        godfestMessage.setText(Html.fromHtml(GodfestOverview.getGodfestMessage()));
        ListView questionList = (ListView) rootView.findViewById(R.id.godsList);
        GodfestListAdapter GodfestListAdapter = new GodfestListAdapter(context, GodfestOverview.getGodNames(),
                                                    GodfestOverview.getImageURLs());
        questionList.setAdapter(GodfestListAdapter);
    }
}
