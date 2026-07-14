<%--
  Created by IntelliJ IDEA.
  User: data8320-07
  Date: 26. 7. 14.
  Time: 오후 4:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>채팅방 입장 및 채팅 리스트</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript" src="/js/jquery-4.0.0.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {

            getRoomList();

            setInterval("getRoomList()",5000);
        })
        function getRoomList() {
            $.ajax({
                url: "/chat/roomList",
                type: "post",
                dataType: "JSON",
                sucess: function (json) {

                    $("#room_list").empty();

                    for (let i = 0; i < json.length; i++) {
                        $("#room_list").append(json[i] + "<br/>");
                    }
                }
            })
        }
    </script>
</head>
<body>
<hi>채팅방 전체 리스트</hi>
<div class="divTable minimalistBlack">
    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableHead">대화가능한 채팅방들</div>
        </div>
    </div>
    <div class="divTableBody">
        <div class="divTableRow">
            <div class="divTableCell" id="room_list"></div>
        </div>
    </div>
</div>
<br/><br/>
<h1>채팅방 입장 정보</h1>
<hr/>
<br/><br/>
<form name="f" id="f" method="post" action="/chat/chatroom">
    <div class="divTable minimalistBlack">
        <div class="divTableBody">
            <div class="divTableRow">
                <div class="divTableCell">채팅방 이름</div>
                <div class="divTableCell">
                    <input type="text" name="roomName">
                </div>
                <div class="divTableCell">대화명(별명)</div>
                <div class="divTableCell">
                    <input type="text" name="userName">
                </div>
            </div>
        </div>
    </div>
    <div>
        <button>입장하기</button>
    </div>
</form>
</body>
</html>
