package com.archer.crazy_circle_games;

import android.content.Intent;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.archer.crazy_circle_games.box_d_ball.Helper;

import java.util.Random;


public class Home extends AppCompatActivity {

    Random objRandom;
    View mBall_1;
    View mBall_2;
    View mBall_3;
    View mBall_4;
    View mBall_5;
    Point ScreenSize;
    Handler mHandler;
    boolean isAnimating;
    private int AvailableHeight;
    private int AvailableWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final ListView gridview = (ListView) findViewById(R.id.gdCards);
        final SelectGameAdapter adapter = new SelectGameAdapter(getApplicationContext());
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ScaleUP_ThenRevert(view.findViewById(R.id.ivCard));
                new CountDownTimer(420, 420) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        StartGame(position);
                        isAnimating=false;
                    }
                }.start();

            }
        });

        mHandler = new Handler();
        objRandom = new Random();

        initializeBalls();
        startAnimations();
    }

    private void startAnimations() {
        animateBall_1();
        animateBall_2();
        animateBall_3();
        animateBall_4();
        animateBall_5();
    }

    private void animateBall_1() {
        long animDuration = 2700;
        mBall_1.animate().setDuration(animDuration);
        new CountDownTimer(1000000,animDuration-27)
        {

            @Override
            public void onTick(long millisUntilFinished) {
                float scale = (float)Math.random();
                float x = objRandom.nextInt(AvailableWidth);
                float y = objRandom.nextInt(AvailableHeight);
                mBall_1.animate()
                        .scaleX(scale)
                        .scaleY(scale)
                        .x(x)
                        .y(y);
            }

            @Override
            public void onFinish() {
            }
        }.start();
    }
    private void animateBall_2() {
        long animDuration = 900;
        mBall_2.animate().setDuration(animDuration);

        new CountDownTimer(1000000,animDuration-27)
        {

            @Override
            public void onTick(long millisUntilFinished) {
                float scale = (float)Math.random();
                float x = objRandom.nextInt(AvailableWidth);
                float y = objRandom.nextInt(AvailableHeight);
                mBall_2.animate()
                        .scaleX(scale)
                        .scaleY(scale)
                        .x(x)
                        .y(y);
            }

            @Override
            public void onFinish() {
            }
        }.start();
    }
    private void animateBall_3() {
        long animDuration = 1200;
        mBall_3.animate().setDuration(animDuration);
        new CountDownTimer(1000000,animDuration-36)
        {

            @Override
            public void onTick(long millisUntilFinished) {
                float scale = (float)Math.random();
                float x = objRandom.nextInt(AvailableWidth);
                float y = objRandom.nextInt(AvailableHeight);
                mBall_3.animate()
                        .scaleX(scale)
                        .scaleY(scale)
                        .x(x)
                        .y(y);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void animateBall_4() {
        long animDuration = 1200;
        mBall_4.animate().setDuration(animDuration);
        new CountDownTimer(1000000,animDuration-36)
        {

            @Override
            public void onTick(long millisUntilFinished) {
                float scale = (float)Math.random();
                float x = objRandom.nextInt(AvailableWidth);
                float y = objRandom.nextInt(AvailableHeight);
                mBall_4.animate()
                        .scaleX(scale)
                        .scaleY(scale)
                        .x(x)
                        .y(y);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
    private void animateBall_5(){
        long animDuration = 1200;
        mBall_5.animate().setDuration(animDuration);
        new CountDownTimer(1000000,animDuration-36)
        {

            @Override
            public void onTick(long millisUntilFinished) {
                float scale = (float)Math.random();
                float x = objRandom.nextInt(AvailableWidth);
                float y = objRandom.nextInt(AvailableHeight);
                mBall_5.animate()
                        .scaleX(scale)
                        .scaleY(scale)
                        .x(x)
                        .y(y);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
    private void initializeBalls() {
        mBall_1 = findViewById(R.id.ball_1);
        mBall_2 = findViewById(R.id.ball_2);
        mBall_3 = findViewById(R.id.ball_3);
        mBall_4 = findViewById(R.id.ball_4);
        mBall_5 = findViewById(R.id.ball_5);


        ScreenSize = Helper.getWindowSize(getWindowManager().getDefaultDisplay());
        AvailableHeight = (int)(2.52f*ScreenSize.y/10 - Helper.ConvertToPx(getApplicationContext(),27));
        AvailableWidth = ScreenSize.x - Helper.ConvertToPx(getApplicationContext(),27);
    }

    public void ScaleUP_ThenRevert(final View v)
    {
        if(isAnimating)
            return;
        isAnimating = true;

        final int duration = 200;
        v.animate()
                .scaleX(1.18f)
                .scaleY(1.18f)
                .setDuration(duration);

        new CountDownTimer(202,202) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                v.animate()
                        .scaleX(1)
                        .scaleY(1)
                        .setDuration(200);

            }
        }.start();
    }

    public void StartGame(int position)
    {
        switch (position) {
            case 0:
                Intent box_d_ball = new Intent(
                        Home.this, com.archer.crazy_circle_games.box_d_ball.MainActivity.class);
                startActivity(box_d_ball);
                break;
            case 1:
                Intent circle_run = new Intent(
                        Home.this, com.archer.crazy_circle_games.circle_run.MainActivity.class);
                startActivity(circle_run);
                break;
            case 2:
                Intent survival_circle = new Intent(
                        Home.this, com.archer.crazy_circle_games.survival_circle.MainActivity.class);
                startActivity(survival_circle);
                break;
            case 3:
                Intent survival_circle_2 = new Intent(
                        Home.this, com.archer.crazy_circle_games.survival_circle_2.MainActivity.class);
                startActivity(survival_circle_2);
                break;
        }

        finish();
    }

}
