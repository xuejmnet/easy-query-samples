package com.easy.query.console.entity;

import com.easy.query.console.entity.proxy.SysUserProxy;
import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2025/1/28 00:11
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_user")
@EntityProxy
public class SysUser implements ProxyEntityAvailable<SysUser , SysUserProxy> {
    /**
     * 用户id
     */
    @Column(primaryKey = true)
    private String id;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户出生日期
     */
    private LocalDateTime birthday;

    /**
     * 用户所属企业id
     */
    private String companyId;
}
