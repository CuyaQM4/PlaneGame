package com.example.planegameclient.thread;

import android.view.SurfaceHolder;

import com.example.planegameclient.view.GameView;

/**
 * Created by CNH on 2016/10/14.
 * 刷帧线程，定时调用gameView的onDraw方法刷新显示界面
 */
public class DrawThread extends Thread{
    //启动线程的标识
    public boolean flag = false;
    //休眠时间
    public int SLEEP_SPAN =50;


    public DrawThread(SurfaceHolder holder, GameView gameView) {
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
