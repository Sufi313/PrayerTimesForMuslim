package net.vorson.muhammadsufwan.prayertimesformuslim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.airbnb.lottie.LottieAnimationView;

import net.vorson.muhammadsufwan.prayertimesformuslim.animationUtil.MyBounceInterpolator;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.MySpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class PrayerViewActivity extends AppCompatActivity{

    private List<String> myList;
    private Animation bounceAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paryer_view);

        final LottieAnimationView lottieAnimationView =  findViewById(R.id.animation_view);
        lottieAnimationView.setImageAssetsFolder("images/");
        lottieAnimationView.setAnimation("world_spin.json");
        lottieAnimationView.playAnimation();

        MySpinner mySpinner = findViewById(R.id.my_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        mySpinner.attachDataSource(dataset);

        bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce_effect);
        double animationDuration = 2 * 1000;
        bounceAnimation.setDuration((long)animationDuration);

        // Use custom animation interpolator to achieve the bounce effect
        MyBounceInterpolator interpolator = new MyBounceInterpolator(2, 10);

    }
}
