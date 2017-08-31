package com.giftpay.pay.order.service.impl;

import com.giftpay.pay.order.dao.WeixinOrderDao;
import com.giftpay.pay.order.dao.ZshIntegralOrderDao;
import com.giftpay.pay.order.model.ZshIntegralOrder;
import com.giftpay.pay.order.service.ZshIntegralOrderService;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/20 0020.
 */
@Service("ZshIntegralOrderService")
@Transactional(rollbackFor = Exception.class)
public class ZshIntegralOrderServiceImpl  implements ZshIntegralOrderService{

    private Logger logger = Logger.getLogger(ZshIntegralOrderServiceImpl.class);

    @Resource
    private ZshIntegralOrderDao zshIntegralOrderDao;

    @Override
    public void insertZshIntegralOrder(ZshIntegralOrder zshIntegralOrder) {
        zshIntegralOrderDao.insertZshIntegralOrder(zshIntegralOrder);
    }

    @Override
    public int updateZshIntegralOrder(ZshIntegralOrder zshIntegralOrder) {
        return zshIntegralOrderDao.updateZshIntegralOrder(zshIntegralOrder);
    }

    @Override
    public List<ZshIntegralOrder> listZshIntegralOrder(ZshIntegralOrder zshIntegralOrder) {
        return zshIntegralOrderDao.listZshIntegralOrder(zshIntegralOrder);
    }

    @Override
    public ZshIntegralOrder getZshIntegralOrderById(@Param("id") String id) {
        return zshIntegralOrderDao.getZshIntegralOrderById(id);
    }
}
