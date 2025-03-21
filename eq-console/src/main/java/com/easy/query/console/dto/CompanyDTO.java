package com.easy.query.console.dto;


import lombok.Data;
import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.RelationTypeEnum;

import java.math.BigDecimal;
import java.util.List;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.ToString;

/**
 * this file automatically generated by easy-query struct dto mapping
 * 当前文件是easy-query自动生成的 结构化dto 映射
 * {@link com.easy.query.console.entity.Company }
 *
 * @author xuejiaming
 * @easy-query-dto schema: normal
 */
@Data
@ToString
public class CompanyDTO {


    /**
     * 企业id
     */
    @Column(dbType = "varchar(32)", comment = "企业id")
    private String id;
    /**
     * 企业名称
     */
    @Column(nullable = false, comment = "企业名称")
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
    @Column(dbType = "varchar(500)", comment = "测试列", renameFrom = "column")
    private String column1;
    /**
     * 企业拥有的用户
     */
    @Navigate(value = RelationTypeEnum.OneToMany)
    private List<InternalUsers> users;


    /**
     * {@link com.easy.query.console.entity.SysUser }
     */
    @Data
    public static class InternalUsers {
        /**
         * 用户id
         */
        @Column(dbType = "varchar(32)", comment = "用户id")
        private String id;
        /**
         * 用户姓名
         */
        @Column(comment = "用户姓名")
        private String name;
        /**
         * 用户出生日期
         */
        @Column(comment = "用户出生日期")
        private LocalDateTime birthday;
        /**
         * 用户所属企业id
         */
        @Column(comment = "用户所属企业id")
        private String companyId;


    }

}
