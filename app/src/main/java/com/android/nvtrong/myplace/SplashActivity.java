package com.android.nvtrong.myplace;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.nvtrong.myplace.activity.CategoriesActivity;
import com.android.nvtrong.myplace.data.PlaceSQLiteHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {
    @BindView(R.id.layoutMain)
    RelativeLayout layout;
    @BindView(R.id.imageLogo)
    ImageView imageLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        Animation transition = AnimationUtils.loadAnimation(this, R.anim.transiton_icon);
        imageLogo.setAnimation(transition);
        Animation alpAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_background);
        layout.setAnimation(alpAnimation);
        alpAnimation.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, CategoriesActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
