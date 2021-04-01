package com.example.planegameclient.thread;

import com.example.planegameclient.MainActivity;
import com.example.planegameclient.util.GameData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by CNH on 2016/10/13.
 * 与服务器端建立连接
 * 接收来自服务器端的数据消息
 */
public class NetworkThread extends Thread{
    MainActivity mainActivity;
    Socket socket;
    //数据输入、输出流
    DataInputStream dis;
    DataOutputStream dos;
    boolean flag = true;
    public NetworkThread(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void run() {
        super.run();
        try {
            //与服务器端建立连接
            socket = new Socket("192.168.31.102", 9999);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            //写入标识符
            dos.writeUTF("<#CONNECT#>");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        while (flag) {

            //读取数据信息
            try {
                String msg = dis.readUTF();
                if (msg.startsWith("<#OK#>")) {
                    //游戏已连接
                    GameData.state = 1;
                } else if (msg.startsWith("<#BEGIN#>")) {
                    //游戏开始
                    GameData.state = 2;
                    this.mainActivity.keyThread.start();
                } else if (msg.startsWith("<#FULL#>")) {
                    //客户端已达上限
                    break;
                } else if (msg.startsWith("<#GAME_DATA#>")) {
                    //获取两架飞机的数据
                    String str = msg.substring(13);
                    String[] strA = str.split("\\|");
                    int temprx = Integer.parseInt(strA[0]);
                    int tempry = Integer.parseInt(strA[1]);
                    int tempgx = Integer.parseInt(strA[2]);
                    int tempgy = Integer.parseInt(strA[3]);
                    synchronized (this.mainActivity.gameData.lock) {
                        this.mainActivity.gameData.rx = temprx;
                        this.mainActivity.gameData.ry = tempry;
                        this.mainActivity.gameData.gx = tempgx;
                        this.mainActivity.gameData.gy = tempgy;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            dis.close();
            dos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
