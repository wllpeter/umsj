package com.tuniu.bi.umsj.aspect;

import com.google.common.base.Joiner;
import com.tuniu.bi.umsj.annotation.RequestLimit;
import com.tuniu.bi.umsj.exception.CommonException;
import com.tuniu.bi.umsj.exception.RequestLimitException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangwei21
 */
@Aspect
@Component
public class RequestLimitAspect {

    private static final String REQUEST_REDIS_KEY_PREFIX = "req:limit";

    private static final String REQUEST_REDIS_KEY_LOCK = "req:limit:lock";

    private static final String REQUEST_REDIS_KEY_LOCK_VALUE = "req:limit:lock:value";

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLimitAspect.class);

    private static final long EXPIRE_LOCK = 10;

    @Autowired
    private RedisTemplate redisTemplate;

    @Before("@annotation(limit)")
    public void requestLimit(final JoinPoint joinPoint, RequestLimit limit) throws RequestLimitException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        if (request == null) {
            throw new RequestLimitException("方法中缺失HttpServletRequest参数");
        }
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        Joiner on = Joiner.on(":");
        String key = on.join(REQUEST_REDIS_KEY_PREFIX, url, ip);
        // 获取锁
        String keyLock = on.join(REQUEST_REDIS_KEY_LOCK, key);
        ListOperations<String, Long> stringLongListOperations = redisTemplate.opsForList();
        Long now = System.currentTimeMillis();
        Long size = stringLongListOperations.size(key);
        int count = limit.count();
        long time = limit.time();
        if (size != null && size > 0) {
            for (long i = 0; i < count; i++) {
                Long value = stringLongListOperations.index(key, 0);
                if (value == null) {
                    break;
                }
                if (now - value <= time) {
                    break;
                }
                stringLongListOperations.leftPop(key);
            }
        }
        try {
            if (redisTemplate.opsForValue().setIfAbsent(keyLock, REQUEST_REDIS_KEY_LOCK_VALUE, EXPIRE_LOCK, TimeUnit.SECONDS)) {
                Long newSize = stringLongListOperations.size(key);
                if (newSize >= count) {
                    throw new RequestLimitException(time/60000 + "分钟内访问次数超过" + count + "次");
                }
                stringLongListOperations.rightPush(key,now);
                if (newSize == null || newSize == 0) {
                    // key设置超时时间，避免redis空间占用太多,
                    redisTemplate.expire(key, time * 60, TimeUnit.MILLISECONDS);
                }
            } else {
                throw new CommonException("获取锁失败");
            }
        } finally {
            redisTemplate.delete(keyLock);
        }
    }
}
