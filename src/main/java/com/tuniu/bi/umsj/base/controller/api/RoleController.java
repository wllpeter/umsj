package com.tuniu.bi.umsj.base.controller.api;

import com.tuniu.bi.umsj.base.service.RoleService;
import com.tuniu.bi.umsj.base.utils.ResponseUtils;
import com.tuniu.bi.umsj.base.vo.Response;
import com.tuniu.bi.umsj.base.vo.RoleItem;
import com.tuniu.bi.umsj.base.vo.RoleListRequestVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * @return
     */
    @ApiOperation(value = "查询所有角色", notes = "查询所有角色接口")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public Response<List<RoleItem>> findAll() {
        List<RoleItem> all = roleService.findAll();
        return ResponseUtils.success(all);
    }

    /**
     * 分页查询角色
     * @return
     */
    @ApiOperation(value = "分页查询角色", notes = "分页查询角色接口")
    @RequestMapping(value = "/findMany", method = RequestMethod.GET)
    public Response<List<RoleItem>> findMany(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                             @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                                             @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
                                             @RequestParam(value = "order", required = false, defaultValue = "desc") String order){
        RoleListRequestVO roleListRequestVO = new RoleListRequestVO();
        roleListRequestVO.setPageNum(pageNum);
        roleListRequestVO.setPageSize(pageSize);
        roleListRequestVO.setSortBy(sortBy);
        roleListRequestVO.setOrder(order);
        List<RoleItem> many = roleService.findMany(roleListRequestVO);
        return ResponseUtils.success(many);
    }
}
