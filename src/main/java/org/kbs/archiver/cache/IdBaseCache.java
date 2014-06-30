package org.kbs.archiver.cache;

import org.kbs.archiver.model.ModelHasID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.List;

/**
 * Created by kcn on 14-6-26.
 */
public class IdBaseCache<T extends ModelHasID>  {

  private static final Logger LOG = LoggerFactory.getLogger(IdBaseCache.class);
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

  public void putAll(List<T> objects) {
    clear();
    for (T obj:objects) {
      put(obj);
    }
  }
/*

  public List<T> getAll() {
    return cache.
  }
*/

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
