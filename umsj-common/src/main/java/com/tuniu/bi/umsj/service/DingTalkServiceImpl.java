package com.tuniu.bi.umsj.service;

import com.alibaba.fastjson.JSONObject;
import com.tuniu.bi.umsj.entitydo.DingTalkRequestDO;
import com.tuniu.bi.umsj.entitydo.DingTalkResponseDO;
import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.exception.CommonException;
import com.tuniu.bi.umsj.mapper.UserMapper;
import com.tuniu.bi.umsj.vo.DingTalkRequestVO;
import com.tuniu.bi.umsj.vo.MessageRequestVO;
import okhttp3.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

/**
 * @author zhangwei21
 */
@Service("dingTalk")
public class DingTalkServiceImpl implements MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DingTalkServiceImpl.class);

    @Value("${dingtalk.step-id}")
    private Integer stepId;

    @Value("${dingtalk.url}")
    private String dingTalkUrl;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    /**
     * 私有化发钉钉消息的代码
     * @param requestVO
     * @throws AbstractException
     */
    private void sendPersonalMsg(DingTalkRequestVO requestVO) throws AbstractException {
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

    @Override
    public void sendMessage(MessageRequestVO messageRequestVO) throws AbstractException {
        // 查询user表,查询salerId，phone
        List<Integer> salerIds = userService.obtainReceiver(messageRequestVO.getType(), messageRequestVO.getNames());
        if (CollectionUtils.isEmpty(salerIds)) {
            throw new CommonException("用户的saleId为空");
        }
        DingTalkRequestVO dingTalkRequestVO = new DingTalkRequestVO();
        dingTalkRequestVO.setMsgType(1);
        dingTalkRequestVO.setContent(messageRequestVO.getContent());
        dingTalkRequestVO.setTitle(messageRequestVO.getTitle());
        dingTalkRequestVO.setUids(salerIds);
        sendPersonalMsg(dingTalkRequestVO);
    }
}
