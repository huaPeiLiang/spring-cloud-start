package com.start.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.start.Constants;
import com.start.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.util.SafeEncoder;

import java.util.HashSet;
import java.util.Set;

@Service
public class CacheServiceImpl implements CacheService {
    private static final Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void putIntoCache(final String key, final Object value, long expireTime) {
        logger.debug("putIntoCache, key={}, value={}", key, value);
        if ((expireTime > Constants.REDIS_CACHE_TIME_TEN_YEAR.longValue()) || (expireTime < 1L)) {
            throw new RuntimeException("expireTime must not less than 1 and longer than 10 years!");
        }
        this.redisTemplate.execute(new RedisCallback() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                SerializeWriter out = new SerializeWriter();
                JSONSerializer serializer = new JSONSerializer(out);
                serializer.write(value);
                connection.set(key.getBytes(), out.toBytes("utf-8"));
                connection.expire(key.getBytes(), expireTime);
                return Boolean.valueOf(true);
            }
        });
    }

    @Override
    public void clearCache(final String key) {
        redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.del(key.getBytes());
                return Boolean.valueOf(true);
            }
        });
    }

    @Override
    public <T> T getFromCache(final String key, final Class<T> clazz) {
        logger.debug("getFromCache key={}", key);
        return (T) redisTemplate.execute(new RedisCallback<Object>() {
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] out = connection.get(key.getBytes());
                if (out == null) {
                    return null;
                }
                return JSONObject.parseObject(out, clazz, new Feature[0]);
            }
        });
    }

    @Override
    public <T> T getSet(final String key, final Object value, Class<T> clazz, final long expireTime) {
        logger.debug("getSet.key={}", key);
        return (T) redisTemplate.execute(new RedisCallback<Object>() {
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] oldValue = connection.getSet(key.getBytes(), JSON.toJSONString(value).getBytes());
                connection.expire(key.getBytes(), expireTime);
                if (oldValue == null) {
                    return null;
                }
                return JSONObject.parseObject(oldValue, clazz, new Feature[0]);
            }
        });
    }

    @Override
    public Set<String> keys(String pattern) {
        RedisConnection connection = null;
        Set<String> results = new HashSet<>();
        try {
            connection = redisTemplate.getConnectionFactory().getConnection();
            Set<byte[]> keys = connection.keys(pattern.getBytes());
            if (!CollectionUtils.isEmpty(keys)) {
                for(byte[] bytes : keys) {
                    results.add(new String(bytes));
                }
            }
        } catch (Exception e) {
            logger.error("Query Redis error",e);
        }
        return results;
    }

    @Override
    public int countKeys(String pattern) {
        return keys(pattern).size();
    }

    @Override
    public void setex(final String key, final long expireTime, final byte[] value) {
        if (expireTime > Constants.REDIS_CACHE_TIME_TEN_YEAR || expireTime < 1){
            throw new RuntimeException("expireTime must not less than 1 and longer than 10 years!");
        }
        redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(key.getBytes(), expireTime, value);
                return true;
            }
        });
    }

    @Override
    public void setex(String key, long expireTime, String value) {
        setex(key, expireTime, SafeEncoder.encode(value));
    }

    /**
     *
     * Description: 设置redis失效时间
     *
     * @param key
     * @param expireTime
     */
    @Override
    public void setExpireTime(final String key, final long expireTime) {
        if (expireTime > Constants.REDIS_CACHE_TIME_TEN_YEAR || expireTime < 1){
            throw new RuntimeException("expireTime must not less than 1 and longer than 10 years!");
        }
        redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.expire(key.getBytes(), expireTime);
                return true;
            }
        });
    }

    @Override
    public Long getExpireTime(final String key) {
        logger.debug("getExpireTime, key={}", key);
        return (Long) redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.ttl(key.getBytes());
            }
        });
    }
}
