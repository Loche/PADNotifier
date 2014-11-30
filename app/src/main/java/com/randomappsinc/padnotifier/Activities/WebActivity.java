package com.randomappsinc.padnotifier.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.randomappsinc.padnotifier.R;

/**
 * Created by Alex on 11/27/2014.
 */
public class WebActivity extends Activity
{
    Context context;
    public static final String URL_KEY = "URL";
    boolean loadingFinished = true;
    boolean redirect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        setContentView(R.layout.webview);
        Intent intent = getIntent();
        String dungeonOverviewUrl = intent.getStringExtra(URL_KEY);
        final WebView webview = new WebView(this);
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urlNewString){

                if (!loadingFinished) {
                    redirect = true;
                }

                loadingFinished = false;
                webview.loadUrl(urlNewString);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                loadingFinished = false;
                setContentView(R.layout.webview);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if(!redirect){
                    loadingFinished = true;
                }

                if(loadingFinished && !redirect)
                {
                    setContentView(webview);
                }
                else
                {
                    redirect = false;
                }

            }
        });
        webview.getSettings().setUseWideViewPort(true);
        webview.loadUrl(dungeonOverviewUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
