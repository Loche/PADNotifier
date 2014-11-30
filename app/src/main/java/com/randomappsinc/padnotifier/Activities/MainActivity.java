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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.randomappsinc.padnotifier.Adapters.TabsPagerAdapter;
import com.randomappsinc.padnotifier.Alarms.DataAlarmReceiver;
import com.randomappsinc.padnotifier.Fragments.GodfestFragment;
import com.randomappsinc.padnotifier.Fragments.MetalsFragment;
import com.randomappsinc.padnotifier.Metals.DungeonMapper;
import com.randomappsinc.padnotifier.Misc.Util;
import com.randomappsinc.padnotifier.R;


public class MainActivity extends FragmentActivity implements
        ActionBar.TabListener
{
    private static Context context;
    private static DungeonMapper dungeonMapper;
    private static final String TAG = "MainActivity";
    private static final String PAD_WIKIA_BASE = "http://pad.wikia.com/wiki/";
    DataAlarmReceiver alarm = new DataAlarmReceiver();

    // private DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    private ViewPager mViewPager;
    private TabsPagerAdapter mAdapter;
    private boolean curled;
    private static final String STATE_CURLED = "isCurled";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        alarm.setAlarm(this);

        super.onCreate(savedInstanceState);

        Log.d(TAG, "MainActivity is created.");
        dungeonMapper = DungeonMapper.getDungeonMapper();

        if (savedInstanceState != null)
        {
            curled = savedInstanceState.getBoolean(STATE_CURLED);
        }

        // TODO: Make this a nightly job.
        if (curled == false)
        {
            Log.d(TAG, "NOW RUNNING A CURL.");
            // DataFetcher.curlPDXHome();
            // DataFetcher.pullEventInfo();
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
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft)
            {
                // show the given tab
                // on tab selected
                // show respected fragment view
                mViewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft)
            {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft)
            {
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
                new ViewPager.SimpleOnPageChangeListener()
                {
                    @Override
                    public void onPageSelected(int position)
                    {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                }
        );
    }

    public static void setUpMetalsListener()
    {
        ListView metalsList = MetalsFragment.getMetalsList();
        metalsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) throws IllegalArgumentException, IllegalStateException
            {
                String dungeonName = ((TextView) view.findViewById(R.id.metal_title)).getText().toString();
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra(WebActivity.URL_KEY,
                        PAD_WIKIA_BASE + dungeonName.replaceAll(" ", "_"));
                context.startActivity(intent);
            }
        });
    }

    public static void setUpGodsListener()
    {
        ListView godsList = GodfestFragment.getGodsList();
        godsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) throws IllegalArgumentException, IllegalStateException
            {
                String godName = ((TextView) view.findViewById(R.id.god_title)).getText().toString();
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra(WebActivity.URL_KEY, PAD_WIKIA_BASE + Util.cleanGodName(godName));
                context.startActivity(intent);
            }
        });
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
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_settings:
                Intent intent = new Intent(context, SettingsActivity.class);
                startActivity(intent);
            case R.id.action_refresh:
                MetalsFragment.refreshView();
                GodfestFragment.refreshView();
                break;
            default:
                break;
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
