package com.hyn.fileupload.FileService;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.hyn.fileupload.util.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.InputStream;

import java.util.Date;
import java.util.UUID;

/**
 * @Auther: hyn
 * @Date: 2022/9/7 - 09 - 17:33
 * @Description: com.hyn.fileupload.FileService
 * @Version: 1.0
 */
@Service("IFileService")
public class FileServiceImpl implements IFileService{
    public static  Integer count = 0;
    @Override
    public String upload(MultipartFile file) {

        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        String fileName = file.getOriginalFilename();
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //获取上传文件的输入流
            InputStream inputStream = file.getInputStream();
            //InputStream inputStream = new URL("https://www.aliyun.com/").openStream();
            //添加 ContentType
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            //生成一个随机的值
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            //fileName = uuid+fileName;


            //把文件按日期分类
            //2022/4/19
            //获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd");

            if (fileName.substring(fileName.lastIndexOf(".")).equalsIgnoreCase(".png")){

                fileName = datePath + "/" + (count++).toString() + ".png" ;
            }

            if (fileName.substring(fileName.lastIndexOf(".")).equalsIgnoreCase(".html")){
                fileName = datePath + "/" + System.currentTimeMillis() + ".html";
            }

            System.out.println(fileName.substring(fileName.lastIndexOf(".")));
            if (fileName.substring(fileName.lastIndexOf(".")).equalsIgnoreCase(".pdf")){
                fileName = datePath + "/" + System.currentTimeMillis() + ".pdf" ;
            }

            //拼接
//            fileName = datePath+"/"+fileName;

            //第一个名称bucket
            //第二个名称上传的文件路径或者文件名称
            //文件上传流
            ossClient.putObject(bucketName,fileName,inputStream,objectMetadata);
            ossClient.shutdown();
            //把上传到阿里云的图片路径返回
            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        if (FilenameExtension.equalsIgnoreCase(".pdf")){
            return "application/pdf";
        }
        return "image/jpg";
    }
}
