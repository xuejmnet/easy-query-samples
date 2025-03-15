package com.easy.query.console.entity;

import com.easy.query.console.entity.proxy.BankCardProxy;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * create time 2025/2/24 21:03
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_bank_card")
@EntityProxy
@FieldNameConstants
public class BankCard implements ProxyEntityAvailable<BankCard , BankCardProxy> {

    private String id;
    private String uid;
    private String no;
}
