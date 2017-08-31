<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/3/16
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>分页</title>
</head>
<body>
    <c:if test="${!empty pageResult}">
        <c:forEach var="user" items="${pageResult.datas}">
            姓名：${user.userName} &nbsp;&nbsp;手机号：${user.userPhone} &nbsp;&nbsp;邮箱：${user.userEmail} &nbsp;&nbsp;<br>
        </c:forEach>
    </c:if>
    <a href = "/web-ssm/user/showPage?pageNum=1" >首页</a>
    <a href = "/web-ssm/user/showPage?pageNum=${pageResult.pageNum-1}" >上一页</a>
    <a href = "/web-ssm/user/showPage?pageNum=${pageResult.pageNum+1}" >下一页</a>
    <a href = "/web-ssm/user/showPage?pageNum=${pageResult.totalPage}" >尾页</a>
    第${pageResult.pageNum}页/共${pageResult.totalPage}页
    <span><a href="/web-ssm/user/logout">登出</a></span>
</body>
</html>
