package com.base.library.util;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.library.R;
import com.base.library.bean.TimeBean;
import com.base.library.view.pickerView.PickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * 作者：王东一 on 2016/5/25 15:09
 **/
public class TimeSelector {
    public interface ResultHandler {
        void handle(String time);
    }

    private ResultHandler handler;
    private Context context;
    private Dialog seletorDialog;
    private PickerView year_pv;
    private PickerView month_pv;
    private PickerView day_pv;

    public int endYearIndex, endMonthIndex, endDayIndex;
    public int endYear, endMonth, endDay;
    public ArrayList<String> endYearList, endMonthList, endDayList;
    private ArrayList<TimeBean> listYear, listMonth, listDay;
    private final long ANIMATORDELAY = 200L;
    private String workStart_str;
    private int StarYear = 1900;
    private int EndYear = 0;
    private String format = "-";
    private Calendar Today;
    private int yearSize;

    public int getStarYear() {
        return StarYear;
    }

    public void setStarYear(int starYear) {
        StarYear = starYear;
    }

    public int getEndYear() {
        return EndYear;
    }

    public void setEndYear(int endYear) {
        EndYear = endYear;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public ResultHandler getHandler() {
        return handler;
    }

    public void setHandler(ResultHandler handler) {
        this.handler = handler;
    }

    /**
     * context
     * resultHandler 选取时间后回调
     * startDate     format："yyyy/MM/dd"
     * endDate
     */
    public TimeSelector(Context context, ResultHandler resultHandler) {
        this.context = context;
        this.handler = resultHandler;
        initDialog();
        initView();
    }

    /**
     * context
     * resultHandler 选取时间后回调
     * startDate     format：""
     * endDate
     */
    public TimeSelector(Context context, String format, ResultHandler resultHandler) {
        this.context = context;
        setFormat(format);
        this.handler = resultHandler;
        initDialog();
        initView();
    }

    /**
     * context
     * startDate
     * endDate
     * workStartTime 工作日起始时间 如：朝九晚五  format："09:00"
     * workEndTime   工作日结束时间  format："17:00"
     */
    public TimeSelector(Context context, ResultHandler resultHandler, String date) {
        this(context, resultHandler);
        this.workStart_str = date;
    }

    public void show(String starTime) {
        initArrayList(starTime);
        addListener();
        seletorDialog.show();
    }

    private void initDialog() {
        if (seletorDialog == null) {
            seletorDialog = new Dialog(context, R.style.DeleteDialog);
            seletorDialog.setCancelable(false);
            seletorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            seletorDialog.setContentView(R.layout.time_layout);
            Window window = seletorDialog.getWindow();
            if (window != null) {
                window.setGravity(Gravity.BOTTOM);
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.width = CodeUtil.getScreenWidth(context);
                window.setAttributes(lp);
            }
        }
    }

    private void initView() {
        year_pv = (PickerView) seletorDialog.findViewById(R.id.pickerView_year);
        month_pv = (PickerView) seletorDialog.findViewById(R.id.pickerView_month);
        day_pv = (PickerView) seletorDialog.findViewById(R.id.pickerView_day);
        LinearLayout layout_bottom = (LinearLayout) seletorDialog.findViewById(R.id.layout_bottom);
        TextView tv_cancle = (TextView) seletorDialog.findViewById(R.id.button_cancel);
        TextView tv_select = (TextView) seletorDialog.findViewById(R.id.button_done);
        RelativeLayout main = (RelativeLayout) seletorDialog.findViewById(R.id.main);
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seletorDialog.dismiss();
            }
        });
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seletorDialog.dismiss();
            }
        });
        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.handle(listYear.get(endYearIndex).getDate() + getFormat() + (listMonth.get(endMonthIndex).getDate() > 9 ? listMonth.get(endMonthIndex).getDate() : ("0" + listMonth.get(endMonthIndex).getDate())) + getFormat() + listDay.get(endDayIndex).getDate());
                seletorDialog.dismiss();
            }
        });
        layout_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private void initArrayList(String time) {
        if (listYear == null) listYear = new ArrayList<>();
        if (listMonth == null) listMonth = new ArrayList<>();
        if (listDay == null) listDay = new ArrayList<>();
        if (endYearList == null) endYearList = new ArrayList<>();
        if (endMonthList == null) endMonthList = new ArrayList<>();
        if (endDayList == null) endDayList = new ArrayList<>();
        listYear.clear();
        listMonth.clear();
        listDay.clear();
        endYearList.clear();
        endMonthList.clear();
        endDayList.clear();
        Today = Calendar.getInstance();
        Today.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        if (getEndYear() == 0) {
            setEndYear(Today.get(Calendar.YEAR));
        }
        yearSize = getEndYear() - getStarYear() + 1;
        String[] star = time.split(format);
        if (CodeUtil.isEmpty(time)||star.length!=3) {
            endYear = Today.get(Calendar.YEAR);
            endMonth = Today.get(Calendar.MONTH) + 1;
            endDay = Today.get(Calendar.DAY_OF_MONTH);
            endYearIndex = yearSize - 1;
            endMonthIndex = Today.get(Calendar.MONTH);
            endDayIndex = Today.get(Calendar.DAY_OF_MONTH) - 1;
        } else {
            endYear = Integer.parseInt(star[0]);
            endMonth = Integer.parseInt(star[1]);
            endDay = Integer.parseInt(star[2]);
            endYearIndex = Integer.parseInt(star[0]) - getStarYear();
            endMonthIndex = Integer.parseInt(star[1]) - 1;
            endDayIndex = Integer.parseInt(star[2]) - 1;
        }
        initList();
    }


    private void initList() {
        for (int i = 0; i < yearSize; i++) {
            endYearList.add(i + getStarYear() + "年");
            TimeBean bean = new TimeBean();
            bean.setDate(i + getStarYear());
            bean.setDateName(i + getStarYear() + "年");
            listYear.add(bean);
        }
        for (int i = 0; i < context.getResources().getStringArray(R.array.months).length; i++) {
            endMonthList.add(context.getResources().getStringArray(R.array.months)[i] + "月");
            TimeBean bean = new TimeBean();
            bean.setDate(i + 1);
            bean.setDateName(context.getResources().getStringArray(R.array.months)[i] + "月");
            listMonth.add(bean);
        }
        switch (DateUtil.DayNum(endYear, endMonth)) {
            case 28:
                for (int i = 0; i < context.getResources().getStringArray(R.array.days_28).length; i++) {
                    endDayList.add(context.getResources().getStringArray(R.array.days_28)[i] + "日");
                    TimeBean bean = new TimeBean();
                    bean.setDate(i + 1);
                    bean.setDateName(context.getResources().getStringArray(R.array.days_28)[i] + "日");
                    listDay.add(bean);
                }
                break;
            case 29:
                for (int i = 0; i < context.getResources().getStringArray(R.array.days_29).length; i++) {
                    endDayList.add(context.getResources().getStringArray(R.array.days_29)[i] + "日");
                    TimeBean bean = new TimeBean();
                    bean.setDate(i + 1);
                    bean.setDateName(context.getResources().getStringArray(R.array.days_29)[i] + "日");
                    listDay.add(bean);
                }
                break;
            case 30:
                for (int i = 0; i < context.getResources().getStringArray(R.array.days_30).length; i++) {
                    endDayList.add(context.getResources().getStringArray(R.array.days_30)[i] + "日");
                    TimeBean bean = new TimeBean();
                    bean.setDate(i + 1);
                    bean.setDateName(context.getResources().getStringArray(R.array.days_30)[i] + "日");
                    listDay.add(bean);
                }
                break;
            case 31:
                for (int i = 0; i < context.getResources().getStringArray(R.array.days_31).length; i++) {
                    endDayList.add(context.getResources().getStringArray(R.array.days_31)[i] + "日");
                    TimeBean bean = new TimeBean();
                    bean.setDate(i + 1);
                    bean.setDateName(context.getResources().getStringArray(R.array.days_31)[i] + "日");
                    listDay.add(bean);
                }
                break;
        }
        year_pv.setData(endYearList);
        month_pv.setData(endMonthList);
        day_pv.setData(endDayList);
        year_pv.setSelected(endYearIndex);
        month_pv.setSelected(endMonthIndex);
        day_pv.setSelected(endDayIndex);
    }


    private void addListener() {
        year_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                for (int i = 0; i < listYear.size(); i++) {
                    if (text.equals(listYear.get(i).getDateName())) {
                        endYearIndex = i;
                        endYear = listYear.get(i).getDate();
                    }
                }
                setDayList(listYear.get(endYearIndex).getDate(), listMonth.get(endMonthIndex).getDate());
                monthChange();


            }
        });
        month_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                for (int i = 0; i < listMonth.size(); i++) {
                    if (text.equals(listMonth.get(i).getDateName())) {
                        endMonthIndex = i;
                        endMonth = listMonth.get(i).getDate();
                    }
                }
                setDayList(listYear.get(endYearIndex).getDate(), listMonth.get(endMonthIndex).getDate());
                dayChange();


            }
        });
        day_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                for (int i = 0; i < listDay.size(); i++) {
                    if (text.equals(listDay.get(i).getDateName())) {
                        endDayIndex = i;
                        endDay = listDay.get(i).getDate();
                    }
                }

            }
        });

    }

    private void setDayList(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            endDayList.clear();
            listDay.clear();
            for (int i = 0; i < 31; i++) {
                endDayList.add(i + 1 + "日");
                TimeBean bean = new TimeBean();
                bean.setDate(i + 1);
                bean.setDateName(i + 1 + "日");
                listDay.add(bean);
            }
            day_pv.setData(endDayList);
            while (endDayIndex >= listDay.size()) {
                endDayIndex = listDay.size() - 1;
            }
            day_pv.setSelected(endDayIndex);
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            endDayList.clear();
            listDay.clear();
            for (int i = 0; i < 30; i++) {
                endDayList.add(i + 1 + "日");
                TimeBean bean = new TimeBean();
                bean.setDate(i + 1);
                bean.setDateName(i + 1 + "日");
                listDay.add(bean);
            }
            day_pv.setData(endDayList);
            while (endDayIndex >= listDay.size()) {
                endDayIndex = listDay.size() - 1;
            }
            day_pv.setSelected(endDayIndex);
        } else {
            if (DateUtil.isLeapYear(year)) {
                endDayList.clear();
                listDay.clear();
                for (int i = 0; i < 29; i++) {
                    endDayList.add(i + 1 + "日");
                    TimeBean bean = new TimeBean();
                    bean.setDate(i + 1);
                    bean.setDateName(i + 1 + "日");
                    listDay.add(bean);
                }
                day_pv.setData(endDayList);
                while (endDayIndex >= listDay.size()) {
                    endDayIndex = listDay.size() - 1;
                }
                day_pv.setSelected(endDayIndex);
            } else {
                endDayList.clear();
                listDay.clear();
                for (int i = 0; i < 28; i++) {
                    endDayList.add(i + 1 + "日");
                    TimeBean bean = new TimeBean();
                    bean.setDate(i + 1);
                    bean.setDateName(i + 1 + "日");
                    listDay.add(bean);
                }
                day_pv.setData(endDayList);
                while (endDayIndex >= listDay.size()) {
                    endDayIndex = listDay.size() - 1;
                }
                day_pv.setSelected(endDayIndex);
            }
        }
    }


    private void monthChange() {
        excuteAnimator(ANIMATORDELAY, month_pv);
        long CHANGEDELAY = 90L;
        month_pv.postDelayed(new Runnable() {
            @Override
            public void run() {
                dayChange();
            }
        }, CHANGEDELAY);

    }

    private void dayChange() {
        excuteAnimator(ANIMATORDELAY, day_pv);
    }


    private void excuteAnimator(long ANIMATORDELAY, View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.3f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.3f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(ANIMATORDELAY).start();
    }

}
