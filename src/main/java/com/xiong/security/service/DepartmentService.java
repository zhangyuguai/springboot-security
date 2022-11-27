package com.xiong.security.service;

import com.xiong.security.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiong.security.entity.query.DepartmentQueryVo;

import java.util.List;

/**
 * @author LENOVO
 * @description 针对表【sys_department】的数据库操作Service
 * @createDate 2022-11-03 23:12:32
 */
public interface DepartmentService extends IService<Department> {

    /**
     * 查询部门列表
     *
     * @param departmentQueryVo
     * @return
     */
    List<Department> findDepartmentList(DepartmentQueryVo departmentQueryVo);

    /**
     * 查询上级部门列表
     *
     * @return
     */
    List<Department> findParentDepartment();

    /**
     * 判断部门下是否有子部门
     * @param id
     * @return
     */
    boolean hasChildrenOfDepartment(Long id);
    /**
     * 判断部门下是否存在用户
     * @param id
     * @return
     */
    boolean hasUserOfDepartment(Long id);

}
