package com.randomappsinc.padbuddy.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.randomappsinc.padbuddy.Adapters.SelectivePushACAdapter;
import com.randomappsinc.padbuddy.Adapters.SelectivePushAdapter;
import com.randomappsinc.padbuddy.Metals.DungeonMapper;
import com.randomappsinc.padbuddy.Misc.Constants;
import com.randomappsinc.padbuddy.Misc.Util;
import com.randomappsinc.padbuddy.R;

public class SelectivePushActivity extends Activity
{
    private Context context;

    // Views
    private ListView dungeonList;
    private AutoCompleteTextView selectiveAcEditText;
    private DungeonMapper dungeonMapper;
    private Button narrowMetals;
    private static final String PAD_WIKIA_BASE = "http://pad.wikia.com/wiki/";

    public boolean killKeyboard()
    {
        View view = this.getWindow().getDecorView().findViewById(android.R.id.content);
        Rect r = new Rect();
        view.getWindowVisibleDisplayFrame(r);

        int heightDiff = view.getRootView().getHeight() - (r.bottom - r.top);
        if (heightDiff > 100)
        {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        dungeonMapper = DungeonMapper.getDungeonMapper();

        setContentView(R.layout.selective_push_list);

        // Find views
        dungeonList = (ListView) findViewById(R.id.dungeon_list);
        selectiveAcEditText = (AutoCompleteTextView) findViewById(R.id.selective_search_box);
        narrowMetals = (Button) findViewById(R.id.narrow_metals);

        // Set up adapters and listeners
        dungeonList.setAdapter(new SelectivePushAdapter(context, ""));
        SelectivePushACAdapter adapter = new SelectivePushACAdapter(context, android.R.layout.simple_dropdown_item_1line,
                dungeonMapper.getDungeonNamesList());
        selectiveAcEditText.setAdapter(adapter);
        narrowMetals.setOnClickListener(narrowMetalsListener);

        dungeonList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) throws IllegalArgumentException, IllegalStateException
            {
                if (Util.haveInternetConnection(context))
                {
                    String dungeonName = ((TextView) view.findViewById(R.id.dungeon_name)).getText().toString();
                    if (dungeonMapper.getDungeonNamesList().contains(dungeonName))
                    {
                        Intent intent = new Intent(context, WebActivity.class);
                        intent.putExtra(WebActivity.URL_KEY, PAD_WIKIA_BASE + dungeonName.replaceAll(" ", "_"));
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

    View.OnClickListener narrowMetalsListener = new View.OnClickListener() {
        public void onClick(View v) {
            killKeyboard();

            // clear previous results in the metals listview
            dungeonList.setAdapter(null);

            // Get contents of textbox. Check it
            String criteria = selectiveAcEditText.getText().toString();

            SelectivePushAdapter adapter = new SelectivePushAdapter(context, criteria);
            dungeonList.setAdapter(adapter);
            selectiveAcEditText.setText("");
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent(context, SettingsActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        else
        {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent intent = new Intent(context, SettingsActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

