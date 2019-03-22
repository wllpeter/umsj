package com.tuniu.bi.umsj.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.tuniu.bi.umsj.entitydo.DingTalkRequestDO;
import com.tuniu.bi.umsj.entitydo.DingTalkResponseDO;
import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.exception.CommonException;
import com.tuniu.bi.umsj.vo.DingTalkReqeustVO;
import okhttp3.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author zhangwei21
 */
@Service
public class DingTalkServiceImpl implements DingTalkService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DingTalkServiceImpl.class);

    @Value("${dingtalk.stepId}")
    private Integer stepId;

    @Value("${dingtalk.url}")
    private String dingTalkUrl;

    @Override
    public void sendPersonalMsg(DingTalkReqeustVO requestVO) throws AbstractException {
        OkHttpClient okHttpClient = new OkHttpClient();
        DingTalkRequestDO dingTalkDO = new DingTalkRequestDO();
        BeanUtils.copyProperties(requestVO, dingTalkDO);
        dingTalkDO.setStepId(stepId);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSONObject.toJSONBytes(dingTalkDO));
        Request build = new Request.Builder().url(dingTalkUrl).post(requestBody).build();

        try {
            Response response = okHttpClient.newCall(build).execute();
            if (!response.isSuccessful()) {
                throw new CommonException("发送钉钉消息失败");
            }
            byte[] bytes = response.body().bytes();
            System.out.println(new String(bytes));
            byte[] bytes1 = Base64.decodeBase64(bytes);
            DingTalkResponseDO responseDO = JSONObject.parseObject(bytes1, DingTalkResponseDO.class);
            if (!responseDO.getSuccess()) {
                throw new CommonException("发送钉钉消息，返回失败");
            }
        } catch (IOException e) {
            LOGGER.error("发送钉钉消息错误", e);
            throw new CommonException("发送钉钉消息错误", e);
        }

    }
}
