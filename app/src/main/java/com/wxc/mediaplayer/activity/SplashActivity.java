package com.wxc.mediaplayer.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.wxc.mediaplayer.R;

public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler();


    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (isFirst)
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity();
                    finish();
                }
            }, 3000);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        startActivity();
        finish();
        return super.onTouchEvent(event);
    }

    public void startActivity() {
        if (isFirst) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            isFirst = false;
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        finish();
        super.onDestroy();
    }
}
