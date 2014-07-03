package org.kbs.archiver.cache;

import net.sf.ehcache.Ehcache;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by kcn on 14-6-21.
 */

@Service("cacheUtils")
@Scope("prototype")
public class CacheUtils {

    @Resource(name = "cacheManager")
    private EhCacheCacheManager cacheManager;

    public Ehcache getCache() {
        return cache;
    }

    public void setCache(Ehcache cache) {
        this.cache = cache;
    }

    private Ehcache cache;

    CacheStatistics saveStatistics;

    public CacheUtils() {

    }

    public void setCache(String cachename) {
        cache = ((EhCacheCache) cacheManager.getCache(cachename)).getNativeCache();
    }

    public void saveStat() {
        saveStatistics = new CacheStatistics(cache.getStatistics());
    }

    public CacheStatistics getSaveStatistics() {
        return saveStatistics;
    }

    public CacheStatistics getStatistics() {
        return new CacheStatistics(cache.getStatistics());
    }

    public void removeAll() {
        cache.removeAll();
    }
}
