package Threads;

import java.util.function.Consumer;

/**
 * A custom thread implementation for executing tasks at regular intervals.
 */
public class TimerThread extends Thread {

    private final int _stepMs;
    private final Consumer<TimerThread> _consumer;

    private volatile boolean _running;

    /**
     * Constructs a TimerThread object.
     *
     * @param stepMs    The time interval (in milliseconds) between each execution of the task.
     * @param consumer  The task to be executed by the thread.
     */
    public TimerThread(int stepMs, Consumer<TimerThread> consumer) {
        _stepMs = stepMs;
        _consumer = consumer;
    }

    /**
     * Terminates the thread.
     */
    public void terminate() {
        _running = false;
    }

    /**
     * Executes the task repeatedly at regular intervals until terminated.
     */
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
