package com.tuniu.bi.umsj.service;

import com.alibaba.fastjson.JSONObject;
import com.tuniu.bi.umsj.entitydo.SmsRequestDO;
import com.tuniu.bi.umsj.entitydo.SmsResponseDO;
import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.exception.CommonException;
import com.tuniu.bi.umsj.vo.SmsRequestVO;
import okhttp3.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangwei21
 */
@Service
public class SmsServiceImpl implements  SmsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Value("${sms.template-id}")
    private Integer tmplateId;

    @Value("${sms.url}")
    private String url;

    @Value("${sms.system-id}")
    private Integer systemId;

    @Value("${sms.client-ip}")
    private String clientIp;

    @Override
    public void sendSms(SmsRequestVO smsRequestVO) throws AbstractException {
        SmsRequestDO smsRequestDO = new SmsRequestDO();
        smsRequestDO.setTemplateId(tmplateId);
        smsRequestDO.setSystemId(systemId);
        smsRequestDO.setMobileNum(smsRequestVO.getMobileNum());
        smsRequestDO.setClientIp(clientIp);
        SmsRequestDO.SmsTemplateParam param = new SmsRequestDO.SmsTemplateParam();
        param.setParamKey("content");
        param.setParamValue(smsRequestVO.getContent());
        smsRequestDO.setSmsTemplateParams(Arrays.asList(param));
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSONObject.toJSONBytes(smsRequestDO));
        Request build = new Request.Builder().url(url).post(requestBody).build();

        try {
            Response response = okHttpClient.newCall(build).execute();
            if (!response.isSuccessful()) {
                throw new CommonException("发送SMS消息失败");
            }
            byte[] bytes = response.body().bytes();
            byte[] bytes1 = Base64.decodeBase64(bytes);
            SmsResponseDO responseDO = JSONObject.parseObject(bytes1, SmsResponseDO.class);
            if (responseDO == null || !responseDO.getSuccess()) {
                throw new CommonException("发送SMS消息失败");
            }
        } catch (IOException e) {
            LOGGER.error("发送SMS消息错误", e);
            throw new CommonException("发送SMS消息错误", e);
        }
    }
}
