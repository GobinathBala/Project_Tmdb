package com.example.vijaymacnn.tmdb.Common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonFunctions {
    public static String convertDate(String objDate){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date;
        try {
            date = fmt.parse(objDate);
            SimpleDateFormat format_day = new SimpleDateFormat("dd",Locale.getDefault());
            SimpleDateFormat format_year = new SimpleDateFormat(" MMMM yyyy",Locale.getDefault());
            String day=format_day.format(date);
            String suffix=getDayOfMonthSuffix(Integer.parseInt(day));
            String rem_Date=format_year.format(date);
            return day+suffix+" "+rem_Date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
    private static String getDayOfMonthSuffix(final int n) {
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }
}
