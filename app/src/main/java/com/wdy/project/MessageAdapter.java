package com.wdy.project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.library.listen.NoDoubleClickListener;
import com.base.library.listen.OnRecyclerClickListen;
import com.base.library.util.CodeUtil;

import java.util.ArrayList;

/**
 * 作者：王东一
 * 创建时间：2017/7/19.
 */

public class MessageAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<MessageBean> list = new ArrayList<>();
    private OnRecyclerClickListen onRecyclerClickListen;

    public OnRecyclerClickListen getOnRecyclerClickListen() {
        return onRecyclerClickListen;
    }

    public void setOnRecyclerClickListen(OnRecyclerClickListen onRecyclerClickListen) {
        this.onRecyclerClickListen = onRecyclerClickListen;
    }

    public MessageAdapter(Context mContext, ArrayList<MessageBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewHolder viewHolder = null;
        view = LayoutInflater.from(mContext).inflate(R.layout.message_item_adapter, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint({"RecyclerView", "SetTextI18n"})
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.title.setText(list.get(position).getTitle());
        viewHolder.time.setText(CodeUtil.TextEmpty(list.get(position).getTime()));
        viewHolder.text_content.setText(CodeUtil.TextEmpty(list.get(position).getContent()));
        viewHolder.item_layout.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if (getOnRecyclerClickListen() != null) {
                    getOnRecyclerClickListen().onClick(position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    private class ViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        TextView title;
        TextView time;
        RelativeLayout title_layout;
        TextView text_content;
        ImageView arrow;
        RelativeLayout item_layout;

        ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.title = (TextView) rootView.findViewById(R.id.title);
            this.time = (TextView) rootView.findViewById(R.id.time);
            this.title_layout = (RelativeLayout) rootView.findViewById(R.id.title_layout);
            this.text_content = (TextView) rootView.findViewById(R.id.text_content);
            this.arrow = (ImageView) rootView.findViewById(R.id.arrow);
            this.item_layout = (RelativeLayout) rootView.findViewById(R.id.item_layout);
        }

    }
}
