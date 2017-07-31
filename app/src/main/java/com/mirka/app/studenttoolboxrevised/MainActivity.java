package com.mirka.app.studenttoolboxrevised;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sectionOnClickListener(View view){
        int view_id = view.getId();

        if (view_id == R.id.tv_thumbnail_courses){
            startActivity(new Intent(this, MoodleLoginActivity.class));
        } else {
            Toast.makeText(this, view.getId() + " clicked", Toast.LENGTH_SHORT).show();
        }
    }
}
