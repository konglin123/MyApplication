package com.example.a12902.myapplication.util;

import android.widget.Toast;

import com.example.a12902.myapplication.app.MyApp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author ChenYe
 */
public class TimeFormatUtils {

    public static String TAG = TimeFormatUtils.class.getSimpleName();

    private TimeFormatUtils() {

    }

    /**
     * 获取当前时间 以 “2017-12-19 14:20:20” 形式字符串返回
     *
     * @return
     */
    public static String getCurrentTimeSeconds() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    public static String getFileNameTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    /**
     * 获取当前时间 以 “2017-12-19” 形式字符串返回
     *
     * @return
     */
    public static String getCurrentTimeDay() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    /**
     * 获取当前时间 以 “2017-12-19” 形式字符串返回
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }


    /**
     * 获取当前时间 以 “2017-12-19 14:20:20” 形式字符串返回
     *
     * @return
     */
    public static String generateFileName() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    /**
     * 获取当前时间 “2017-12-19” 形式字符串返回
     *
     * @return
     */
    public static String getCurrentYear() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    /**
     * 获取当前时间 “2017-12-19” 形式字符串返回
     *
     * @return
     */
    public static String getCurrentMonth() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    /**
     * 获取当前时间 “2017-12-19” 形式字符串返回
     *
     * @return
     */
    public static String getCurrentSingleDay() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }


    /**
     * 获取当前时间 “2017年12月19日” 形式字符串返回
     *
     * @return
     */
    public static String getCurrentDayHaveChinese() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }


    /**
     * 传进来 “2017-12-19 14:20:20” 形式的字符串，返回date对象
     *
     * @param str
     * @return
     */
    public static Date getDateFromAllNumStr(String str) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return formatDate.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 传进来 “20171219142020” 形式的字符串，返回2017-12-19 14:20:20 类型
     *
     * @param str
     * @return
     */
    public static String convertTime(String str) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat formatDate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return formatDate2.format(formatDate.parse(str));
        } catch (ParseException e) {
            Toast.makeText(MyApp.getInstance(),"转换时间出错,用默认时间代替",Toast.LENGTH_SHORT).show();
            return getCurrentTimeSeconds();
        }
    }

    /**
     * 传递一个时间进来，对传进来的时间做修改(增加)然后范围String 字符串
     *
     * @param time    2017-08-12 15:34:22
     * @param addDays 3
     * @return
     */
    public static String changeTimeAdd(String time, int addDays) {
        if (time == null || time.isEmpty()) {
            return "时间转换失败";
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = df.parse(time);
            long newTime = parse.getTime() + addDays * 24 * 60 * 60 * 1000;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return formatter.format(new Date(newTime));
        } catch (ParseException e) {
            e.printStackTrace();
            return "时间转换异常";
        }
    }

    /**
     * 当前时间有没有超过传进来的时间那么多天
     *
     * @param time
     * @param day
     * @return false为没有超过
     */
    public static boolean isOverThisDay(String time, int day) {
        String endTime = changeTimeAdd(time, day);
        //当前时间
        String currentTime = getCurrentTimeSeconds();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        try {
            one = df.parse(endTime);
            two = df.parse(currentTime);
            long end = one.getTime();
            long current = two.getTime();
            if (current - end > 0) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 判断传进来的后面一个时间(str2)是否比前面时间(str1)大：
     * 请注意：需求更改，现在的判断是传进来的第一个时间的当天结尾，所以加入
     * 下面传进来的是2018-04-02 12:00:00，那么后面的时间至少是2018-04-03 00:00:01
     * 才反会返回true，
     *
     * @param str1 2018-04-02 12:00:00
     * @param str2
     * @return true代表后面的那个时间比前面的时间大
     */
    public static boolean isOver(String str1, String str2) {
        String newDate = str1.substring(0, 11) + "23:59:58";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        try {
            one = df.parse(newDate);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            if (time1 < time2) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
