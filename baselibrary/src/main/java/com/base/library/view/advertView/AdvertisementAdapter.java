package com.base.library.view.advertView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.base.library.R;
import com.base.library.bean.AdvertisementBean;

import java.util.ArrayList;

/**
 * 作者：王东一
 * 创建时间：2017/4/6.
 * 广告位-指示器-适配器
 */

public class AdvertisementAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<AdvertisementBean> list = new ArrayList<>();


    public AdvertisementAdapter(Context mContext, ArrayList<AdvertisementBean> list) {
        this.mContext = mContext;
        this.list = list;

    }

    public interface onClickItem {
        void onClick(int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewHolder viewHolder = null;
        view = LayoutInflater.from(mContext).inflate(R.layout.adapter_advertisement, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (list.get(position).isSelected()) {
            viewHolder.imageView.setImageResource(list.get(position).getPictureSelected());
        } else {
            viewHolder.imageView.setImageResource(list.get(position).getPictureUnSelected());
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.adapter_advertisement_imageView);
        }
    }
}
