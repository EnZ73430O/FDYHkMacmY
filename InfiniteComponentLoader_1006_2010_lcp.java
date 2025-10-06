// 代码生成时间: 2025-10-06 20:10:51
package com.quarkus.example;

import io.quarkus.runtime.StartupEvent;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Path("/components")
@ApplicationScoped
public class InfiniteComponentLoader {

    @Inject
    private ComponentLoader componentLoader;

    private ExecutorService executorService;

    public void onStart(@Observes StartupEvent event) {
        // Initialize the executor service for running the infinite loading process
        executorService = Executors.newSingleThreadExecutor();

        // Schedule the infinite loading task on a separate thread
        executorService.submit(this::loadComponentsInfinite);
    }

    private void loadComponentsInfinite() {
        try {
            while (true) {
                // Load components continuously with a delay between each load
                componentLoader.loadComponents();

                // Wait for a specified delay before the next load
                // This simulates an infinite loading process with controlled pacing
                TimeUnit.SECONDS.sleep(5);
            }
        } catch (InterruptedException e) {
            // Handle the interruption to allow the thread to be stopped gracefully
            System.out.println("Component loading interrupted.");
            Thread.currentThread().interrupt();
        }
    }

    @GET
    @Path("/load")
    @Produces(MediaType.TEXT_PLAIN)
    public String triggerComponentLoad() {
        // This method allows for manual triggering of component loading
        componentLoader.loadComponents();
        return "Component loading triggered.";
    }

    @GET
    @Path("/status")
    @Produces(MediaType.TEXT_PLAIN)
    public String getStatus() {
        // Return the current status of the component loading process
        Instant lastLoad = componentLoader.getLastLoadTime();
        return "Last component load time: " + lastLoad.toString();
    }
}

/*
 * ComponentLoader.java
 *
 * A simple service class to simulate component loading.
 */
package com.quarkus.example;

import java.time.Instant;

public class ComponentLoader {

    private Instant lastLoadTime;

    public void loadComponents() {
        lastLoadTime = Instant.now();
        // Simulate loading components
        System.out.println("Loading components at: " + lastLoadTime);
    }

    public Instant getLastLoadTime() {
        return lastLoadTime;
    }
}
