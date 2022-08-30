package online.nasgar.vanilla.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.experimental.UtilityClass;
import online.nasgar.vanilla.Vanilla;
import online.nasgar.vanilla.utils.call.Callback;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.ThreadFactory;

@UtilityClass
public class TaskUtil {

    private final Vanilla plugin = Vanilla.getInstance();
    private final BukkitScheduler scheduler = Bukkit.getScheduler();

    public static ThreadFactory newThreadFactory(String name) {
        return new ThreadFactoryBuilder().setNameFormat(name).build();
    }

    public static ThreadFactory newThreadFactory(String name, Thread.UncaughtExceptionHandler handler) {
        return new ThreadFactoryBuilder().setNameFormat(name).setUncaughtExceptionHandler(handler).build();
    }

    public BukkitTask runTaskTimer(Callback callback, long delay, long tick) {
        return scheduler.runTaskTimer(plugin, callback::call, delay, tick);
    }

    public BukkitTask runTaskLater(Callback callback, long tick) {
        return scheduler.runTaskLater(plugin, callback::call, tick);
    }

    public static BukkitTask asyncLater(Callback callback, long delay) {
        return scheduler.runTaskLaterAsynchronously(plugin, callback::call, delay);
    }

    public BukkitTask runTaskTimerAsync(Callback callback, long delay, long tick) {
        return scheduler.runTaskTimerAsynchronously(plugin, callback::call, delay, tick);
    }

    public BukkitTask runTaskAsync(Callback callback) {
        return scheduler.runTaskAsynchronously(plugin, callback::call);
    }

    public BukkitTask runTask(Callback callback) {
        return scheduler.runTask(plugin, callback::call);
    }

    public void cancel(int id) {
        scheduler.cancelTask(id);
    }

}
