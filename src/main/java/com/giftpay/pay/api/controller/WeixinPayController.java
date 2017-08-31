package com.giftpay.pay.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.giftpay.pay.order.model.WeixinOrder;
import com.giftpay.pay.order.service.WeixinOrderService;
import com.giftpay.pay.utils.HttpClientUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 17:24 2017/8/21
 * @Modified By:
 */
@Controller
@RequestMapping(value = "/weixinPay", method = {RequestMethod.POST,RequestMethod.GET } )
public class WeixinPayController {

    private Logger logger = Logger.getLogger(WeixinPayController.class);

    @Autowired
    private WeixinOrderService weixinOrderService;

    @RequestMapping(value = "/success" , method = RequestMethod.POST )
    public String success(HttpServletRequest request, HttpServletResponse response, HttpSession session,Model model){
        WeixinOrder weixinOrder = (WeixinOrder)session.getAttribute("weixinOrder");
        if(weixinOrder!=null){
            model.addAttribute("weixinOrder",weixinOrder);
            session.removeAttribute("weixinOrder");
            return "weixinPay/success";
        }
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/getJsConfig" , method = RequestMethod.POST )
    public String getJsConfig(HttpServletRequest request, HttpServletResponse response, HttpSession session,Model model){
        WeixinOrder weixinOrder = (WeixinOrder)session.getAttribute("weixinOrder");
        if(weixinOrder!=null){
            String jsConfig = weixinOrder.getJs_config();
            return jsConfig;
        }
        return null;
    }


    @RequestMapping(value = "/notify" , method = RequestMethod.POST )
    public void notify(HttpServletRequest request, HttpServletResponse response, HttpSession session,Model model) throws Exception{
        logger.info("=================进入支付回调方法");
        net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(this.getResponseMap(request));
        //JSONObject jsonObject = JSON.parseObject("{\"transaction_id\":\"4007012001201708227606328819\",\"nonce_str\":\"vmqh3bxjr9zoib57zh1kezbtuhsljydd\",\"bank_type\":\"CFT\",\"openid\":\"o4FD4v_VSL8r87LR713h4s_ywo1Y\",\"sign\":\"E75429831305946D26B30F1FA2E08329\",\"fee_type\":\"CNY\",\"mch_id\":\"1426694102\",\"cash_fee\":\"1\",\"out_trade_no\":\"17\",\"appid\":\"wxee9d9ad775f75311\",\"total_fee\":\"1\",\"trade_type\":\"JSAPI\",\"result_code\":\"SUCCESS\",\"time_end\":\"20170822122839\",\"is_subscribe\":\"Y\",\"return_code\":\"SUCCESS\"}");
        logger.info("===========-=====回调支付信息:" + jsonObject);
        if (jsonObject.getString("return_code") != null && jsonObject.getString("return_code").equals("SUCCESS")) {
            if (jsonObject.getString("result_code") != null && jsonObject.getString("result_code").equals("SUCCESS")) {
                String wxMoney = jsonObject.getString("total_fee");
                String outTradeNo = jsonObject.getString("out_trade_no");
                logger.info("ouTradeNo为"+outTradeNo);
                WeixinOrder weixinOrder = new WeixinOrder();
                weixinOrder.setOut_trade_no(outTradeNo);
                List<WeixinOrder> weixinOrderList = weixinOrderService.listWeixinOrder(weixinOrder);
                logger.info("weixinOrderList为"+weixinOrderList);
                if(weixinOrderList.size()>0&&"0".equals(weixinOrderList.get(0).getStatus())){
                    logger.info("进入修改状态方法");
                    weixinOrderList.get(0).setStatus("1");
                    weixinOrderService.updateWeixinOrder(weixinOrderList.get(0));
                    String notifyUrl = getNotifyUrl(weixinOrderList.get(0).getReNotifyUrl(),weixinOrderList.get(0));
                    logger.info("notifyUrl为"+notifyUrl);
                    try{
                        HttpClientUtil.doHttpsPost(notifyUrl,null);
                    }
                    finally {
                        logger.info("执行httpGet请求结束:"+notifyUrl);
                    }

                }
            }
        }
    }

    private static Map<String, String> getResponseMap(HttpServletRequest request) throws IOException, DocumentException {
        Map<String, String> map = new HashMap<String, String>();
        InputStream inputStream = request.getInputStream();
        if (inputStream == null) {
            return null;
        }
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for (Element e : elementList)
            map.put(e.getName(), e.getText());
        inputStream.close();
        inputStream = null;
        return map;
    }


    private static String getNotifyUrl(String url,WeixinOrder weixinOrder){
        StringBuffer stringBuffer = new StringBuffer(url);
        if(url.indexOf("?")>0){
            stringBuffer.append("&pay=success");
            stringBuffer.append("&type=weixin");
            stringBuffer.append("&amount="+weixinOrder.getTotal_fee());
        }else{
            stringBuffer.append("?pay=success");
            stringBuffer.append("&type=weixin");
            stringBuffer.append("&amount="+weixinOrder.getTotal_fee());
        }
        return stringBuffer.toString();
    }
}