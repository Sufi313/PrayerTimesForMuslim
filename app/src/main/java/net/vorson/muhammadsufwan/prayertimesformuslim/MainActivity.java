package net.vorson.muhammadsufwan.prayertimesformuslim;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.vorson.muhammadsufwan.prayertimesformuslim.models.Rotatable;
import net.vorson.muhammadsufwan.prayertimesformuslim.rotatingText.RotatingTextWrapper;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.ScreenUtils;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {

    private Animation zoomIn;
    private ImageView zoomImage;
    private RotatingTextWrapper rotatingText;
    private TextView textZoom;
    private int screenWidthDp = 0;
    private int screenHeightDp = 0;
    private boolean isBackOfCardShowing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_main);
        ScreenUtils.lockOrientation(this);

        zoomImage = findViewById(R.id.imageZoom);
        rotatingText = findViewById(R.id.rotatableText);
        textZoom = findViewById(R.id.zoom_inText);
        Glide.with(this).load(R.drawable.world_spin).into(zoomImage);

        zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
//        zoomIn.setAnimationListener(this);

        startActivity(new Intent(MainActivity.this,PrayerViewActivity.class));

        rotatingText.setSize(30);
        Rotatable rotatable = new Rotatable(Color.parseColor("#FFFFFF"), 2500, "حيَّ على الفلاح", "Come To The Success");
        rotatable.setSize(30);
        rotatable.setCenter(true);
        rotatable.setAnimationDuration(500);

        rotatingText.setContent("", rotatable);

        Configuration configuration = this.getResources().getConfiguration();
        screenWidthDp = configuration.screenWidthDp;
        screenHeightDp = configuration.screenHeightDp;

//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(screenWidthDp, screenHeightDp/2);
//        layoutParams.gravity = Gravity.BOTTOM;
//        zoomImage.setLayoutParams(layoutParams);

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        textZoom.setText("");
        rotatingText.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,PrayerViewActivity.class));
            }
        },4000);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
