package org.DABB.Controller;

import lombok.extern.slf4j.Slf4j;
import org.DABB.commons.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
        String UUIDs = UUID.randomUUID().toString() + substring;
        //判断是否有Path设置的文件夹
        File file1 = new File(reggiePath);
        if (!file1.exists()) {
            //生成文件夹
            file1.mkdirs();
        }
//        将临时文件转存到指定文件夹下
        try {
            file.transferTo(new File(reggiePath + UUIDs));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return R.success(filename);
    }

    //    文件下载
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {

        try {
            //输入流读取文件
            FileInputStream fileInputStream = new FileInputStream(reggiePath + name);
            //输出流，将文件写回浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes, 0, len)) != -1) {
                outputStream.write(bytes);
                outputStream.flush();
            }

            fileInputStream.close();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


