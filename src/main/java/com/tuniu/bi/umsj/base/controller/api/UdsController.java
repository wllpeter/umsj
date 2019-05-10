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
import org.springframework.web.bind.annotation.*;

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
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param sortBy
     * @param order
     * @param applyUser
     * @param jiraId
     * @param publishUser
     * @param status
     * @param title
     * @return
     */
    @ApiOperation(value = "发布单列表", notes = "发布单列表接口")
    @RequestMapping(value = "/publishList", method = RequestMethod.GET)
    public Response<UdsPublishListResponseVO> publishList(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                                          @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
                                                          @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                          @RequestParam(name = "order", defaultValue = "DESC") String order,
                                                          @RequestParam(name = "applyUser") String applyUser,
                                                          @RequestParam(name = "jiraId") String jiraId,
                                                          @RequestParam(name = "publishUser") String publishUser,
                                                          @RequestParam(name = "status") Integer status,
                                                          @RequestParam(name = "title") String title) {
        UdsPublishListRequestVO udsPublishListRequestVO = new UdsPublishListRequestVO(pageNum, pageSize, sortBy, order, applyUser, publishUser, status, jiraId, title);
        UdsPublishListResponseVO udsPublishListResponseVO = udsPublishService.findMany(udsPublishListRequestVO);
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
    public Response createPublish(@RequestBody @Valid UdsPublishVO udsPublishVO) {
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
    public Response updatePublish(@RequestBody @Valid UdsPublishVO udsPublishVO, HttpServletRequest request) throws AbstractException {
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
    public Response updatePublishStatus(@RequestBody UdsPublishVO udsPublishVO) throws AbstractException {
        if (udsPublishVO == null || udsPublishVO.getId() == null || udsPublishVO.getStatus() == null) {
            throw new CommonException("参数错误");
        }
        udsPublishService.updatePublishStatus(udsPublishVO);
        return ResponseUtils.success();
    }
}
