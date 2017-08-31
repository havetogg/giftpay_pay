<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/3/14
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>TestAjax</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/jquery-3.2.0.min.js"></script>
    <script>
        alert(1);
        $(function(){
            console.log(123);
            $.ajax({
                url:'showUser2',
                type:'POST', //GET
                async:false,    //或false,是否异步
                data:{
                    name:'yang',age:25
                },
                timeout:5000,    //超时时间
                dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
                success:function(data){
                    console.log(data);
                }
            });
        });

    </script>
</head>
<body>
    <h1>123</h1>
</body>
</html>
