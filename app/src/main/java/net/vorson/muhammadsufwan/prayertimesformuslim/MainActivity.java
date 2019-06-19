package net.vorson.muhammadsufwan.prayertimesformuslim;

import android.content.Intent;
import android.content.res.Configuration;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import net.vorson.muhammadsufwan.prayertimesformuslim.util.ScreenUtils;

public class MainActivity extends AppCompatActivity  {

    private int screenWidthDp = 0;
    private int screenHeightDp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_main);
        ScreenUtils.lockOrientation(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                finish();
            }
        },4000);

        Configuration configuration = this.getResources().getConfiguration();
        screenWidthDp = configuration.screenWidthDp;
        screenHeightDp = configuration.screenHeightDp;

//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(screenWidthDp, screenHeightDp/2);
//        layoutParams.gravity = Gravity.BOTTOM;
//        zoomImage.setLayoutParams(layoutParams);

    }

}
