<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html>
<head>
  <title>GomokuGame | 登录</title>
  <link href="${pageContext.request.contextPath }/static/source/css/login.css" rel='stylesheet' type='text/css' />
  <script src="${pageContext.request.contextPath }/static/plugins/jquery/jquery-2.1.4.min.js"></script>
  <script src="${pageContext.request.contextPath }/static/plugins/layer/layer.js"></script>
</head>

<body>

<h1>GomokuGame</h1>
<div class="login-form">
  <div class="close"> </div>
  <div class="head-info">
    <label class="lbl-1"></label>
    <label class="lbl-2"></label>
    <label class="lbl-3"></label>
    <a href="<%=path%>/login.jsp"><span style="font-size:30px;padding-left:40px" ; font-align="left">登录</span></a>
    <a href="<%=path%>/user/toregister" ><span style="font-size:30px;padding-left:60px"; font-align="left" >注册</span></a>

  </div>
  <div class="clear"> </div>
  <div class="avtar"><img src="${pageContext.request.contextPath }/static/source/img/tou7.jpg" /></div>
  <form id="login-form" action="<%=path%>/user/login" method="post" onsubmit="return checkLoginForm()">
    <div class="key">
      <input type="text" id="userid" name="userid" placeholder="请输入账号" onblur="checkUser()">
    </div>

    <div class="key">
      <input type="password" id="password" name="password" placeholder="请输入密码">
<%--      <input type="password" id="password" name="password" placeholder="请输入密码" onblur="checkPwd()">--%>
    </div>

    <div class="signin">
      <input type="submit" id="submit">
    </div>
    <%
      String param = (String)session.getAttribute("pwdInfo");
      System.out.println("pwdInfo:"+param);
      if (param!=null){
        out.println("<script>$('#submit').attr('value','密码错误!').css('background','red')</script>");
      }else {
        out.println("<script>$('#submit').attr('value','login~')</script>");
      }
    %>
  </form>
</div>

<script type="text/javascript">
  function checkUser() {
    var f = false;
    var name = $('#userid').val().trim();
    if(name === ""){
      $('#submit').attr('value','请输入账号!!!').css('background','red');
    }else{
      $.ajax({
        url:"<%=path%>/user/checkLogin",
        data:{"userid":name},
        type:"post",
        dataType:"json",
        success:function (data) {
          if(data.msg ==="false"){
            $('#submit').attr('value', '用户不存在!').css('background', 'red');
          }else {
            f = true;
          }

        }
      });
    }
    return f ;
  }
  function checkPwd(){

    var f = false;
    var name = $('#userid').val().trim();
    var pwd = $('#password').val().trim();
      $.ajax({
        url:"<%=path%>/user/checkLoginPwd",
        data:JSON.stringify({"userid":name,"password":pwd}),
        type:"post",
        dataType:"json",
        contentType: "application/json",
        success:function (data) {
          if(data.msg ==="false"){
            $('#submit').attr('value', '密码错误!').css('background', 'red');
          }else {
            f = true;
          }

        }
      });
    return f ;
  }
  $(function(){
    $('.close').on('click', function(c){
      $('.login-form').fadeOut('slow', function(c){
        $('.login-form').remove();
      });
    });

    $('#userid,#password').change(function(){
      $('#submit').attr('value','Login').css('background','#3ea751');
    });
  });

  /**
   * check the login form before user login.
   * @returns {boolean}
   */
  function checkLoginForm(){
    $('#submit').attr('value','Login').css('background','#caebfb');
    var username = $('#userid').val();
    var password = $('#password').val();
    if(isNull(username) && isNull(password)){
      $('#submit').attr('value','请输入账号和密码!!!').css('background','red');
      return false;
    }
    // if(isNull(username)){
    //   $('#submit').attr('value','请输入账号!!!').css('background','red');
    //   return false;
    // }
    if(isNull(password)){
      $('#submit').attr('value','请输入密码!!!').css('background','red');
      return false;
    }
    else{
      $('#submit').attr('value','Logining~');
      return true;
    }
  }

  function isNull(input){
    if(input == null || input === '' || input === undefined){
      return true;
    }
    else{
      return false;
    }
  }
</script>
</body>
</html>