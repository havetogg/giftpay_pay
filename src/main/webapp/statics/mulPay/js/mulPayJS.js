/**
 * Created by Administrator on 2017/8/22.
 */
var payMode = "";
$(document).ready(function() {
    $(".order li").eq(0).show();
    $(".pay li").eq(0).find("input").addClass("checked");
    $(".pay li").eq(0).find("input").attr("checked", "checked");
    payMode = $(".pay li").eq(0).find("input").val();
    $(".pay li").on("click", function() {
        if(!$(this).hasClass("no")) {
            payMode = $(this).find("input").val();
            $(".order li").hide();
            $("."+payMode).show();
            $(".pay li").find("input").removeClass("checked");
            $(".pay li").find("input").attr("checked", false);
            $(this).find("input").addClass("checked");
            $(this).find("input").attr("checked", "checked");
        }
    })
    $(".toPay").click(function () {
        if(payMode=="zshIntegral"){
            chooseOilPay();
        }else if(payMode=="wxPay"){
            chooseWeixinPay();
        }
     })
});
function chooseOilPay() {
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
}
function chooseWeixinPay() {
    loading("start");
    $.ajax({
        url: getRootPath() + "/weixinPay/getJsConfig",
        type: "post",
        data: {},
        dataType: "json",
        success: function (data) {
            loading("stop");
            weixinPay(data);
        }
    })
}
function weixinPay(payResult) {
    wx.config({
        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: payResult.appId, // 必填，公众号的唯一标识
        timestamp: payResult.timeStamp, // 必填，生成签名的时间戳
        nonceStr: payResult.nonceStr, // 必填，生成签名的随机串
        signature: payResult.paySign, // 必填，签名，见附录1
        jsApiList: ['chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    });
    wx.ready(function() {
        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
        wx.chooseWXPay({
            timestamp: payResult.timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
            nonceStr: payResult.nonceStr, // 支付签名随机串，不长于 32 位
            package: payResult.package, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
            signType: payResult.signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
            paySign: payResult.paySign, // 支付签名
            success: function(res) {
                window.location.href = getRootPath()+"/weixinPay/success.htm";
            },
            cancel: function(res) {//取消支付
                loading("stop");
            },
            fail:function(res){//支付失败
                loading("stop");
            }
        });
    });
}