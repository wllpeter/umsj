package com.tuniu.bi.umsj.base.aspect;

/**
 * @author zhangwei21
 */
//@Aspect
//@Component
public class RequestLimitAspect {

//    private static final String REQUEST_REDIS_KEY_PREFIX = "req:limit";
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLimitAspect.class);
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @Before("@annotation(limit)")
//    public void requestLimit(final JoinPoint joinPoint, RequestLimit limit) throws RequestLimitException {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        if (request == null) {
//            throw new RequestLimitException("方法中缺失HttpServletRequest参数");
//        }
//        String url = request.getRequestURL().toString();
//        String ip = request.getRemoteAddr();
//        Joiner on = Joiner.on(":");
//        String key = on.join(REQUEST_REDIS_KEY_PREFIX, url, ip);
//        // 获取锁
//        int limitCount = limit.count();
//        long limitTime = limit.time();
//        ImmutableList keys = ImmutableList.of(key);
//
//        String luaScript = buildLuaScript();
//
//        RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
//        Number count = (Number) redisTemplate.execute(redisScript, keys, limitCount, limitTime/1000);
//        if (null != count && count.intValue() > limitCount) {
//            throw new RequestLimitException(limitTime/60000 + "分钟内访问次数超过" + limitCount + "次");
//        }
//    }
//
//
//    /**
//     * 限流脚本
//     */
//    private String buildLuaScript() {
//        return "local c" +
//                "\nc = tonumber(redis.call('get',KEYS[1]) or '0')" +
//                "\nif c > tonumber(ARGV[1]) then" +
//                "\nreturn c;" +
//                "\nend" +
//                "\nc = redis.call('incr',KEYS[1])" +
//                "\nif tonumber(c) == 1 then" +
//                "\nredis.call('expire',KEYS[1],ARGV[2])" +
//                "\nend" +
//                "\nreturn c;";
//    }
}
