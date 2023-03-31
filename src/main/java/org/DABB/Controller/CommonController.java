package org.DABB.Controller;

import lombok.extern.slf4j.Slf4j;
import org.DABB.commons.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.path}")
    public String reggiePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        log.info(file.toString());
//        获取文件名
        String filename = file.getOriginalFilename();
        // 获取后缀名
        assert filename != null;
        String substring = filename.substring(filename.lastIndexOf("."));
        String s = UUID.randomUUID().toString() + substring;
        //判断是否有Path设置的文件夹
        File file1 = new File(reggiePath);
        if (!file1.exists()) {
            //生成文件夹
            file1.mkdirs();
        }
//        输入流
        try {
            file.transferTo(new File(reggiePath + s));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
