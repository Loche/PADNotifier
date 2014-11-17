package com.randomappsinc.padnotifier.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.randomappsinc.padnotifier.Adapters.StarterColorSpinnerAdapter;
import com.randomappsinc.padnotifier.Misc.PreferencesManager;
import com.randomappsinc.padnotifier.Misc.Util;
import com.randomappsinc.padnotifier.R;

import java.io.File;


public class LoginActivity extends Activity {

    private Context context;
    private PreferencesManager m_prefs_manager;

    private static final String[] colors = {"Fire", "Water", "Grass"};

    // Views
    private Button submitButton;
    private Spinner starterColorSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
        {
            File Dir = new File(android.os.Environment.getExternalStorageDirectory(), "PADNotifier");
            if (!Dir.exists()) // if directory is not here
            {
                Dir.mkdirs(); // make directory
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        context = this;
        m_prefs_manager = new PreferencesManager(context);

        if (m_prefs_manager.getGroup() != null)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        submitButton = (Button) (findViewById(R.id.id_req_submit_button));
        submitButton.setOnClickListener(loginSubmitListener);
        starterColorSpinner = ((Spinner) (findViewById(R.id.starter_color_spinner)));
        starterColorSpinner.
                setAdapter(new StarterColorSpinnerAdapter(LoginActivity.this, R.layout.starter_color_spinner_item, colors));
    }

    View.OnClickListener loginSubmitListener = new View.OnClickListener() {
        public void onClick(View v) {
            EditText id_edittext = (EditText) findViewById(R.id.user_id_group_determiner);
            String input = id_edittext.getText().toString();

            if (input.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter your PAD ID's third digit!", Toast.LENGTH_LONG).show();
            }
            else {
                m_prefs_manager.setThirdDigit(input);

                String group = Util.digitToGroup(Integer.parseInt(input));
                m_prefs_manager.setGroup(group);

                String starterColor = Util.starterColorToChar(starterColorSpinner.getSelectedItem().toString());
                m_prefs_manager.setStarterColor(starterColor);

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
}
