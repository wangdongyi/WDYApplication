package com.base.library.view.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.library.R;
import com.base.library.util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 作者：王东一 on 2016/6/2 16:04
 **/
public class WDYCalendar extends FrameLayout {
    private Context mContext;
    private TextView textView_left_month, textView_title, textView_right_month;
    private RecyclerView recyclerView;
    private LinearLayout layout_left, layout_right;
    private CalendarAdapter adapter;
    private ArrayList<DayBean> list = new ArrayList<>();
    private int year, month;
    private onClickLeft onClickLeft;
    private onClickRight onClickRight;
    private GregorianCalendar time;
    private onClickItem onClickItem;

    public WDYCalendar.onClickItem getOnClickItem() {
        return onClickItem;
    }

    public void setOnClickItem(WDYCalendar.onClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public ArrayList<DayBean> getList() {
        return list;
    }

    public void setList(ArrayList<DayBean> list) {
        this.list = list;
    }

    public WDYCalendar.onClickLeft getOnClickLeft() {
        return onClickLeft;
    }

    public void setOnClickLeft(WDYCalendar.onClickLeft onClickLeft) {
        this.onClickLeft = onClickLeft;
    }

    public WDYCalendar.onClickRight getOnClickRight() {
        return onClickRight;
    }

    public void setOnClickRight(WDYCalendar.onClickRight onClickRight) {
        this.onClickRight = onClickRight;
    }

    public interface onClickItem {
        void onClick(int position);
    }

    public interface onClickLeft {
        void onClick();
    }

    public interface onClickRight {
        void onClick();
    }

    public WDYCalendar(Context context) {
        this(context, null);
        //  Auto-generated constructor stub
    }

    public WDYCalendar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        //  Auto-generated constructor stub
    }

    public WDYCalendar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //  Auto-generated constructor stub
        mContext = context;
        initData();
    }

    /**
     * 初始化相关Data
     */
    private void initData() {
        LayoutInflater.from(mContext).inflate(R.layout.wdy_calendar_layout, this, true);
        textView_left_month = (TextView) findViewById(R.id.textView_left_month);
        textView_title = (TextView) findViewById(R.id.textView_title);
        textView_right_month = (TextView) findViewById(R.id.textView_right_month);
//        textView_week1 = (TextView) findViewById(R.id.textView_week1);
//        textView_week2 = (TextView) findViewById(R.id.textView_week2);
//        textView_week3 = (TextView) findViewById(R.id.textView_week3);
//        textView_week4 = (TextView) findViewById(R.id.textView_week4);
//        textView_week5 = (TextView) findViewById(R.id.textView_week5);
//        textView_week6 = (TextView) findViewById(R.id.textView_week6);
//        textView_week7 = (TextView) findViewById(R.id.textView_week7);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layout_left = (LinearLayout) findViewById(R.id.layout_left);
        layout_right = (LinearLayout) findViewById(R.id.layout_right);
        layout_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getMonth() - 1 > 0) {
                    setMonth(getMonth() - 1);
                } else {
                    setMonth(12);
                    month = 12;
                    setYear(getYear() - 1);
                }
                changeTitle();
                if (getOnClickLeft() != null)
                    getOnClickLeft().onClick();
            }
        });
        layout_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getMonth() + 1 < 12) {
                    setMonth(getMonth() + 1);
                } else {
                    setMonth(1);
                    setYear(getYear() + 1);
                }
                changeTitle();
                if (getOnClickRight() != null)
                    getOnClickRight().onClick();
            }
        });
        adapter = new CalendarAdapter(mContext, getList());
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 7));
        recyclerView.setAdapter(adapter);
        adapter.setOnClickItem(new CalendarAdapter.onClickItem() {
            @Override
            public void click(int position) {
                if (getOnClickItem() != null) {
                    getOnClickItem().onClick(position);
                }
            }
        });
        initDate();

    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    private void initDate() {
        time = new GregorianCalendar();
        setYear(time.get(Calendar.YEAR));
        setMonth(time.get(Calendar.MONTH) + 1);
        changeTitle();
    }

    @SuppressLint("SetTextI18n")
    private void changeTitle() {
        textView_title.setText(getYear() + "年" + getMonth() + "月");
        textView_left_month.setText((getMonth() - 1 == 0 ? 12 : getMonth() - 1) + "月");
        textView_right_month.setText((getMonth() + 1 == 13 ? 1 : getMonth() + 1) + "月");
        list.clear();
        GregorianCalendar dateTime = new GregorianCalendar(getYear(), getMonth() - 1, 1, 0, 0);
        switch (dateTime.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:

                break;
            case Calendar.TUESDAY:
                for (int i = 0; i < 1; i++) {
                    DayBean bean = new DayBean();
                    bean.setDay(0);
                    bean.setToday(false);
                    bean.setSelected(false);
                    list.add(bean);
                }
                break;
            case Calendar.WEDNESDAY:
                for (int i = 0; i < 2; i++) {
                    DayBean bean = new DayBean();
                    bean.setDay(0);
                    bean.setToday(false);
                    bean.setSelected(false);
                    list.add(bean);
                }
                break;
            case Calendar.THURSDAY:
                for (int i = 0; i < 3; i++) {
                    DayBean bean = new DayBean();
                    bean.setDay(0);
                    bean.setToday(false);
                    bean.setSelected(false);
                    list.add(bean);
                }
                break;
            case Calendar.FRIDAY:
                for (int i = 0; i < 4; i++) {
                    DayBean bean = new DayBean();
                    bean.setDay(0);
                    bean.setToday(false);
                    bean.setSelected(false);
                    list.add(bean);
                }
                break;
            case Calendar.SATURDAY:
                for (int i = 0; i < 5; i++) {
                    DayBean bean = new DayBean();
                    bean.setDay(0);
                    bean.setToday(false);
                    bean.setSelected(false);
                    list.add(bean);
                }
                break;
            case Calendar.SUNDAY:
                for (int i = 0; i < 6; i++) {
                    DayBean bean = new DayBean();
                    bean.setDay(0);
                    bean.setToday(false);
                    bean.setSelected(false);
                    list.add(bean);
                }
                break;
        }

        for (int i = 0; i < DateUtil.DayNum(getYear(), getMonth()); i++) {
            DayBean bean = new DayBean();
            bean.setDay(i + 1);
            if (i + 1 == time.get(Calendar.DAY_OF_MONTH) && getMonth() == time.get(Calendar.MONTH) + 1 && getYear() == time.get(Calendar.YEAR)) {
                bean.setToday(true);
            } else {
                bean.setToday(false);
            }
            list.add(bean);
        }
        notifyCalendar();
    }

    public void notifyCalendar() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
