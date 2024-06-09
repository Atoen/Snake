package Utils;

import java.util.Random;

/**
 * The SharedRandom class provides a thread-safe way to generate random numbers.
 * It encapsulates a single instance of the Random class and provides a synchronized method
 * for generating random integers within a specified bound.
 */
public class SharedRandom {
    private static final Random _random = new Random();

    /**
     * Generates a random integer between 0 (inclusive) and the specified bound (exclusive).
     * @param bound the upper bound (exclusive) of the random number to be generated
     * @return a random integer between 0 (inclusive) and the bound (exclusive)
     */
    public static synchronized int nextInt(int bound) {
        return _random.nextInt(bound);
    }
}

