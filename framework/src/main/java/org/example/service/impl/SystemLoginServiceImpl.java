package org.example.service.impl;

import org.example.constants.SystemConstants;
import org.example.domain.ResponseResult;
import org.example.domain.entity.LoginUser;
import org.example.domain.vo.MenuVo;
import org.example.domain.entity.User;
import org.example.domain.vo.AdminUserInfoVo;
import org.example.domain.vo.RoutersVo;
import org.example.domain.vo.UserInfoVo;
import org.example.service.LoginService;
import org.example.service.MenuService;
import org.example.service.RoleService;
import org.example.utils.BeanCopyUtils;
import org.example.utils.JwtUtil;
import org.example.utils.RedisCache;
import org.example.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class SystemLoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    /**
     * 登录
     * @param user 用户信息
     * @return 登录结果
     */
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        // 获取userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        // 把用户信息存入redis
        redisCache.setCacheObject(SystemConstants.ADMIN_LOGIN_PREFIX + userId, loginUser);
        // 把token封装返回
        Map<String, String> map = new HashMap<>();
        map.put(SystemConstants.TOKEN_HEADER, jwt);
        return ResponseResult.okResult(map);
    }

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @Override
    public ResponseResult<AdminUserInfoVo> getInfo() {
        // 获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUser().getId();
        // 根据用户id查询权限信息
        List<String> perms =  menuService.selectPermsByUserId(userId);
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleByUserId(userId);
        // 获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        // 封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    /**
     * 获取路由信息
     * @return 路由信息
     */
    @Override
    public ResponseResult<RoutersVo> getRouters() {
        // 查询menu 结果是tree的形式
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUser().getId();
        List<MenuVo> menus = menuService.selectRouterMenuTreeByUserId(userId);
        // 封装数据返回
        RoutersVo routersVo = new RoutersVo(menus);
        return ResponseResult.okResult(routersVo);
    }

    /**
     * 登出
     * @return 登出结果
     */
    @Override
    public ResponseResult logout() {
        // 获取当前登录的用户
        Long userId = SecurityUtils.getUserId();
        // 删除redis中对应的值
        redisCache.deleteObject(SystemConstants.ADMIN_LOGIN_PREFIX + userId);
        return ResponseResult.okResult();
    }
}
