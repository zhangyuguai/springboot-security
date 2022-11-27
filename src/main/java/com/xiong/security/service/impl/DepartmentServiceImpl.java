package com.xiong.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.security.entity.Department;
import com.xiong.security.entity.User;
import com.xiong.security.entity.query.DepartmentQueryVo;
import com.xiong.security.mapper.DepartmentMapper;
import com.xiong.security.mapper.UserMapper;
import com.xiong.security.service.DepartmentService;
import com.xiong.security.utils.DepartmentTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author LENOVO
 * @description 针对表【sys_department】的数据库操作Service实现
 * @createDate 2022-11-03 23:12:32
 */
@Service
@Transactional
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
        implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private UserMapper userMapper;


    @Override
    public List<Department> findDepartmentList(DepartmentQueryVo departmentQueryVo) {
        LambdaQueryWrapper<Department> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(departmentQueryVo.getDepartmentName()), Department::getDepartmentName, departmentQueryVo.getDepartmentName())
                .orderByAsc(Department::getOrderNum);
        List<Department> departmentList = departmentMapper.selectList(queryWrapper);
        //查询部门树
        List<Department> departmentTree = DepartmentTree.makeDepartmentTree(departmentList, 0L);

        return departmentTree;
    }

    @Override
    public List<Department> findParentDepartment() {
        //创建条件构造器对象
        QueryWrapper<Department> queryWrapper = new QueryWrapper<Department>();
        //排序
        queryWrapper.orderByAsc("order_num");
        //查询部门列表
        List<Department> departmentList = baseMapper.selectList(queryWrapper);
        //创建部门对象
        Department department = new Department();
        department.setId(0L);
        department.setDepartmentName("顶级部门");
        department.setPid(-1L);
        departmentList.add(department);
        //生成部门树列表
        List<Department> departmentTree =
                DepartmentTree.makeDepartmentTree(departmentList, -1L);
        //返回部门列表
        return departmentTree;
    }

    @Override
    public boolean hasChildrenOfDepartment(Long id) {
        //如果有Pid为id的则有子部门
        LambdaQueryWrapper<Department> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!Objects.isNull(id),Department::getPid,id);

        if (departmentMapper.selectCount(queryWrapper)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean hasUserOfDepartment(Long id) {
        //用户的department_id和id相等,则部门下有用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!Objects.isNull(id),User::getDepartmentId,id);

        if (userMapper.selectCount(queryWrapper)>0) {
            return true;
        }
        return false;
    }
}




