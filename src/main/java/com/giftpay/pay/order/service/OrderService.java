package com.giftpay.pay.order.service;

import com.giftpay.pay.common.result.CommonResult;
import com.giftpay.pay.order.model.Order;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2017/8/20 0020.
 */
public interface OrderService {
    void insertOrder(Order order);

    int updateOrder(Order order);

    List<Order> listOrder(Order order);

    Order getOrderById(String id);

}
