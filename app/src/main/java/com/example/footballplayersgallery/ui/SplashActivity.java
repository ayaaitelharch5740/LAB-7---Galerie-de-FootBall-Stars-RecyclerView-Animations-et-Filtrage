package com.example.footballplayersgallery.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.footballplayersgallery.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView   ball     = findViewById(R.id.splash_ball);
        TextView    title    = findViewById(R.id.splash_title);
        TextView    tagline  = findViewById(R.id.splash_tagline);
        ProgressBar progress = findViewById(R.id.splash_progress);

        // ── Ballon tombe depuis le haut avec rebond ──
        ball.setTranslationY(-900f);
        ball.setAlpha(0f);

        ObjectAnimator fadeIn   = ObjectAnimator.ofFloat(ball, "alpha", 0f, 1f);
        fadeIn.setDuration(300);

        ObjectAnimator fallDown = ObjectAnimator.ofFloat(ball, "translationY", -900f, 0f);
        fallDown.setDuration(1000);
        fallDown.setInterpolator(new BounceInterpolator());

        // ── Rotation (effet roulement) ──
        ObjectAnimator rotate = ObjectAnimator.ofFloat(ball, "rotation", 0f, 720f);
        rotate.setDuration(1400);
        rotate.setInterpolator(new AccelerateDecelerateInterpolator());

        // ── Zoom d'apparition ──
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(ball, "scaleX", 0.3f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(ball, "scaleY", 0.3f, 1f);
        scaleX.setDuration(700);
        scaleY.setDuration(700);

        // ── Rebond final ──
        ObjectAnimator bounce = ObjectAnimator.ofFloat(ball, "translationY", 0f, -50f, 0f, -25f, 0f);
        bounce.setDuration(900);
        bounce.setInterpolator(new AccelerateDecelerateInterpolator());

        // ── Lancer les animations ──
        AnimatorSet set = new AnimatorSet();
        set.play(fallDown).with(rotate).with(scaleX).with(scaleY).with(fadeIn);
        set.play(bounce).after(fallDown);
        set.start();

        // ── Titre et tagline en fondu décalé ──
        title.setAlpha(0f);
        tagline.setAlpha(0f);
        progress.setAlpha(0f);

        title.animate().alpha(1f).setStartDelay(900).setDuration(600).start();
        tagline.animate().alpha(1f).setStartDelay(1300).setDuration(600).start();
        progress.animate().alpha(1f).setStartDelay(1500).setDuration(500).start();

        // ── Redirection après 3.5s ──
        ball.postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, 3500);
    }
}