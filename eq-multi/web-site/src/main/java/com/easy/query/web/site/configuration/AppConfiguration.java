package com.easy.query.web.site.configuration;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.basic.api.database.CodeFirstExecutable;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.domain.entity.Company;
import com.easy.query.domain.entity.SysUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * create time 2025/1/30 15:40
 * 文件说明
 *
 * @author xuejiaming
 */
@Configuration
public class AppConfiguration {

    public AppConfiguration(EasyEntityQuery easyEntityQuery){

        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        //如果不存在数据库则创建
        databaseCodeFirst.createDatabaseIfNotExists();
        //自动同步数据库表
        CodeFirstExecutable codeFirstExecutable = databaseCodeFirst.syncTables(Arrays.asList(Company.class, SysUser.class));
        //执行命令
        codeFirstExecutable.executeWithTransaction(arg -> {
            System.out.println(arg.sql);
            arg.commit();
        });

    }
}
