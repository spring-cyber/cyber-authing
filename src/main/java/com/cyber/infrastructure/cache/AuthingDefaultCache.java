package com.cyber.infrastructure.cache;


import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AuthingDefaultCache implements AuthingCache {

    private static Map<String, CacheState> stateCache = new ConcurrentHashMap<>();
    private final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock(true);
    private final Lock writeLock = cacheLock.writeLock();
    private final Lock readLock = cacheLock.readLock();

    public AuthingDefaultCache() {
        if (AuthingCacheConfig.schedulePrune) {
            this.schedulePrune(AuthingCacheConfig.timeout);
        }
    }

    @Override
    public void set(String key, String value) {
        set(key, value, AuthingCacheConfig.timeout);
    }

    @Override
    public void set(String key, String value, long timeout) {
        writeLock.lock();
        try {
            stateCache.put(key, new CacheState(value, timeout));
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public String get(String key) {
        readLock.lock();
        try {
            CacheState cacheState = stateCache.get(key);
            if (null == cacheState || cacheState.isExpired()) {
                return null;
            }
            return cacheState.getState();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsKey(String key) {
        readLock.lock();
        try {
            CacheState cacheState = stateCache.get(key);
            return null != cacheState && !cacheState.isExpired();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void pruneCache() {
        Iterator<CacheState> values = stateCache.values().iterator();
        CacheState cacheState;
        while (values.hasNext()) {
            cacheState = values.next();
            if (cacheState.isExpired()) {
                values.remove();
            }
        }
    }

    public void schedulePrune(long delay) {
        AuthingCacheScheduler.INSTANCE.schedule(this::pruneCache, delay);
    }

    private class CacheState implements Serializable {
        private String state;
        private long expire;

        CacheState(String state, long expire) {
            this.state = state;
            this.expire = System.currentTimeMillis() + expire;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > this.expire;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public long getExpire() {
            return expire;
        }

        public void setExpire(long expire) {
            this.expire = expire;
        }
    }
}
