package com.giftpay.pay.api.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.giftpay.pay.api.service.WeixinPayService;
import com.giftpay.pay.api.service.ZshIntegralPayService;
import com.giftpay.pay.common.result.CommonResult;
import com.giftpay.pay.order.model.Order;
import com.giftpay.pay.order.model.WeixinOrder;
import com.giftpay.pay.order.model.ZshIntegralOrder;
import com.giftpay.pay.order.service.OrderService;
import com.giftpay.pay.order.service.WeixinOrderService;
import com.giftpay.pay.order.service.ZshIntegralOrderService;
import com.giftpay.pay.utils.AESUtil;
import com.giftpay.pay.utils.DateUtil;
import com.giftpay.pay.utils.ZshIntegralPayUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 11:45 2017/8/17
 * @Modified By:
 */
@Controller
@RequestMapping(value = "/api", method = {RequestMethod.POST,RequestMethod.GET } )
public class ApiController {

    private static final Logger logger = Logger.getLogger(ApiController.class);

    @Value(value = "#{configProperties['orderKey']}")
    private String key;

    @Autowired
    private OrderService orderService;

    @Autowired
    private WeixinOrderService weixinOrderService;

    @Autowired
    private WeixinPayService weixinPayService;

    @Autowired
    private ZshIntegralOrderService zshIntegralOrderService;

    @Autowired
    private ZshIntegralPayService zshIntegralPayService;

    /**
     * 支付控制Controller
     */
    @ResponseBody
    @RequestMapping(value = "/getPayApi" , method = RequestMethod.POST )
    public String getPayApi(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                          @RequestParam(value = "pay_type" , required = true)String payType,
                                          @RequestParam(value = "pay_mode" , required = true)String payMode){
        JSONObject returnObj = new JSONObject();
        if("0".equals(payType)){
            CommonResult commonResult = null;
            //单一支付模式
            switch(payMode){
                case "weixin":
                    String weiChatStr = request.getParameter("weixin");
                    if(!StringUtils.isEmpty(weiChatStr)){
                        commonResult = weixinPayService.preOrder(weiChatStr);
                    }else{
                        commonResult = CommonResult.getInstance(10001,"缺少参数weichat参数");
                    }
                    break;
                case "zsh_integral":
                    String zshIntegralStr = request.getParameter("zsh_integral");
                    if(!StringUtils.isEmpty(zshIntegralStr)){
                        commonResult = zshIntegralPayService.preOrder(zshIntegralStr);
                    }else{
                        commonResult = CommonResult.getInstance(10001,"缺少参数zshIntegral参数");
                    }

                    break;
                case "yicode":break;
                case "giftpay_balance":break;
                case "zsh_balance":break;
                default:break;
            }

            if(commonResult.getCode()==0){
                logger.error("commRsult为:"+commonResult);
                Order order = new Order();
                //order.setUserId(String.valueOf(session.getAttribute("userId")));
                order.setUserId("123");
                order.setPayType("0");
                order.setPayMode(((JSONObject)commonResult.getMessage()).getString("payMode"));
                order.setRefId(((JSONObject)commonResult.getMessage()).getString("refId"));
                order.setCreateTime(DateUtil.get4yMdHms(new Date()));
                orderService.insertOrder(order);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("orderId",order.getId());
                return JSON.toJSONString(CommonResult.getInstance(0,jsonObject));
            }else{
                return JSON.toJSONString(commonResult);
            }
        }else {
            String[] payModes = payMode.split(",");
            List<CommonResult> commonResults = new ArrayList<>();
            for(String s:payModes){
                CommonResult commonResult = null;
                switch(s){
                    case "weixin":
                        String weiChatStr = request.getParameter("weixin");
                        if(!StringUtils.isEmpty(weiChatStr)){
                            commonResult = weixinPayService.preOrder(weiChatStr);
                        }else{
                            commonResult = CommonResult.getInstance(10001,"缺少参数weichat参数");
                        }
                        break;
                    case "zsh_integral":
                        String zshIntegralStr = request.getParameter("zsh_integral");
                        if(!StringUtils.isEmpty(zshIntegralStr)){
                            commonResult = zshIntegralPayService.preOrder(zshIntegralStr);
                        }else{
                            commonResult = CommonResult.getInstance(10001,"缺少参数zshIntegral参数");
                        }

                        break;
                    case "yicode":break;
                    case "giftpay_balance":break;
                    case "zsh_balance":break;
                    default:break;
                }
                if(commonResult.getCode()==0){
                    commonResults.add(commonResult);
                }
            }
            if(commonResults.size()>0){
                Order order = new Order();
                //order.setUserId(String.valueOf(session.getAttribute("userId")));
                order.setUserId("123");
                order.setPayType("1");
                StringBuffer mulMode = new StringBuffer();
                StringBuffer mulRefId = new StringBuffer();
                for(CommonResult commonResult:commonResults){
                    mulMode.append(((JSONObject)commonResult.getMessage()).getString("payMode")+",");
                    mulRefId.append(((JSONObject)commonResult.getMessage()).getString("refId")+",");
                }
                order.setPayMode(mulMode.toString().substring(0,mulMode.toString().length()-1));
                order.setRefId(mulRefId.toString().substring(0,mulRefId.toString().length()-1));
                order.setCreateTime(DateUtil.get4yMdHms(new Date()));
                orderService.insertOrder(order);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("orderId",order.getId());
                return JSON.toJSONString(CommonResult.getInstance(0,jsonObject));
            }else{
                return JSON.toJSONString(CommonResult.getInstance(10006,"支付方式获取失败"));
            }
        }
    }

