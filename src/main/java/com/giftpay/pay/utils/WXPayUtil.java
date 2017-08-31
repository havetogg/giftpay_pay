package com.giftpay.pay.utils;

import com.giftpay.pay.api.model.WCPreorder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public class WXPayUtil {
	// 申请支付
	public static final String _LAUNCH_PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// 订单查询
	public static final String _ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	// 关闭订单
	public static final String _CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	// 退款
	public static final String _REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	// 退款查询
	public static final String _REFUND_QUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	// 下载对账单
	public static final String _DOWNLOAD_BILL = "https://api.mch.weixin.qq.com/pay/downloadbill";

	public static String preOrder(WCPreorder wcPreorder,String key) {

		SortedMap<String, String> parameters = new TreeMap<String, String>();
		parameters.put("appid", wcPreorder.getAppid());
		parameters.put("body", wcPreorder.getBody());
		parameters.put("mch_id", wcPreorder.getMch_id());
		parameters.put("nonce_str", wcPreorder.getNonce_str());
		parameters.put("notify_url", wcPreorder.getNotify_url());
		parameters.put("openid", wcPreorder.getOpenid());
		parameters.put("out_trade_no", wcPreorder.getOut_trade_no());
		parameters.put("spbill_create_ip", wcPreorder.getSpbill_create_ip());
		parameters.put("total_fee", wcPreorder.getTotal_fee());
		parameters.put("trade_type", wcPreorder.getTrade_type());

		wcPreorder.setSign(createSign(parameters, key));

		XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
		xStream.autodetectAnnotations(true);
		String xml = xStream.toXML(wcPreorder);
		System.out.println(xml);
		return HttpUtil.sendPost(_LAUNCH_PAY_URL, "utf-8", xml);
	}


	public static Map<String, String> loadJavaScriptPayConfig(String appID, String prePayId, String key) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
		String timestamp = simpleDateFormat.format(new Date());
		String nonceStr = UUID.randomUUID().toString().replace("-", "");
		String packageStr = prePayId;
		SortedMap<String, String> parameters = new TreeMap<String, String>();
		parameters.put("appId", appID);
		parameters.put("timeStamp", timestamp);
		parameters.put("nonceStr", nonceStr);
		parameters.put("package", "prepay_id=" + packageStr);
		parameters.put("signType", "MD5");
		parameters.put("paySign", WXPayUtil.createSign(parameters, key));
		return parameters;
	}


	private static String createSign(SortedMap<String, String> parameters, String key) {
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = (Entry<String, String>) it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			sb.append(k + "=" + v + "&");
		}
		sb.append("key=" + key);
		System.out.println(sb);
		String sign = MD5X.getUpperCaseMD5For32(sb.toString());
		return sign;
	}

}
