package com.base.library.view.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.library.R;

import java.util.ArrayList;

/**
 * 作者：王东一 on 2016/6/2 16:42
 **/
public class CalendarAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<DayBean> list = new ArrayList<>();

    public CalendarAdapter(Context mContext, ArrayList<DayBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public CalendarAdapter.onClickItem getOnClickItem() {
        return onClickItem;
    }

    public void setOnClickItem(CalendarAdapter.onClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    private onClickItem onClickItem;

    public interface onClickItem {
        void click(int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerViewHolder viewHolder;
        view = LayoutInflater.from(mContext).inflate(R.layout.wdt_calendar_item, parent, false);
        viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder viewHolder = (RecyclerViewHolder) holder;
        if (list.get(position).getDay() == 0) {
            viewHolder.day.setText("");
            viewHolder.day.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
        } else {
            viewHolder.day.setText(list.get(position).getDay() + "");
            if (list.get(position).isToday()) {
                viewHolder.day.setBackgroundResource(R.drawable.oval_blue);
                viewHolder.day.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            } else {
                if (list.get(position).isSelected()) {
                    viewHolder.day.setBackgroundResource(R.drawable.oval_orange);
                    viewHolder.day.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                } else {
                    viewHolder.day.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    viewHolder.day.setTextColor(ContextCompat.getColor(mContext, R.color.text_black));
                }
            }
            viewHolder.day.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getOnClickItem() != null) {
                        getOnClickItem().click(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView day;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            day = (TextView) itemView.findViewById(R.id.textView_day);
        }

    }

}
