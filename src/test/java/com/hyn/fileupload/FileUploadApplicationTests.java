package com.hyn.fileupload;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FileUploadApplicationTests {

    @Test
    void contextLoads() {
        String random = ""+Math.random();
        String substring = random.substring(random.length()-4);
        System.out.println(substring);
    }

}
