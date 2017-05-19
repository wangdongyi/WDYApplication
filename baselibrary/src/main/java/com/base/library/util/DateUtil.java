package com.base.library.util;

import android.text.TextUtils;

import com.base.library.bean.WeekTimeBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * 作者：王东一 on 2016/5/20 13:42
 **/
public class DateUtil {
    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16时09分00秒"）
     *
     *  time
     * @return
     */
    public static String getTime(String SimpleDateFormat) {
        SimpleDateFormat sf = null;
        Date d = new Date(System.currentTimeMillis());
        sf = new SimpleDateFormat(SimpleDateFormat);
        return sf.format(d);

    }
    public static String getTime() {
        SimpleDateFormat sf = null;
        Date d = new Date(System.currentTimeMillis());
        sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);

    }
    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16时09分00秒"）
     *
     * @param time
     * @return
     */
    public static String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    /*时间戳转换成字符窜*/
    public static String getDateToString(long time) {
        SimpleDateFormat sf = null;
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);
    }
    /*时间戳转换成字符窜*/
    public static String getDateToString(long time,String SimpleDateFormat) {
        SimpleDateFormat sf = null;
        Date d = new Date(time);
        sf = new SimpleDateFormat(SimpleDateFormat);
        return sf.format(d);
    }
    /**
     * 判断是否是闰年
     */
    public static boolean isLeapYear(int str) {
        return (str % 4 == 0 && str % 100 != 0) || str % 400 == 0;
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param year  当前年
     * @param month 当前月
     * @return
     */
    public static int DayNum(int year, int month) {
        int dayNum = 31;
        if (isLeapYear(year)) {
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                dayNum = 31;
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                dayNum = 30;
            } else {
                dayNum = 29;
            }

        } else {
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                dayNum = 31;
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                dayNum = 30;
            } else {
                dayNum = 28;
            }
        }
        return dayNum;
    }

    public static Date parse(String strDate, String pattern) {

        if (TextUtils.isEmpty(strDate)) {
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */

    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 根据当前日期获得是星期几
     *
     * @return
     */
    public static String getWeek(String time) {
        String Week = "";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(time));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            Week += "日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            Week += "六";
        }
        return Week;
    }

    /**
     * 获取今天往后一周的日期（几月几号）
     */
    public static List<WeekTimeBean> getSevenDate() {
        List<WeekTimeBean> dates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Calendar Today = Calendar.getInstance();
            Today.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            Today.roll(Calendar.DATE, i);//日期回滚7天
            WeekTimeBean bean = new WeekTimeBean();
            bean.setYear(Today.get(Calendar.YEAR));
            bean.setMonth(Today.get(Calendar.MONTH) + 1);
            bean.setDay(Today.get(Calendar.DAY_OF_MONTH));
            bean.setWeek(Today.get(Calendar.DAY_OF_WEEK));
            dates.add(bean);
        }
        return dates;
    }

    public static String getWeek(int week) {
        String Week = "";
        switch (week) {
            case Calendar.SUNDAY:
                Week = "日";
                break;
            case Calendar.MONDAY:
                Week = "一";
                break;
            case Calendar.TUESDAY:
                Week = "二";
                break;
            case Calendar.WEDNESDAY:
                Week = "三";
                break;
            case Calendar.THURSDAY:
                Week = "四";
                break;
            case Calendar.FRIDAY:
                Week = "五";
                break;
            case Calendar.SATURDAY:
                Week = "六";
                break;
        }
        return Week;
    }

    /**
     * 根据当前日期获得是星期几
     *
     * @return
     */
    public static int getNextWeek(int week) {
        int Week = Calendar.SUNDAY;
        switch (week) {
            case Calendar.SUNDAY:
                Week = Calendar.MONDAY;
                break;
            case Calendar.MONDAY:
                Week = Calendar.TUESDAY;
                break;
            case Calendar.TUESDAY:
                Week = Calendar.WEDNESDAY;
                break;
            case Calendar.WEDNESDAY:
                Week = Calendar.THURSDAY;
                break;
            case Calendar.THURSDAY:
                Week = Calendar.FRIDAY;
                break;
            case Calendar.FRIDAY:
                Week = Calendar.SATURDAY;
                break;
            case Calendar.SATURDAY:
                Week = Calendar.SUNDAY;
                break;
        }
        return Week;
    }

    /**
     * 根据当前日期获得是星期几
     *
     * @return
     */
    public static int getNextDay(int week) {
        int Week = Calendar.SUNDAY;
        switch (week) {
            case Calendar.SUNDAY:
                Week = Calendar.MONDAY;
                break;
            case Calendar.MONDAY:
                Week = Calendar.TUESDAY;
                break;
            case Calendar.TUESDAY:
                Week = Calendar.WEDNESDAY;
                break;
            case Calendar.WEDNESDAY:
                Week = Calendar.THURSDAY;
                break;
            case Calendar.THURSDAY:
                Week = Calendar.FRIDAY;
                break;
            case Calendar.FRIDAY:
                Week = Calendar.SATURDAY;
                break;
            case Calendar.SATURDAY:
                Week = Calendar.SUNDAY;
                break;
        }
        return Week;
    }
    public static String minute(int second) {
        String time = "00:00";
        time = (second / 60 < 10 ? "0" + String.valueOf(second / 60) : String.valueOf(second / 60))
                + ":" +
                (second % 60 < 10 ? "0" + String.valueOf(second % 60) : String.valueOf(second % 60));
        return time;
    }
}
