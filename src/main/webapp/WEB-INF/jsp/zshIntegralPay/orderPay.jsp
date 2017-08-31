<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		<link type="text/css" href="${pageContext.request.contextPath}/statics/zshIntegralPay/css/account.css" rel="stylesheet">
		<script type="text/javascript" src="${pageContext.request.contextPath}/statics/zshIntegralPay/js/common/jQuery-1.11.3.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/statics/zshIntegralPay/js/common/jWeChat-Adaptive.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/statics/zshIntegralPay/js/common/common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/statics/zshIntegralPay/js/common/m.tool.juxinbox.com.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/statics/zshIntegralPay/js/common/jWeChat-1.0.0.js"></script>
	</head>
	<body>
		<div class="zoomer">
			<div class="orderPay">
				<!--订单金额-->
				<div class="tip">
					<h1 class="rechargeName"></h1>
					<ul class="order">
						<li>
							<label>积分</label>
							<span class="payamount">${costIntegral}</span>
						</li>
					</ul>
				</div>
				<!--支付方式选择-->
				<div class="tip">
					<h1>支付方式</h1>
					<ul class="pay">
						<li class="jf">
							积分余额
							<input class="checked" type="radio" name="pay" value="zshIntegral" checked/>
							<label></label>
							<span class="zshIntegral">${integral}</span>
						</li>
					</ul>
				</div>
				<!--支付按钮-->
				<a class="toPay" href="javascript:;">立即支付</a>
			</div>
		</div>
	</body>
	<script>
        $(document).ready(function() {
			$(".toPay").click(function () {
                loading("start");
                $.ajax({
                    url:getRootPath()+"/zshIntegralPay/pay.htm",
                    type:"post",
                    data:{},
                    dataType:"json",
                    success:function(data){
                        loading("stop");
                        if(data.code == 0){
                            window.location.href = getRootPath()+"/zshIntegralPay/success.htm";
						}else{
                            alert("当前积分不足，请切换支付方式");
						}
                    }
                });
            })
        });
	</script>

</html>