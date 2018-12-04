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

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;

import net.vorson.muhammadsufwan.prayertimesformuslim.models.Rotatable;
import net.vorson.muhammadsufwan.prayertimesformuslim.rotatingText.RotatingTextWrapper;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {

    private Animation middleToLeft ,middleToRight ,zoomIn;
    private ImageView leftDoor, rightDoor, zoomImage;
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

        leftDoor = findViewById(R.id.imageDoorLeft);
        rightDoor = findViewById(R.id.imageDoorRight);
        zoomImage = findViewById(R.id.imageZoom);
        rotatingText = findViewById(R.id.rotatableText);
        textZoom = findViewById(R.id.zoom_inText);
        textZoom.setVisibility(View.GONE);



        middleToLeft = AnimationUtils.loadAnimation(this, R.anim.from_middle_to_left);
        middleToRight = AnimationUtils.loadAnimation(this, R.anim.from_middle_to_right);
        zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        middleToLeft.setAnimationListener(this);
        middleToRight.setAnimationListener(this);

        leftDoor.setAnimation(middleToLeft);
        rightDoor.setAnimation(middleToRight);

        leftDoor.startAnimation(middleToLeft);
        rightDoor.startAnimation(middleToRight);

        rotatingText.setSize(30);

        Rotatable rotatable = new Rotatable(Color.parseColor("#FFFFFF"), 2500, "حيَّ على الفلاح", "Come To The Success");
        rotatable.setSize(30);
        rotatable.setCenter(true);
        rotatable.setAnimationDuration(500);

        rotatingText.setContent("", rotatable);

        Configuration configuration = this.getResources().getConfiguration();
        screenWidthDp = configuration.screenWidthDp;
        screenHeightDp = configuration.screenHeightDp;

        Glide.with(this).load(R.drawable.world_spin).into(zoomImage);

//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(screenWidthDp, screenHeightDp/2);
//        layoutParams.gravity = Gravity.BOTTOM;
//        zoomImage.setLayoutParams(layoutParams);

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        leftDoor.setVisibility(View.INVISIBLE);
        rightDoor.setVisibility(View.INVISIBLE);
        if (leftDoor.getVisibility() == View.INVISIBLE) {
            textZoom.setVisibility(View.VISIBLE);
            textZoom.setAnimation(zoomIn);
            textZoom.startAnimation(zoomIn);

            zoomIn.setAnimationListener(new Animation.AnimationListener() {
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
            });

        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
