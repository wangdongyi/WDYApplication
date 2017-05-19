package com.base.library.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.library.R;
import com.base.library.cache.DownloadCache;

/**
 * Created by wangdongyi on 2017/2/9.
 * 播放音乐列表
 */

public class MusicAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private onClickItem onClickItem;

    public onClickItem getOnClickItem() {
        return onClickItem;
    }

    public void setOnClickItem(onClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public interface onClickItem {
        void onClick(int position);
    }

    public MusicAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewHolder viewHolder = null;
        view = LayoutInflater.from(mContext).inflate(R.layout.adapter_music_item, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.musicItemNameTextView.setText(DownloadCache.mp3InfoArrayList.get(position).getTitle());
        if(DownloadCache.mp3InfoArrayList.get(position).getIsPlay()==1){
            viewHolder.musicItemPlayingImageView.setVisibility(View.VISIBLE);
        }else {
            viewHolder.musicItemPlayingImageView.setVisibility(View.GONE);
        }
        viewHolder.musicItemMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getOnClickItem() != null) {
                    getOnClickItem().onClick(position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return DownloadCache.mp3InfoArrayList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView musicItemNameTextView;
        ImageView musicItemPlayingImageView;
        RelativeLayout musicItemMainLayout;

        ViewHolder(View view) {
            super(view);
            musicItemNameTextView = (TextView) view.findViewById(R.id.music_item_name_textView);
            musicItemPlayingImageView = (ImageView) view.findViewById(R.id.music_item_playing_imageView);
            musicItemMainLayout = (RelativeLayout) view.findViewById(R.id.music_item_main_layout);
        }
    }

}
