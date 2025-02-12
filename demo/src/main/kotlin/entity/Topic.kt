package com.test.entity

import com.easy.query.core.annotation.Column
import com.easy.query.core.annotation.EntityProxy
import com.easy.query.core.annotation.Table

import com.easy.query.core.proxy.ProxyEntityAvailable;

import com.test.entity.proxy.TopicProxy;




@Table("t_topic")
@EntityProxy
class Topic : ProxyEntityAvailable<Topic, TopicProxy> {
    @Column(primaryKey = true)
    var id:String?=null;
    var stars:Int?=null;
    var title:String?=null;
}