<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>五子棋游戏平台 | 帮助</title>
    <jsp:include page="view/include/commonfile.jsp"/>
    <style>
        html, body{
            height:80%;;
        }
    </style>
</head>
<body>
<jsp:include page="view/include/header.jsp"/>
<div class="am-cf admin-main">
    <jsp:include page="view/include/sidebar.jsp"/>

    <!-- content start -->
    <div class="admin-content">
        <div class="am-cf am-padding">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">帮助</strong></div>
        </div>
        <div class="am-tabs am-margin" data-am-tabs>
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active"><a href="#tab1">帮助</a></li>
            </ul>
            <div class="am-tabs-bd">
                <div class="am-tab-panel am-fade am-in am-active" id="tab1">
                    <h3>欢迎加入Gomuku游戏平台</h3>
                    聊天：你可以通过发送框群发您的消息，也可以点击用户列表上其他用户后面的私聊按钮，与其他用户私聊，当然您也可以邀请机器人进行对话。<br>
                    <br>
                    个人信息：在个人信息功能栏，您可以查看您的个人信息<br>  <br>
                    设置：通过个人设置，您可以修改您的个人信息；通过系统设置，您可以修改日志显示行数以及信息保密性和修改您的登录密码<br>  <br>
                    五子棋：在人机对战五子棋，当您点击棋盘，落下棋子，游戏就开始了。在游戏过程中，您可以点击悔棋，也可以重新开始游戏；在人人对战五子棋，您可以在五子棋大厅与玩友交流游戏心得
                    ，也可以加入房间进行对战哦，房间满两人便可以开始游戏了，您也可以邀请其他人观战哦。在游戏过程中，您可以选择和棋，退出房间和重新游戏。<br>  <br>
                    注销：通过注销功能栏，您可以注销当前用户，退到登录界面。

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
