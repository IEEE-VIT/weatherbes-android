package com.example.weather;

import static android.view.animation.AnimationUtils.*;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    Animation topAnim, bottomAnim;
    ImageView icon;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        topAnim = loadAnimation(this,R.anim.top_animation);
        bottomAnim = loadAnimation(this,R.anim.bottom_animation);

        icon = findViewById(R.id.imageView2);
        name = findViewById(R.id.textView);


        icon.setAnimation(topAnim);
        name.setAnimation(bottomAnim);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }


        }, 4000);
    }
}
