package com.giftpay.pay.common.model.base;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/3/16.
 * 基础分页类
 */
public class BaseEntity implements Serializable {
    public Integer pageIndex;

    public Integer getPageIndex() {
        if (this.pageNum==null||this.pageSize==null)
            return 0;
        this.pageIndex = (pageNum-1)*pageSize;
        return pageIndex;
    }


    public Integer pageNum;
    public Integer pageSize;
    /**
     * 升序字段List --（数据库字段名称）
     */
    public List<String> ascList = null;
    /**
     * 降序字段List --（数据库字段名称）
     */
    public List<String> descList = null;
}
