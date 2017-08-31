package com.giftpay.pay.api.controller;

import com.alibaba.fastjson.JSON;
import com.giftpay.pay.common.result.CommonResult;
import com.giftpay.pay.order.model.WeixinOrder;
import com.giftpay.pay.order.model.ZshIntegralOrder;
import com.giftpay.pay.order.service.ZshIntegralOrderService;
import com.giftpay.pay.utils.HttpClientUtil;
import com.giftpay.pay.utils.ZshIntegralPayUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 15:07 2017/8/21
 * @Modified By:
 */
@Controller
@RequestMapping(value = "/zshIntegralPay", method = {RequestMethod.POST,RequestMethod.GET } )
public class ZshIntegralPayController {

    private Logger logger = Logger.getLogger(ZshIntegralPayController.class);

    @Autowired
    private ZshIntegralOrderService zshIntegralOrderService;

    @ResponseBody
    @RequestMapping(value = "/pay" , method = RequestMethod.POST )
    public String getPayApi(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
        CommonResult commonResult = null;
        ZshIntegralOrder zshIntegralOrder = (ZshIntegralOrder)session.getAttribute("zshIntegralOrder");
        if(zshIntegralOrder!=null&&"0".equals(zshIntegralOrder.getStatus())){
            String result = ZshIntegralPayUtil.doOrder(zshIntegralOrder);
            if(!StringUtils.isEmpty(result)&&JSON.parseObject(result).getBoolean("result")==true){
                zshIntegralOrder.setStatus("1");
                zshIntegralOrderService.updateZshIntegralOrder(zshIntegralOrder);
                String notifyUrl = getNotifyUrl(zshIntegralOrder.getNotifyUrl(),zshIntegralOrder);
                logger.info("notifyUrl为"+notifyUrl);
                try{
                    HttpClientUtil.doHttpsPost(notifyUrl,null);
                }
                finally {
                    logger.info("执行httpGet请求结束:"+notifyUrl);
                    commonResult = CommonResult.getInstance(0,null);
                    return JSON.toJSONString(commonResult);
                }
            }else{
                commonResult = CommonResult.getInstance(10005,"积分支付失败");
            }
        }else{
            commonResult = CommonResult.getInstance(10005,"获取订单信息失败");
        }
        return JSON.toJSONString(commonResult);
    }

    @RequestMapping(value = "/success" , method = RequestMethod.POST )
    public String success(HttpServletRequest request, HttpServletResponse response, HttpSession session,Model model){
        ZshIntegralOrder zshIntegralOrder = (ZshIntegralOrder)session.getAttribute("zshIntegralOrder");
        if(zshIntegralOrder!=null&&"1".equals(zshIntegralOrder.getStatus())){
            model.addAttribute("zshIntegralOrder",zshIntegralOrder);
            return "zshIntegralPay/success";
        }
        return null;
    }

    private static String getNotifyUrl(String url,ZshIntegralOrder zshIntegralOrder){
        StringBuffer stringBuffer = new StringBuffer(url);
        if(url.indexOf("?")>0){
            stringBuffer.append("&pay=success");
            stringBuffer.append("&type=zshIntegral");
            stringBuffer.append("&amount="+zshIntegralOrder.getCostIntegral());
        }else{
            stringBuffer.append("?pay=success");
            stringBuffer.append("&type=zshIntegral");
            stringBuffer.append("&amount="+zshIntegralOrder.getCostIntegral());
        }
        return stringBuffer.toString();
    }
}
