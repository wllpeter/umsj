package com.tuniu.bi.umsj.base.controller.api;

import com.tuniu.bi.umsj.base.exception.AbstractException;
import com.tuniu.bi.umsj.base.service.RoleService;
import com.tuniu.bi.umsj.base.utils.ResponseUtils;
import com.tuniu.bi.umsj.base.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author zhangwei21
 */
@Api(tags = "角色相关接口")
@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询所有的角色
     *
     * @return
     */
    @ApiOperation(value = "查询所有角色", notes = "查询所有角色接口")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public Response<List<RoleItem>> findAll() {
        List<RoleItem> all = roleService.findAll();
        return ResponseUtils.success(all);
    }

    /**
     * @param pageNum
     * @param pageSize
     * @param sortBy
     * @param order
     * @return
     */
    @ApiOperation(value = "分页查询角色", notes = "分页查询角色接口")
    @RequestMapping(value = "/findMany", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'privilege_all', 'privilege_read')")
    public Response<RoleListResponseVO> findMany(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                                                 @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
                                                 @RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
        RoleListRequestVO roleListRequestVO = new RoleListRequestVO();
        roleListRequestVO.setPageNum(pageNum);
        roleListRequestVO.setPageSize(pageSize);
        roleListRequestVO.setSortBy(sortBy);
        roleListRequestVO.setOrder(order);
        return ResponseUtils.success(roleService.findMany(roleListRequestVO));
    }


    /**
     * @return
     */
    @ApiOperation(value = "创建角色", notes = "创建角色接口")
    @RequestMapping(value = "/createRole", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'privilege_all')")
    public Response createRole(@RequestBody @Valid CreateRoleReqeustVO reqeustVO) throws AbstractException {
        roleService.createRole(reqeustVO);
        return ResponseUtils.success("角色创建成功");
    }

    /**
     * @return
     */
    @ApiOperation(value = "更新角色", notes = " 更新角色接口")
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'privilege_all')")
    public Response<RoleListResponseVO> updateRole(@RequestBody @Valid UpdateRoleRequestVO requestVO) throws AbstractException {
        roleService.updateRole(requestVO);
        return ResponseUtils.success("角色更新成功");
    }

    /**
     * @return
     */
    @ApiOperation(value = "删除角色", notes = " 删除角色接口")
    @RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'privilege_all')")
    public Response<RoleListResponseVO> deleteRole(@RequestBody DeleteRoleRequestVO requestVO) throws AbstractException {
        roleService.deleteRole(requestVO.getId());
        return ResponseUtils.success("角色删除成功");
    }
}
