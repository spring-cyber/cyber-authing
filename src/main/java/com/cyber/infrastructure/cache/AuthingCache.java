package com.cyber.infrastructure.cache;

public interface AuthingCache {

    void set(String key, String value);

    void set(String key, String value, long timeout);

    String get(String key);

    boolean containsKey(String key);

    default void pruneCache() {}

}
