package com.randomappsinc.padnotifier.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.randomappsinc.padnotifier.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Alex on 11/27/2014.
 */
public class WebActivity extends Activity
{
    Context context;
    public static final String URL_KEY = "URL";
    boolean loadingFinished = true;
    boolean redirect = false;
    private static WebView webView;
    public static String padInfoUrl;
    public static ProgressBar progressBar;
    private static String data;
    private static final String PAD_WIKIA_BASE = "http://pad.wikia.com/wiki/";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        data = null;
        setContentView(R.layout.webview);
        progressBar = (ProgressBar) findViewById(R.id.web_progress_bar);

        Intent intent = getIntent();
        padInfoUrl = intent.getStringExtra(URL_KEY);
        webView = new WebView(this);
        configureSettings();

        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urlNewString)
            {
                if (!loadingFinished)
                {
                    redirect = true;
                }

                loadingFinished = false;
                data = null;
                padInfoUrl = urlNewString;
                new getWebPage().execute("This string isn't used.");
                // Can't call webview methods outside main thread, so we need to wait for the async task to finish. Yeah...
                while (data == null) {}
                webView.loadDataWithBaseURL(PAD_WIKIA_BASE, data, null, "UTF-8", null);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                loadingFinished = false;
                setContentView(R.layout.webview);
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                if (!redirect)
                {
                    loadingFinished = true;
                }

                if (loadingFinished && !redirect)
                {
                    configureSettings();
                    setContentView(webView);
                }
                else
                {
                    redirect = false;
                }
            }
        });
        new getWebPage().execute("This string isn't used.");
        // Can't call webview methods outside main thread, so we need to wait for the async task to finish. Yeah...
        while (data == null) {}
        webView.loadDataWithBaseURL(PAD_WIKIA_BASE, data, null, "UTF-8", null);
    }

    private static void configureSettings()
    {
        WebSettings settings = webView.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
    }

    private static class getWebPage extends AsyncTask<String, Integer, Long>
    {
        @Override
        protected Long doInBackground(String... strings)
        {
            HttpGet httpget = new HttpGet(padInfoUrl);
            HttpClient client = new DefaultHttpClient();
            try
            {
                HttpResponse response = client.execute(httpget);
                int status = response.getStatusLine().getStatusCode();
                if (status == 200)
                {
                    HttpEntity entity = response.getEntity();
                    data = EntityUtils.toString(entity);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return new Long(-1);
            }
            return null;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Long aLong)
        {
            super.onPostExecute(aLong);
            progressBar.setVisibility(View.GONE);
        }
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
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
