package com.ycgrp.cloudticket.utils;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    /**
     * 获取系统时间
     * @return 2018-11-04
     */
    public static String getSystemDate(){

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     *
     * @param front 第一个日期
     * @param last  第二个日期
     * @return true 第二个日期在第一个日期前面，逾期
     *
     */
    public  static  boolean compareDate(String front,String last){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date  dateOne = simpleDateFormat.parse(front);
            Date dateTwo=simpleDateFormat.parse(last);
            if (dateTwo.before(dateOne)){
                return true;
            }else {
                return  false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return false;
    }
    /**
     * 格式转换
     * @param date
     * @return 2018-11-04
     */
    public static  String getTime(Date date) {
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 获取两个日期之间的间隔天数
     * @return
     */
    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }



}
