package com.giftpay.pay.order.service;

import com.giftpay.pay.order.model.WeixinOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/8/20 0020.
 */
public interface WeixinOrderService {

    void insertWeixinOrder(WeixinOrder weixinOrder);

    int updateWeixinOrder(WeixinOrder weixinOrder);

    List<WeixinOrder> listWeixinOrder(WeixinOrder weixinOrder);

    WeixinOrder getWeixinOrderById(String id);

}
