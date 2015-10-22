
package com.hexanome.utils;

/**
 *
 * @author paul
 */
public class TypeWrapper {
    /**
     * 
     * @param timestamp
     * @return 
     */
    public static int timestampToSeconds(String timestamp) {
        int hour =  Integer.parseInt(timestamp.split(":")[0]);
        int min =  Integer.parseInt(timestamp.split(":")[1]);
        int sec =  Integer.parseInt(timestamp.split(":")[2]);
        return sec + 60*min + 3600*hour;
    }
    /**
     * 
     * @param seconds
     * @return 
     */
    public static String secondsToTimestamp(int seconds) {
        int hour = (int)(seconds/3600);
        int min = (int)((seconds-hour*3600)/3600);
        int sec = (int)((seconds-min*60-hour*3600)/3600);
        return String.format("%d:%d:%d", hour, min, sec);
    } 
}
