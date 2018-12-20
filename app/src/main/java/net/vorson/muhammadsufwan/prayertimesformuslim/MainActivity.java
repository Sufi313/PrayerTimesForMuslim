package net.vorson.muhammadsufwan.prayertimesformuslim;

import android.content.Intent;
import android.content.res.Configuration;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import net.vorson.muhammadsufwan.prayertimesformuslim.util.ScreenUtils;

public class MainActivity extends AppCompatActivity  {


    private ImageView zoomImage;
    private int screenWidthDp = 0;
    private int screenHeightDp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_main);
        ScreenUtils.lockOrientation(this);

        zoomImage = findViewById(R.id.imageZoom);
        Glide.with(this).load(R.drawable.world_spin).into(zoomImage);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,HomeActivity.class));
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
