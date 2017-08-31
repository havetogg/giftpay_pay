package com.giftpay.pay.order.model;

import com.giftpay.pay.order.model.base.BaseEntity;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 18:03 2017/8/17
 * @Modified By:
 */
public class WeixinOrder extends BaseEntity{

    private String id;
    private String appid;//公众账号ID 微信分配的公众账号ID（企业号corpid即为此appId）
    private String body;
    private String mch_id;//商户号
    private String nonce_str;
    private String notify_url;
    private String openid;
    private String out_trade_no;
    private String spbill_create_ip;
    private String total_fee;
    private String trade_type;
    private String sign;
    private String js_config;
    private String backUrl;
    private String reNotifyUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getJs_config() {
        return js_config;
    }

    public void setJs_config(String js_config) {
        this.js_config = js_config;
    }

    @Override
    public String getBackUrl() {
        return backUrl;
    }

    @Override
    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public String getReNotifyUrl() {
        return reNotifyUrl;
    }

    public void setReNotifyUrl(String reNotifyUrl) {
        this.reNotifyUrl = reNotifyUrl;
    }
}
