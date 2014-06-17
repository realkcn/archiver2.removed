<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="java.util.Date" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="UTF-8" session="false" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


    <meta http-equiv="cache-control" content="max-age=120;">
    <meta http-equiv="expires" content="<%
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); //use iso8601 time format.....maybe change to rfc2616?
    format.setTimeZone(TimeZone.getTimeZone("UTC"));
    out.print(format.format(new Date(System.currentTimeMillis()+1000*120)));
%>">
    <meta http-equiv="description" content="水木社区版面列表">

    <jsp:include page="include/htmlheader.jsp"/>
    <title>水木社区归档站 - 版面列表</title>
</head>
<body onLoad='document.getElementById("boardsearch").focus()'>
<c:set var="pagedetail" value="'版面列表'" scope="request"/>
<jsp:include page="include/header.jsp"/>
<div class="container-fluid">
    <div class="table-responsive">
        <table class="table table-striped">
            <c:forEach var="board" items="${boards}">
                <tr>
                    <td>${board.cname}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<jsp:include page="include/footer.jsp"/>
</body>
</html>
