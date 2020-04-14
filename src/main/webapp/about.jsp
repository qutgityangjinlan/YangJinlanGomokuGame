<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>GomokuGame | 关于</title>
    <jsp:include page="view/include/commonfile.jsp"/>
</head>
<body>
<jsp:include page="view/include/header.jsp"/>
<div class="am-cf admin-main">
    <jsp:include page="view/include/sidebar1.jsp"/>

    <!-- content start -->
    <div class="admin-content">
        <div class="am-cf am-padding">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">关于</strong> / <small>about</small></div>
        </div>
        <div class="am-tabs am-margin" data-am-tabs>
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active"><a href="#tab1">所用技术</a></li>
                <li><a href="#tab2">版本号</a></li>
                <li><a href="#tab3">更多网页游戏</a></li>
            </ul>
            <div class="am-tabs-bd">
                <div class="am-tab-panel am-fade am-in am-active" id="tab1">
                    <hr>
                    <blockquote>
                        <p>GomokuGame主要使用SSM框架,即Spring + Spring MVC + Mybatis</p>
                        <p>通讯使用的是websocket</p>
                        <p>数据库使用的是Mysql</p>
                        <p>前端框架采用的是<a href="http://amazeui.shopxo.net/" target="_blank">Amaze UI</a>,弹窗控件和分页控件采用的是<a href="https://layer.layui.com/" target="_blank">Layer</a>和<a href="https://www.layui.com/" target="_blank">Laypage</a></p>
                    </blockquote>
                </div>

                <div class="am-tab-panel am-fade am-in" id="tab2">
                    <hr>
                    <blockquote>
                        <p>2020</p>
                    </blockquote>
                </div>

                <div class="am-tab-panel am-fade am-in" id="tab3">
                    <hr>
                    <blockquote>
                        <p><a href="http://html5.huceo.com/">http://html5.huceo.com/</a></p>
                    </blockquote>
                </div>
            </div>
        </div>
        <!-- content end -->
    </div>
    <a href="#" class="am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}">
        <span class="am-icon-btn am-icon-th-list"></span>
    </a>
    <jsp:include page="view/include/footer.jsp"/>
</body>
</html>
