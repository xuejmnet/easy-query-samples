package com.easy.query.web.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * create time 2025/1/10 20:06
 * 文件说明
 *
 * @author xuejiaming
 */
@SpringBootApplication
//@ComponentScan("com.easy.query.web.*")
public class WebSiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebSiteApplication.class, args);
    }
}
