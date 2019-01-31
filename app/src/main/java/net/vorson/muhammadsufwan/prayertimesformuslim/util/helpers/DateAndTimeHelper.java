package net.vorson.muhammadsufwan.prayertimesformuslim.util.helpers;

import java.util.Calendar;
import java.util.Date;

public class DateAndTimeHelper {

    public static Date getTimeDate(String time, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String[] temp = time.split(":");
        int hour = Integer.parseInt(temp[0]);
        int min = Integer.parseInt(temp[1]);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Calendar GetToday() {
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        return cal;
    }

    public static Calendar GetTomorrow() {
        Date now = new Date();
        Calendar calt = Calendar.getInstance();
        calt.setTime(now);
        calt.add(Calendar.DATE, 1);
        return calt;
    }

    public static Calendar GetYesturday() {
        Date now = new Date();
        Calendar calt = Calendar.getInstance();
        calt.setTime(now);
        calt.add(Calendar.DATE, -1);
        return calt;
    }

    public static String SplitTimesHour(String time) {
        return time.split(" ")[0].split(":")[0];
    }

    public static String SplitTimesMinute(String time) {
        return time.split(" ")[0].split(":")[1];
    }

    public static String SplitTimesAmPM(String time) {
        String AmPM = "";
        try {
            return time.split(" ")[1];
        } catch (Exception e) {
            return "";
        }
    }

    public static String SplitTimesFromAmPM(String time) {
        String TimeDisplay = "";
        try {
            return time.split(" ")[0];
        } catch (Exception e) {
            return "";
        }
    }

    public static String SplitTimesAP(String time) {
        String AmPM = "";
        try {
            StringBuilder myName = new StringBuilder(time.split(" ")[1]);
            myName.setCharAt(1, '.');
            return String.valueOf(myName);
        } catch (Exception e) {
            return "";
        }
    }

    public static String HoursFromDiff(long diff) {
        return (((diff / 1000) / 60) / 60) + "";
    }

    public static String MinutesFromDiff(long diff) {
        long minutes = (diff / 1000) / 60;
        long minutesremain = minutes - ((minutes / 60) * 60);
        String MinutesDisplay = minutesremain + "";
        if (minutesremain < 10) {
            return "0" + minutesremain;
        }
        return MinutesDisplay;
    }

    public static boolean isFriday() {
        if (GetToday().get(Calendar.DAY_OF_WEEK) == 6) {
            return true;
        }
        return false;
    }



}
