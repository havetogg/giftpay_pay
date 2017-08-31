package com.giftpay.pay.order.service.impl;

import com.giftpay.pay.order.dao.WeixinOrderDao;
import com.giftpay.pay.order.model.WeixinOrder;
import com.giftpay.pay.order.service.WeixinOrderService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/20 0020.
 */
@Service("WeixinOrderService")
@Transactional(rollbackFor = Exception.class)
public class WeixinOrderServiceImpl implements WeixinOrderService {

    private Logger logger = Logger.getLogger(WeixinOrderServiceImpl.class);

    @Resource
    private WeixinOrderDao weixinOrderDao;

    @Override
    public void insertWeixinOrder(WeixinOrder weixinOrder) {
        weixinOrderDao.insertWeixinOrder(weixinOrder);
    }

    @Override
    public int updateWeixinOrder(WeixinOrder weixinOrder) {
        return weixinOrderDao.updateWeixinOrder(weixinOrder);
    }

    @Override
    public List<WeixinOrder> listWeixinOrder(WeixinOrder weixinOrder) {
        return weixinOrderDao.listWeixinOrder(weixinOrder);
    }

    @Override
    public WeixinOrder getWeixinOrderById(String id) {
        return weixinOrderDao.getWeixinOrderById(id);
    }
}
