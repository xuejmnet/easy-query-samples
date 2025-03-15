package com.easy.query.console.entity;

import com.easy.query.console.entity.proxy.CompanyProxy;
import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/1/27 23:52
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table(value = "t_company",comment = "企业表")
@EntityProxy
@FieldNameConstants
public class Company implements ProxyEntityAvailable<Company , CompanyProxy> {
    /**
     * 企业id
     */
    @Column(primaryKey = true,comment = "企业id",dbType = "varchar(32)")
    private String id;
    /**
     * 企业名称
     */
    @Column(comment = "企业名称",nullable = false)
    private String name;

    /**
     * 企业创建时间
     */
    @Column(comment = "企业创建时间")
    private LocalDateTime createTime;

    /**
     * 注册资金
     */
    @Column(comment = "注册资金")
    private BigDecimal registerMoney;


    @Column(comment = "测试列",dbType = "varchar(500)",renameFrom = "column")
    private String column1;


    /**
     * 企业拥有的用户
     */
    @Navigate(value = RelationTypeEnum.OneToMany,
            selfProperty = {Company.Fields.id},
            targetProperty = {SysUser.Fields.companyId})
    private List<SysUser> users;

}
