package com.giftpay.pay.order.dao;

import com.giftpay.pay.order.model.WeixinOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 11:27 2017/8/18
 * @Modified By:
 */
@Repository
public interface WeixinOrderDao {

    void insertWeixinOrder(WeixinOrder weixinOrder);

    int updateWeixinOrder(WeixinOrder weixinOrder);

    List<WeixinOrder> listWeixinOrder(WeixinOrder weixinOrder);

    WeixinOrder getWeixinOrderById(@Param("id")  String id);

}
