package com.easy.entity

import com.easy.query.core.annotation.Column
import com.easy.query.core.annotation.EntityProxy
import com.easy.query.core.annotation.Table
import java.time.LocalDateTime


import com.easy.query.core.proxy.ProxyEntityAvailable;

import com.easy.entity.proxy.CompanyProxy;


@Table("company")
@EntityProxy
class Company : ProxyEntityAvailable<Company, CompanyProxy> {
    @Column(primaryKey = true)
    var id: String? = null;
    var name: String? = null;
    var createTime: LocalDateTime? = null;

}