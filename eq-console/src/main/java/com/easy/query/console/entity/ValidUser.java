package com.easy.query.console.entity;

import com.easy.query.console.entity.proxy.ValidUserProxy;
import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * create time 2025/2/13 08:19
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("v_user")
@Data
@EntityProxy
public class ValidUser implements ProxyEntityAvailable<ValidUser , ValidUserProxy> {
    @Column(primaryKey = true,comment = "id")
    @Length(max = 32)
    private String id;
    @Column(comment = "姓名")
    @Length(max = 128)
    @NotNull
    private String name;
    @Column(comment = "年龄")
    @NotNull
    private Integer age;
}
