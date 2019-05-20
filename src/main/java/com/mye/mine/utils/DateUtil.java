package com.mye.mine.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 日期工具
 */
public class DateUtil {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static DateTimeFormatter DATE_FORMAT_2 = DateTimeFormatter.ofPattern("yyyyMMdd");
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

    public static String nextDayFormat(LocalDate localDate) {
        return format(nextDay(localDate));
    }

    /**
     * 返回指定区间的所有时间列表
     *
     * @param begin 包括
     * @param end 包括
     * @return
     */
    public static List<String> days(LocalDate begin, LocalDate end) {
        List<String> ret = new LinkedList<>();
        if (begin.isAfter(end)){ // 如果begin 在 end 后面 说明反了，直接返回空
            return ret;
        }

        // 如果begin在end前面，就加入列表，然后计算下一天
        while (begin.isBefore(end)) {
            ret.add(format(begin));

            begin = nextDay(begin);
        }

        // begin == end 就会退出循环到这里，然后加入列表
        ret.add(format(begin));

        return ret;
    }

    public static List<String>days(Date begin, Date end) {
        try {
            String begin_day = sdf.format(begin);
            String end_day = sdf.format(end);

            // 根据begin end 计算出中间的日期列表
            Calendar beginDate = Calendar.getInstance();
            beginDate.setTime(sdf.parse(begin_day + " 00:00:00"));

            Calendar endDate = Calendar.getInstance();
            endDate.setTime(sdf.parse(end_day + " 00:00:00"));

            List<String> dateList = new ArrayList<>();

            if (beginDate.after(endDate)) {
                return dateList;
            }

            while (beginDate.before(endDate)) {
                dateList.add(sdf.format(beginDate.getTime()));
                beginDate.add(Calendar.DAY_OF_MONTH, 1);
            }
            dateList.add(sdf.format(beginDate.getTime()));

            return  dateList;
        }catch (Exception e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }

    /**
     * 添加对应天数，并返回字符串
     * @param localDate
     * @param days
     * @return
     */
    public static String addDays2String(LocalDate localDate, int days) {
        return format(localDate.plusDays(days));
    }

    public  static long getMillis(Date date) {
        return date.getTime();
    }
}
