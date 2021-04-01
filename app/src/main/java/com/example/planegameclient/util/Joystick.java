package com.example.planegameclient.util;

import android.graphics.Canvas;

import com.example.planegameclient.MainActivity;
import com.example.planegameclient.view.GameView;

/**
 * Created by CNH on 2016/10/14.
 */
public class Joystick {
    public MainActivity activity;
    public GameView gameView;
    public int length;
    public float x;
    public float y;

    public Joystick(GameView gameView, MainActivity activity, float x, float y) {
        this.gameView = gameView;
        this.activity = activity;
        this.x = x;
        this.y = y;
    }

    public void change(float v, float v1) {
    }

    public void drawJoystick(Canvas canvas) {
    }
}
