package com.giftpay.pay.utils;

import com.giftpay.pay.order.model.ZshIntegralOrder;
import org.apache.commons.lang.StringUtils;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 15:15 2017/8/21
 * @Modified By:
 */
public class ZshIntegralPayUtil {

    // 申请支付
    public static final String _LAUNCH_PAY_URL = "https://prod1.juxinbox.com/zsh.integral/api/v1/integral/cost.htm?sbxUserId=%s&payOrderId=%s&paySource=%s&costIntegral=%s";
    //public static final String _LAUNCH_PAY_URL = "https://prod1.juxinbox.com/zsh.integral.test/api/v1/integral/cost.htm?sbxUserId=%s&payOrderId=%s&paySource=%s&costIntegral=%s";

    public static final String _GET_INTEGRAL = "https://prod1.juxinbox.com/zsh.integral/api/v1/user/integral.htm?sbxUserId=%s";



    public static String doOrder(ZshIntegralOrder zshIntegralOrder) {
        String sbxUserId = zshIntegralOrder.getJsUserId();
        String costIntegral = zshIntegralOrder.getCostIntegral();
        String paySource = zshIntegralOrder.getPaySource();
        if(StringUtils.isBlank(paySource)){
            paySource = "1";
        }
        String launchPayUrl = String.format(_LAUNCH_PAY_URL, sbxUserId,zshIntegralOrder.getId(),paySource,costIntegral);
        String result = null;
        try{
            result = HttpClientUtil.doHttpsGet(launchPayUrl,null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static String getIntegral(ZshIntegralOrder zshIntegralOrder) {
        String sbxUserId = zshIntegralOrder.getJsUserId();
        String launchPayUrl = String.format(_GET_INTEGRAL, sbxUserId);
        String result = null;
        try{
            result = HttpClientUtil.doHttpsGet(launchPayUrl,null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        ZshIntegralOrder zshIntegralOrder = new ZshIntegralOrder();
        zshIntegralOrder.setJsUserId("js103033074");
        System.out.println(ZshIntegralPayUtil.getIntegral(zshIntegralOrder));
    }
}
