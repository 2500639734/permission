package com.permission.util;

import com.permission.enumeration.ResultEnum;
import com.permission.exception.BusinessException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @auther: shenke
 * @date: 2020/2/22 3:48
 * @description: 基于Spring Redis Template的Redis操作工具类
 */
public class RedisUtils {

    private RedisUtils () {

    }

    /**
     * Spring Boot封装的Spring Redis Template
     */
    private static RedisTemplate<String, Object> redisTemplate = SpringContextUtils.getBean("redisTemplate", RedisTemplate.class);

    /**
     * 设置key过期时间
     * @param key Redis键
     * @param timeout 超时时间，单位毫秒
     * @return true：设置成功，false：设置失败
     */
    public static boolean expire(String key, long timeout) {
        return expire(key, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 设置key过期时间
     * @param key Redis键
     * @param timeout 超时时间
     * @param timeUnit 超时单位：天、小时、分、秒、毫秒等
     * @return true：设置成功，false：设置失败
     */
    public static boolean expire(String key, long timeout, TimeUnit timeUnit) {
        try {
            return redisTemplate.expire(key, timeout, timeUnit);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REDIS_OPERATION_FAIL, e);
        }
    }

    /**
     * 获取过期时间，单位毫秒
     * @param key Redis键
     * @return 时间值
     */
    public static long getTime(String key) {
        try {
            return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REDIS_OPERATION_FAIL, e);
        }
    }

    /**
     * 对一个 key-value 的值进行自增操作
     * @param key Redis键
     * @param number 数值
     * @return 自增后的值
     */
    public long increment(String key, long number) {
        try {
            return redisTemplate.opsForValue().increment(key, number);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REDIS_OPERATION_FAIL, e);
        }
    }

    /**
     * 是否存在key
     * @param key Redis键
     * @return true：存在，false：不存在
     */
    public static boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REDIS_OPERATION_FAIL, e);
        }
    }

    /**
     * 删除key的过期时间
     * @param key Redis键
     * @return true：删除成功，false：删除失败
     */
    public boolean persist(String key) {
        try {
            return redisTemplate.boundValueOps(key).persist();
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REDIS_OPERATION_FAIL, e);
        }
    }

    /**
     * 删除单个key
     * @param key Redis键
     * @return true：删除成功，false：删除失败
     */
    public static boolean del(String key) {
        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REDIS_OPERATION_FAIL, e);
        }
    }

    /**
     * 删除多个key
     * @param keys Redis键集合
     * @return true：删除成功，false：删除失败
     */
    public static boolean del(Collection<String> keys) {
        try {
            return redisTemplate.delete(keys) > 0;
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REDIS_OPERATION_FAIL, e);
        }
    }

    /**
     * 保存数据到String
     * @param key Redis键
     * @param value Redis键对应的值
     * @param timeout 超时时间，单位毫秒
     */
    public static void set(String key, Object value, long timeout) {
        set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 保存数据到String
     * @param key Redis键
     * @param value Redis键对应的值
     * @param timeout 超时时间，单位秒
     * @param timeUnit 超时单位：天、小时、分、秒、毫秒等
     */
    public static boolean set(String key, Object value, long timeout, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
            return true;
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REDIS_OPERATION_FAIL, e);
        }
    }

    /**
     * 获取String中的数据
     * @param key Redis键
     * @return
     */
    public static Object get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REDIS_OPERATION_FAIL, e);
        }
    }

    /**
     * 获取String中的数据
     * @param key Redis键
     * @return
     */
    public static String getString(String key) {
        try {
            return redisTemplate.opsForValue().get(key).toString();
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REDIS_OPERATION_FAIL, e);
        }
    }

    /**
     * 保存数据到Hash
     * @param key Redis键
     * @param hKey Hash键
     * @param value Hash值
     */
    public static boolean hPut(String key, String hKey, Object value) {
        try {
            redisTemplate.opsForHash().put(key, hKey, value);
            return true;
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REDIS_OPERATION_FAIL, e);
        }
    }

    /**
     * 批量保存数据到Hash
     * @param key Redis键
     * @param values Hash键值对
     */
    public static boolean hPutAll(String key, Map<String, Object> values) {
        try {
            redisTemplate.opsForHash().putAll(key, values);
            return true;
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REDIS_OPERATION_FAIL, e);
        }
    }

    /**
     * 获取Hash中的数据
     * @param key Redis键
     * @param hKey Hash键
     * @return
     */
    public static Object hGet(String key, String hKey) {
        try {
            return redisTemplate.opsForHash().get(key, hKey);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REDIS_OPERATION_FAIL, e);
        }
    }

    /**
     * 获取Hash中的多个数据
     * @param key Redis键
     * @param hKeys Hash键集合
     * @return
     */
    public static List<Object> hMultiGet(String key, Collection<Object> hKeys) {
        try {
            return redisTemplate.opsForHash().multiGet(key, hKeys);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REDIS_OPERATION_FAIL, e);
        }
    }

    /**
     * 保存数据到Set
     * @param key Redis键
     * @param values 数据集合
     * @return true：保存成功，false：保存失败
     */
    public static boolean sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values) > 0;
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REDIS_OPERATION_FAIL, e);
        }
    }

    /**
     * 删除Set中的数据
     * @param key Redis键
     * @param values 数据集合
     * @return true：删除成功，false：删除失败
     */
    public static boolean sDel(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values) > 0;
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REDIS_OPERATION_FAIL, e);
        }
    }

    /**
     * 保存数据到List
     * @param key Redis键
     * @param value 数据
     * @return true：保存成功，false：保存失败
     */
    public static boolean lPush(String key, Object value) {
        try {
            return redisTemplate.opsForList().rightPush(key, value) > 0;
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REDIS_OPERATION_FAIL, e);
        }
    }

    /**
     * 保存数据集合到List
     * @param key Redis键
     * @param values 数据集合
     * @return true：保存成功，false：保存失败
     */
    public static boolean lPushAll(String key, Collection<Object> values) {
        try {
            return redisTemplate.opsForList().rightPushAll(key, values) > 0;
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REDIS_OPERATION_FAIL, e);
        }
    }

    /**
     * 保存数据集合到List
     * @param key Redis键
     * @param values 数据集合
     * @return true：保存成功，false：保存失败
     */
    public static boolean lPushAll(String key, Object... values) {
        try {
            return redisTemplate.opsForList().rightPushAll(key, values) > 0;
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REDIS_OPERATION_FAIL, e);
        }
    }

    /**
     * 从List中获取begin到end之间的元素
     * @param key Redis键
     * @param start 开始位置
     * @param end 结束位置（start=0，end=-1表示获取全部元素）
     * @return List集合
     */
    public static List<Object> lGet(String key, int start, int end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REDIS_OPERATION_FAIL, e);
        }
    }

}
