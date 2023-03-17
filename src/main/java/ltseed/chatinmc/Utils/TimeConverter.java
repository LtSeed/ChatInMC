package ltseed.chatinmc.Utils;
import java.util.HashMap;
import java.util.Map;

public class TimeConverter {

    private static final Map<Character, Long> timeMap = new HashMap<>();
    static {
        timeMap.put('毫', 1L);
        timeMap.put('秒', 1000L);
        timeMap.put('分', 60 * 1000L);
        timeMap.put('时', 60 * 60 * 1000L);
        timeMap.put('天', 24 * 60 * 60 * 1000L);
    }

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
