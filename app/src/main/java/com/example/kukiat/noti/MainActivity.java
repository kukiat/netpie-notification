package com.example.kukiat.noti;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import io.netpie.microgear.Microgear;
import io.netpie.microgear.MicrogearEventListener;

public class MainActivity extends AppCompatActivity {

    private Microgear microgear = new Microgear(this);
    private String appid = "noti";
    private String key = "6xeLdlHHWBuM49O";
    private String secret = "tzTRtxJbuejASaIBHWD3snUa3";
    private String alias = "client";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void sendNoti(View v) {
        Log.i("click", "test");
    }
    public void startService(View v) {
        startService(new Intent(MainActivity.this, BackgroudService.class));
    }

}

