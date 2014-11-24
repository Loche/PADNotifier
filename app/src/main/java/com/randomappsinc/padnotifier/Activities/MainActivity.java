package com.randomappsinc.padnotifier.Activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.randomappsinc.padnotifier.Adapters.TabsPagerAdapter;
import com.randomappsinc.padnotifier.Alarms.DataAlarmReceiver;
import com.randomappsinc.padnotifier.Data.DataFetcher;
import com.randomappsinc.padnotifier.R;

import java.util.Calendar;


public class MainActivity extends FragmentActivity implements
        ActionBar.TabListener
{
    public static Context context; // I'm not happy with this, but it works, I guess.
    public static final String METALS_CACHE_FILENAME = "metals_info";
    private static final String TAG = "MainActivity";
    DataAlarmReceiver alarm = new DataAlarmReceiver();

    // private DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    private ViewPager mViewPager;
    private TabsPagerAdapter mAdapter;
    private boolean curled;
    private static final String STATE_CURLED = "isCurled";

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        alarm.setAlarm(this);

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            curled = savedInstanceState.getBoolean(STATE_CURLED);
        }

        // TODO: Make this a nightly job.
        if (curled == false) {
            Log.d(TAG, "NOW RUNNING A CURL.");
            DataFetcher.curlPDXHome();
            DataFetcher.pullEventInfo();
            curled = true;
        }
        context = this;

        setContentView(R.layout.activity_main);

        // Initialization
        mViewPager = (ViewPager) findViewById(R.id.pager);
        ActionBar actionBar = getActionBar();
        actionBar.setIcon(R.drawable.icon_177);

        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener = new ActionBar.TabListener()
        {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // show the given tab
                // on tab selected
                // show respected fragment view
                mViewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
                mViewPager.setCurrentItem(tab.getPosition());
            }
        };

        // Add two tabs, specifying the tab's text and TabListener
        // Metals
        actionBar.addTab(
                actionBar.newTab()
                        .setText("Metals")
                        .setTabListener(tabListener));

        // Godfest
        actionBar.addTab(
                actionBar.newTab()
                        .setText("Godfest")
                        .setTabListener(tabListener));

        // Select corresponding tab on swipe
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(context, SettingsActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
        // on tab selected
        // show respected fragment view
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putBoolean(STATE_CURLED, curled);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
}
