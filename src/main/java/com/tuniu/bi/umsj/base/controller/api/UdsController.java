package com.tuniu.bi.umsj.base.controller.api;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.tuniu.bi.umsj.base.annotation.RequestData;
import com.tuniu.bi.umsj.base.exception.AbstractException;
import com.tuniu.bi.umsj.base.exception.CommonException;
import com.tuniu.bi.umsj.base.utils.JwtUtils;
import com.tuniu.bi.umsj.base.utils.ResponseUtils;
import com.tuniu.bi.umsj.base.vo.Response;
import com.tuniu.bi.umsj.base.vo.UdsPublishListRequestVO;
import com.tuniu.bi.umsj.base.vo.UdsPublishListResponseVO;
import com.tuniu.bi.umsj.base.vo.UdsPublishVO;
import com.tuniu.bi.umsj.uds.service.UdsPublishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author zhangwei21
 */
@Api(tags = "uds相关接口")
@RestController
@RequestMapping(value = "/uds")
public class UdsController extends BaseController {

    @Autowired
    private UdsPublishService udsPublishService;


    /**
     * 查询所有
     *
     * @param udsPublishQueryVO
     * @return
     * @throws AbstractException
     */
    @ApiOperation(value = "发布单列表", notes = "发布单列表接口")
    @RequestMapping(value = "/publishList", method = RequestMethod.GET)
    public Response<UdsPublishListResponseVO> publishList(@RequestData UdsPublishListRequestVO udsPublishQueryVO) {
        UdsPublishListResponseVO udsPublishListResponseVO = udsPublishService.findMany(udsPublishQueryVO);
        return ResponseUtils.success(udsPublishListResponseVO);
    }

    /**
     * 创建发布单
     *
     * @param udsPublishVO
     * @return
     * @throws AbstractException
     */
    @ApiOperation(value = "创建发布单", notes = "创建发布单接口")
    @RequestMapping(value = "/createPublish", method = RequestMethod.POST)
    public Response createPublish(@RequestData @Valid UdsPublishVO udsPublishVO) {
        udsPublishService.createPublish(udsPublishVO);
        return ResponseUtils.success();
    }

    /**
     * 更新发布单内容
     *
     * @param udsPublishVO
     * @return
     * @throws AbstractException
     */
    @RequestMapping(value = "/updatePublish", method = RequestMethod.POST)
    public Response updatePublish(@RequestData @Valid UdsPublishVO udsPublishVO, HttpServletRequest request) throws AbstractException {
        udsPublishService.updatePublish(udsPublishVO, getUsernameFromToken(request));
        return ResponseUtils.success();
    }

    /**
     * 更新发布单状态
     *
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
