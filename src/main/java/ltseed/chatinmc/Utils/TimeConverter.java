package ltseed.chatinmc.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * The TimeConverter class provides static methods for converting time strings
 * to their equivalent long values in milliseconds, and vice versa.
 * <p>
 * The conversion is done by parsing a time string in the format of a number followed
 * by a time unit character ('毫' for milliseconds, '秒' for seconds, '分' for minutes,
 * '时' for hours, and '天' for days) and multiplying the number with the corresponding
 * value in milliseconds. The converted long value can be used to represent time
 * duration in milliseconds.
 *
 * @author ltseed
 * @version 1.0
 */
public class TimeConverter {

    /**
     * A map containing the values in milliseconds for each time unit character.
     */
    private static final Map<Character, Long> timeMap = new HashMap<>();

    /*
      Initializes the timeMap with values for each time unit character.
     */
    static {
        timeMap.put('毫', 1L);
        timeMap.put('秒', 1000L);
        timeMap.put('分', 60 * 1000L);
        timeMap.put('时', 60 * 60 * 1000L);
        timeMap.put('天', 24 * 60 * 60 * 1000L);
    }

    /**
     * Converts a time string to its equivalent long value in milliseconds.
     * <p>
     * The time string should be in the format of a number followed by a time unit character.
     * For example, "5分" represents 5 minutes, and "2天3时" represents 2 days and 3 hours.
     *
     * @param s the time string to convert
     * @return the long value in milliseconds that represents the time duration
     * @throws NumberFormatException if the time string is not in the correct format
     */
    public static Long getTime(String s) {
        long time = 0L;
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                int j = i + 1;
                while (j < len && Character.isDigit(s.charAt(j))) {
                    j++;
                }
                long num = Long.parseLong(s.substring(i, j));
                char unit = s.charAt(j);
                time += num * timeMap.get(unit);
                i = j;
            }
        }
        return time;
    }

    /**
     * Converts a long value in milliseconds to a time string in the format of
     * a number followed by a time unit character.
     * <p>
     * The time string represents the time duration of the long value in milliseconds.
     * For example, 300000L represents 5 minutes, and 202800000L represents 2 days and 7 hours.
     *
     * @param time the long value in milliseconds to convert
     * @return the time string that represents the time duration
     */
    public static String getCode(Long time) {
        StringBuilder sb = new StringBuilder();
        if (time >= timeMap.get('天')) {
            sb.append(time / timeMap.get('天')).append("天");
            time %= timeMap.get('天');
        }
        if (time >= timeMap.get('时')) {
            sb.append(time / timeMap.get('时')).append("小时");
            time %= timeMap.get('时');
        }
        if (time >= timeMap.get('分')) {
            sb.append(time / timeMap.get('分')).append("分钟");
            time %= timeMap.get('分');
        }
        if (time >= timeMap.get('秒')) {
            sb.append(time / timeMap.get('秒')).append("秒");
            time %= timeMap.get('秒');
        }
        if (time >= timeMap.get('毫')) {
            sb.append(time / timeMap.get('毫')).append("毫秒");
        }
        return sb.toString();
    }

}
