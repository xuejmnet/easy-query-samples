package com.ml.demo.controller;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.ml.demo.domain.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create time 2025/6/20 17:00
 * 文件说明
 *
 * @author xuejiaming
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/api/test")
public class TestController {
    private final EasyEntityQuery entityQuery;

    @GetMapping("/say")
    public Object say(){
        return entityQuery.queryable(Company.class).where(c -> c.name().like("123")).toList();
    }
}