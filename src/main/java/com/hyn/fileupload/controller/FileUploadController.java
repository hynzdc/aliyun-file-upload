package com.hyn.fileupload.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther: hyn
 * @Date: 2022/9/17 - 09 - 21:41
 * @Description: com.hyn.fileupload.controller
 * @Version: 1.0
 */
@RestController
@RequestMapping("/file")
public class FileUploadController {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("/yyyy/MM/dd/");
    @PostMapping("/upload")
    public Map<String,Object> fileUploadLoad(MultipartFile file, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>();
        String originName = file.getOriginalFilename();
        if (!originName.endsWith(".png")){
            result.put("status","error");
            result.put("msg","文件类型不对");
            return  result;
        }
        String format = simpleDateFormat.format(new Date());
        //String realPath = request.getSession().getServletContext().getRealPath("/Users/austin/IdeaProjects/upload/");
        String realPath = System.getProperty("user.dir");
        //File folder = new File(realPath+"/src/main/resources/static");
        File folder = new File("/Users/austin/IdeaProjects/book/target/classes/static/");
//        if (!folder.exists()){
//            folder.mkdir();
//        }
        String newName = UUID.randomUUID().toString()+".png";
        try {
            file.transferTo(new File(folder,newName));
//            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
//            String url = servletRequestAttributes.getRequest().getServletContext().getRealPath("/")+newName;
            String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+newName;

            result.put("status","success");
            result.put("url",url);
        } catch (IOException e) {
            result.put("status","error");
            result.put("msg",e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

}
