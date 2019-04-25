package com.tuniu.bi.umsj.base.utils;

import com.tuniu.bi.umsj.base.constant.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(StrUtils.class);
    public static final String SEPARATOR = ":";

    private static final String MD5 = "MD5";
    private static final String X02 = "%02x";

    /**
     * 获取字符串的 MD5 值
     */
    public static String getMd5(String str) {
        if (StringUtils.isEmpty(str)) {
            str = "";
        }
        try {
            byte[] bs = MessageDigest.getInstance(MD5).digest(str.getBytes());
            StringBuilder sb = new StringBuilder(40);
            for (byte x : bs) {
                sb.append(String.format(X02, x));
            }
            return sb.toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return "";
    }

    /**
     * 获取未来几天的日期
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(today);
    }

    /**
     * 是否包含这个前缀
     */
    public static boolean hasPrefix(String str, String prefix) {
        return (str != null) && (str.indexOf(prefix) == 0);
    }

    /**
     * 从位置的 key 里解析出关键词
     * 例如: app_search_日本_2500
     */
    public static String getKeywordFromPositionKey(String positionKey) {
        String[] parts = positionKey.split("_");
        if (parts.length >= 2) {
            return parts[2];
        } else {
            return null;
        }
    }

    /**
     * 将逗号分隔的数字字符串数组转成整型List
     */
    public static List<Integer> parseStringToIntList(String string) {
        List<String> stringList = Arrays.asList(string.split(","));
        List<Integer> integerList = new ArrayList<>();
        if (CollectionUtils.isEmpty(stringList)) {
            return integerList;
        }
        for (String str : stringList) {
            try {
                integerList.add(Integer.parseInt(str));
            } catch (NumberFormatException e) {
                LOGGER.error(e.getMessage());
            }
        }
        return integerList;
    }

    /**
     * 数字字符串List转整型List
     */
    public static List<Integer> strListToIntList(List<String> stringList) {
        List<Integer> intList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(stringList)) {
            for (String str : stringList) {
                try {
                    if (!str.equals(Symbol.EMPTY)) {
                        intList.add(Integer.parseInt(str));
                    }
                } catch (NumberFormatException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }

        return intList;
    }

    /**
     * 数组合成 String，英文逗号分隔
     */
    public static String implode(List<Integer> intList) {
        if (CollectionUtils.isEmpty(intList)) {
            return Symbol.EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        for (Integer item : intList) {
            sb.append(item).append(",");
        }
        String str = sb.toString();
        return str.substring(0, str.length() - 1);
    }

    public static String getMapString(Map map, String key) {
        if (map == null) {
            return null;
        } else {
            return map.get(key) == null ? null : map.get(key).toString();
        }
    }


    public static boolean isStrEmpty(String str) {

        return str == null || str.equals("") || "0".equals(str);
    }

    public static String generateKey(String... keys) {
        StringBuilder sb = new StringBuilder("");
        for (String key : keys) {
            sb.append(key).append(SEPARATOR);
        }
        return sb.substring(0, sb.length() - 1);
    }

    public static boolean isOneEmpty(Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                return true;
            }
        }
        return false;
    }

    public static String toStringFromSet(Set<Integer> strSet) {
        if (strSet == null) {
            return null;
        }
        String str = strSet.toString();
        return str.substring(1, str.length() - 1);
    }


    /**
     * 获取缓存的key
     * key 定义在注解上，支持表达式
     * 注： method的参数支持Javabean和Map
     * method的基本类型要定义为对象，否则没法读取到名称
     * <p>
     * example1:
     * Phone phone = new Phone();
     * "#{phone.cpu}"  为对象的取值
     * example2:
     * Map apple = new HashMap(); apple.put("name","good apple");
     * "#{apple[name]}"  为map的取值
     * example3:
     * "#{phone.cpu}_#{apple[name]}"
     */
    public static String parseKey(Method method, String key, Object[] args) {
        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);
        //使用进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        ParserContext parserContext = new TemplateParserContext();
        // 把 #{ 替换成 #{# ,以适配SpEl模板的格式
        Object returnVal =
                parser.parseExpression(key.replace("#{", "#{#"), parserContext).getValue(context, Object.class);
        return returnVal == null ? null : returnVal.toString();
    }

    /**
     * 判断字符串中是否包含中文
     *
     * @param str 待校验字符串
     * @return 是否为中文
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        return m.find();
    }


    /**
     * toString
     */
    public static String toString(Object obj) {
        if (obj != null) {
            return obj.toString();
        } else {
            return Symbol.EMPTY;
        }
    }

    public static String[] splitInput(String strInfo, String strSplit) {
        //第1步计算数组大小(6行程序)
        int size = 1;
        for (int k = 0; k < strInfo.length(); k++) {
            if (strInfo.substring(k, k + 1).equals(",")) {
                size++;
            }
        }
        String[] arrRtn = new String[size]; //返回数组
        String strTemp = "";                   //临时变量
        //第2步给数组赋值(25行程序)
        int len = strInfo.length();
        int index = 0;
        int i = 0;
        while (len > 1) {
            index = strInfo.indexOf(strSplit);
            if (index > 0) {
                strTemp = strInfo.substring(0, index);
                strInfo = strInfo.substring(index + 1);
                arrRtn[i++] = strTemp;
            } else if (index == 0) {
                strInfo = strInfo.substring(index + 1);
                arrRtn[i++] = "";
            } else {
                break;//极端情况，如"4556658"
            }
            len = strInfo.length();
        }
        index = strInfo.indexOf(strSplit);
        if (index == 0) {
            arrRtn[i++] = "";
            arrRtn[i] = "";
        } else {
            arrRtn[i] = strInfo;
        }
        return arrRtn;
    }
}
