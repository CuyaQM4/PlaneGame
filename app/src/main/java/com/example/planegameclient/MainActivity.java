package com.example.planegameclient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.planegameclient.thread.KeyThread;
import com.example.planegameclient.thread.NetworkThread;
import com.example.planegameclient.util.GameData;
import com.example.planegameclient.view.GameView;

public class MainActivity extends AppCompatActivity {

    //按键x，y的状态值
    public int keyX;
    public int keyY;

    public Bitmap planer;
    public Bitmap planeg;
    //数据类对象
    public GameData gameData = new GameData();
    //界面显示类对象
    GameView gameView;
    //网络数据接收线程
    public NetworkThread networkThread;
    public KeyThread keyThread = new KeyThread(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
        ,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //获取飞机图片
        planer = BitmapFactory.decodeResource(getResources(), R.mipmap.red);
        planeg = BitmapFactory.decodeResource(getResources(), R.mipmap.yellow);
        gameView = (GameView) findViewById(R.id.gameView);
    }

    //选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //初始化menu对象
        getMenuInflater().inflate(R.menu.activity_menu,menu);
        return true;
    }

    //菜单项的监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_connect){
            if (this.networkThread == null){
                this.networkThread = new NetworkThread(MainActivity.this);
                this.networkThread.start();
            }
        }
        return true;
    }

}
