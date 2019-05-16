package com.mye.mine.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期工具
 */
public class DateUtil {
    public static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 把date转化为字符串
     * @param format
     * @param date
     * @return
     */
    public static String format(LocalDate date, DateTimeFormatter format) {
        return  date.format(format);
    }

    public static String format(LocalDate date) {
        return  format(date, DATE_FORMAT);
    }

    /**
     *
     * @param format
     * @param date
     * @return
     */
    public static LocalDate parse(String date, DateTimeFormatter format) {
        return  LocalDate.parse(date, format);
    }

    public static LocalDate parse(String date) {
        return parse(date, DATE_FORMAT);
    }

    /**
     * 添加对应的天数
     * @param localDate
     * @param days
     * @return
     */
    public static LocalDate addDays(LocalDate localDate, int days) {
        return localDate.plusDays(days);
    }

    public static LocalDate nextDay(LocalDate localDate) {
        return addDays(localDate, 1);
    }

    public static String addDays2String(LocalDate localDate, int days) {
        return format(localDate.plusDays(days));
    }
}
