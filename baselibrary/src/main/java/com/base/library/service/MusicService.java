package com.base.library.service;
//Created by 王东一 on 2016/11/16.


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.base.library.bean.Mp3Info;
import com.base.library.cache.DownloadCache;
import com.base.library.util.WDYLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MusicService extends Service implements
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        AudioManager.OnAudioFocusChangeListener {
    public static final String TAG = "MusicService";
    //musicservice的name
    public static final String MUSIC_SERVICE = "com.base.library.service.MusicService";
    //本地歌曲listview点击
    public static final String ACTION_LIST_ITEM = "com.base.library.listitem";
    //暂停音乐
    public static final String ACTION_PAUSE = "com.base.library.pause";
    //播放音乐
    public static final String ACTION_PLAY = "com.base.library.play";
    //下一曲
    public static final String ACTION_NEXT = "com.base.library.next";
    //上一曲
    public static final String ACTION_PRV = "com.base.library.prv";
    //seekBar手动控制
    public static final String ACTION_SEEK = "com.base.library.seek";
    //播放指定歌曲
    public static final String ACTION_POSITION = "com.base.library.position";
    //以上操作结束的时候
    public static final String ACTION_COMPLETION = "com.base.library.completion";
    public static final int MSG_ONPREPARED = 001;
    public static final int MSG_PREPARED = 002;
    public static final int MSG_PLAY = 003;
    // 取消
    public static final int ACTION_CANCEL = 004;
    private Messenger mMessenger;
    private MediaPlayer mPlayer;
    private MusicBroadReceiver receiver;
    private int mCurrentPosition;
    private boolean isFirst = true;
    private boolean isPlaying;
    public int mPosition;//播放位置
    public static int playMode = 1;//1.单曲循环 2.列表循环 0.随机播放
    private Timer mTimer;
    private Random mRandom = new Random();
    public static int prv_position;
    private Message mMessage;
    public static boolean serviceRun = false;

    @Override
    public void onCreate() {
        WDYLog.d(TAG, "service : onCreate");
        serviceRun = true;
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
        }
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnErrorListener(this);//资源出错
        mPlayer.setOnPreparedListener(this);//资源准备好的时候
        mPlayer.setOnCompletionListener(this);//播放完成的时候
        regFilter();
        //创建audioManger
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            ArrayList<Mp3Info> mMusic_list = intent.getParcelableArrayListExtra("music_list");
            setmMusic_list(mMusic_list);
            mMessenger = (Messenger) intent.getExtras().get("messenger");
            mPosition = intent.getIntExtra("music_current_position", 0);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        serviceRun = false;
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        mMessage = Message.obtain();
        mMessage.what = MusicService.ACTION_CANCEL;
        try {
            mMessenger.send(mMessage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
        if (receiver != null) {
            unregisterReceiver(receiver); // 服务终止时解绑
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

    }


    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        WDYLog.d(TAG, "service : OnError");
        Intent intent = new Intent();
        intent.setAction(MusicService.ACTION_NEXT);
        sendBroadcast(intent);
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mPlayer.start();//开始播放
        if (mMessenger != null) {
            sentPreparedMessageToMain();
            sentPositionToMainByTimer();
        }
    }

    private void sentPreparedMessageToMain() {
        mMessage = Message.obtain();
        mMessage.what = MusicService.MSG_PREPARED;
        mMessage.arg1 = mPosition;
        mMessage.obj = mPlayer.isPlaying();
        try {
            //发送位置信息
            mMessenger.send(mMessage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void sentPlayStateToMain() {
        mMessage = Message.obtain();
        mMessage.what = MusicService.MSG_PLAY;
        mMessage.obj = mPlayer.isPlaying();
        try {
            //发送位置信息
            mMessenger.send(mMessage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void sentPositionToMainByTimer() {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if(!serviceRun){
                        return;
                    }
                    if (mPlayer != null)
                        if (mPlayer.isPlaying()) {
                            //1.准备好的时候.告诉activity,当前歌曲的总时长
                            int currentPosition = mPlayer.getCurrentPosition();
                            int totalDuration = mPlayer.getDuration();
                            mMessage = Message.obtain();
                            mMessage.what = MusicService.MSG_ONPREPARED;
                            mMessage.arg1 = currentPosition;
                            mMessage.arg2 = totalDuration;
                            //2.发送消息
                            mMessenger.send(mMessage);
                        }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        //播放结束
        Intent intent = new Intent();
        intent.setAction(MusicService.ACTION_NEXT);
        sendBroadcast(intent);
    }

    /**
     * 播放
     */
    private void play(int position) {
        if (mPlayer != null && getmMusic_list().size() > 0) {
            mPlayer.reset();
            try {
                mPlayer.setDataSource(getmMusic_list().get(position).getUrl());
                mPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 暂停
     */
    private void pause() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mCurrentPosition = mPlayer.getCurrentPosition();
            mPlayer.pause();
            sentPlayStateToMain();
        }
    }

    /**
     * 注册广播
     */
    private void regFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MusicService.ACTION_LIST_ITEM);
        intentFilter.addAction(MusicService.ACTION_PAUSE);
        intentFilter.addAction(MusicService.ACTION_PLAY);
        intentFilter.addAction(MusicService.ACTION_NEXT);
        intentFilter.addAction(MusicService.ACTION_PRV);
        intentFilter.addAction(MusicService.ACTION_SEEK);
        intentFilter.addAction(MusicService.ACTION_POSITION);
        intentFilter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        intentFilter.setPriority(1000);
        if (receiver == null) {
            receiver = new MusicBroadReceiver();
        }
        registerReceiver(receiver, intentFilter);
    }

    /**
     * 广播接收者
     */
    public class MusicBroadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case MusicService.ACTION_LIST_ITEM:
                    //点击左侧菜单
                    isPlaying = true;
                    isFirst = false;
                    mPosition = intent.getIntExtra("position", 0);
                    play(mPosition);
                    break;
                case MusicService.ACTION_PAUSE:
                    //暂停播放
                    isPlaying = false;
                    pause();
                    break;
                case MusicService.ACTION_PLAY:
                    isPlaying = true;
                    //开始播放
                    if (isFirst) {
                        isFirst = false;
                        play(mPosition);
                    } else {
                        mPlayer.seekTo(mCurrentPosition);
                        mPlayer.start();
                        //通知是否在播放
                        sentPlayStateToMain();
                    }
                    break;
                case MusicService.ACTION_NEXT:
                    //下一首
                    prv_position = mPosition;
                    isPlaying = true;
                    if (playMode % 3 == 1) {//1.单曲循环
                        play(mPosition);
                    } else if (playMode % 3 == 2) {//2.列表播放
                        mPosition++;
                        if (mPosition <= getmMusic_list().size() - 1) {
                            play(mPosition);
                        } else {
                            mPosition = 0;
                            play(mPosition);
                        }
                    } else if (playMode % 3 == 0) {// 0.随机播放
                        play(getRandom());
                    }
                    break;
                case MusicService.ACTION_PRV:
                    //上一首
                    prv_position = mPosition;
                    isPlaying = true;
                    if (playMode % 3 == 1) {//1.单曲循环
                        play(mPosition);
                    } else if (playMode % 3 == 2) {//2.列表播放
                        mPosition--;
                        if (mPosition < 0) {
                            mPosition = getmMusic_list().size() - 1;
                            play(mPosition);
                        } else {
                            play(mPosition);
                        }
                    } else if (playMode % 3 == 0) {// 0.随机播放
                        play(getRandom());
                    }
                    break;
                case AudioManager.ACTION_AUDIO_BECOMING_NOISY:
                    //如果耳机拨出时暂停播放
                    Intent intent_pause = new Intent();
                    intent_pause.setAction(MusicService.ACTION_PAUSE);
                    sendBroadcast(intent_pause);

                    break;
                case MusicService.ACTION_SEEK:
                    //拖拽播放
                    mCurrentPosition = intent.getIntExtra("postion", 0);
                    mPlayer.seekTo(mCurrentPosition);
                    break;
                case MusicService.ACTION_POSITION:
                    //播放选的音乐
                    isPlaying = true;
                    mPosition = intent.getIntExtra("postion", 0);
                    play(mPosition);
                    break;
            }
        }
    }

    public ArrayList<Mp3Info> getmMusic_list() {
        return DownloadCache.mp3InfoArrayList;
    }

    public void setmMusic_list(ArrayList<Mp3Info> mMusic_list) {
        DownloadCache.mp3InfoArrayList = mMusic_list;
    }

    private int getRandom() {
        mPosition = mRandom.nextInt(getmMusic_list().size());
        return mPosition;
    }

    /**
     * ---------------音频焦点处理相关的方法---------------
     **/
    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN://你已经得到了音频焦点。
                WDYLog.d(TAG, "-------------AUDIOFOCUS_GAIN---------------");
                // resume playback
                mPlayer.start();
                mPlayer.setVolume(1.0f, 1.0f);
                break;
            case AudioManager.AUDIOFOCUS_LOSS://你已经失去了音频焦点很长时间了。你必须停止所有的音频播放
                WDYLog.d(TAG, "-------------AUDIOFOCUS_LOSS---------------");
                // Lost focus for an unbounded amount of time: stop playback and release media player
                if (mPlayer != null) {
                    if (mPlayer.isPlaying())
                        mPlayer.stop();
                    mPlayer.release();
                    mPlayer = null;
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT://你暂时失去了音频焦点
                WDYLog.d(TAG, "-------------AUDIOFOCUS_LOSS_TRANSIENT---------------");
                // Lost focus for a short time, but we have to stop
                // playback. We don't release the media player because playback
                // is likely to resume
                if (mPlayer.isPlaying())
                    mPlayer.pause();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK://你暂时失去了音频焦点，但你可以小声地继续播放音频（低音量）而不是完全扼杀音频。
                WDYLog.d(TAG, "-------------AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK---------------");
                // Lost focus for a short time, but it's ok to keep playing
                // at an attenuated level
                if (mPlayer.isPlaying())
                    mPlayer.setVolume(0.1f, 0.1f);
                break;
        }

    }
}
