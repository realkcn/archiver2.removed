<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" session="false" %>
<%@ page import="org.kbs.sso.client.SSOFilter" %>
<%@ page import="org.kbs.sso.principal.AttributePrincipal" %>
<%@ page import="org.apache.commons.lang3.StringEscapeUtils" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="#request.docbase==null">
    <c:set var="docbase" value="'./'" scope="request"/>
</c:if>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">导航</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <ul class="nav navbar-nav">
                <li class="active"><a class="navbar-brand" href="http://www.newsmth.net">水木社区主站</a></li>
                <li><a href="${request.docbase}">归档站首页</a></li>
                <li><a href="${request.docbase}boards.html">版面列表</a></li>
                <li><a href="${request.docbase}search.jsp">搜索</a></li>
                <li>
                    <%
                        AttributePrincipal principal = SSOFilter.getPrincipal(request);
                        if (principal != null) {
                    %><a href="#"><%
                    String nickname = (String) principal.get("nickname");
                    if (!StringUtils.isEmpty(nickname))
                        out.print(StringEscapeUtils.escapeHtml4(nickname));
                    else
                        out.print("无名氏");
                %>，你好&nbsp;|</a></li>
                <li class="active"><a href="${request.docbase}user/logout.jsp">注销</a>
                    <%
                    } else {
                    %>
                    <a href="${request.docbase}user/login.jsp">登录</a>
                    <% } %>
                </li>
            </ul>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <form class="navbar-form navbar-right" action="${request.docbase}searchArticle.do" method="GET">
                    <input class="search" placeholder="" id="search_input" type="text" value="" name="body"/>
                    <input class="btn" type="submit" value="搜索"/></form>
                </form>
            </ul>
        </div>
        <!--/.nav-collapse -->
    </div>
</div>
<c:if test="${requestScope.nobreadcrumbs==null}">
    <div class="container">
        <ul class="breadcrumb">
            <a href="${request.docbase}">首页</a> <span class="divider">/</span>
            <c:if test="${requestScope.board}!=null">
                <a href="${request.docbase}boards.html">版面列表</a> <span class="divider">/</span>
                <a href="${request.docbase}board-${board.boardid}.html">${board.cname}</a> <span
                    class="divider">/</span>
            </c:if>
                ${request.pagedetail}
                <c:if test="board!=null"><a style="float: right"
                                            href="http://www.newsmth.net/nForum/board/${board.name}" target="_blank">回主站[${board.cname}]版</a>
            </c:if>
        </ul>
    </div>
</c:if>
