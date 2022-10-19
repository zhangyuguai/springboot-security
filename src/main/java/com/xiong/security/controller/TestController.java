package com.xiong.security.controller;

import com.xiong.security.ResBean.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xsy
 * @date 2022/10/19
 * description:
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public Result test(){
        return Result.success("哈哈哈");
    }
}
