package com.tuniu.bi.umsj.base.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;

/**
 * @author zhangwei21
 */
@Component
public class SecurityConfigConst {


    @Autowired
    private Environment env;

    public static String key;
    public static Long expire;


    @PostConstruct
    public void readConfig() throws Exception {
        String prefix = "umsj.security.jwt.";
        Field[] fields = SecurityConfigConst.class.getFields();
        for(Field field : fields ){
            field.set(null, getProperty(prefix + field.getName(),field));
        }
    }

    private Object getProperty(String name, Field field) throws UnsupportedEncodingException {
        Class<?> type = field.getType();
        if (type == Long.class) {
            return  Long.valueOf(new String(env.getProperty(name).getBytes("UTF-8"), "UTF-8"));
        }
        return new String(env.getProperty(name).getBytes("UTF-8"), "UTF-8");
    }
}
