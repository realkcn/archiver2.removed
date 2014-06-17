<%--
  User: kcn
  Date: 14-6-17
  Time: 下午3:53
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>error page</title>
</head>
<body style="margin: 0;padding: 0;background-color: #f5f5f5;">
<div id="center-div">
    <table style="height: 100%; width: 600px; text-align: center;">
        <tr>
            <td>
                <%= exception.getMessage()%>
                <a href="javascript:history.go(-1);">返回</a>!!!
            </td>
        </tr>
    </table>
</div>
</body>
</html>
