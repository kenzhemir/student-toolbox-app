package com.mirka.app.studenttoolboxrevised;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mirka.app.studenttoolboxrevised.data.MoodleContract;
import com.mirka.app.studenttoolboxrevised.data.MoodleDbHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sectionOnClickListener(View view){
        int view_id = view.getId();
        boolean isEnabled = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getResources().getString(R.string.pref_moodle_enabled_key), getResources().getBoolean(R.bool.pref_enable_moodle_default));
        if (view_id == R.id.tv_thumbnail_courses){
            if (isEnabled && !isLoggedIn()) {
                startActivity(new Intent(this, MoodleLoginActivity.class));
            } else {
                startActivity(new Intent(this));
            }
        } else {
            Toast.makeText(this, view.getId() + " clicked", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int item_id = item.getItemId();

        if (item_id == R.id.action_settings){
            startActivity(new Intent(this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isLoggedIn() {
        Cursor c = new MoodleDbHelper(this).getWritableDatabase().query(MoodleContract.UserEntry.TABLE_NAME,null,null,null,null,null,null);
        return c.getCount() != 0;
    }
}
