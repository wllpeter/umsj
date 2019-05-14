package com.tuniu.bi.umsj.uds.controller.api;

import com.tuniu.bi.umsj.base.controller.api.BaseController;
import com.tuniu.bi.umsj.base.exception.AbstractException;
import com.tuniu.bi.umsj.base.utils.ResponseUtils;
import com.tuniu.bi.umsj.base.vo.*;
import com.tuniu.bi.umsj.uds.service.UdsPublishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
                                                          @RequestParam(name = "applyUser", required = false) String applyUser,
                                                          @RequestParam(name = "jiraId", required = false) String jiraId,
                                                          @RequestParam(name = "publishUser", required = false) String publishUser,
                                                          @RequestParam(name = "status", required = false) Integer status,
                                                          @RequestParam(name = "title", required = false) String title) {
        UdsPublishListRequestVO udsPublishListRequestVO = new UdsPublishListRequestVO(pageNum, pageSize, sortBy, order, applyUser, publishUser, status, jiraId, title);
        UdsPublishListResponseVO udsPublishListResponseVO = udsPublishService.findMany(udsPublishListRequestVO);
        return ResponseUtils.success(udsPublishListResponseVO);
    }

    /**
     * 创建发布单
     *
     * @param requestVO
     * @param request
     * @return
     * @throws AbstractException
     */
    @ApiOperation(value = "创建发布单", notes = "创建发布单接口")
    @RequestMapping(value = "/createPublish", method = RequestMethod.POST)
    public Response createPublish(@RequestBody @Valid CreateUdsRequestVO requestVO, HttpServletRequest request) throws AbstractException {
        udsPublishService.createPublish(requestVO, getUsernameFromToken(request));
        return ResponseUtils.success();
    }

    /**
     * 更新发布单内容
     *
     * @param updateUdsRequestVO
     * @param request
     * @return
     * @throws AbstractException
     */
    @RequestMapping(value = "/updatePublish", method = RequestMethod.POST)
    public Response updatePublish(@RequestBody @Valid UpdateUdsRequestVO updateUdsRequestVO, HttpServletRequest request) throws AbstractException {
        udsPublishService.updatePublish(updateUdsRequestVO, getUsernameFromToken(request));
        return ResponseUtils.success();
    }

    /**
     * 更新发布单状态
     *
     * @param updateUdsStatusRequstVO
     * @return
     * @throws AbstractException
     */
    @RequestMapping(value = "/updatePublishStatus", method = RequestMethod.POST)
    public Response updatePublishStatus(@RequestBody @Valid UpdateUdsStatusRequestVO updateUdsStatusRequstVO, HttpServletRequest request) throws AbstractException {
        udsPublishService.updatePublishStatus(updateUdsStatusRequstVO, getUsernameFromToken(request));
        return ResponseUtils.success();
    }

    /**
     * 查看发布单详情接口
     *
     * @param id
     * @return
     * @throws AbstractException
     */
    @RequestMapping(value = "/publishDetail", method = RequestMethod.GET)
    public UdsPublishVO publishDetail(@RequestParam Integer id) throws AbstractException {
        return udsPublishService.publishDetail(id);
    }
}
