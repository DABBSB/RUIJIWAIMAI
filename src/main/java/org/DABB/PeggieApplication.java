package org.DABB;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PeggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(PeggieApplication.class, args);
        log.info("项目启动");
    }
}
