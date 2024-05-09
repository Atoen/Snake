package Threads;

public class TimerThread extends Thread {

    private final int _stepMs;
    private final Runnable _runnable;

    private volatile boolean _running;

    public TimerThread(int stepMs, Runnable runnable) {
        _stepMs = stepMs;
        _runnable = runnable;
    }

    public void terminate() {
        _running = false;
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        if (_running) return;
        _running = true;

        while (_running) {
            try {
                _runnable.run();
                Thread.sleep(_stepMs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
