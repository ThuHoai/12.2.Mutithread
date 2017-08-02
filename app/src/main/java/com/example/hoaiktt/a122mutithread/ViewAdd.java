package com.example.hoaiktt.a122mutithread;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by hoaiktt on 8/2/2017.
 */

public class ViewAdd implements Runnable{
    private LinearLayout mLayoutResult;
    private TextView mViewToAdd;

    public ViewAdd(LinearLayout mLayoutResult, TextView mViewToAdd) {
        this.mLayoutResult = mLayoutResult;
        this.mViewToAdd = mViewToAdd;
    }

    @Override
    public void run() {
            mLayoutResult.addView(mViewToAdd);
    }
}
