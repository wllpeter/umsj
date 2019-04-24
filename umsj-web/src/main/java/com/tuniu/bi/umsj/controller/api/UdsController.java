package com.tuniu.bi.umsj.controller.api;

import com.tuniu.bi.umsj.annotation.RequestData;
import com.tuniu.bi.umsj.exception.AbstractException;
import com.tuniu.bi.umsj.exception.CommonException;
import com.tuniu.bi.umsj.service.UdsPublishService;
import com.tuniu.bi.umsj.utils.ResponseUtils;
import com.tuniu.bi.umsj.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author zhangwei21
 */
@RestController
@RequestMapping(value = "/uds")
public class UdsController {

    @Autowired
    private UdsPublishService udsPublishService;

    /**
     * 查询所有
     *
     * @param udsPublishQueryVO
     * @return
     * @throws AbstractException
     */
    @RequestMapping(value = "/publishList", method = RequestMethod.GET)
    public Response<UdsPublishListResponseVO> publishList(@RequestData UdsPublishListRequestVO udsPublishQueryVO) throws AbstractException {
        UdsPublishListResponseVO udsPublishListResponseVO = udsPublishService.findMany(udsPublishQueryVO);
        return ResponseUtils.success(udsPublishListResponseVO);
    }

    /**
     * 创建发布单
     * @param udsPublishVO
     * @return
     * @throws AbstractException
     */
    @RequestMapping(value = "/createPublish", method = RequestMethod.POST)
    public Response createPublish(@RequestData @Valid UdsPublishVO udsPublishVO) throws AbstractException {
        udsPublishService.createPublish(udsPublishVO);
        return ResponseUtils.success();
    }

    /**
     * 更新发布单内容
     * @param udsPublishVO
     * @return
     * @throws AbstractException
     */
    @RequestMapping(value = "/updatePublish", method = RequestMethod.POST)
    public Response updatePublish(@RequestData @Valid UdsPublishVO udsPublishVO) throws AbstractException {
        udsPublishService.updatePublish(udsPublishVO);
        return ResponseUtils.success();
    }

    /**
     * 更新发布单状态
     * @param udsPublishVO
     * @return
     * @throws AbstractException
     */
    public Response updatePublishStatus(@RequestData UdsPublishVO udsPublishVO) throws AbstractException {
        if (udsPublishVO == null || udsPublishVO.getId() == null || udsPublishVO.getStatus() == null) {
            throw new CommonException("参数错误");
        }
        udsPublishService.updatePublishStatus(udsPublishVO);
        return ResponseUtils.success();
    }
}
