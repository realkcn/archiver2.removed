package org.kbs.archiver.cache;

import net.sf.ehcache.statistics.StatisticsGateway;

/**
 * Created by kcn on 14-6-21.
 */
public class CacheStatistics {
    private long hitCount;

    private long evictedCount;

    private long expiredCount;

    private long missCount;

    private long missExpiredCount;

    private long putCount;

    private long removeCount;

    private long putUpdatedCount;

    private long size;

    public CacheStatistics() {
        hitCount = 0;
        evictedCount = 0;
        expiredCount = 0;
        missCount = 0;
        missExpiredCount = 0;
        putCount = 0;
        removeCount = 0;
        putUpdatedCount = 0;
        size = 0;
    }

    public CacheStatistics(StatisticsGateway statistics) {
        hitCount = statistics.cacheHitCount();
        evictedCount = statistics.cacheEvictedCount();
        expiredCount = statistics.cacheExpiredCount();
        missCount = statistics.cacheMissCount();
        missExpiredCount = statistics.cacheMissCount();
        putCount = statistics.cachePutCount();
        removeCount = statistics.cacheRemoveCount();
        putUpdatedCount = statistics.cachePutUpdatedCount();
        size = statistics.getSize();
    }

    public synchronized void incHitCount() {
        hitCount++;
    }

    public synchronized void incEvictedCount() {
        evictedCount++;
    }

    public synchronized void incExpiredCount() {
        expiredCount++;
    }

    public synchronized void incMissCount() {
        missCount++;
    }

    public synchronized void incMissExpiredCount() {
        missExpiredCount++;
    }

    public synchronized void incputCount() {
        putCount++;
    }

    public synchronized void incputUpdatedCount() {
        putUpdatedCount++;
    }

    public long getSize() {
        return size;
    }

    public synchronized void setSize(long size) {
        this.size = size;
    }

    public long getHitCount() {
        return hitCount;
    }

    public long getEvictedCount() {
        return evictedCount;
    }

    public long getExpiredCount() {
        return expiredCount;
    }

    public long getMissCount() {
        return missCount;
    }

    public long getMissExpiredCount() {
        return missExpiredCount;
    }

    public long getPutCount() {
        return putCount;
    }

    public long getRemoveCount() {
        return removeCount;
    }

    public long getPutUpdatedCount() {
        return putUpdatedCount;
    }
}
