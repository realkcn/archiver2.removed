<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="java.util.Date" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="UTF-8" session="false" %>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="include/htmlheader.jsp"/>
    <meta http-equiv="cache-control" content="max-age=120;">
    <meta http-equiv="expires" content="<%
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); //use iso8601 time format.....maybe change to rfc2616?
    format.setTimeZone(TimeZone.getTimeZone("UTC"));
    out.print(format.format(new Date(System.currentTimeMillis()+1000*120)));
%>">
    <meta http-equiv="description" content="水木社区版面列表">
    <link rel="stylesheet" type="text/css" href="${docbase}css/jquery-ui-1.10.4.custom.min.css"/>
    <script type="text/javascript" src="${docbase}js/jquery-ui-1.10.4.custom.min.js"></script>
    <title>水木社区归档站 - 版面列表</title>
</head>
<body onLoad='document.getElementById("boardsearch").focus()'>
<c:set var="pagedetail" value="版面列表" scope="request"/>
<jsp:include page="include/header.jsp"/>
<div class="container">
    <div class="control-group form-horizontal pull-left">
        <label class="control-label h4">快速查找版面</label>
        <input id="boardsearch" name="boardsearch" class="input-medium"/>
    </div>

    <section class="table-responsive">
        <table class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th>序号</th>
                <th>英文名</th>
                <th>中文名</th>
                <th>主题数</th>
                <th>文章数</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="boardarray" value=""/>
            <c:forEach var="board" varStatus="loopstatus" items="${boards}">
                <c:choose>
                    <c:when test="${loopstatus.count==1}">
                        <c:set var="boardarray"
                               value="{value:'${board.name} ${board.cname}',label:'${board.name} ${board.cname}',id:'${board.boardid}'}"/>
                    </c:when><c:otherwise>
                    <c:set var="boardarray"
                           value="${boardarray},{value:'${board.name} ${board.cname}',label:'${board.name} ${board.cname}',id:'${board.boardid}'}"/>
                </c:otherwise>
                </c:choose>
                <tr>
                    <td>${loopstatus.count}</td>
                    <td><a href="board-${board.boardid}.html">${board.name}</a></td>
                    <td><a href="board-${board.boardid}.html">${board.cname}</a></td>
                    <td>${board.threads}</td>
                    <td>${board.articles}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
</div>

<script>
    $(function () {
        var boardArray = [
            ${boardarray}
        ];

        $("#boardsearch").autocomplete({
            minLength: 1,
            source: boardArray,
            focus: function (event, ui) {
                $("#project").val(ui.item.label);
                return false;
            },
            select: function (event, ui) {
                window.location.href = "board-" + ui.item.id + ".html";
                return false;
            }
        })
                .data("autocomplete")._renderItem = function (ul, item) {
            return $("<li></li>")
                    .data("item.autocomplete", item)
                    .append("<a>" + item.value + "</a>")
                    .appendTo(ul);
        };
    });
</script>
<jsp:include page="include/footer.jsp"/>
</body>
</html>
