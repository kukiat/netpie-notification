package com.example.kukiat.noti;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String message = bundle.getString("myKey");
            TextView tv = (TextView)findViewById(R.id.textView);

            tv.append(message+"\n");

//            Notification notification = new NotificationCompat.Builder(getApplicationContext()) // this is context
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setContentTitle("wdwdwd")
//                    .setContentText("kuy")
//                    .setAutoCancel(true)
//                    .build();
//
//            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            notificationManager.notify(1000, notification);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MicrogearCallBack callback = new MicrogearCallBack();

        microgear.connect(appid, key, secret, alias);
        microgear.setCallback(callback);
        microgear.subscribe("message");

    }

    public void sendNoti(View v) {
        microgear.publish("message", "eieieieieiei");

    }

    protected void onDestroy() {
        super.onDestroy();
        microgear.disconnect();
    }

    protected void onResume() {
        super.onResume();
        microgear.bindServiceResume();
    }

    class MicrogearCallBack implements MicrogearEventListener {
        @Override
        public void onConnect() {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", "Now I'm connected with netpie");
            msg.setData(bundle);
            handler.sendMessage(msg);
            Log.i("Connected","Now I'm connected with netpie");
        }

        @Override
        public void onMessage(String topic, String message) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", topic+" : "+message);
            msg.setData(bundle);
            handler.sendMessage(msg);
            Log.i("Message", topic+" : " +message);
        }

        @Override
        public void onPresent(String token) {
//            Message msg = handler.obtainMessage();
//            Bundle bundle = new Bundle();

//            bundle.putString("myKey", "New friend Connect :"+ token);

//            msg.setData(bundle);
//            handler.sendMessage(msg);
//            Log.i("present","New friend Connect :"+ token);
        }

        @Override
        public void onAbsent(String token) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", "Friend lost :"+token);
            msg.setData(bundle);
            handler.sendMessage(msg);
            Log.i("absent","Friend lost :"+token);
        }

        @Override
        public void onDisconnect() {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", "Disconnected");
            msg.setData(bundle);
            handler.sendMessage(msg);
            Log.i("disconnect","Disconnected");
        }

        @Override
        public void onError(String error) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", "Exception : "+error);
            msg.setData(bundle);
            handler.sendMessage(msg);
            Log.i("exception","Exception : "+error);
        }

        @Override
        public void onInfo(String info) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", "Exception : "+info);
            msg.setData(bundle);
            handler.sendMessage(msg);
            Log.i("info","Info : "+info);
        }
    }
}

