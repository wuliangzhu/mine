package com.mye.mine.utils;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtilTest {
    @Test
    public void daysTest() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date begin = simpleDateFormat.parse("2019-04-29 12:20:00");
            Date end = simpleDateFormat.parse("2019-05-01 05:20:00");

            List<String> ret = DateUtil.days(begin, end);

            Assert.assertArrayEquals(new String[]{"2019-04-29","2019-04-30","2019-05-01",}, ret.toArray());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void daysTest2() {
        LocalDate begin = LocalDate.of(2019, 4, 29);
        LocalDate end = LocalDate.of(2019, 5, 1);

        List<String> ret = DateUtil.days(begin, end);

        Assert.assertArrayEquals(new String[]{"2019-04-29","2019-04-30","2019-05-01",}, ret.toArray());
    }
}
