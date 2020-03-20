package com.start.service;

import java.util.Set;

public interface CacheService {
    /**
     *
     * @param key
     * @param value
     * @param expire 过期时间，以秒为单位
     */
    void putIntoCache(final String key, final Object value, long expire);
    void clearCache(final String key);
    <T> T getFromCache(final String key, final Class<T> clazz);
    <T> T getSet(final String key, final Object value, Class<T> clazz, final long expireTime);

    Set<String> keys(String pattern);
    int countKeys(String pattern);
//    byte[] getByte(final String key);
    void setex(final String key, final long expireTime, final byte[] value);
    void setex(String key, long expireTime, String value);
    void setExpireTime(final String key, final long expireTime);

    Long getExpireTime(final String key);
    boolean lock(String key);
    void delete(String key);
}
