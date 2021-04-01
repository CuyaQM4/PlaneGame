package com.example.planegameclient.util;


/**
 * Created by CNH on 2016/10/13.
 */

public class GameData {
    //连接状态,0-未连接，1-已连接，2-游戏开始
    public static int state = 0;
    public Object lock = new Object();
    //分别是第一，二架飞机的x，y坐标
    public int rx = 150;
    public int ry = 750;
    public int gx = 460;
    public int gy = 750;

}
