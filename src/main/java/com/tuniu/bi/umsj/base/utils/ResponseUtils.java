package com.tuniu.bi.umsj.base.utils;

import com.tuniu.bi.umsj.base.enums.ErrorCodeEnum;
import com.tuniu.bi.umsj.base.vo.Response;

/**
 * 返回工具类
 *
 * @author zhangwei21
 */
public class ResponseUtils {

    /**
     * 请求成功
     *
     * @return
     */
    public static <T> Response<T> success() {
        return new Response(true, ErrorCodeEnum.OK.getCode(), ErrorCodeEnum.OK.getMsg());
    }

    /**
     * 成功请求
     *
     * @param data
     * @return
     */
    public static <T> Response<T> success(T data) {
        return new Response(true, ErrorCodeEnum.OK.getCode(), ErrorCodeEnum.OK.getMsg(), data);
    }


    /**
     * 自定义返回
     *
     * @param success
     * @param e
     * @return
     */
    public static <T> Response<T> custom(boolean success, ErrorCodeEnum e) {
        return new Response(success, e.getCode(), e.getMsg());
    }

    /**
     * 自定义返回
     *
     * @param success
     * @param error
     * @return
     */
    public static <T> Response<T> custom(boolean success, int code, String error) {
        return new Response(success, code, error);
    }

    /**
     * 自定义返回
     *
     * @param success
     * @param error
     * @param data
     * @return
     */
    public static <T> Response<T> custom(boolean success, int code, String error, T data) {
        return new Response(success, code, error, data);
    }
}
