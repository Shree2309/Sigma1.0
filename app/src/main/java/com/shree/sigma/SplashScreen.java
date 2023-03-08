package com.shree.sigma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ImageView logoImg;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        FirebaseApp.initializeApp(this);

        logoImg = findViewById(R.id.logo_img);
        linearLayout = findViewById(R.id.linearlayout1);

        linearLayout.animate().alpha(0f).setDuration(10);
        TranslateAnimation animation = new TranslateAnimation(0,0,0,-1500);
        animation.setDuration(1500);
        animation.setFillAfter(false);
        animation.setAnimationListener(new MyAnimationListener());

        logoImg.setAnimation(animation);


        mAuth = FirebaseAuth.getInstance();


    }
    private class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            logoImg.clearAnimation();
            logoImg.setVisibility(View.INVISIBLE);
            linearLayout.animate().alpha(1f).setDuration(1000);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    FirebaseUser mFirebaseuser = mAuth.getCurrentUser();

                    if(mFirebaseuser!=null){
                        //ther is a user logged  in
                        startActivity(new Intent(SplashScreen.this,HomeActivity.class));
                    }
                    else {
                        //no one logged in
                        startActivity(new Intent(SplashScreen.this,MainActivity.class));
                    }
                    finish();
                }
            },1000);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}