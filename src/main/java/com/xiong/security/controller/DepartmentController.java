package com.xiong.security.controller;

import com.xiong.security.common.utools.Result;
import com.xiong.security.entity.Department;
import com.xiong.security.entity.query.DepartmentQueryVo;
import com.xiong.security.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xsy
 * @date 2022/11/5 22:42
 * description:
 */
@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    /**
     * 查询部门列表
     * @param departmentQueryVo
     * @return
     */
    @GetMapping("/list")
    public Result list(DepartmentQueryVo departmentQueryVo){
        List<Department> departmentList = departmentService.findDepartmentList(departmentQueryVo);
        return new Result(departmentList);
    }

    /**
     * 查询上级部门列表
     * @return
     */
    @GetMapping("/parent/list")
    public Result getParentDepartment(){
        List<Department> parentDepartment = departmentService.findParentDepartment();
        return new Result(parentDepartment);
    }

    /**
     * 添加部门
     * @param department
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Department department){
        if (departmentService.save(department)) {
            return new Result(200,"添加部门成功!",null);
        }
        return new Result(500,"添加部门失败!",null);
    }

    /**
     * 修改部门
     * @param department
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody Department department){
        if (departmentService.updateById(department)) {
            return new Result(200,"更新部门成功!",null);
        }
        return new Result(500,"更新部门失败!",null);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable long id){
        if (departmentService.removeById(id)) {
            return new Result(200,"删除部门成功!",null);
        }
        return new Result(500,"删除部门失败!",null);
    }

    @GetMapping("/check/{id}")
    public Result check(@PathVariable Long id){
        //查看部门下是否有子部门
        if (departmentService.hasChildrenOfDepartment(id)) {
            return new Result(200,"部门下有子部门,不能删除",null,false);
        }
        //查看部门下是否有用户
        if (departmentService.hasUserOfDepartment(id)) {
            return new Result(200,"部门下有用户,不能删除",null,false);
        }
        return new Result(200,null,null,true);
    }
}
