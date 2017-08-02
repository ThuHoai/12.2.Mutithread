package com.example.hoaiktt.a122mutithread;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements Runnable {
    private LinearLayout mResultRegion;
    private final static int HEAD_TIMEOUT = 10;
    private static final int LIMIT_COIN = 500;
    private final static int POOL_SIZE = 5;
    private MaxResult result = new MaxResult();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResultRegion = (LinearLayout) findViewById(R.id.llResultsRegion);
    }

    public void ClickFlippingCoin(View clickButton) throws InterruptedException {
        mResultRegion.removeAllViews();
        mResultRegion.requestLayout();
        ExecutorService taskList = Executors.newFixedThreadPool(100);
        taskList.execute(new ShowResult());

        taskList.shutdown();
        taskList.awaitTermination(HEAD_TIMEOUT, TimeUnit.SECONDS);
        TextView dislayResult = new TextView(this);
        dislayResult.setText("Max consecutive heads:" + result.getmMaxFlippingCoin());
        dislayResult.setTextSize(18);
        dislayResult.setPadding(5, 10, 5, 5);
        dislayResult.setTextColor(Color.BLACK);
        // mResultRegion.addView(dislayResult);
    }

    @Override
    public void run() {
        Random random = new Random();
        int x = 0;
        int Max = 0;
        for (int i = 0; i < LIMIT_COIN; i++) {
            int coin = random.nextInt(2);
            if (coin == 1) {
                x++;
                if (x >= 3) {
                    Max = Max < x ? x : Max;
                }
            } else if (coin == 0) {
                x = 0;
            }
        }
        result.setmMaxFlippingCoin(Max);
    }

    public class ShowResult implements Runnable {

        @Override
        public void run() {
            Random random = new Random();
            TextView viewToAdd;
            int x = 0;
            int Max =0;
            for (int i = 0; i < LIMIT_COIN; i++) {
                int coin = random.nextInt(2);
                if (coin == 1) {
                    x++;
                    Max =x;
                    if (x >= 3) {
                        viewToAdd = new TextView(getApplicationContext());
                        viewToAdd.setText("Max consecutive heads:" + Max);
                        mResultRegion.post(new ViewAdd(mResultRegion, viewToAdd));
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (coin == 0) {
                    x = 0;
                }
            }

        }
    }
}
