package com.giftpay.pay.order.service.impl;


import com.giftpay.pay.common.result.CommonResult;
import com.giftpay.pay.order.dao.OrderDao;
import com.giftpay.pay.order.dao.WeixinOrderDao;
import com.giftpay.pay.order.model.Order;
import com.giftpay.pay.order.service.OrderService;
import com.giftpay.pay.utils.AESUtil;
import com.giftpay.pay.utils.MD5Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by Administrator on 2017/8/20 0020.
 */
@Service("OrderService")
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    private Logger logger = Logger.getLogger(OrderServiceImpl.class);

    @Value(value = "#{configProperties['orderKey']}")
    private String key;

    @Resource
    private OrderDao orderDao;

    @Override
    public void insertOrder(Order order) {
        orderDao.insertOrder(order);
    }

    @Override
    public int updateOrder(Order order) {
        return orderDao.updateOrder(order);
    }

    @Override
    public List<Order> listOrder(Order order) {
        return orderDao.listOrder(order);
    }

    @Override
    public Order getOrderById(String id) {
        return orderDao.getOrderById(id);
    }
}
