package Threads;

import java.util.function.Consumer;

public class TimerThread extends Thread {

    private final int _stepMs;
    private final Consumer<TimerThread> _consumer;

    private volatile boolean _running;

    public TimerThread(int stepMs, Consumer<TimerThread> consumer) {
        _stepMs = stepMs;
        _consumer = consumer;
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
                _consumer.accept(this);
                Thread.sleep(_stepMs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
