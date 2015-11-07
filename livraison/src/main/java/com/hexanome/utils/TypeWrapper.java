package com.hexanome.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * This class provides type conversion static methods
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class TypeWrapper {

    /**
     * Converts a String timestamp to its sum in seconds
     *
     * @param timestamp formated string HH:mm:ss
     * @return
     * @see secondsToTimestamp
     */
    public static int timestampToSeconds(String timestamp) {
        int hour = Integer.parseInt(timestamp.split(":")[0]);
        int min = Integer.parseInt(timestamp.split(":")[1]);
        int sec = Integer.parseInt(timestamp.split(":")[2]);
        return sec + 60 * min + 3600 * hour;
    }

    /**
     * Converts a sum of seconds to an formated timestamp
     *
     * @param seconds sum of seconds, minutes and hours to convert
     * @return
     * @see timestampToSeconds
     */
    public static String secondsToTimestamp(int seconds) {
        int hour = (int) (seconds / 3600);
        int min = (int) ((seconds - hour * 3600) / 60);
        int sec = (int) ((seconds - min * 60 - hour * 3600) / 60);
        return String.format("%d:%d:%d", hour, min, sec);
    }


}
