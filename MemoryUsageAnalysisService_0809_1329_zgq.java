// 代码生成时间: 2025-08-09 13:29:38
package com.example.memory;

import javax.enterprise.context.ApplicationScoped;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * A service class for analyzing memory usage.
 */
@ApplicationScoped
public class MemoryUsageAnalysisService {

    private final MemoryMXBean memoryMXBean;

    /**
     * Constructor that initializes the MemoryMXBean.
     */
    public MemoryUsageAnalysisService() {
        this.memoryMXBean = ManagementFactory.getMemoryMXBean();
    }

    /**
     * Retrieves the current non-heap memory usage.
     *
     * @return MemoryUsage representing the non-heap memory usage.
     */
    public MemoryUsage getNonHeapMemoryUsage() {
        return memoryMXBean.getNonHeapMemoryUsage();
    }

    /**
     * Retrieves the current heap memory usage.
     *
     * @return MemoryUsage representing the heap memory usage.
     */
    public MemoryUsage getHeapMemoryUsage() {
        return memoryMXBean.getHeapMemoryUsage();
    }

    /**
     * Performs a full garbage collection.
     *
     * @return long representing the memory freed after garbage collection.
     */
    public long performGarbageCollection() {
        long initialMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.gc();
        long finalMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        return initialMemory - finalMemory;
    }
}
