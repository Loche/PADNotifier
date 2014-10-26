package com.randomappsinc.padnotifier;

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
import android.widget.Toast;


public class LoginActivity extends Activity {

    private Context context;
    SharedPreferences prefs;
    public static final String groupKey = "com.randomappsinc.padnotifier.group";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        context = this;
        prefs = context.getSharedPreferences(
                "com.randomappsinc.padnotifier", Context.MODE_PRIVATE);

        String group = prefs.getString(groupKey, "");
        if (group.isEmpty())
            System.out.println("No ID detected.");
        else {
            System.out.println("You are in group: " + group);

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(groupKey, group);
            startActivity(intent);
            finish();
        }

        ((Button) (findViewById(R.id.id_req_submit_button))).setOnClickListener(loginSubmitListener);
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
                int group_determiner = Integer.parseInt(input);

                // 9 to 4, 8 to 3, 7 to 2, 1 to 1, etc.
                group_determiner %= 5;

                String group = "" + (char) (group_determiner + (int) 'A');

                prefs.edit().putString(groupKey, group).apply();

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(groupKey, group);
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
