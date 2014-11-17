package com.randomappsinc.padnotifier.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.randomappsinc.padnotifier.Misc.PreferencesManager;
import com.randomappsinc.padnotifier.Misc.Util;
import com.randomappsinc.padnotifier.R;

public class SettingsActivity extends Activity
{
    private Context context;
    private PreferencesManager m_prefs_manager;

    private static final String[] colors = {"Fire", "Water", "Grass"};

    // Views
    private Spinner starterColorSpinner;
    private EditText inputtedId;
    private Switch muteSetting;
    private Button saveSettingsButton;

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
        inputtedId = (EditText) (findViewById(R.id.user_id_group_determiner));
        muteSetting = (Switch) (findViewById(R.id.muteSwitch));
        saveSettingsButton = (Button) (findViewById(R.id.save_settings_button));

        inputtedId.setText(m_prefs_manager.getThirdDigit());
        inputtedId.setSelection(1);
        muteSetting.setChecked(m_prefs_manager.getMuteSetting());

        starterColorSpinner.setAdapter(new StarterColorSpinnerAdapter(SettingsActivity.this,
                R.layout.starter_color_spinner_item, colors));
        starterColorSpinner.setSelection(Integer.parseInt(Character.toString(m_prefs_manager.getStarterColor())) - 1);
        saveSettingsButton.setOnClickListener(settingsSubmitListener);
    }

    View.OnClickListener settingsSubmitListener = new View.OnClickListener() {
        public void onClick(View v) {
            EditText id_edittext = (EditText) findViewById(R.id.user_id_group_determiner);
            String input = id_edittext.getText().toString();

            if (input.isEmpty())
            {
                Toast.makeText(getApplicationContext(), "Please enter your PAD ID's third digit!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                m_prefs_manager.setThirdDigit(input);

                String group = Util.digitToGroup(Integer.parseInt(input));
                m_prefs_manager.setGroup(group);

                String starterColor = Util.starterColorToChar(starterColorSpinner.getSelectedItem().toString());
                m_prefs_manager.setStarterColor(starterColor);

                m_prefs_manager.setMuteSetting(muteSetting.isChecked());

                Toast.makeText(getApplicationContext(), "Your settings changes have been saved.", Toast.LENGTH_LONG).show();
            }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
