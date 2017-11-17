package com.example.kukiat.noti;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;

import io.netpie.microgear.Microgear;
import io.netpie.microgear.MicrogearEventListener;

/**
 * Created by kukiat on 11/17/2017 AD.
 */

public class BackgroudService extends Service{
    private Microgear microgear = new Microgear(this);
    private String appid = "noti";
    private String key = "6xeLdlHHWBuM49O";
    private String secret = "tzTRtxJbuejASaIBHWD3snUa3";
    private String alias = "client";

    Handler handler;

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("service", "onBlind BackgroudService");
        return null;
    }

    @Override
    public void onCreate() {
        Log.i("service", "create BackgroudService");
    }

    @SuppressLint("HandlerLeak")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MicrogearCallBack callback = new MicrogearCallBack();

        microgear.connect(appid, key, secret, alias);
        microgear.setCallback(callback);
        microgear.subscribe("message");
        Log.i("service", "onStartCommand BackgroudService");

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                String message = bundle.getString("myKey");

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);

                NotificationCompat.Builder n = new NotificationCompat.Builder(getBaseContext())
                        .setContentTitle(message)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.male)
                        .setContentIntent(pIntent);

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                notificationManager.notify(0, n.build());

            }
        };
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i("service", "destroy BackgroudService");
        super.onDestroy();
    }

    class MicrogearCallBack implements MicrogearEventListener {
        @Override
        public void onConnect() {
            Log.i("service", "netpie connect");
        }

        @Override
        public void onMessage(String topic, String message) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", topic+" : "+message);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }

        @Override
        public void onPresent(String token) {
            Log.i("service", "netpie"+token);
        }

        @Override
        public void onAbsent(String token) {
            Log.i("service", "netpie absend");
        }

        @Override
        public void onDisconnect() {
            Log.i("service", "netpie disconnect");
        }

        @Override
        public void onError(String error) {
            Log.i("service", "netpie error");
        }

        @Override
        public void onInfo(String info) {
            Log.i("service", "netpie info");
        }
    }
}
