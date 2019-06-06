package com.dream.chat.cache;

import com.dream.chat.cache.dto.BaseRedisDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author lw
 * @since 2018-10-29
 */
/*@Component*/
@Service
public interface RedisService {

    void set(String key,String value);

    void set(BaseRedisDTO redisDTO);

    void set(String key,String value, long timeout, TimeUnit unit);

    void set(BaseRedisDTO redisDTO, long timeout, TimeUnit unit);

    void set(BaseRedisDTO redisDTO,long ttl);

    <T> T get(BaseRedisDTO redisDTO,Class<T> clazz);

    <T> T get(String key,Class<T> clazz);

    Object get(String key);

    Object get(String key , long start , long end);

    void append(Object key,String value);

    void setHashObj(String key,Map<String, Object> map);

    Object getHashObj(String HK,String key);

    Map<String,Object> getHashObj(String HK);

    Long delHashObj(String HK, Object... hashKeys);

    void setListObj(String HK,List<?> list);

    List<?> getListObj(String HK);

    Long delListObj(String HK, long count, Object value);

    Set getListByKeys(String HK);

    Boolean delObj(Object key);

    Long getExpire(String key,TimeUnit unit);

    Boolean setExpireSecond(String key,long second);

    Long getExpireSeconds(String key);
    /**
     * key是否已锁
     * 默认存在60s
     * @param key
     * @return
     */
    boolean isLock(String key);

    /**
     * key是否已锁
     * 默认存在60s
     * @param key
     * @return
     */
    boolean isLock(String key, long seconds);

    /**
     *  获取锁，如果key已经被锁住，则等待锁被释放
     * @param key
     * @return
     */
    boolean getLock(String key);

    void unLock(String key, long lockValue);

    long lock(String key, Long timeoutSecond);

    /**
     *  获取锁，如果key已经被锁住，则等待锁被释放
     * @param key
     * @return
     */
    boolean getLock(String key, long seconds);

    /**
     * 释放锁
     * @param key
     * @return
     */
    boolean unLock(String key);

}
