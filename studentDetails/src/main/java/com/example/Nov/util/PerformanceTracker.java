package com.example.Nov.util;

import org.springframework.stereotype.Component;

import java.util.function.Supplier;

// util/PerformanceTracker.java
@Component
public class PerformanceTracker {

    public <T> T track(String label, Supplier<T> task) {
        long start = System.currentTimeMillis();
        T result = task.get();
        long end = System.currentTimeMillis();
        System.out.println("⏱ [" + label + "] Time taken: " + (end - start) + " ms");
        return result;
    }
}
