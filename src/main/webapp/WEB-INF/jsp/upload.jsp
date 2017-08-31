<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/3/16
  Time: 11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>上传图片</title>
</head>
<body>
<form action="/web-ssm/upload/uploadFile" method="post" enctype="multipart/form-data">
    <input type="file" name="file" /> <input type="submit" value="Submit" /></form>
</body>
</html>
