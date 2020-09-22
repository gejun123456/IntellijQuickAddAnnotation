package com.bruce.QuickAddAnnotation;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author bruce ge 2020/9/6
 */
public class SimpleDateTimeTest {
    @Test
    public void testSimpleDateFormat() {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String format = simpleDateFormat.format(new Date());
        System.out.println(format);
    }


    @Test
    public void StringToDateJava8() {
        // Example 1
        DateTimeFormatter formatter_1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String str_date_1 = "24/09/2019";

        LocalDate local_date_1 = LocalDate.parse(str_date_1, formatter_1);

        System.out.println(local_date_1);

        // Example 2
        DateTimeFormatter formatter_2 = DateTimeFormatter.ofPattern("MMM d yyyy");
        String str_date_2 = "Sep 24 2019";

        LocalDate local_date_2 = LocalDate.parse(str_date_2, formatter_2);

        System.out.println(local_date_2);

        // Example 3
        DateTimeFormatter formatter_3 = DateTimeFormatter.ofPattern("d-MMM-yyyy");
        String str_date_3 = "24-Sep-2019";

        LocalDate local_date_3 = LocalDate.parse(str_date_3, formatter_3);

        System.out.println(local_date_3);
    }
}
