package org.example.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"unchecked", "rawtypes"})
@Component
public class RedisCache {
    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param <T> 缓存的对象类型
     */
    public <T> void setCacheObject(String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     * @param <T> 缓存的对象类型
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     * @param key Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     * @param key Redis键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获得缓存的基本对象
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     * @param <T> 缓存的对象类型
     */
    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * 删除单个对象
     * @param key 缓存键值
     * @return true=删除成功；false=删除失败
     */
    public boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     * @param collection 多个对象
     * @return 删除的个数
     */
    public long deleteObject(final Collection collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     * @param <T> 缓存的对象类型
     */
    public <T> long setCacheList(String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     * @param <T> 缓存的对象类型
     */
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     * @param key 缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     * @param <T> 缓存的对象类型
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet) {
        BoundSetOperations<String, T> setOperations = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperations.add(it.next());
        }
        return setOperations;
    }

    /**
     * 获得缓存的set
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     * @param <T> 缓存的对象类型
     */
    public <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     * @param key 缓存键值
     * @param dataMap 待缓存的Map
     * @param <T> 缓存的对象类型
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     * @param <T> 缓存的对象类型
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     * @param key Redis键
     * @param field Hash键
     * @param value 值
     * @param <T> 值类型
     */
    public <T> void setCacheMapValue(final String key, final String field, final T value) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        opsForHash.put(key, field, value);
    }
    /**
     * 获取Hash中的数据
     * @param key Redis键
     * @param field Hash键
     * @return 值
     * @param <T> 值类型
     */
    public <T> T getCacheMapValue(final String key, final String field) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, field);
    }

    /**
     * 判断缓存中是否有对应的value
     * @param key 缓存的键值
     * @param hKey Hash键
     * @param v 值
     */
    public void incrementCacheMapValue(String key, String hKey, long v) {
        redisTemplate.boundHashOps(key).increment(hKey, v);
    }

    /**
     * 删除Hash中的数据
     * @param key Redis键
     * @param hKey Hash键
     */
    public void deleteCacheMapValue(final String key, final String hKey) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, hKey);
    }

    /**
     * 获取多个Hash中的数据
     * @param key Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     * @param <T> 值类型
     */
    public <T> List<T> getCacheMapValues(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 获得缓存的基本对象列表
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

}
