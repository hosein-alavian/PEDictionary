package com.example.pedictionary.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.pedictionary.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container_layout,MainFragment.newInstance())
                .commit();
    }

    public void newIntent(Activity activity){
        Intent intent=new Intent(MainActivity.this,activity.getClass());
        startActivity(intent);
    }
}
