package com.giftpay.pay.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.giftpay.pay.api.service.ZshIntegralPayService;
import com.giftpay.pay.common.result.CommonResult;
import com.giftpay.pay.order.dao.ZshIntegralOrderDao;
import com.giftpay.pay.order.model.ZshIntegralOrder;
import com.giftpay.pay.utils.DateUtil;
import com.giftpay.pay.utils.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/20 0020.
 */
@Service("ZshIntegralPayService")
@Transactional(rollbackFor = Exception.class)
public class ZshIntegralPayServiceImpl implements ZshIntegralPayService {

    private Logger logger = Logger.getLogger(ZshIntegralPayServiceImpl.class);

    @Resource
    private ZshIntegralOrderDao zshIntegralOrderDao;

    @Override
    public CommonResult preOrder(String zshIntegralStr) {
        JSONObject jsonObject = JSON.parseObject(zshIntegralStr);
        String jsUserId = jsonObject.getString("js_user_id");
        String costIntegral = jsonObject.getString("cost_integral");
        String paySource = jsonObject.getString("pay_source");
        String backUrl = jsonObject.getString("back_url");
        String notifyUrl = jsonObject.getString("notify_url");

        if(!StringUtils.isEmpty(jsUserId)&&!StringUtils.isEmpty(costIntegral)&&!StringUtils.isEmpty(backUrl)&&!StringUtils.isEmpty(notifyUrl)){
            //http
            if(true){
                ZshIntegralOrder zshIntegralOrder = new ZshIntegralOrder();
                zshIntegralOrder.setJsUserId(jsUserId);
                zshIntegralOrder.setCostIntegral(costIntegral);
                zshIntegralOrder.setPaySource(paySource);
                zshIntegralOrder.setBackUrl(backUrl);
                zshIntegralOrder.setNotifyUrl(notifyUrl);
                zshIntegralOrder.setCreateTime(DateUtil.get4yMdHms(new Date()));
                zshIntegralOrder.setStatus("0");
                zshIntegralOrderDao.insertZshIntegralOrder(zshIntegralOrder);
                String zshIntegralId = zshIntegralOrder.getId();
                JSONObject returnObj = new JSONObject();
                returnObj.put("payMode","zsh_integral");
                returnObj.put("refId",zshIntegralId);
                return CommonResult.getInstance(0,returnObj);
            }else{
                return CommonResult.getInstance(10004,"中石化积分接口获取失败");
            }
        }else{
            return CommonResult.getInstance(10003,"缺少参数");
        }

    }
}
