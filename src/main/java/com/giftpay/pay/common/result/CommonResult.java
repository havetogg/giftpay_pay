package com.giftpay.pay.common.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/3/17.
 */
public class CommonResult<T> implements Serializable {
    private int code = 0;
    private T message = null;

    public static <T> CommonResult<T> getInstance(int code,T t){
        CommonResult<T> commonResult = new CommonResult();
        commonResult.setCode(code);
        commonResult.setMessage(t);
        return commonResult;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }
}
