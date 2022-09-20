package com.hyn.fileupload.FileService;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther: hyn
 * @Date: 2022/9/7 - 09 - 17:32
 * @Description: com.hyn.fileupload.FileService
 * @Version: 1.0
 */
public interface IFileService {
    /**
     * 文件上传至阿里云
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
