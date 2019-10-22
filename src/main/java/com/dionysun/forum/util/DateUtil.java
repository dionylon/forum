package com.dionysun.forum.util;

import java.util.Date;

public class DateUtil {
    // 一天
    public static long DAY_MSECS = 1000*3600*24L;
    // 一月
    public static long MONTH_MSECS = DAY_MSECS*30;
    // 一年
    public static long YEAR_MSECS = MONTH_MSECS*12;

    public static Date daysBefore(long days){
        return new Date(System.currentTimeMillis() - days * DAY_MSECS);
    }

    public static Date monthBefore(long months){
        return new Date(System.currentTimeMillis() - months *MONTH_MSECS);
    }

    private DateUtil(){}
}
