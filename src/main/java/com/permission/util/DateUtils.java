package com.permission.util;

import cn.hutool.core.date.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * @auther: shenke
 * @date: 2020/2/22 10:20
 * @description: 日期工具类扩展
 */
public class DateUtils extends DateUtil {

    private DateUtils () {

    }

    /**
     * 获取现在到今天结束的毫秒数
     * @return
     */
    public static long getCurrent2TodayEndMillisTime() {
        Calendar todayEnd = Calendar.getInstance();
        // HOUR_OF_DAY 24小时制
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTimeInMillis() - new Date().getTime();
    }

}
