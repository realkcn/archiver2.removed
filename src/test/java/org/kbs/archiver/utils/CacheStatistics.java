package org.kbs.archiver.utils;

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

  public CacheStatistics(StatisticsGateway statistics) {
    hitCount=statistics.cacheHitCount();
    evictedCount=statistics.cacheEvictedCount();
    expiredCount=statistics.cacheExpiredCount();
    missCount=statistics.cacheMissCount();
    missExpiredCount=statistics.cacheMissCount();
    putCount=statistics.cachePutCount();
    removeCount=statistics.cacheRemoveCount();
    putUpdatedCount=statistics.cachePutUpdatedCount();
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

