package com.base.library.window;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.base.library.R;
import com.base.library.adapter.MusicAdapter;
import com.base.library.bean.Mp3Info;
import com.base.library.cache.DownloadCache;
import com.base.library.volleyUtil.DownLoadUtil;
import com.base.library.service.MusicService;
import com.base.library.util.CodeUtil;
import com.base.library.util.DateUtil;
import com.base.library.util.MD5Util;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by wangdongyi on 2017/2/9.
 * 音乐播放器
 */

public class MusicWindow {
    private Context mContext;
    private boolean mIsPlaying = false;
    private int MusicLong;
    private MusicService musicService;
    private Intent intentMusicService;
    private String DownloadPath = Environment.getExternalStorageDirectory() + "/WDYDownLoad/";
    private File file;
    private PopupWindow mPopupWindow;
    private View popupWindow;
    public ViewHolder viewHolder;
    private MusicAdapter musicAdapter;
    private String title = "播放选择";
    private int position = 0;//播放位置

    public MusicWindow(Context mContext) {
        this.mContext = mContext;

    }

    @SuppressLint("InflateParams")
    private void initPopupWindow() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        popupWindow = layoutInflater.inflate(R.layout.window_play_music, null);
        mPopupWindow = new PopupWindow(popupWindow, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(false); // 设置PopupWindow可获得焦点
        mPopupWindow.setTouchable(true); // 设置PopupWindow可触摸
        mPopupWindow.setAnimationStyle(R.style.AnimBottom);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mContext.stopService(intentMusicService);
            }
        });
        initView();
        startMusicService();
        resetView();
    }

    private void initView() {
        viewHolder = new ViewHolder(popupWindow);
        viewHolder.seekBar.setMax(100);
        viewHolder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                viewHolder.playMusicOnTimeTextView.setText(DateUtil.minute(seekBar.getProgress() * MusicLong / 100 / 1000));
                viewHolder.playMusicTimeTextView.setText(DateUtil.minute(MusicLong / 1000));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent intent = new Intent();
                intent.putExtra("postion", seekBar.getProgress() * MusicLong / 100);
                intent.setAction(MusicService.ACTION_SEEK);
                mContext.sendBroadcast(intent);
            }
        });
        viewHolder.playMusicImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicWindow.this.position = musicService.mPosition;
                for (int i = 0; i < getMusicList().size(); i++) {
                    if (i == position) {
                        getMusicList().get(i).setIsPlay(1);
                    } else {
                        getMusicList().get(i).setIsPlay(0);
                    }
                }
                musicAdapter.notifyDataSetChanged();
                if (mIsPlaying) {
                    mIsPlaying = false;
                    sendBroadcast(MusicService.ACTION_PAUSE);
                    viewHolder.playMusicImageView.setImageResource(R.drawable.play);
                } else {
                    mIsPlaying = true;
                    sendBroadcast(MusicService.ACTION_PLAY);
                    viewHolder.playMusicImageView.setImageResource(R.drawable.pause);
                }
                if (getMusicList().get(position).getIsCache() == 1) {
                    viewHolder.seekBar.setSecondaryProgress(100);
                } else {
                    viewHolder.seekBar.setSecondaryProgress(0);
                    downloadMusic(position);
                }
            }
        });
        //歌曲列表
        musicAdapter = new MusicAdapter(mContext);
        musicAdapter.setOnClickItem(new MusicAdapter.onClickItem() {
            @Override
            public void onClick(int position) {
                MusicWindow.this.position = position;
                for (int i = 0; i < getMusicList().size(); i++) {
                    if (i == position) {
                        getMusicList().get(i).setIsPlay(1);
                    } else {
                        getMusicList().get(i).setIsPlay(0);
                    }
                }
                musicAdapter.notifyDataSetChanged();
                Intent intent = new Intent();
                intent.putExtra("postion", position);
                intent.setAction(MusicService.ACTION_POSITION);
                mContext.sendBroadcast(intent);
                mIsPlaying = true;
                viewHolder.playMusicImageView.setImageResource(R.drawable.pause);
                if (getMusicList().get(position).getIsCache() == 1) {
                    File file = new File(getMusicList().get(position).getUrl());
                    try {
                        if (getMusicList().get(position).getSize() == CodeUtil.getFileSize(file)) {
                            viewHolder.seekBar.setSecondaryProgress(100);
                        } else {
                            CodeUtil.deleteFile(getMusicList().get(position).getUrl());
                            viewHolder.seekBar.setSecondaryProgress(0);
                            downloadMusic(position);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    viewHolder.seekBar.setSecondaryProgress(0);
                    downloadMusic(position);
                }
            }
        });
        viewHolder.windowMusicRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        viewHolder.windowMusicRecyclerView.setAdapter(musicAdapter);
        //设置标题
        viewHolder.windowMusicTitleTextView.setText(getTitle());
        //点击隐藏
        viewHolder.windowMusicDownLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.windowMusicLayout.setVisibility(View.GONE);
            }
        });
        //点击关闭
        viewHolder.windowMusicCloseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mPopupWindow) {
                    mPopupWindow.dismiss();
                }
            }
        });
        viewHolder.playMusicMenuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.windowMusicLayout.setVisibility(View.VISIBLE);
            }
        });
        viewHolder.windowMusicLayout.setVisibility(View.VISIBLE);
    }

    //更新播放列表
    private void upMusicList() {
        musicAdapter.notifyDataSetChanged();
        viewHolder.windowMusicLayout.setVisibility(View.VISIBLE);
        if (!MusicService.serviceRun) {
            intentMusicService.putParcelableArrayListExtra("music_list", getMusicList());
            intentMusicService.putExtra("music_current_position", position);
            mContext.startService(intentMusicService);
            musicService.setmMusic_list(getMusicList());
        }

    }

    //重置播放器
    private void resetView() {
        makeData();
        viewHolder.seekBar.setSecondaryProgress(0);
        viewHolder.seekBar.setProgress(0);
        viewHolder.playMusicOnTimeTextView.setText(DateUtil.minute(0 / 1000));
        viewHolder.playMusicTimeTextView.setText(DateUtil.minute(0 / 1000));
        upMusicList();
    }

    //更新播放列表数据
    private void makeData() {
        ArrayList<String> musicCache = DownloadCache.searchFile(DownloadPath);
        for (int j = 0; j < getMusicList().size(); j++) {
            getMusicList().get(j).setIsCache(0);
        }
        for (int i = 0; i < musicCache.size(); i++) {
            for (int j = 0; j < getMusicList().size(); j++) {
                if (CodeUtil.isUrl(getMusicList().get(j).getUrl())) {
                    if ((MD5Util.string2MD5(getMusicList().get(j).getUrl()) + ".mp3").equals(musicCache.get(i))) {
                        getMusicList().get(j).setUrl(DownloadPath + musicCache.get(i));
                        getMusicList().get(j).setIsCache(1);
                    }
                } else {
                    String a[] = getMusicList().get(j).getUrl().split("/");
                    if (a.length > 0)
                        if (a[a.length - 1].equals(musicCache.get(i))) {
                            getMusicList().get(j).setIsCache(1);
                        }
                }
            }
        }
        musicAdapter.notifyDataSetChanged();
    }

    private void getPopupWindowInstance() {
        if (null != mPopupWindow) {
            mPopupWindow.dismiss();
            resetView();
        } else {
            initPopupWindow();
        }
    }

    private ArrayList<Mp3Info> getMusicList() {
        return DownloadCache.mp3InfoArrayList;
    }

    public void setMusicList(ArrayList<Mp3Info> musicList) {
        DownloadCache.mp3InfoArrayList = musicList;
    }

    public void back() {
        if (null != mPopupWindow) {
            mPopupWindow.dismiss();
        }
    }

    public class ViewHolder {
        LinearLayout windowMusicDownLayout;
        LinearLayout windowMusicLayout;
        TextView windowMusicTitleTextView;
        LinearLayout windowMusicCloseLayout;
        RecyclerView windowMusicRecyclerView;
        ImageView playMusicImageView;
        TextView playMusicOnTimeTextView;
        SeekBar seekBar;
        TextView playMusicTimeTextView;
        RelativeLayout windowMusicPlayLayout;
        RelativeLayout windowPlayMusicTitleLayout;
        ImageView playMusicMenuImageView;

        ViewHolder(View view) {
            windowMusicDownLayout = (LinearLayout) view.findViewById(R.id.window_music_down_layout);
            windowMusicTitleTextView = (TextView) view.findViewById(R.id.window_music_title_textView);
            windowMusicCloseLayout = (LinearLayout) view.findViewById(R.id.window_music_close_layout);
            windowMusicRecyclerView = (RecyclerView) view.findViewById(R.id.window_music_recyclerView);
            playMusicImageView = (ImageView) view.findViewById(R.id.play_music_imageView);
            playMusicOnTimeTextView = (TextView) view.findViewById(R.id.play_music_on_time_textView);
            seekBar = (SeekBar) view.findViewById(R.id.seekBar);
            playMusicTimeTextView = (TextView) view.findViewById(R.id.play_music_time_textView);
            windowMusicPlayLayout = (RelativeLayout) view.findViewById(R.id.window_music_play_layout);
            windowPlayMusicTitleLayout = (RelativeLayout) view.findViewById(R.id.window_play_music_title_layout);
            windowMusicLayout = (LinearLayout) view.findViewById(R.id.music_list_layout);
            playMusicMenuImageView = (ImageView) view.findViewById(R.id.play_music_menu_imageView);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MusicService.MSG_ONPREPARED) {
                //播放进度
                int currentPosition = msg.arg1;
                MusicLong = msg.arg2;
                viewHolder.seekBar.setProgress(currentPosition * 100 / MusicLong);
                viewHolder.playMusicOnTimeTextView.setText(DateUtil.minute(currentPosition / 1000));
                viewHolder.playMusicTimeTextView.setText(DateUtil.minute(MusicLong / 1000));
            }
//            if (msg.what == MusicService.MSG_PREPARED) {
//                mIsPlaying = (boolean) msg.obj;
//            }
//            if (msg.what == MusicService.MSG_PLAY) {
//                mIsPlaying = (boolean) msg.obj;
//            }
//            if (msg.what == MusicService.ACTION_CANCEL) {
//
//            }
        }
    };

    private void startMusicService() {
        if (musicService == null) {
            musicService = new MusicService();
            musicService.setmMusic_list(getMusicList());
            intentMusicService = new Intent();
            intentMusicService.setClass(mContext, musicService.getClass());
            intentMusicService.putParcelableArrayListExtra("music_list", getMusicList());
            intentMusicService.putExtra("messenger", new Messenger(handler));
            intentMusicService.putExtra("music_current_position", position);
            mContext.startService(intentMusicService);
        }
    }

    public void show(View view) {
        getPopupWindowInstance();
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    private void downloadMusic(int position) {
        DownLoadUtil downLoadUtil = new DownLoadUtil();
        downLoadUtil.setDownloadLister(new DownLoadUtil.DownloadLister() {
            @Override
            public void onStart() {

            }

            @Override
            public void onIsHave() {

            }

            @Override
            public void onProgress(int progress) {
                viewHolder.seekBar.setSecondaryProgress(progress);
            }

            @Override
            public void onSucceed(String path) {
                for (int i = 0; i < getMusicList().size(); i++) {
                    if (path.equals(getMusicList().get(i).getUrl())) {
                        getMusicList().get(i).setUrl(DownloadPath + path);
                        getMusicList().get(i).setIsCache(1);
                    }
                }
            }

            @Override
            public void onCancel() {

            }
        });
        downLoadUtil.execute(getMusicList().get(position).getUrl(), MD5Util.string2MD5(getMusicList().get(position).getUrl()) + ".mp3");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 发送广播
     * <p>
     * action
     */
    private void sendBroadcast(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        mContext.sendBroadcast(intent);
    }
}
