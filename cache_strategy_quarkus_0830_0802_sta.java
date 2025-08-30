// 代码生成时间: 2025-08-30 08:02:22
package com.example.cache;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.cache.Cache;
import org.eclipse.microprofile.cache.CacheManager;
import org.eclipse.microprofile.cache.Cached;
import org.eclipse.microprofile.cache.Evict;
import org.eclipse.microprofile.cache.Refresh;
import org.eclipse.microprofile.cache.Refreshes;

@ApplicationScoped
public class CacheStrategyQuarkus {

    @Inject
    CacheManager cacheManager;

    // Cache a value under the key 'data' for 10 seconds
    @Cached(cacheName = "dataCache", cacheKey = "'data'", duration = 10000)
    public String getData() {
        // This method will be called only once in 10 seconds if the cache is warm
        return "Data from cache";
    }

    // Evict a cache entry by key
    @Evict(cacheName = "dataCache", cacheKey = "'data'")
    public void evictData() {
        // This method will remove the 'data' entry from the cache
    }

    // Refresh the cache entry by key
    @Refresh(cacheName = "dataCache", cacheKey = "'data'")
    public void refreshData() {
        // This method will force a refresh of the 'data' entry in the cache
    }

    // Add a new entry to the cache
    public void addToCache(String key, String value) {
        cacheManager.getCache("dataCache").put(key, value);
    }

    // Get a value from the cache by key
    public String getValueFromCache(String key) {
        return (String) cacheManager.getCache("dataCache").get(key);
    }

    // Remove a value from the cache by key
    public void removeFromCache(String key) {
        cacheManager.getCache("dataCache").remove(key);
    }

    // Clear the entire cache
    public void clearCache() {
        cacheManager.getCache("dataCache").clear();
    }
}