package com.example.planegameclient.util;

import java.util.Objects;

/**
 * Created by CNH on 2016/10/13.
 */

public class GameData {
    //连接状态,0-未连接，1-已连接，2-游戏开始
    private static int state = 0;
    private Object lock = new Object();
    //第一架飞机的x坐标
    private int rx = 150;
    public int ry = 750;
    public int gx = 460;
    public int gy = 750;

}
