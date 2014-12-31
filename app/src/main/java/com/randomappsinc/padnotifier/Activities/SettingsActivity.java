package com.randomappsinc.padnotifier.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.randomappsinc.padnotifier.Adapters.StarterColorSpinnerAdapter;
import com.randomappsinc.padnotifier.Alarms.MetalsAlarmReceiver;
import com.randomappsinc.padnotifier.Misc.Constants;
import com.randomappsinc.padnotifier.Misc.PreferencesManager;
import com.randomappsinc.padnotifier.Misc.Util;
import com.randomappsinc.padnotifier.R;

public class SettingsActivity extends Activity
{
    private Context context;
    private PreferencesManager m_prefs_manager;

    private static final String[] colors = {"Fire", "Water", "Grass"};
    private static final String SETTINGS_SAVED = "Your changes have been saved.";

    // Views
    private Spinner starterColorSpinner;
    private EditText inputtedId;
    private Switch muteSetting;
    private Button selectivePushButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        m_prefs_manager = new PreferencesManager(context);

        setContentView(R.layout.settings);

        // Find views
        starterColorSpinner = ((Spinner) (findViewById(R.id.starter_color_spinner)));
        inputtedId = (EditText) (findViewById(R.id.pad_id));
        muteSetting = (Switch) (findViewById(R.id.muteSwitch));
        selectivePushButton = (Button) (findViewById(R.id.selective_push_button));

        inputtedId.setText(m_prefs_manager.getPadId());
        inputtedId.setSelection(9);
        muteSetting.setChecked(m_prefs_manager.getMuteSetting());

        starterColorSpinner.setAdapter(new StarterColorSpinnerAdapter(SettingsActivity.this,
                R.layout.starter_color_spinner_item, colors));
        starterColorSpinner.setSelection(Integer.parseInt(Character.toString(m_prefs_manager.getStarterColor())) - 1);
        selectivePushButton.setOnClickListener(selectivePushListener);
    }

    View.OnClickListener selectivePushListener = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            if (persistSettings())
            {
                Intent intent = new Intent(context, SelectivePushActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean persistSettings()
    {
        String input = inputtedId.getText().toString();
        if (input.length() != 9)
        {
            Toast.makeText(getApplicationContext(), Constants.INCOMPLETE_PAD_ID, Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            m_prefs_manager.setPadId(input);
            String thirdDigit = Character.toString(input.charAt(2));
            m_prefs_manager.setThirdDigit(thirdDigit);
            String group = Util.digitToGroup(Integer.parseInt(thirdDigit));
            m_prefs_manager.setGroup(group);
            String starterColor = Util.starterColorToChar(starterColorSpinner.getSelectedItem().toString());
            m_prefs_manager.setStarterColor(starterColor);
            m_prefs_manager.setMuteSetting(muteSetting.isChecked());

            // Cancel all of the alarms and set them all again to cover any changes in
            // dungeons' individual alarm settings. (Dungeons' alarms are checked when being set,
            // not when they are received.)
            MetalsAlarmReceiver metalsAlarm = new MetalsAlarmReceiver();
            metalsAlarm.cancelAlarm(this);
            if (!muteSetting.isChecked()) {
                metalsAlarm.setAlarm(this);
            }

            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (persistSettings())
            {
                Toast.makeText(getApplicationContext(), SETTINGS_SAVED, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
            return true;
        }
        else
        {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            if (persistSettings())
            {
                Toast.makeText(getApplicationContext(), SETTINGS_SAVED, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
