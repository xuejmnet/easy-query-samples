package com.test

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery
import com.easy.query.core.api.client.DefaultEasyQueryClient
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper
import com.easy.query.core.logging.LogFactory
import com.easy.query.mysql.config.MySQLDatabaseConfiguration
import com.test.entity.Topic
import com.zaxxer.hikari.HikariDataSource

fun main() {

    println("Hello World!")

    var hikariDataSource = HikariDataSource()
    hikariDataSource.jdbcUrl =
        "jdbc:mysql://127.0.0.1:3306/easy-query-test?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true";
    hikariDataSource.username = "root";
    hikariDataSource.password = "root";
    hikariDataSource.driverClassName = "com.mysql.cj.jdbc.Driver";
    hikariDataSource.maximumPoolSize = 20;
    LogFactory.useStdOutLogging();

    var easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
        .setDefaultDataSource(hikariDataSource)
        .useDatabaseConfigure(MySQLDatabaseConfiguration())
        .build()
    var entityQuery = DefaultEasyEntityQuery(easyQueryClient)
    var toList = easyQueryClient.queryable(Topic::class.java)
        .toList()
    var toList1 = entityQuery.queryable(Topic::class.java)
        .where {
            it.id().eq("123")
        }.toList()

}