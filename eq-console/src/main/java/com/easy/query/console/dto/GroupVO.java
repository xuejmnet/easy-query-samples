package com.easy.query.console.dto;

import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;

/**
 * create time 2025/2/22 15:43
 * 文件说明
 *
 * @author xuejiaming
 */
@EntityProxy
@Data
public class GroupVO {
    private String userName;

    private Long before2020Count;

    private Long userCount;
}
