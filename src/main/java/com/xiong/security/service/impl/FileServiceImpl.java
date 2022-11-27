package com.xiong.security.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.xiong.security.config.OssProperties;
import com.xiong.security.service.FileService;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author xsy
 * @date 2022/11/11 1:36
 * description:
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {
    /**
     * 文件上传
     *
     * @param file 文件上传对象
     * @param module 文件夹名称
     * @return
     */
    @Resource
    private OssProperties ossProperties;

    /**
     * 文件上传
     *
     * @param file   文件上传对象
     * @param module 文件夹名称
     * @return
     */
    @Override
    public String upload(MultipartFile file, String module) {
        //获取地域节点
        String endPoint = ossProperties.getEndPoint();
        //获取AccessKeyId
        String keyId = ossProperties.getKeyId();
        //获取AccessKeySecret
        String keySecret = ossProperties.getKeySecret();
            //获取BucketName
        String bucketName = ossProperties.getBucketName();
        try {
            //创建OSSClient实例
            OSS ossClient = new OSSClientBuilder().build(endPoint, keyId,
                    keySecret);
            //上传文件流
            InputStream inputStream = file.getInputStream();
            //获取旧名称
            String originalFilename = file.getOriginalFilename();
            //获取文件后缀名
            String extension = FilenameUtils.getExtension(originalFilename);
            //将文件名重命名
            String newFileName = UUID.randomUUID().toString()
                    .replace("-", "") + "." + extension;
            //使用当前日期进行分类管理
            String datePath = new DateTime().toString("yyyy/MM/dd");
            //构建文件名
            newFileName = module + "/" + datePath + "/" + newFileName;
            //调用OSS文件上传的方法
            ossClient.putObject(bucketName, newFileName, inputStream);
            //关闭OSSClient
            ossClient.shutdown();
            //返回文件地址
            return "https://" + bucketName + "." + endPoint + "/" + newFileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteFile(String url) {
        //获取地域节点
        String endPoint = ossProperties.getEndPoint();
        //获取AccessKeyId
        String keyId = ossProperties.getKeyId();
        //获取AccessKeySecret
        String keySecret = ossProperties.getKeySecret();
        //获取BucketName
        String bucketName = ossProperties.getBucketName();
        try {
            //创建OSSClient实例
            OSS ossClient = new OSSClientBuilder().build(endPoint, keyId,
                    keySecret);
            //组装文件地址
            String host = "https://"+bucketName+"."+endPoint+"/";
            //获取文件名称
            String objectName = url.substring(host.length());
            //删除文件
            ossClient.deleteObject(bucketName,objectName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
