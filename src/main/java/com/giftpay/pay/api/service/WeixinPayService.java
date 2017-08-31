package com.giftpay.pay.api.service;

import com.alibaba.fastjson.JSONObject;
import com.giftpay.pay.common.result.CommonResult;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 16:07 2017/8/17
 * @Modified By:
 */
public interface WeixinPayService {

    CommonResult preOrder(String weixinStr);

}
