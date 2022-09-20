package com.hyn.fileupload.controller;

import com.hyn.fileupload.FileService.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin //跨域
@RestController
@RequestMapping("/user")
public class FileController {

    @Autowired
    private IFileService fileService;

    /**
     * 文件上传
     *
     * @param file
     */
    @PostMapping("/upload")
    public String upload(
            @RequestParam("file") MultipartFile file) {

        //返回r对象
        return fileService.upload(file);

    }
}
