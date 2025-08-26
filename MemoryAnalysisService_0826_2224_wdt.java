// 代码生成时间: 2025-08-26 22:24:23
package com.example.memoryanalysis;

import io.quarkus.runtime.annotations.RegisterForReflection;
# 扩展功能模块
import org.eclipse.microprofile.health.HealthCheck;
# TODO: 优化性能
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
# 扩展功能模块
 * A service class to analyze memory usage.
 */
@RegisterForReflection
public class MemoryAnalysisService implements HealthCheck {

    private static final String MEMORY_POOL_NAME = "java.lang:type=Memory";
    private static final String OBJECT_NAME = "com.example:type=MemoryAnalysis";

    /**
     * Check the current memory usage and return a health check response.
     *
     * @return A HealthCheckResponse object indicating the memory status.
     */
# 添加错误处理
    @Readiness
# NOTE: 重要实现细节
    public HealthCheckResponse call() {
        try {
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
# TODO: 优化性能
            ObjectName objectName = new ObjectName(MEMORY_POOL_NAME);
            MemoryMXBean memoryMXBean = ManagementFactory.newPlatformMXBeanProxy(
                server, MEMORY_POOL_NAME, MemoryMXBean.class
# 扩展功能模块
            );
            MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
            MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

            if (heapMemoryUsage.getUsed() > heapMemoryUsage.getMax()) {
                return HealthCheckResponse.builder().status(HealthCheckResponse.Status.DOWN).build();
            } else {
                return HealthCheckResponse.builder().status(HealthCheckResponse.Status.UP).build();
            }
        } catch (Exception e) {
            // Log the exception and return a DOWN status
            e.printStackTrace();
            return HealthCheckResponse.builder().status(HealthCheckResponse.Status.DOWN).build();
        }
    }

    /**
     * Reports the current memory usage.
     *
     * @return A string containing the memory usage report.
     */
    public String reportMemoryUsage() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
# 扩展功能模块

        StringBuilder report = new StringBuilder();
        report.append("Heap Memory Usage: 
");
        report.append("  Initial: ").append(heapMemoryUsage.getInit()).append(" bytes
");
        report.append("  Used: ").append(heapMemoryUsage.getUsed()).append(" bytes
");
        report.append("  Commited: ").append(heapMemoryUsage.getCommitted()).append(" bytes
");
        report.append("  Max: ").append(heapMemoryUsage.getMax()).append(" bytes
");
        report.append("Non-Heap Memory Usage: 
");
        report.append("  Initialization: ").append(nonHeapMemoryUsage.getInit()).append(" bytes
");
        report.append("  Used: ").append(nonHeapMemoryUsage.getUsed()).append(" bytes
");
        report.append("  Commited: ").append(nonHeapMemoryUsage.getCommitted()).append(" bytes
");
        report.append("  Max: ").append(nonHeapMemoryUsage.getMax()).append(" bytes
");

        return report.toString();
    }
}
# 优化算法效率
