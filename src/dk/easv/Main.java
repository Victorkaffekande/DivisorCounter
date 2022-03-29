package dk.easv;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws Exception {

        // Fetches the start time of the method.
        Instant start = Instant.now();

        // Invokes the divisor counter
        ExecutorService es = Executors.newCachedThreadPool();

        int min = 1;
        int max = 500000;
        int jumpSize = 500; //chuck size every thread has to check

        int newMax = jumpSize;
        int oldmax = min;
        ArrayList<DivisorCounter> tasklist = new ArrayList<>();
        while (newMax < max) {
            DivisorCounter task = new DivisorCounter(oldmax, newMax);
            tasklist.add(task);
            oldmax += jumpSize;
            newMax += jumpSize;
        }
        System.out.println("Looking for the best result...");
        es.invokeAll(tasklist);

        // Fetches the end time of the method.
        Instant end = Instant.now();

        // Find the highest result
        Result result = DivisorCounter.getBestResult();
        System.out.println(result.getNumber() + " maxResult " + result.getDivisorCounter() + " divisors!");
        System.out.println("Duration: " + Duration.between(start, end).toMillis() + " ms");
        es.shutdown();
    }
}
