package com.example.movietickets;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/* Class to retrieve dates and times stored inside lists */
public class DateTime {

    private Calendar calendar = Calendar.getInstance();

    /* Returns current date and the dates of the next four days stored in a list */
    public List<String> getDates() {

        List<String> dates = new ArrayList<>();

        Date day1 = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date day2 = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date day3 = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date day4 = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date day5 = calendar.getTime();

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        final String day1_str = dateFormat.format(day1);
        final String day2_str = dateFormat.format(day2);
        final String day3_str = dateFormat.format(day3);
        final String day4_str = dateFormat.format(day4);
        final String day5_str = dateFormat.format(day5);

        dates.add("Tomorrow");
        dates.add(day3_str);
        dates.add(day4_str);
        dates.add(day5_str);

        return dates;
    }

    /* Returns two times stored in a list */
    public List<String> getTimes(String time1, String time2) {

        List<String> times = new ArrayList<>();

        times.add(time1);
        times.add(time2);

        return times;
    }
}

