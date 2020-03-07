package com.permission.dto.input;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @auther: shenke
 * @date: 2020/2/24 11:23
 * @description: 分页参数
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PageParam {

    /**
     * 默认起始页
     */
    private static final Integer DEFAULT_PAGE_START = 1;

    /**
     * 默认每页记录数
     */
    private static final Integer DEFAULT_PAGE_SIZE = 15;

    /**
     * 设置分页默认参数
     */
    public PageParam() {
        this.pageStart = pageStart == null ? DEFAULT_PAGE_START : pageStart;
        this.pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
    }

    /**
     * 起始页
     */
    private Integer pageStart;

    /**
     * 每页记录数
     */
    private Integer pageSize;

}
