package com.xiong.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.security.entity.User;
import com.xiong.security.entity.query.UserQueryVo;
import com.xiong.security.service.FileService;
import com.xiong.security.service.UserService;
import com.xiong.security.mapper.UserMapper;
import com.xiong.security.utils.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2022-11-03 23:12:54
*/
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private  UserMapper userMapper;
    @Autowired
    private FileService fileService;

    @Override
    public IPage<User> findUserListByPage(IPage<User> page, UserQueryVo userQueryVo) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        //根据用户名模糊查询
        queryWrapper.like(!ObjectUtils.isEmpty(userQueryVo.getUsername()),User::getUsername,userQueryVo.getUsername());

        //部门id
        queryWrapper.like(!ObjectUtils.isEmpty(userQueryVo.getDepartmentId()),User::getDepartmentId,userQueryVo.getDepartmentId());

        //真实姓名
        queryWrapper.like(!ObjectUtils.isEmpty(userQueryVo.getRealName()),User::getRealName,userQueryVo.getRealName());

        //电话
        queryWrapper.like(!ObjectUtils.isEmpty(userQueryVo.getPhone()),User::getPhone,userQueryVo.getPhone());

        return userMapper.selectPage(page,queryWrapper);
    }

    @Override
    public User getUserByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!ObjectUtils.isEmpty(username),User::getUsername,username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean deleteUserById(Long userId) {
        User user = userMapper.selectById(userId);

        //删除用户角色关系
        userMapper.deleteUserRole(userId);

        if (userMapper.deleteById(userId)>0) {
            if (user!=null&&user.getAvatar()!=null&&!user.getAvatar().equals((SystemConstants.DEFAULT_AVATAR))){
                //删除阿里云头像文件
                fileService.deleteFile(user.getAvatar());
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean saveUserRole(Long userId, List<Long> roleIds) {
        //删除用户角色关系
        userMapper.deleteUserRole(userId);
        //保存用户角色关系
        return  userMapper.saveUserRole(userId,roleIds)>0;
    }
}




