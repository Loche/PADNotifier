package com.randomappsinc.padnotifier.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.randomappsinc.padnotifier.Adapters.StarterColorSpinnerAdapter;
import com.randomappsinc.padnotifier.Misc.Util;
import com.randomappsinc.padnotifier.R;

import java.io.File;


public class LoginActivity extends Activity {

    private Context context;
    private SharedPreferences prefs;

    // Intent keys
    public static final String groupKey = "com.randomappsinc.padnotifier.group";
    public static final String starterColorKey = "com.randomappsinc.padnotifier.starterColor";

    private static final String TAG = "LoginActivity";
    private static final String[] colors = {"Fire","Water", "Grass"};

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
        prefs = context.getSharedPreferences("com.randomappsinc.padnotifier", Context.MODE_PRIVATE);

        String group = prefs.getString(groupKey, "");
        if (!group.isEmpty())
        {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(groupKey, group);
            startActivity(intent);
            finish();
        }

        submitButton = (Button) (findViewById(R.id.id_req_submit_button));
        submitButton.setOnClickListener(loginSubmitListener);
        starterColorSpinner = ((Spinner) (findViewById(R.id.starter_color_spinner)));
        starterColorSpinner.
                setAdapter(new StarterColorSpinnerAdapter(LoginActivity.this, R.layout.starter_color_spinner_item, colors));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    View.OnClickListener loginSubmitListener = new View.OnClickListener() {
        public void onClick(View v) {
            EditText id_edittext = (EditText) findViewById(R.id.user_id_group_determiner);
            String input = id_edittext.getText().toString();

            if (input.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter your PAD ID's third digit!", Toast.LENGTH_LONG).show();
            }
            else {
                String group = Util.digitToGroup(Integer.parseInt(input));
                prefs.edit().putString(groupKey, group).apply();

                String starterColor = Util.starterColorToChar(starterColorSpinner.getSelectedItem().toString());
                prefs.edit().putString(groupKey, starterColor);

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(groupKey, group);
                intent.putExtra(starterColorKey, starterColor);
                startActivity(intent);
                finish();
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
