package com.armyof2.poll4bunk;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity{
    ImageView logoView;
    Animation fade;
    Thread timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logoView = (ImageView) findViewById(R.id.imageView);
        fade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        logoView.startAnimation(fade);

        timer = new Thread(){

            @Override
            public void run() {
                try {
                    sleep(10000);
                    Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(intent);
                    finish();
                    super.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();
    }
}