    /**
     * 跳转到微信支付
     */
    @RequestMapping(value = "/toPay")
    public String toPay(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                    @RequestParam(value = "orderId" , required = true)String orderId,
                                    Model model) throws Exception{
        orderId = AESUtil.AESDncode(key,orderId);
        Order order = orderService.getOrderById(orderId);
        if(order != null){
            if("0".equals(order.getPayType())){
                switch(order.getPayMode()){
                    case "weixin":
                        String weiChatStr = order.getRefId();
                        if(!StringUtils.isEmpty(weiChatStr)){
                            WeixinOrder weixinOrder = weixinOrderService.getWeixinOrderById(weiChatStr);
                            if(weixinOrder !=null&&"0".equals(weixinOrder.getStatus())){
                                session.removeAttribute("weixinOrder");
                                session.setAttribute("weixinOrder",weixinOrder);
                                logger.error("session的weinxinOrder为:"+weixinOrder.getId());
                                Double totalFee = Double.parseDouble(weixinOrder.getTotal_fee())/100;
                                model.addAttribute("totalFee",totalFee);
                                return "weixinPay/weixinOrderPay";
                            }
                        }else{
                            return null;
                        }
                        break;
                    case "zsh_integral":
                        String zshIntegralStr = order.getRefId();
                        ZshIntegralOrder zshIntegralOrder = zshIntegralOrderService.getZshIntegralOrderById(zshIntegralStr);
                        if(zshIntegralOrder !=null&&"0".equals(zshIntegralOrder.getStatus())){
                            session.removeAttribute("zshIntegralOrder");
                            session.setAttribute("zshIntegralOrder",zshIntegralOrder);
                            String costIntegral = zshIntegralOrder.getCostIntegral();
                            model.addAttribute("costIntegral",costIntegral);
                            return "zshIntegralPay/orderPay";
                        }
                        break;
                    case "yicode":break;
                    case "giftpay_balance":break;
                    case "zsh_balance":break;
                    default:break;
                }
            }else{
                boolean isPay = false;
                String[] payModes = order.getPayMode().split(",");
                String[] refIds = order.getRefId().split(",");
                for(int i=0;i<payModes.length;i++){
                    switch(payModes[i]){
                        case "weixin":
                            String weiChatStr = refIds[i];
                            if(!StringUtils.isEmpty(weiChatStr)){
                                WeixinOrder weixinOrder = weixinOrderService.getWeixinOrderById(weiChatStr);
                                if(weixinOrder !=null&&"0".equals(weixinOrder.getStatus())){
                                    session.removeAttribute("weixinOrder");
                                    session.setAttribute("weixinOrder",weixinOrder);
                                    logger.error("session的weinxinOrder为:"+weixinOrder.getId());
                                    Double totalFee = Double.parseDouble(weixinOrder.getTotal_fee())/100;
                                    model.addAttribute("totalFee",totalFee);
                                }
                                if(weixinOrder !=null&&"1".equals(weixinOrder.getStatus())){
                                    isPay = true;
                                }
                            }
                            break;
                        case "zsh_integral":
                            String zshIntegralStr = refIds[i];
                            ZshIntegralOrder zshIntegralOrder = zshIntegralOrderService.getZshIntegralOrderById(zshIntegralStr);
                            if(zshIntegralOrder !=null&&"0".equals(zshIntegralOrder.getStatus())){
                                session.removeAttribute("zshIntegralOrder");
                                session.setAttribute("zshIntegralOrder",zshIntegralOrder);
                                String costIntegral = zshIntegralOrder.getCostIntegral();
                                model.addAttribute("costIntegral",costIntegral);
                                String resultStr = ZshIntegralPayUtil.getIntegral(zshIntegralOrder);
                                JSONObject jsonObject = JSON.parseObject(resultStr);
                                String integral = jsonObject.getJSONObject("data").getString("integralBalance");
                                model.addAttribute("integral",integral);
                            }
                            if(zshIntegralOrder !=null&&"1".equals(zshIntegralOrder.getStatus())){
                                isPay = true;
                            }
                            break;
                        case "yicode":break;
                        case "giftpay_balance":break;
                        case "zsh_balance":break;
                        default:break;
                    }
                }
                if(!isPay){
                    return "mulPay/mulOrderPay";
                }
            }
        }else{
            return null;
        }
        return null;
    }
}
