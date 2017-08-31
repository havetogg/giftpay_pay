package com.giftpay.pay.common.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/3/16.
 */
public class PageResult<T> implements Serializable {
    private Integer pageNum = 1;
    private Integer pageSize = 20;
    private List<T> datas;
    private Integer totalPage;
    private Integer totalCount;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {

        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize>1000)
            pageSize = 20;
        this.pageSize = pageSize;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public Integer getTotalPage() {
        if (totalCount%pageSize==0)
            this.totalPage =  totalCount/pageSize;
        else
            this.totalPage =  totalCount/pageSize+1;
        return totalPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
