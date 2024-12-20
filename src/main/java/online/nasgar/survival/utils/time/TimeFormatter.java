package online.nasgar.survival.utils.time;

import org.apache.commons.lang.time.DurationFormatUtils;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class TimeFormatter {

    private static final long MINUTE = TimeUnit.MINUTES.toMillis(1L);
    private static final long HOUR = TimeUnit.HOURS.toMillis(1L);
    public static ThreadLocal<DecimalFormat> REMAINING_SECONDS = ThreadLocal.withInitial(() -> new DecimalFormat("0.#"));
    public static ThreadLocal<DecimalFormat> REMAINING_SECONDS_TRAILING = ThreadLocal.withInitial(() -> new DecimalFormat("0.0"));

    public static String getRemaining(long millis, boolean milliseconds) {
        return getRemaining(millis, milliseconds, true);
    }

    public static String getRemaining(long duration, boolean milliseconds, boolean trail) {
        if (milliseconds && duration < MINUTE) {
            return (trail ? REMAINING_SECONDS_TRAILING : REMAINING_SECONDS).get().format(duration * 0.001) + 's';
        } else {
            return DurationFormatUtils.formatDuration(duration, (duration >= HOUR ? "HH:" : "") + "mm:ss");
        }
    }

}