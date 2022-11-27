package com.xiong.security.utils;

import com.xiong.security.entity.Department;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author xsy
 * @date 2022/11/5 20:34
 * description:
 */

public class DepartmentTree {

    /**
     * 生成部门树
     * @param departmentList
     * @param pid
     * @return
     */
    public static List<Department> makeDepartmentTree(List<Department> departmentList,Long pid){
        List<Department> list=new ArrayList<>();

        Optional.ofNullable(departmentList).orElse(new ArrayList<Department>())
                .stream().filter(item->item!=null&&item.getPid()==pid)
                .forEach(item->{
                    //创建部门对象
                    Department department = new Department();
                    //复制属性
                    BeanUtils.copyProperties(item,department);

                    //递归生成下级部门
                    List<Department> children = makeDepartmentTree(departmentList, item.getId());
                    //设置子部门
                    department.setChildren(children);

                    //将部门对象添加到集合
                    list.add(department);
                });
            return list;
    }
}
