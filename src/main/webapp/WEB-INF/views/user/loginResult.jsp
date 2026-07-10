<%--
  Created by IntelliJ IDEA.
  User: data8320-07
  Date: 26. 7. 10.
  Time: 오후 11:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>로그인 성공</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript" src="js/jquery-4.0.0.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function (){
            $("#btnSend").on("click",function (){
                location.href = "/html/index.html";
            })
        })
    </script>
</head>
<body>
<div class="divTable minimalistBlack">
    <div class="divTableRow">
        <div class="divTableCell">로그인된 사용자이름
        </div>
        <div class="divTableRow">
            <div class="divTableCell">로그인된 사용자아이디
            </div>
            <div class="divTableCell"><%ssUserId%> 입니다</div>
        </div>
    </div>
</div>
<div></div>
<button id="btnSend" type="button">메인 화면 이동</button>
</body>
</html>
