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
  <div class="avtar"><img src="${pageContext.request.contextPath }/static/source/img/tou2.jpg" /></div>
  <form id="login-form" action="<%=path%>/user/login" method="post" onsubmit="return checkLoginForm()">
    <div class="key">
      <input type="text" id="username" name="userid" placeholder="请输入账号" >
    </div>

    <div class="key">
      <input type="password" id="password" name="password" placeholder="请输入密码">
    </div>

    <div class="signin">
      <input type="submit" id="submit">
    </div>
    <%
      String param = (String)session.getAttribute("errorInfo");
      if (param!=null){
        out.println("<script>$('#submit').attr('value','用户名密码错误!').css('background','red')</script>");
      }else {
        out.println("<script>$('#submit').attr('value','login~')</script>");
      }
    %>
  </form>
</div>

<script type="text/javascript">

  function refreshCode(){
    document.getElementById("code").src = "validateCode?" + Math.random();
  }

  $(function(){
    <c:if test="${not empty param.timeout}">
      layer.msg('连接超时,请重新登陆!', {
        offset: 0,
        shift: 6
      });
    </c:if>

    if("${error}"){
      $('#submit').attr('value',"${error}").css('background','red');
    }
    if("${registersuccess}"){
    	 layer.msg('注册成功', {
    	        offset: 0,
    	      });
      }
    if("${message}"){
      layer.msg('${message}', {
        offset: 0,
      });
    }

    $('.close').on('click', function(c){
      $('.login-form').fadeOut('slow', function(c){
        $('.login-form').remove();
      });
    });

    $('#username,#password').change(function(){
      $('#submit').attr('value','Login').css('background','#3ea751');
    });
  });

  /**
   * check the login form before user login.
   * @returns {boolean}
   */
  function checkLoginForm(){
    var username = $('#username').val();
    var password = $('#password').val();
    if(isNull(username) && isNull(password)){
      $('#submit').attr('value','请输入账号和密码!!!').css('background','red');
      return false;
    }
    if(isNull(username)){
      $('#submit').attr('value','请输入账号!!!').css('background','red');
      return false;
    }
    if(isNull(password)){
      $('#submit').attr('value','请输入密码!!!').css('background','red');
      return false;
    }
    else{
      $('#submit').attr('value','Logining~');
      return true;
    }
  }

  /**
   * check the param if it's null or '' or undefined
   * @param input
   * @returns {boolean}
   */
  function isNull(input){
    if(input == null || input == '' || input == undefined){
      return true;
    }
    else{
      return false;
    }
  }
</script>
</body>
</html>