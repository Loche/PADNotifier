package com.randomappsinc.padnotifier.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
public class DungeonOverviewActivity extends Activity
{
    Context context;
    public static final String DUNGEON_URL_KEY = "dungeonOverviewUrl";
    boolean loadingFinished = true;
    boolean redirect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        setContentView(R.layout.dungeon_overview);
        Intent intent = getIntent();
        String dungeonOverviewUrl = intent.getStringExtra(DUNGEON_URL_KEY);
        final WebView webview = new WebView(this);
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urlNewString){

                if (!loadingFinished) {
                    redirect = true;
                }

                loadingFinished = false;
                webview.loadUrl(urlNewString);
                return true;
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
