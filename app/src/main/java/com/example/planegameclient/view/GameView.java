package com.example.planegameclient.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.planegameclient.MainActivity;
import com.example.planegameclient.thread.DrawThread;
import com.example.planegameclient.util.GameData;
import com.example.planegameclient.util.Joystick;

import static com.example.planegameclient.util.Constant.xJoystick;
import static com.example.planegameclient.util.Constant.yJoystick;

/**
 * Created by CNH on 2016/10/13.
 * 绘制场景
 * 通过遥感改变按键状态数据
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    MainActivity activity;
    //刷帧线程对象
    DrawThread drawThread;
    //画笔
    Paint paint;
    //遥感中心点
    Point centerPoint = new Point(100, 900);
    //遥感类
    Joystick joystick;
    String str2;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.activity = (MainActivity) context;
        //回调函数，设置生命周期回调接口
        this.getHolder().addCallback(this);
        this.drawThread = new DrawThread(this.getHolder(), this);
        this.paint = new Paint();
        this.joystick = new Joystick(this, this.activity, xJoystick, yJoystick);
    }

    //绘制方法
    public void change_onDraw(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        //游戏未连接
        if (GameData.state == 0) {
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            canvas.drawText(str2,200,700,paint);
        } else if (GameData.state == 1) {
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.WHITE);
            paint.setTextSize(100);
            str2 = "等待开始.....";
            canvas.drawText(str2,150,700,paint);
        } else if (GameData.state == 2) {
            int rx = 0;
            int gx = 0;
            int ry = 0;
            int gy = 0;
            synchronized (this.activity.gameData.lock) {
                rx = this.activity.gameData.rx;
                ry = this.activity.gameData.ry;
                gx = this.activity.gameData.gx;
                gy = this.activity.gameData.gy;
            }
            canvas.drawColor(Color.BLACK);
            //绘制飞机
            canvas.drawBitmap(this.activity.planer, rx, ry, paint);
            canvas.drawBitmap(this.activity.planeg, gx, gy, paint);
            //绘制遥感
            this.joystick.drawJoystick(canvas);
        }

    }

    //触控方法,根据遥感的移动，改变按键的状态数据
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取触点x,y的值
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            //按下
            case MotionEvent.ACTION_DOWN:
                this.joystick.change(x - 20, y - 20);
                break;
            //滑动
            case MotionEvent.ACTION_MOVE:
                this.joystick.change(x - 20, y - 20);
                break;
            //抬起
            case MotionEvent.ACTION_UP:
                //将按键状态修改为0，并重新赋值
                this.activity.keyX = 0;
                this.activity.keyY = 0;
                //回到中心点
                this.joystick.x = this.centerPoint.x;
                this.joystick.y = this.centerPoint.y;
                break;
        }
        return true;

    }

    //创建当前view时调用
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //开启线程
        this.drawThread.setFlag(true);
        //如果后台重绘线程没启动，就启动
        if (!drawThread.isAlive()) {
            drawThread.start();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.drawThread.setFlag(false);
    }
}
