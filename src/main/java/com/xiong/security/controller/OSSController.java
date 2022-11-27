package com.xiong.security.controller;

import com.xiong.security.common.utools.Result;
import com.xiong.security.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author xsy
 * @date 2022/11/11 1:38
 * description:
 */
@RestController
@RequestMapping("/api/oss/file")
public class OSSController {
    @Resource
    private FileService fileService;
    /**
     * 文件上传
     * @param file
     * @param module
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file, String module){
        //返回上传到oss的路径
        String url = fileService.upload(file,module);
        return new Result(200,"文件上传成功!",url,true);
    }
}

