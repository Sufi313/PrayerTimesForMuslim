package com.sufi.prayertimes;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.sufi.prayertimes.util.ScreenUtils;

public class MainActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScreenUtils.lockOrientation(this);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
            finish();
        },4000);

    }

}
