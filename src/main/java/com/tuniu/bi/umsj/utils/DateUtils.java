package com.tuniu.bi.umsj.utils;

import com.tuniu.bi.umsj.constant.Symbol;
import com.tuniu.bi.umsj.exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    /**
     * 时 时间格式
     */
    private static final String HOUR_STYLE = "HH";

    /**
     * 年-月 时间格式
     */
    public static final String DATE_YM_STYLE = "yyyy-MM";

    /**
     * 年-月-日 时间格式
     */
    public static final String DATE_YMD_STYLE = "yyyy-MM-dd";

    /**
     * 年月日 时间格式
     */
    public static final String DATE_YYMMDD_STYLE = "yyyyMMdd";

    /**
     * 年-月-日 时-分-秒 时间格式
     */
    public static final String DATE_STYLE = "yyyy-MM-dd HH:mm:ss";

    /**
     * 数据库中读出的时间格式
     */
    public static final String DB_DATE_STYLE = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * 年-月-日 时-分-秒-毫秒 时间格式
     */
    private static final String MILLI_STYLE = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 零点
     */
    private static final String CLOCK_ZERO = "00:00:00";

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    public static String getNowDateTime() {
        return getNowDateTime(DATE_STYLE);
    }

    public static String getNowDateTime(String style) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern(style);
        LocalDateTime date = LocalDateTime.now();
        return date.format(f);
    }

    /**
     * 如"yyyy-MM-dd HH:mm:ss.S"  转 "yyyy-MM-dd HH:mm:ss"
     * Date 2017/4/6 10:08
     *
     * @param orgDate      源时间
     * @param orgFormat    源格式
     * @param targetFormat 源格式
     * @author liushuiwen
     */
    public static String formatDateTime(String orgDate, String orgFormat, String targetFormat) {
        LocalDateTime localDateTime = LocalDateTime.parse(orgDate, DateTimeFormatter.ofPattern(orgFormat));
        DateTimeFormatter f = DateTimeFormatter.ofPattern(targetFormat);
        return localDateTime.format(f);
    }

    /**
     * 将数据库格式的时间转换为正常的
     */
    public static String formatDbDateTime2Normal(String dbDateTime) {
        return DateUtils.formatDateTime(dbDateTime, DateUtils.DB_DATE_STYLE, DateUtils.DATE_STYLE);
    }

    /**
     * 如"yyyy-MM-dd"  转 "yyyy-MM"
     *
     * @param orgDate      源日期
     * @param orgFormat    源格式
     * @param targetFormat 源格式
     * @author bainingning
     */
    public static String formatDate(String orgDate, String orgFormat, String targetFormat) {
        LocalDate localDate = LocalDate.parse(orgDate, DateTimeFormatter.ofPattern(orgFormat));
        DateTimeFormatter f = DateTimeFormatter.ofPattern(targetFormat);
        return localDate.format(f);
    }


    public static Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String toMilliDateStr(Date date) {
        SimpleDateFormat milliStyle = new SimpleDateFormat(MILLI_STYLE);
        if (date == null) {
            return Symbol.EMPTY;
        }
        return milliStyle.format(date);
    }

    /**
     * 获取指定时间的前后几个小时
     * eg in:2017-04-03 14:20:11,5 out:2017-04-03 19:20:11
     */
    public static String getDelayDay(String dateStr, int hour) throws CommonException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateStyle = new SimpleDateFormat(DATE_STYLE);
        SimpleDateFormat dayStyle = new SimpleDateFormat(DATE_YMD_STYLE);
        try {
            calendar.setTime(dateStyle.parse(dateStr));
        } catch (ParseException e) {
            LOGGER.error("getDelayDay 方法报错 dateStr:{} ,hour:{}", dateStr, hour);
            throw new CommonException("时间格式不正确！");
        }
        calendar.add(Calendar.DAY_OF_MONTH, hour);
        return dayStyle.format(calendar.getTime());
    }

    /**
     * 获取指定时间的前后几个小时
     * eg in:2017-04-03 14:20:11,5 out:2017-04-03 19:20:11
     */
    public static String getDelayTime(String dateStr, int hour) throws CommonException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateStyle = new SimpleDateFormat(DATE_STYLE);
        try {
            calendar.setTime(dateStyle.parse(dateStr));
        } catch (ParseException e) {
            LOGGER.error("getDelayTime 方法报错 dateStr:{} ,hour:{}", dateStr, hour);
            throw new CommonException("时间格式不正确！");
        }
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return dateStyle.format(calendar.getTime());
    }

    /**
     * 获取今天的前后几天
     * eg in:2017-04-03,-1 out:2017-04-02
     */
    public static String getDelayDateOfNow(int day) throws CommonException {
        Calendar calendar = Calendar.getInstance();
        String nowDate = getNowDateTime(DATE_YYMMDD_STYLE);
        SimpleDateFormat dateStyle = new SimpleDateFormat(DATE_YYMMDD_STYLE);
        try {
            calendar.setTime(dateStyle.parse(nowDate));
        } catch (ParseException e) {
            LOGGER.error("getAheadDateOfNow 方法报错 nowDate:{} ,day:{}", nowDate, day);
            throw new CommonException("时间格式不正确！");
        }
        calendar.add(Calendar.DATE, day);
        return dateStyle.format(calendar.getTime());
    }

    /**
     * 获取今天的前后几天
     * eg in:2017-04-03,-1 out:2017-04-02
     */
    public static String getDelayDateOfNowYYMMDD(int day) throws CommonException {
        Calendar calendar = Calendar.getInstance();
        String nowDate = getNowDateTime(DATE_YMD_STYLE);
        SimpleDateFormat dateStyle = new SimpleDateFormat(DATE_YMD_STYLE);
        try {
            calendar.setTime(dateStyle.parse(nowDate));
        } catch (ParseException e) {
            LOGGER.error("getAheadDateOfNow 方法报错 nowDate:{} ,day:{}", nowDate, day);
            throw new CommonException("时间格式不正确！");
        }
        calendar.add(Calendar.DATE, day);
        return dateStyle.format(calendar.getTime());
    }

    public static boolean compareTimeStr(String dateStr1, String dateStr2) throws CommonException {
        SimpleDateFormat dayStyle = new SimpleDateFormat(DATE_YMD_STYLE);
        if (StrUtils.isStrEmpty(dateStr1) || StrUtils.isStrEmpty(dateStr2)) {
            throw new CommonException("比较的时间字符串不能为空！");
        }
        String formatStr1;
        String formatStr2;
        try {
            formatStr1 = dayStyle.format(dayStyle.parse(dateStr1));
            formatStr2 = dayStyle.format(dayStyle.parse(dateStr2));
        } catch (Exception ex) {
            LOGGER.error("compareTimeStr 方法报错 dateStr1:{} dateStr2:{}", dateStr1, dateStr2);
            throw new CommonException("时间格式不正确！");
        }
        return formatStr1.equals(formatStr2);
    }

    public static String getTomorrowLastTime(String dateStr1) throws CommonException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dayStyle = new SimpleDateFormat(DATE_YMD_STYLE);
        try {
            calendar.setTime(dayStyle.parse(dateStr1));
        } catch (Exception ex) {
            LOGGER.error("getTomorrowLastTime 方法报错 dateStr1:", dateStr1);
            throw new CommonException("时间格式不正确！");
        }
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        return dayStyle.format(calendar.getTime()) + " 23:59:59";
    }

    public static String getTodayDate() {
        return getNowDateTime(DateUtils.DATE_YMD_STYLE);
    }

    /**
     * 获取今天开始时间点
     */
    public static String getTodayStart() {
        return getTodayDate() + " " + CLOCK_ZERO;
    }

    /**
     * 获取传入时间的日期
     */
    public static int getHourStr(String timeStr) throws CommonException {
        if (StrUtils.isStrEmpty(timeStr)) {
            return 0;
        }
        int date;
        SimpleDateFormat dateStyle = new SimpleDateFormat(DATE_STYLE);
        SimpleDateFormat hourStyle = new SimpleDateFormat(HOUR_STYLE);
        try {
            date = Integer.valueOf(hourStyle.format(dateStyle.parse(timeStr)));
        } catch (Exception ex) {
            LOGGER.error("getHourStr 方法报错 args:", timeStr);
            throw new CommonException("时间格式不正确！");
        }
        return date;
    }

    /**
     * 获取当前的时间戳(单位为秒)
     */
    public static int getCurrentSecondTimestamp() {
        Date date = new Date();
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0, length - 3));
        } else {
            return 0;
        }
    }

    /**
     * 获取当前的时间戳(单位为毫秒)
     */
    public static long getCurrentTimestamp() {
        Date date = new Date();
        return date.getTime();
    }

    /**
     * 比较日期大小
     * <p>
     * 2018年11月2日 16:15:53
     *
     * @param d1 第一个时间
     * @param d2 第二个时间
     * @return Integer null日期格式有误，1：第一个日期大，0：两个日期一样，-1：第二个日期大
     */
    public static int compareDate(String d1, String d2) throws ParseException {
        return baseCompare(d1, d2, DateUtils.DATE_YMD_STYLE);
    }

    /**
     * 比较时间大小(是否 t1 > t2)
     */
    public static int compareDateTime(String dt1, String dt2) throws ParseException {
        return baseCompare(dt1, dt2, DateUtils.DATE_STYLE);
    }

    private static int baseCompare(String t1, String t2, String style) throws ParseException {
        DateFormat df = new SimpleDateFormat(style);
        Date dt1 = df.parse(t1);
        Date dt2 = df.parse(t2);
        if (dt1.getTime() > dt2.getTime()) {
            return 1;
        } else if (dt1.getTime() < dt2.getTime()) {
            return -1;
        } else {
            return 0;
        }
    }
}
