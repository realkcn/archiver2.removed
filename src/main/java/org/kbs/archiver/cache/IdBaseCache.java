package org.kbs.archiver.cache;

import org.kbs.archiver.model.ModelHasID;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * Created by kcn on 14-6-26.
 */
public class IdBaseCache<T extends ModelHasID>  {

  public CacheManager getCacheManager() {
    return cacheManager;
  }

  public void setCacheManager(CacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }

  private CacheManager cacheManager;
  private Cache cache;
  ThreadLocal<CacheStatistics> localStatistics;

  public Cache getCache() {
    return cache;
  }

  public void setCache(Cache cache) {
    this.cache = cache;
  }

  public T get(long key) {
    T result=(T)cache.get(key);
    return result;
  }

  public void put(T object) {
    cache.put(object.getId(),object);
  }

  public void evit(long key) {
    cache.evict(key);
  }

  public void clear() {
    cache.clear();
  }
}
