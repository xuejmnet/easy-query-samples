package com.easy.query.console.vo;

import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

/**
 * create time 2025/1/29 23:28
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@FieldNameConstants
public class CompanyNameAndUserNameVO {
    /**
     * 企业名称
     */
    private String companyName;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户出生日期
     */
    private LocalDateTime birthday;

    /**
     * 用户所属企业id
     */
    private String companyId;
}
