<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5, width=640, target-densitydpi=device-dpi">
		<meta http-eqiv="X-UA-Compatible" content="IE=Edge,chrome=1">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta content="telephone=no" name="format-detection">
		<title>订单确认</title>
		<link type="text/css" href="${pageContext.request.contextPath}/statics/weixinPay/css/account.css" rel="stylesheet">
		<script type="text/javascript" src="${pageContext.request.contextPath}/statics/weixinPay/js/common/jQuery-1.11.3.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/statics/weixinPay/js/common/jWeChat-Adaptive.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/statics/weixinPay/js/common/common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/statics/weixinPay/js/common/m.tool.juxinbox.com.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/statics/weixinPay/js/common/jWeChat-1.0.0.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/statics/weixinPay/js/weixinPayJS.js"></script>
	</head>
	<body>
		<div class="zoomer">
			<div class="orderPay">
				<!--订单金额-->
				<div class="tip">
					<h1 class="rechargeName"></h1>
					<ul class="order">
						<li>
							<label>金额</label>
							<span class="payamount">${totalFee}</span>
						</li>
					</ul>
					<div class="toPaid">
						待支付
						<span class="rmb paymoney"></span>
					</div>
				</div>
				<!--支付方式选择-->
				<div class="tip">
					<h1>支付方式</h1>
					<ul class="pay">
						<li class="wx">
							微信支付
							<input class="checked" type="radio" value="wxpay" name="pay" checked/>
							<label></label>
						</li>
					</ul>
				</div>
				<!--支付按钮-->
				<a class="toPay" href="javascript:;">立即支付</a>
			</div>
		</div>
	</body>
</html>