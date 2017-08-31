package com.giftpay.pay.order.model.base;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 17:56 2017/8/17
 * @Modified By:
 */
public class BaseEntity {

    private String backUrl;

    private String notifyUrl;

    private String createTime;

    private String dealTime;

    private String status;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
