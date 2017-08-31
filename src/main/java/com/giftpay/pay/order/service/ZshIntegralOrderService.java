package com.giftpay.pay.order.service;

import com.giftpay.pay.order.model.ZshIntegralOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/8/20 0020.
 */
public interface ZshIntegralOrderService {

    void insertZshIntegralOrder(ZshIntegralOrder zshIntegralOrder);

    int updateZshIntegralOrder(ZshIntegralOrder zshIntegralOrder);

    List<ZshIntegralOrder> listZshIntegralOrder(ZshIntegralOrder zshIntegralOrder);

    ZshIntegralOrder getZshIntegralOrderById(@Param("id")  String id);
}
