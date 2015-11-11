package insa.h4401.utils;

/**
 * This class provides type conversion static methods
 *
 * @author Lisa, Estelle, Antoine, Pierre, Hugues, Guillaume, Paul
 */
public class TypeWrapper {

    /**
     * Converts a String timeString to its sum in seconds
     *
     * @param timeString time formated string HH:mm:ss
     * @return time in seconds
     */
    public static int xmlTimeStringToSeconds(String timeString) {
        int hour = Integer.parseInt(timeString.split(":")[0]);
        int min = Integer.parseInt(timeString.split(":")[1]);
        int sec = Integer.parseInt(timeString.split(":")[2]);
        return sec + 60 * min + 3600 * hour;
    }

    /**
     * Converts a sum of seconds to an formated timestamp
     *
     * @param seconds sum of seconds, minutes and hours to convert
     * @return formated time
     */
    public static String secondsToFormatedTime(int seconds) {
        int hour = seconds / 3600;
        int min = (int) ((seconds - hour * 3600.) / 60.);
        int sec = (int) (seconds - min * 60. - hour * 3600.);
        return String.format("%02d:%02d:%02d", hour, min, sec);
    }

}
