package com.giftpay.pay.order.dao;

import com.giftpay.pay.order.model.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/8/20 0020.
 */
@Repository
public interface OrderDao {

    void insertOrder(Order order);

    int updateOrder(Order order);

    List<Order> listOrder(Order order);

    Order getOrderById(@Param("id")  String id);
}
