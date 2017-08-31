package com.giftpay.pay.order.model;


import com.giftpay.pay.order.model.base.BaseEntity;

/**
 * Created by Administrator on 2017/8/20 0020.
 */
public class ZshIntegralOrder extends BaseEntity {

    private String id;

    private String jsUserId;

    private String costIntegral;

    private String paySource;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJsUserId() {
        return jsUserId;
    }

    public void setJsUserId(String jsUserId) {
        this.jsUserId = jsUserId;
    }

    public String getCostIntegral() {
        return costIntegral;
    }

    public void setCostIntegral(String costIntegral) {
        this.costIntegral = costIntegral;
    }

    public String getPaySource() {
        return paySource;
    }

    public void setPaySource(String paySource) {
        this.paySource = paySource;
    }
}
