package com.example.planegameclient.thread;

import com.example.planegameclient.MainActivity;
import com.example.planegameclient.util.GameData;

import java.io.IOException;

/**
 * Created by CNH on 2016/10/14.
 * 按键状态
 * 定时扫描按键状态
 * 并通过流对象像服务器发送状态数据
 */

public class KeyThread extends Thread {
    static final int TIME_SPAN = 100;
    MainActivity activity;
    boolean flag = true;

    public KeyThread(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        super.run();
        while (flag) {
            try {
                //如果游戏正在进行
                if (GameData.state == 2) {
                    activity.networkThread.dos.writeUTF("<#KEY#>"+activity.keyX+"|"+activity.keyY);
                }
                Thread.sleep(TIME_SPAN);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
