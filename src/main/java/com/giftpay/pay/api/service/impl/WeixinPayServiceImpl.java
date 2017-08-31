package com.giftpay.pay.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.giftpay.pay.api.model.WCPreOrderResponse;
import com.giftpay.pay.api.model.WCPreorder;
import com.giftpay.pay.api.service.WeixinPayService;
import com.giftpay.pay.common.result.CommonResult;
import com.giftpay.pay.order.dao.WeixinOrderDao;
import com.giftpay.pay.order.model.WeixinOrder;
import com.giftpay.pay.utils.DateUtil;
import com.giftpay.pay.utils.StringUtil;
import com.giftpay.pay.utils.WXPayUtil;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 16:20 2017/8/17
 * @Modified By:
 */
@Service("WeixinPayService")
@Transactional(rollbackFor = Exception.class)
public class WeixinPayServiceImpl implements WeixinPayService{

    private Logger logger = Logger.getLogger(WeixinPayServiceImpl.class);

    @Value(value = "#{configProperties['appID']}")
    private String appID;

    @Value(value = "#{configProperties['appSecret']}")
    private String appSecret;

    @Value(value = "#{configProperties['mchId']}")
    private String mchId;

    @Value(value = "#{configProperties['key']}")
    private String key;

    @Value(value = "#{configProperties['notifyUrl']}")
    private String notifyUrl;

    @Value(value = "#{configProperties['outTradeFlag']}")
    private String outTradeFlag;

    @Resource
    private WeixinOrderDao weixinOrderDao;

    @Override
    public CommonResult preOrder(String weixinStr) {
        JSONObject jsonObject = JSON.parseObject(weixinStr);
        String appid = appID;
        String mch_id = mchId;
        String nonce_str = StringUtil.getRandomString(32);
        String body = jsonObject.getString("body");
        String total_fee = jsonObject.getString("total_fee");
        String spbill_create_ip = jsonObject.getString("spbill_create_ip");
        String back_url = jsonObject.getString("back_url");
        String notify_url = jsonObject.getString("notify_url");
        String trade_type = "JSAPI";
        String openid = jsonObject.getString("openid");

        WeixinOrder weixinOrder = new WeixinOrder();
        weixinOrder.setAppid(appid);
        weixinOrder.setMch_id(mch_id);
        weixinOrderDao.insertWeixinOrder(weixinOrder);


        WCPreorder wcPreorder = new WCPreorder();
        wcPreorder.setAppid(appid);
        wcPreorder.setMch_id(mch_id);
        wcPreorder.setNonce_str(nonce_str);
        wcPreorder.setBody(body);
        wcPreorder.setOut_trade_no(outTradeFlag+weixinOrder.getId());
        wcPreorder.setTotal_fee(total_fee);
        wcPreorder.setSpbill_create_ip(spbill_create_ip);
        wcPreorder.setNotify_url(notifyUrl);
        wcPreorder.setTrade_type(trade_type);
        wcPreorder.setOpenid(openid);
        String responseXml = WXPayUtil.preOrder(wcPreorder,key);
        logger.info("responseXml=" + responseXml);
        XStream stream = new XStream();
        stream.alias("xml", WCPreOrderResponse.class);
        WCPreOrderResponse wcPreOrderResponse = (WCPreOrderResponse) stream.fromXML(responseXml);
        if (wcPreOrderResponse.getReturn_code().equals("SUCCESS")) {
            if (wcPreOrderResponse.getResult_code().equals("SUCCESS")) {
                Map<String, String> map = WXPayUtil.loadJavaScriptPayConfig( appid, wcPreOrderResponse.getPrepay_id(), key);
                String jsonStr = JSON.toJSONString(map);

                //保存微信order数据
                weixinOrder.setNonce_str(nonce_str);
                weixinOrder.setBody(body);
                weixinOrder.setOut_trade_no(outTradeFlag+weixinOrder.getId());
                weixinOrder.setTotal_fee(total_fee);
                weixinOrder.setSpbill_create_ip(spbill_create_ip);
                weixinOrder.setNotify_url(notifyUrl);
                weixinOrder.setTrade_type(trade_type);
                weixinOrder.setOpenid(openid);
                weixinOrder.setSign(wcPreorder.getSign());
                weixinOrder.setJs_config(jsonStr);
                weixinOrder.setBackUrl(back_url);
                weixinOrder.setReNotifyUrl(notify_url);
                weixinOrder.setCreateTime(DateUtil.get4yMdHms(new Date()));
                weixinOrder.setStatus("0");
                weixinOrderDao.updateWeixinOrder(weixinOrder);

                String weixinOrderId = weixinOrder.getId();
                JSONObject returnObj = new JSONObject();
                returnObj.put("payMode","weixin");
                returnObj.put("refId",weixinOrderId);
                return CommonResult.getInstance(0,returnObj);
            } else {
                return CommonResult.getInstance(10002,"微信预支付订单接口调用失败");
            }
        } else {
            return CommonResult.getInstance(10002,"微信预支付订单接口调用失败");
        }
    }
}
