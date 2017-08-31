package com.giftpay.pay.api.service;

import com.giftpay.pay.common.result.CommonResult;

/**
 * Created by Administrator on 2017/8/20 0020.
 */
public interface ZshIntegralPayService {
    CommonResult preOrder(String zshIntegralStr);
}
