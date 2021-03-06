package com.randomappsinc.padbuddy.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.randomappsinc.padbuddy.Adapters.StarterColorSpinnerAdapter;
import com.randomappsinc.padbuddy.Misc.Constants;
import com.randomappsinc.padbuddy.Misc.PreferencesManager;
import com.randomappsinc.padbuddy.Misc.Util;
import com.randomappsinc.padbuddy.R;


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
            EditText id_edittext = (EditText) findViewById(R.id.pad_id);
            String input = id_edittext.getText().toString();

            if (input.length() != 9)
            {
                Toast.makeText(getApplicationContext(), Constants.INCOMPLETE_PAD_ID, Toast.LENGTH_LONG).show();
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

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
}
