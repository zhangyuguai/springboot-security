package com.xiong.security.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 文件上传
     *
     * @param file   文件上传对象
     * @param module 文件夹名称
     * @return
     */
    String upload(MultipartFile file, String module);

    /**
     * 删除文件
     *
     * @param url
     */
    void deleteFile(String url);
}