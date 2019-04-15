package com.tuniu.bi.umsj.aspect;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.tuniu.bi.umsj.annotation.RequestLimit;
import com.tuniu.bi.umsj.exception.RequestLimitException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangwei21
 */
@Aspect
@Component
public class RequestLimitAspect {

    private static final String REQUEST_REDIS_KEY_PREFIX = "req:limit";

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLimitAspect.class);

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
        int limitCount = limit.count();
        long limitTime = limit.time();
        ImmutableList keys = ImmutableList.of(key);

        String luaScript = buildLuaScript();

        RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
        Number count = (Number) redisTemplate.execute(redisScript, keys, limitCount, limitTime/1000);
        if (null != count && count.intValue() > limitCount) {
            throw new RequestLimitException(limitTime/60000 + "分钟内访问次数超过" + limitCount + "次");
        }
    }


    /**
     * 限流脚本
     */
    private String buildLuaScript() {
        return "local c" +
                "\nc = tonumber(redis.call('get',KEYS[1]) or '0')" +
                "\nif c > tonumber(ARGV[1]) then" +
                "\nreturn c;" +
                "\nend" +
                "\nc = redis.call('incr',KEYS[1])" +
                "\nif tonumber(c) == 1 then" +
                "\nredis.call('expire',KEYS[1],ARGV[2])" +
                "\nend" +
                "\nreturn c;";
    }
}
