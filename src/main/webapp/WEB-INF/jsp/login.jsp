<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/3/15
  Time: 10:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>登录</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/jquery-3.2.0.min.js"></script>
    <%--<script type="text/javascript" src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>--%>
    <script>
        $(document).ready(function() {
            $('#captchaImage').click(function()
            {
                $('#captchaImage').attr("src", "/web-pay/user/captcha?timestamp=" + (new Date()).valueOf());
            });
        });
        function login() {
            if($("#name").val()==""||$("#pwd").val()==""){
                alert("请输入用户名或者密码");
                return;
            }
            if($("#captcha").length>0){
                if($("#captcha").val().trim()==""){
                    alert("请输入验证码！");
                }
                $.ajax({
                    url:'captchaTest',
                    type:'POST', //GET
                    async:false,    //或false,是否异步
                    data:{
                        captcha:$("#captcha").val(),
                    },
                    timeout:5000,    //超时时间
                    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
                    success:function(data){
                        if(!data){
                            alert("验证码错误");
                            $('#captchaImage').attr("src", "/web-pay/user/captcha?timestamp=" + (new Date()).valueOf());
                        }else{
                            $("form").submit();
                        }
                    }
                });
            }else{
                $("form").submit();
            }
        }
    </script>
</head>
<body>
    <h2>登录</h2>
    <form name="teacForm" action="/web-ssm/user/login" method="post">
        <label>name:</label><input type="text" name="name" id="name"/><br/>
        <label>pawd:</label><input type="password" name="pwd" id="pwd"/><c:if test="${!empty errorMessage}"><label>${errorMessage}</label></c:if><br/>
        <c:if test="${errorTimes>=3}">
            <input type="text" id="captcha" name="captcha" maxlength="10"/>
            <img id="captchaImage" src="/web-ssm/user/captcha"/>
        </c:if>
        <label id="captchaMessage"/>
        <button type="button" onclick="login()">登录</button>
    </form>
</body>
</html>
