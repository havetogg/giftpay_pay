<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/3/17
  Time: 13:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>获取accessToken</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/jquery-3.2.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/sha1.js"></script>
    <script>
        function getAssessToken(){
            if($("#userId").val()!=""){
                var stringArray = [];
                stringArray[0] = $("#userId").val();
                stringArray[1] = getRandomString(8);
                stringArray.sort();
                var stringConcat = stringArray[0]+stringArray[1];
                var sha = hex_sha1(stringConcat);
                $.ajax({
                    url:'encryption/getAssessToken',
                    type:'POST', //GET
                    async:false,    //或false,是否异步
                    data:{
                        uId:stringArray[0],
                        randomString:stringArray[1],
                        sha:sha
                    },
                    timeout:5000,    //超时时间
                    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
                    success:function(data){
                        console.log(data);
                    }
                });
            }
        }
        function getRandomString(len) {
            len = len || 32;
            var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'; // 默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1
            var maxPos = $chars.length;
            var pwd = '';
            for (var i = 0; i < len; i++) {
                pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
            }
            return pwd;
        }

    </script>
</head>
<body>
    <c:if test="${!empty user}">
        <label>欢迎你:</label><label id="userName">${user.userName}</label>
        <input type="text" id="userId" value="${user.id}" style="display:none;"/>
        <button type="button" onclick="getAssessToken()">获取assessToken</button>
    </c:if>
</body>
</html>
