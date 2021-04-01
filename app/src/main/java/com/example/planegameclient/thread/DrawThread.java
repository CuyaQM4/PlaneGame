package com.example.planegameclient.thread;

import android.view.SurfaceHolder;

import com.example.planegameclient.view.GameView;

/**
 * Created by CNH on 2016/10/14.
 */
public class DrawThread extends Thread{
    public boolean flag = false;

    public DrawThread(SurfaceHolder holder, GameView gameView) {
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
