package com.randomappsinc.padnotifier.Data;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

/**
 * Created by Alex on 10/23/2014.
 */
public class PadherderHttpResponseHandler extends AsyncHttpResponseHandler
{
    private static final String TAG = "PDNHttpResponseHandler";

    @Override
    public void onStart()
    {
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] response)
    {
        Log.d(TAG, "SUCCESSFULLY GRABBED PADHERDER EVENT INFO.");
        String content = new String (response);

        DataFetcher.extractPadherderAPIJSON(content);
        // DataFetcher.extractPDXHomeContent(Util.readFile("PDXHomeToday.html"));

        // TODO: VVVVVVVVVVVVVVVVVVVVVVVVVV
//        MetalsAlarmReceiver alarm = new MetalsAlarmReceiver();
//        alarm.cancelAlarm(MainActivity.mContext);
//        alarm.setAlarm(MainActivity.mContext);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e)
    {
        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
        Log.d(TAG, "FAILED TO GRAB PADHERDER EVENT INFO.");
    }

    @Override
    public void onRetry(int retryNo)
    {
        // called request is tried for the retryNo time
        String retryMsg = "Retry #" + retryNo + " to grab Padherder event info.";
        Log.d(TAG, retryMsg);
    }
}
