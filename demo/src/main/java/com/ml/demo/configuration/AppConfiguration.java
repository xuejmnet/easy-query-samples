package com.ml.demo.configuration;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.ml.demo.domain.Company;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * create time 2025/6/20 16:59
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
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(Company.class));
        //执行命令
        codeFirstCommand.executeWithTransaction(arg -> {
            System.out.println(arg.getSQL());
            arg.commit();
        });

    }
}
