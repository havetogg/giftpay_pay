package com.giftpay.pay.common.model;

import com.giftpay.pay.common.model.base.BaseEntity;

import java.util.Date;

/**
 * Created by admin on 2017/3/16.
 * accessToken类实体
 */
public class AccessToken extends BaseEntity{
    private long id;
    private long userId;
    private String accessToken;
    private Date createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
