package com.xiong.security.controller.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xiong.security.common.utools.Result;
import com.xiong.security.entity.Permission;
import com.xiong.security.entity.Role;
import com.xiong.security.entity.User;
import com.xiong.security.entity.vo.RouterVo;
import com.xiong.security.entity.vo.TokenVo;
import com.xiong.security.entity.vo.UserInfo;
import com.xiong.security.service.PermissionService;
import com.xiong.security.service.RoleService;
import com.xiong.security.service.UserService;
import com.xiong.security.utils.MenuTree;
import com.xiong.security.utils.RedisUtil;
import com.xiong.security.utils.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xsy
 * @date 2022/11/4 11:54
 * description:
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    /**
     * 返回用户相关信息(姓名，权限，菜单...)
     * @return
     */
    @GetMapping("/getInfo")
    public Result getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取当前用户的权限
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roleList = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            roleList.add(role);
        }

        //获取当前用户菜单
        String username = (String) authentication.getPrincipal();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(username),User::getUsername,username);
        User user = userService.getOne(queryWrapper);
        //当前用户所有菜单
        List<Permission> permissionList = permissionService.findPermissionListByUserId(user.getId());
        //筛选目录和菜单
        List<Permission> collect = permissionList.stream()
                .filter(item -> item != null && item.getType() !=2)
                .collect(Collectors.toList());
        Role role = roleService.getRoleByUid(user.getId());
        //转化成router
        List<RouterVo> routerVoList = MenuTree.makeRouter(collect, 0L);

        //返回用户信息vo
        UserInfo userInfo = new UserInfo();
        userInfo.setName(user.getUsername());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setId(user.getId());
        if (!ObjectUtils.isEmpty(role)) {
            userInfo.setIntroduction(role.getRemark());
        }
        userInfo.setRoles(roleList);
        userInfo.setMenus(routerVoList);
        return new Result(userInfo);
    }

    /**
     * 刷新token
     * @param request
     * @return
     */
    @GetMapping("/refreshToken")
    public Result refreshToken(HttpServletRequest request){
        //从请求头中获取token
        String token = request.getHeader("token");
        //如果获取不到，则从请求参数中获取
        if (Objects.isNull(token)){
            token= request.getParameter("token");
        }
        //删除原本的token
        redisUtil.remove("token_"+token);

        //刷新token
        String newToken = tokenManager.refreshToken(token);

        long expirationTime = tokenManager.getExpirationFromToken(newToken).getTime();
        //存入redis
        redisUtil.set("token_"+newToken,newToken,expirationTime);
        //创建tokenVo对象
        TokenVo tokenVo = new TokenVo();
        tokenVo.setToken(newToken);
        tokenVo.setExpireTime(expirationTime);
        return new Result(200,"刷新成功",tokenVo,true);
    }
}
