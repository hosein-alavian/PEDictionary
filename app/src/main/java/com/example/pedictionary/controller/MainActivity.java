package com.example.pedictionary.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pedictionary.R;

public class MainActivity extends AppCompatActivity {


    public void newIntent(Activity activity) {
        Intent intent = new Intent(MainActivity.this, activity.getClass());
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container_layout, MainFragment.newInstance())
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
