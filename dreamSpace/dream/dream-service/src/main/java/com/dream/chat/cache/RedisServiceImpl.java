package com.dream.chat.cache;

import com.dream.chat.cache.dto.BaseRedisDTO;
import com.dream.common.core.util.MmlJsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 功能描述
 * <p>
 *
 * @author xiangw
 * @since 2018-10-30
 */
@Service("redisService")
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate redisTemplate;

    /**
     * 锁的名称空间
     */
    private final String LOCK_NAMESPACE = "LOCK:";

    /**
     * 加锁默认超时时间
     */
    private static final long DEFAULT_TIMEOUT_SECOND = 5;

    /**
     * 加锁循环等待时间
     */
    private static final long LOOP_WAIT_TIME_MILLISECOND = 30;

    public RedisServiceImpl(RedisTemplate<String, String> redisTemplate) {

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());

        this.redisTemplate = redisTemplate;
    }

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(BaseRedisDTO redisDTO) {
        redisTemplate.opsForValue().set(redisDTO.key(), redisDTO.value());
    }

    @Override
    public <T> T get(BaseRedisDTO redisDTO, Class<T> clazz) {
        String value = (String) redisTemplate.opsForValue().get(redisDTO.key());
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return MmlJsonUtil.jsonToObj(value, clazz);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        String value = (String) redisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return MmlJsonUtil.jsonToObj(value, clazz);
    }

    @Override
    public void set(BaseRedisDTO redisDTO, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(redisDTO.key(), redisDTO.value(), timeout, unit);
    }

    public void set(BaseRedisDTO redisDTO, long ttl) {
        set(redisDTO);
        redisTemplate.expire(redisDTO.key(), ttl, TimeUnit.SECONDS);
    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Object get(String key, long start, long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    @Override
    public void append(Object key, String value) {
        redisTemplate.opsForValue().append(key, value);
    }

    @Override
    public void setHashObj(String key, Map<String, Object> map) {
        HashOperations<String, String, Object> vo = redisTemplate.opsForHash();
        vo.putAll(key, map);
    }

    @Override
    public Object getHashObj(String HK, String key) {
        return redisTemplate.opsForHash().get(HK, key);
    }

    @Override
    public Map<String, Object> getHashObj(String HK) {
        return redisTemplate.opsForHash().entries(HK);
    }

    @Override
    public Long delHashObj(String HK, Object... hashKeys) {
        return redisTemplate.opsForHash().delete(HK, hashKeys);
    }

    @Override
    public void setListObj(String HK, List<?> list) {
        redisTemplate.opsForList().rightPushAll(HK, list);
    }

    @Override
    public List<?> getListObj(String HK) {
        ListOperations listOperations = redisTemplate.opsForList();
        return listOperations.range(HK, 0, listOperations.size(HK));
    }

    @Override
    public Long delListObj(String HK, long count, Object value) {
        return redisTemplate.opsForList().remove(HK, count, value);
    }

    @Override
    public Set getListByKeys(String HK) {
        return redisTemplate.keys(HK);
    }

    @Override
    public Boolean delObj(Object key) {
        return redisTemplate.delete(key);
    }

    @Override
    public Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    public Boolean setExpireSecond(String key, long second) {
        return (Boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            StringRedisSerializer serializer = new StringRedisSerializer();
            return connection.expire(serializer.serialize(key), second);
        });
    }

    @Override
    public Long getExpireSeconds(String key) {
        return getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * key是否已锁
     * 默认存在60s
     *
     * @param key
     * @return
     */
    @Override
    public boolean isLock(String key) {
        return isLock(key, 60);
    }

    @Override
    public boolean isLock(String key, long seconds) {
        String nsKey = LOCK_NAMESPACE + key;
        Object isLock = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            StringRedisSerializer serializer = new StringRedisSerializer();
            Boolean success = connection.setNX(serializer.serialize(nsKey), serializer.serialize("0"));
            connection.expire(serializer.serialize(nsKey), seconds);
            return success;
        });
        return isLock == null || (Boolean) isLock;
    }

    public long lock(String key, Long timeoutSecond) {

        //LogConstant.workorder.info("Thread：" + Thread.currentThread().getName() + " start lock");

        //如果参数错误
        if (timeoutSecond != null && timeoutSecond <= 0) {
            timeoutSecond = DEFAULT_TIMEOUT_SECOND;
        }
        timeoutSecond = timeoutSecond == null ? DEFAULT_TIMEOUT_SECOND : timeoutSecond;

        while (true) {

            //超时时间点
            long timeoutTimeMilli = currentTimeMilliForRedis() + timeoutSecond * 1000;

            //如果设置成功
            if (redisTemplate.opsForValue().setIfAbsent(key, timeoutTimeMilli)) {
                //LogConstant.workorder.info("Thread：" + Thread.currentThread().getName() + " lock success");
                return timeoutTimeMilli;
            }

            //如果已经超时
            Long value = (Long) redisTemplate.opsForValue().get(key);
            if (value != null && value.longValue() < currentTimeMilliForRedis()) {

                //设置新的超时时间
                Long oldValue = (Long) redisTemplate.opsForValue().getAndSet(key, timeoutTimeMilli);//旧的值

                //多个线程同时getset，只有第一个才可以获取到锁
                if (value.equals(oldValue)) {
                    //LogConstant.workorder.info("Thread：" + Thread.currentThread().getName() + " lock success");
                    return timeoutTimeMilli;
                }
            }

            //延迟一定毫秒，防止请求太频繁
            try {
                Thread.sleep(LOOP_WAIT_TIME_MILLISECOND);
            } catch (InterruptedException e) {
                //LogConstant.workorder.error("DistributedLockUtil lock sleep error", e);
            }
        }
    }

    private long currentTimeMilliForRedis() {

        return (long) redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.time();
            }
        });
    }

    /**
     * 获取锁，如果key已经被锁住，则等待锁被释放
     *
     * @param key
     * @return
     */
    @Override
    public boolean getLock(String key) {
        return getLock(key, 60);
    }

    /**
     * 获取锁，如果key已经被锁住，则等待锁被释放
     *
     * @param key
     * @return
     */
    @Override
    public boolean getLock(String key, long seconds) {
        String nsKey = getNSKey(key);
        Object isLock = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            StringRedisSerializer serializer = new StringRedisSerializer();
            boolean success = setNx(connection, serializer.serialize(nsKey), serializer.serialize("0"));
            connection.expire(serializer.serialize(nsKey), seconds);
            return success;
        });
        return isLock == null || (Boolean) isLock;
    }

    /**
     * 设置值，如果值已经存在，则重新设置，直到设置成功
     *
     * @param connection
     * @param nsKey
     * @param value
     * @return
     */
    private boolean setNx(RedisConnection connection, byte[] nsKey, byte[] value) {
        Boolean success = connection.setNX(nsKey, value);
        //值已经被设置，重新设置
        while (success != null && !success) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            success = setNx(connection, nsKey, value);
        }
        return success == null || success;
    }

    /**
     * 释放锁
     *
     * @param key
     * @return
     */
    @Override
    public boolean unLock(String key) {
         return redisTemplate.delete(getNSKey(key));
    }
        /**
         * 释放锁
         * @param key
         * @param lockValue
         */
        public void unLock(String key, long lockValue){

            //LogConstant.workorder.info("Thread：" + Thread.currentThread().getName() + " start unlock");

            Long value = (Long)redisTemplate.opsForValue().get(key);
            if(value != null/* && value.equals(lockValue)*/) {//如果是本线程加锁
                redisTemplate.delete(key);
                //LogConstant.workorder.info("Thread：" + Thread.currentThread().getName() + " unlock success");
            }
        }

    /**
     * @param key
     * @return
     */
    private String getNSKey(String key) {
        return LOCK_NAMESPACE + key;
    }

    public void setRange(){

    }

}
