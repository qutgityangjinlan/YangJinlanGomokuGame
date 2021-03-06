<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html>
<head>
    <title>五子棋游戏平台 | 登录</title>
    <link href="${pageContext.request.contextPath }/static/source/css/login.css" rel='stylesheet' type='text/css'/>
    <script src="${pageContext.request.contextPath }/static/plugins/jquery/jquery-3.4.1.js"></script>
    <script src="${pageContext.request.contextPath }/static/plugins/jquery/jquery-2.1.4.min.js"></script>
    <script src="${pageContext.request.contextPath }/static/plugins/layer/layer.js"></script>
</head>

<body>

<h1>欢迎注册五子棋游戏平台账号</h1>
<div class="login-form">
    <div class="close"></div>
    <div class="head-info">
        <label class="lbl-1"></label>
        <label class="lbl-2"></label>
        <label class="lbl-3"></label>
        <a href="<%=path%>/login.jsp"><span style="font-size:30px" ; font-align="left">登录</span></a>
    </div>
    <div class="clear"></div>
    <div class="avtar"><img src="${pageContext.request.contextPath }/static/source/img/tou1.png"/></div>
    <form action="<%=path%>/user/register" method="post" onsubmit="return checkRegisterForm()">
        <div class="key">
            <input type="text" id="userid" name="userid" placeholder="账号" onblur=checkUser()>
        </div>
        <div class="key">
            <input type="password" id="password" name="password" placeholder="密码">
        </div>
        
        <div class="signin">
            <input type="submit" id="submit" value="注册">
        </div>
    </form>
</div>

<script>
    function checkUser() {
        var f = false;
        var name = $('#userid').val().trim();
        if(name === ""){
            $('#submit').attr('value', '请输入账号!!!').css('background', 'red');
        }else{
            $.ajax({
                url:"<%=path%>/user/checkRegister",
                data:{"userid":name},
                type:"post",
                dataType:"json",
                success:function (data) {
                    if(data.msg ==="false"){
                        $('#submit').attr('value', '用户名已存在!请直接登录').css('background', 'red');
                    }else {
                        $('#submit').attr('value', '注册').css('background', 'pink');
                        f = true;
                    }

                }
            });
        }
        return f ;
    }

    function checkRegisterForm() {
        var userid = $('#userid').val();
        var password = $('#password').val();

        if (isNull(userid) && isNull(password)) {
            $('#submit').attr('value', '请输入账号和密码!!!').css('background', 'red');
            return false;
        }
        // if (isNull(userid)) {
        //     $('#submit').attr('value', '请输入账号!!!').css('background', 'red');
        //     return false;
        // }
        if (isNull(password)) {
            $('#submit').attr('value', '请输入密码!!!').css('background', 'red');
            return false;
        }
        else {
            $('#submit').attr('value', '注册成功,去登录~');
            return true;
        }
    }

    /**
     * check the param if it's null or '' or undefined
     * @param input
     * @returns {boolean}
     */
    function isNull(input) {
        if (input == null || input === '' || input === undefined) {
            return true;
        } else {
            return false;
        }
    }
</script>
</body>
</html>