package com.tuniu.bi.umsj.base.controller.api;

import com.tuniu.bi.umsj.base.dao.entity.UserEntity;
import com.tuniu.bi.umsj.base.exception.AbstractException;
import com.tuniu.bi.umsj.base.exception.CommonException;
import com.tuniu.bi.umsj.base.service.OaClientService;
import com.tuniu.bi.umsj.base.service.UserService;
import com.tuniu.bi.umsj.base.utils.JwtUtils;
import com.tuniu.bi.umsj.base.utils.ResponseUtils;
import com.tuniu.bi.umsj.base.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangwei21
 */
@Api(tags = "用户相关接口")
@RestController
@RequestMapping("/api/user")
public class UserController {

    /**
     * 登录（目前已经在拦截器中拦截,此处放着用于swagger中接口展示）
     *
     * @param user
     * @return Response<Map < String, String>>
     */
    @ApiOperation(value = "用户登录", notes = "用户登录接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response<Map<String, String>> login(@RequestBody @Valid User user) throws AbstractException {
        String username = user.getUsername();
        String password = user.getPassword();
        // ldap 认证
        oaClientService.checkOaAccount(username, password);
        // 自动创建账户信息
        UserEntity userEntity = userService.init(username);
        // 返回token
        String token = JwtUtils.generateToken(username);
        Map<String, String> map = new HashMap<>(16);
        map.put("token", token);
        return ResponseUtils.success(map);
    }

    @Autowired
    private UserService userService;

    @Autowired
    private OaClientService oaClientService;

    @ApiOperation(value = "根据id查询用户信息", notes = "根据唯一标识查询用户信息")
    @ApiImplicitParam(name = "id", dataType = "int", dataTypeClass = Integer.class, value = "用户唯一标识", paramType = "query", required = true)
    @RequestMapping(value = "/findUser", method = RequestMethod.GET)
    public ResponseEntity<UserEntity> findUser(@RequestParam(value = "id", required = true) Integer id) {
        UserEntity byId = userService.findById(id);
        return ResponseEntity.ok(byId);
    }

    /**
     * 分页查询用户信息
     *
     * @param username
     * @param pageNum
     * @param pageSize
     * @return
     * @throws AbstractException
     */
    @ApiOperation(value = "查询用户列表", notes = "查询用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", dataType = "string", dataTypeClass = String.class, value = "用户名", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", dataType = "int", dataTypeClass = Integer.class, value = "页数", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", dataType = "int", dataTypeClass = Integer.class, value = "每页数量", paramType = "query"),
    })
    @RequestMapping(value = "/findMany", method = RequestMethod.GET)
    public Response<UserListResponseVO> findMany(@RequestParam(value = "username", required = false) String username,
                                                 @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                                                 @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
                                                 @RequestParam(value = "order", required = false, defaultValue = "desc") String order) throws AbstractException {
        UserListRequestVO requestVO = new UserListRequestVO();
        requestVO.setUsername(username);
        requestVO.setPageNum(pageNum);
        requestVO.setPageSize(pageSize);
        requestVO.setSortBy(sortBy);
        requestVO.setOrder(order);
        UserListResponseVO response = userService.findMany(requestVO);
        return ResponseUtils.success(response);
    }

    /**
     * 创建用户
     *
     * @return
     * @throws AbstractException
     * @Param userEntity
     */
    @ApiOperation(value = "创建用户", notes = "创建用户")
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public Response createUser(@RequestBody @Valid UserRequestVO requestVO) throws AbstractException {
        userService.init(requestVO.getUsername(), requestVO.getRoleCodes());
        return ResponseUtils.success("用户创建成功");
    }

    /**
     * 修改用户
     *
     * @return
     * @throws AbstractException
     * @Param userEntity
     */
    @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public Response updateUser(@RequestBody @Valid UserUpdateRequestVO requestVO) throws AbstractException {
        userService.updateUser(requestVO);
        return ResponseUtils.success("用户更新成功");
    }

    @ApiOperation(value = "用户信息", notes = "用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Response<UserInfoResponseVO> getInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new CommonException("token参数为空");
        }
        String username = JwtUtils.getUsername(token);
        return ResponseUtils.success(userService.getUserInfo(username));
    }
}
