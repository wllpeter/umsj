package com.tuniu.bi.umsj.controller.api;

import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.exception.InvalidParamException;
import com.tuniu.bi.umsj.service.MessageService;
import com.tuniu.bi.umsj.service.WebHookService;
import com.tuniu.bi.umsj.utils.ResponseUtils;
import com.tuniu.bi.umsj.vo.AlertManagerRequestVO;
import com.tuniu.bi.umsj.vo.MessageRequestVO;
import com.tuniu.bi.umsj.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;


/**
 * 告警api(集成钉钉，邮箱，微信)
 *
 * @author zhangwei21
 */
@RestController
@RequestMapping(value = "/api/alert")
public class AlertController {

    @Autowired
    private WebHookService webHookService;

    @Autowired
    @Qualifier("sendMessageMap")
    private Map<Integer, MessageService> sendMessageMap;


    /**
     * 发送短信
     *
     * @param messageRequestVO
     * @return
     * @throws AbstractException
     */
    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public Response sendMessage(@RequestBody @Valid MessageRequestVO messageRequestVO) throws AbstractException {
        Integer type = messageRequestVO.getType();
        MessageService messageService = sendMessageMap.get(type);
        if (messageService == null) {
            throw new InvalidParamException("暂不支持的发消息类型");
        }
        messageService.sendMessage(messageRequestVO);

        return ResponseUtils.success();
    }

    /**
     * webHook
     *
     * @param requestVO
     * @return
     * @throws AbstractException
     */
    @RequestMapping(value = "/webHook", method = RequestMethod.POST)
    public Response webHookEmail(@RequestBody AlertManagerRequestVO requestVO) throws AbstractException {
        webHookService.sendMessage(requestVO);
        return ResponseUtils.success();
    }

}
