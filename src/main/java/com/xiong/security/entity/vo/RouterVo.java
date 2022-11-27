package com.xiong.security.entity.vo;

import com.xiong.security.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xsy
 * @date 2022/11/4 12:46
 * description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouterVo {
    //路由地址
    private String path;
    //路由对应的组件
    private String component;
    //路由名称
    private String name;
    //路由meta信息
    private Meta meta;
    //一直显示根路由
    private boolean alwaysShow;
    // 不在侧边栏显示
    private boolean hidden;
    private String redirect;

    //子路由
    private List<RouterVo> children;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Meta {
        private String title;// 设置该路由在侧边栏和面包屑中展示的名字
        private String icon;// 设置该路由的图标，支持 svg-class，也支持 el-icon-x element-ui 的 icon
        private String[] roles;// 设置该路由进入的权限，支持多个权限叠加
    }
}
