package com.abe.llln.vlcdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.videolan.libvlc.EventHandler;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.LibVlcException;
import org.videolan.vlc.Util;

public class Main3Activity extends AppCompatActivity {

    protected static final String TAG = "MainActivity/Vlc";
    private LibVLC mLibVLC = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // LibVLC.init(getApplicationContext());
            EventHandler em = EventHandler.getInstance();
            em.addHandler(handler);

            mLibVLC = Util.getLibVlcInstance();

            if (mLibVLC != null) {
                String pathUri = "rtsp://192.168.2.10";
//              String pathUri = "rtsp://192.168.1.1/720P_1M";
//              String pathUri = "rtsp://192.168.99.1/media/stream2";
//				String pathUri = "rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp";
//				String pathUri = "rtsp://192.168.1.1/MJPG?W=640&H=360&Q=50&BR=3000000";
//				String pathUri = "rtsp://10.10.10.254:8554/webcam";
//				String pathUri = "file:///storage/emulated/0/MustCapture/video/winmax/2014-01-27 115155.mp4";
                mLibVLC.playMRL(pathUri);
            }

        } catch (LibVlcException e) {
            e.printStackTrace();
        }

    }


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Log.d(TAG, "Event = " + msg.getData().getInt("event"));
            switch (msg.getData().getInt("event")) {
                case EventHandler.MediaPlayerPlaying:

                case EventHandler.MediaPlayerPaused:

                    break;
                case EventHandler.MediaPlayerStopped:

                    break;
                case EventHandler.MediaPlayerEndReached:

                    break;
                case EventHandler.MediaPlayerVout:
                    if (msg.getData().getInt("data") > 0) {
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(),
                                Main2Activity.class);
                        startActivity(intent);
                        Main3Activity.this.finish();
                    }
                    break;
                case EventHandler.MediaPlayerPositionChanged:
                    break;
                case EventHandler.MediaPlayerEncounteredError:
                    AlertDialog dialog = new AlertDialog.Builder(Main3Activity.this)
                            .setTitle("提示信息")
                            .setMessage("请确认手机已经连接到正确的的wifi热点")
                            .setNegativeButton("ok",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            Main3Activity.this.finish();
                                        }
                                    }).create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    break;
                default:
                    Log.d(TAG, "Event not handled ");
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        EventHandler em = EventHandler.getInstance();
        em.removeHandler(handler);
        super.onDestroy();
    }
}
