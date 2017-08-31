package com.giftpay.pay.interceptor;

import com.giftpay.pay.common.result.CommonResult;
import com.giftpay.pay.utils.AESUtil;
import com.giftpay.pay.utils.MD5Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 19:04 2017/8/14
 * @Modified By:
 */
@Service("CheckService")
public class CheckService {

    @Value(value = "#{configProperties['orderKey']}")
    private String key;

    private static final Logger logger = Logger.getLogger(CheckService.class);

    //检验accessToken是否有效
    public CommonResult checkOrder(HttpServletRequest request, HttpServletResponse response) throws Exception{
        CommonResult commonResult = null;
            String timeStamp = request.getParameter("timeStamp");
        String orderId = request.getParameter("orderId");
        String sign = request.getParameter("sign");

        if(StringUtils.isEmpty(timeStamp)||StringUtils.isEmpty(orderId)||StringUtils.isEmpty(sign)){
            logger.error("有参数为空");
            commonResult = CommonResult.getInstance(1002,"有参数为空");
            return commonResult;
        }
        logger.info("传入参数timeStamp:"+timeStamp);
        logger.info("传入参数orderId:"+orderId);
        logger.info("传入参数sign:"+sign);
        /*timeStamp = URLDecoder.decode(timeStamp,"utf-8");
        orderId = URLDecoder.decode(orderId,"utf-8");
        sign = URLDecoder.decode(sign,"utf-8");*/

        logger.info("timeStamp为"+timeStamp);
        logger.info("orderId为"+orderId);
        logger.info("sign为"+sign);

        long computeTimeStamp = Long.parseLong(AESUtil.AESDncode(key,timeStamp));
        Long nowTime = System.currentTimeMillis();
        Long s = (nowTime - computeTimeStamp) / (1000 * 60);
        if(s>=5){
            logger.error("时间戳时间距离当前时间太久");
            commonResult = CommonResult.getInstance(1003,"时间戳时间距离当前时间太久");
            return commonResult;
        }

        Map<String,String> sortedMap = new TreeMap<>();
        sortedMap.put("orderId",orderId);
        sortedMap.put("timeStamp",timeStamp);
        sortedMap.put("sign",sign);

        StringBuffer stringBuffer = new StringBuffer();
        for(String key:sortedMap.keySet()){
            if(!"sign".equals(key)){
                stringBuffer.append("&"+key+"="+sortedMap.get(key));
            }
        }
        stringBuffer.append("&key="+key);
        stringBuffer.deleteCharAt(0);
        logger.error("stringBuffer为"+stringBuffer);
        String computeSign = MD5Util.getMD5(stringBuffer.toString());
        logger.error("computeSign为"+computeSign);
        if(computeSign.equals(sign)){
            logger.info("参数匹配正确，拦截器跳转控制层");
            commonResult = CommonResult.getInstance(0,null);
            return commonResult;
        }else{
            logger.error("sign校验错误");
            commonResult = CommonResult.getInstance(1003,"sign校验错误");
            return commonResult;
        }
    }
}
