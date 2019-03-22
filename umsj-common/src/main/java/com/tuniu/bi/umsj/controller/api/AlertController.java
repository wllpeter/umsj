package com.tuniu.bi.umsj.controller.api;

import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.service.DingTalkService;
import com.tuniu.bi.umsj.utils.ResponseUtils;
import com.tuniu.bi.umsj.vo.DingTalkReqeustVO;
import com.tuniu.bi.umsj.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 告警api(集成钉钉，邮箱，微信)
 *
 * @author zhangwei21
 */
@RestController
@RequestMapping(value = "/api/alert")
public class AlertController {

    @Autowired
    private DingTalkService dingTalkService;

    /**
     * 发送钉钉消息
     *
     * @param requestVO
     * @return
     * @throws AbstractException
     */
    @RequestMapping(value = "/sendDingTalk", method = RequestMethod.POST)
    public Response sendDingDing(@RequestBody DingTalkReqeustVO requestVO) throws AbstractException {
        dingTalkService.sendPersonalMsg(requestVO);
        return ResponseUtils.success();
    }


}
