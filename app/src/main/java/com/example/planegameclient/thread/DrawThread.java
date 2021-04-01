package com.example.planegameclient.thread;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.planegameclient.view.GameView;

/**
 * Created by CNH on 2016/10/14.
 * 刷帧线程，定时调用gameView的onDraw方法刷新显示界面
 */
public class DrawThread extends Thread{
    //启动线程的标识
    private boolean flag = true;
    //休眠时间
    private int SLEEP_SPAN =50;
    private SurfaceHolder surFaceHolder;
    private GameView view;



    public DrawThread(SurfaceHolder surFaceHolder, GameView view) {
        this.surFaceHolder = surFaceHolder;
        this.view = view;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    //定时加锁调用onDraw方法刷新界面，更新完后解锁
    @Override
    @SuppressLint("WrongCall")
    public void run() {
        Canvas canvas;
        super.run();
        while (flag) {
            canvas = null;
            try {
                //锁定画布
//                canvas = this.surFaceHolder.lockCanvas(null);
                synchronized (this.surFaceHolder) {
                    //调用绘制方法
                    this.view.change_onDraw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    this.surFaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            try {
                Thread.sleep(SLEEP_SPAN);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
