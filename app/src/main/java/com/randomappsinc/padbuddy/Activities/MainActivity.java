package com.randomappsinc.padbuddy.Activities;

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
import android.widget.Toast;

import com.randomappsinc.padbuddy.Adapters.TabsPagerAdapter;
import com.randomappsinc.padbuddy.Alarms.DataAlarmReceiver;
import com.randomappsinc.padbuddy.Alarms.MetalsAlarmReceiver;
import com.randomappsinc.padbuddy.Fragments.GodfestFragment;
import com.randomappsinc.padbuddy.Fragments.MetalsFragment;
import com.randomappsinc.padbuddy.Metals.DungeonMapper;
import com.randomappsinc.padbuddy.Misc.Constants;
import com.randomappsinc.padbuddy.Misc.Util;
import com.randomappsinc.padbuddy.R;


public class MainActivity extends FragmentActivity implements
        ActionBar.TabListener
{
    private static Context context;
    private static DungeonMapper dungeonMapper;
    private static final String TAG = "MainActivity";
    DataAlarmReceiver alarm = new DataAlarmReceiver();
    MetalsAlarmReceiver metalsAlarm = new MetalsAlarmReceiver();

    private ViewPager mViewPager;
    private TabsPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        alarm.setAlarm(this);

        super.onCreate(savedInstanceState);

        Log.d(TAG, "MainActivity is created.");
        dungeonMapper = DungeonMapper.getDungeonMapper();
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
                if (Util.haveInternetConnection(context))
                {
                    String dungeonName = ((TextView) view.findViewById(R.id.metal_title)).getText().toString();
                    if (dungeonMapper.getDungeonNamesList().contains(dungeonName))
                    {
                        Intent intent = new Intent(context, WebActivity.class);
                        intent.putExtra(WebActivity.URL_KEY, Constants.PAD_WIKIA_BASE + dungeonName.replaceAll(" ", "_"));
                        context.startActivity(intent);
                    }
                }
                else
                {
                    Toast.makeText(context, Constants.NO_INTERNET, Toast.LENGTH_LONG).show();
                }
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
                if (Util.haveInternetConnection(context))
                {
                    String godName = Util.cleanGodName(((TextView) view.findViewById(R.id.god_title)).getText().toString());
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra(WebActivity.URL_KEY, Constants.PAD_WIKIA_BASE + godName);
                    context.startActivity(intent);
                }
                else
                {
                    Toast.makeText(context, Constants.NO_INTERNET, Toast.LENGTH_LONG).show();
                }
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
        Intent intent;
        switch (id)
        {
            case R.id.action_settings:
                intent = new Intent(context, SettingsActivity.class);
                startActivity(intent);
                GodfestFragment.killCountDownTimer();
                finish();
                break;
            case R.id.action_refresh:
                MetalsFragment.refreshView();
                GodfestFragment.refreshView();
                metalsAlarm.setAlarm(this);
                break;
            case R.id.action_find_friends:
                intent = new Intent(context, MonsterSearchActivity.class);
                startActivity(intent);
                GodfestFragment.killCountDownTimer();
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }
}
