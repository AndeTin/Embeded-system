package com.example.lab04;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class DrawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Draw d = new Draw(this);
        setContentView(d);

    }

}