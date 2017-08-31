<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="initial-scale=0.5,maximum-scale=0.5,minimum-scale=0.5, width=640, target-densitydpi=device-dpi">
    <meta http-eqiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <title>支付结果</title>
    <link type="text/css" href="${pageContext.request.contextPath}/statics/zshIntegralPay/css/account.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/zshIntegralPay/js/common/jQuery-1.11.3.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/zshIntegralPay/js/common/common.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/zshIntegralPay/js/common/m.tool.juxinbox.com.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/zshIntegralPay/js/common/jWeChat-Adaptive.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/zshIntegralPay/js/Scratch.js"></script>
</head>

<script>
    $(function () {
        var backUrl = '${zshIntegralOrder.backUrl}';
        $(".toHomePage").on("click", function () {
            if (backUrl != '') {
                window.location.href = backUrl;
            }
        });
        //initAdvertise();
    })
    /*function initAdvertise(){
        $.ajax({
            url: getRootPath() + "/giftpay/commonSetting/queryRandomAdvertise.htm",
            type: "post",
            dataType: "json",
            success: function(data) {
                console.log(data);
                $(".index_content6").attr("itemHref",data[0].advertiseHref);
                $(".index_content6").attr("itemId",data[0].advertiseId);
                $(".index_content6").html('<img src="'+data[0].advertiseImgUrl+'" alt="" width="100%">');
                $(".index_content6").on("click",function(){
                    $.ajax({
                        url: getRootPath() + "/giftpay/commonSetting/updateAdverClickNumOL.htm",
                        type: "post",
                        data: {"id": $(".index_content6").attr("itemId")},
                        dataType: "json",
                        success: function (res) {
                            console.log(res);
                            if(res.code=='1'){
                                location.href=$(".index_content6").attr("itemHref");
                            }
                        }
                    });
                });
            }
        });
    }*/
</script>

<body>
<div class="zoomer">
    <div class="success">
        <header>
            <img src="${pageContext.request.contextPath}/statics/zshIntegralPay/img/success.png"/>
            <p>订单支付成功</p>
            <a class="toHomePage" href="javascript:;">前往首页</a>
        </header>
        <ul class="recharge">
            <li>
                <label>产品名称:</label>
                <span class="name">积分消费</span>
            </li>
            <li>
                <label>付款方式:</label>
                <span class="type">中石化积分</span>
            </li>
            <li>
                <label>实付积分:</label>
                <span class="rmb">${zshIntegralOrder.costIntegral}</span>
            </li>
            <li>
                <label>下单时间:</label>
                <span class="time">${zshIntegralOrder.createTime}</span>
            </li>
        </ul>

        <div class="htmleaf-container">
            <div class="max-width">
                <div class="scratch_container">
                    <div class="scratch_viewport">
                        <a id="getRed">

                        </a>
                        <canvas id="js-scratch-canvas"></canvas>
                    </div>
                </div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/statics/zshIntegralPay/js/Scratch.js"></script>
        <script type="text/javascript">

            var scratch = new Scratch({
                canvasId: 'js-scratch-canvas',
                imageBackground: './img/ggbg_prize.png',
                imageBackgroundNone: './img/ggbg_none.png',
                pictureOver: './img/ggbg.png',
                cursor: {
//                png: './images/piece.png',
//                cur: './images/piece.cur',
                    x: '20',
                    y: '17'
                },
                radius: 30,
                nPoints: 100,
                percent: 30,
                callback: function () {
                },
                pointSize: { x: 3, y: 3}
            });
        </script>
        <div class="index_content12">
            <ul class="flex">
                <li class="flex-1 tuijian_line"></li>
                <li class="tuiguang_">推广</li>
                <li class="flex-1 tuijian_line"></li>
            </ul>
        </div>

       <!-- <a class="index_content6" href="http://m.linkgift.cn/index.html">
            <img src="img/shopping.jpg" alt="" width="100%">
        </a>-->
        <a class="index_content6">
        </a>
        <div class="jmt_center pay_bottom_tel">
            <a  href="tel:4000808065">推广合作</a>
        </div>
    </div>
</div>
</body>
</html>