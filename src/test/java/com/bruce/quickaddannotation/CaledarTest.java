package com.bruce.QuickAddAnnotation;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author bruce ge 2020/9/7
 */
public class CaledarTest {
    @Test
    public void testCalendar(){
        Calendar instance = Calendar.getInstance();
        instance.clear();
        instance.set(Calendar.YEAR, 2020);
        instance.set(Calendar.MONTH, 11);
        instance.set(Calendar.DAY_OF_MONTH, 11);
        instance.set(Calendar.HOUR, 13);
        instance.set(Calendar.MINUTE, 24);
        instance.set(Calendar.SECOND, 14);
        Date time = instance.getTime();
        System.out.println(time);


        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        String nowAsISO = df.format(time);
        System.out.println(nowAsISO);

    }
}
