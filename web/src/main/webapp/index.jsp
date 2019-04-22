<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path;
%>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
<script>
       window.location.href="<%=basePath%>/mobile/#/?hyk=bd6775ce";
</script>
</html>
