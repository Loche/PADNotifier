package com.randomappsinc.padnotifier;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

/**
 * Created by Alex on 10/23/2014.
 */
public class PDNHttpResponseHandler extends AsyncHttpResponseHandler
{
    @Override
    public void onStart()
    {
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] response)
    {
        Log.d("FOR NARNIA", "SUCCESSFULLY CURLED THAT ISH.");
        String content = new String (response);
        // DataFetcher.extractPDXHomeContent(content);
        DataFetcher.extractPDXHomeContent(Util.readFile("PDXHomeToday.html"));
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e)
    {
        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
    }

    @Override
    public void onRetry(int retryNo)
    {
        // called when request is retried
    }
}
