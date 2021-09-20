package online.nasgar.survival.scoreboard.thread;

import online.nasgar.survival.scoreboard.NautilusManager;

public class NautilusThread extends Thread {

    private final NautilusManager nautilusManager;
    private final long ticks;

    public NautilusThread(NautilusManager nautilusManager, long ticks){
        this.nautilusManager = nautilusManager;
        this.ticks = ticks;

        this.start();
    }

    @Override public void run() {
        while (true){
            try {
                this.nautilusManager.forEach(player -> this.nautilusManager.getNautilus().update(player));
                sleep(this.ticks * 50L);
            } catch (Exception exception){
                throw new RuntimeException(exception);
            }
        }
    }

}
