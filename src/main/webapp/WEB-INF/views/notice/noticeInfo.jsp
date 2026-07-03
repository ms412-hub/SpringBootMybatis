<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.dto.NoticeDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%
    NoticeDTO rDTO = (NoticeDTO) request.getAttribute("rDTO");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>게시판 글보기</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript" src="/js/jquery-4.0.0.min.js"></script>
    <script>
        const session_user_id = "<%=CmmUtil.nvl((String)session.getAttribute("SESSION_USER_ID"))%>";

        const user_id = "<%=CmmUtil.nvl(rDTO.getUserId())%>";

        const nSeq = "<%=CmmUtil.nvl(rDTO.getNoticeSeq())%>";

        $(document).ready(function () {

            $("#btnEdit").on("click", function () {
                doEdit(); // 공지사항 수정하기 실행
            })

            $("#btnDelete").on("click", function () {
                doDelete(); // 공지사항 수정하기 실행
            })

            $("#btnList").on("click", function () {
                location.href = "/notice/noticeList";
            })
        })


        function doEdit() {
            if (session_user_id === user_id) {
                location.href = "/notice/noticeEditInfo?nSeq=" + nSeq;

            } else if (session_user_id === "") {
                alert("로그인 하시길 바랍니다.");

            } else {
                alert("본인이 작성한 글만 수정 가능합니다.");

            }
        }

        function doDelete() {
            if (session_user_id === user_id) {
                if (confirm("작성한 글을 삭제하시겠습니까?")) {

                    $.ajax({
                            url: "/notice/noticeDelete",
                            type: "post",
                            dataType: "JSON",
                            data: {"nSeq": nSeq},
                            success:
                                function (json) {
                                    alert(json.msg);
                                    location.href = "/notice/noticeList";
                                }
                        }
                    )
                }

            } else if (session_user_id === "") {
                alert("로그인 하시길 바랍니다.");

            } else {
                alert("본인이 작성한 글만 수정 가능합니다.");

            }
        }
    </script>
</head>
<body>
<h2>공지사항 상세보기</h2>
<hr/>
<br/>
<div class="divTable minimalistBlack">
    <div class="divTableBody">
        <div class="divTableRow">
            <div class="divTableCell">제목
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getTitle())%>
            </div>
        </div>
        <div class="divTableRow">
            <div class="divTableCell">공지글 여부
            </div>
            <div class="divTableCell">
                예<input type="radio" name="noticeYn"
                        value="Y" <%=CmmUtil.checked(CmmUtil.nvl(rDTO.getNoticeYn()), "Y") %> />
                아니오<input type="radio" name="noticeYn"
                          value="N" <%=CmmUtil.checked(CmmUtil.nvl(rDTO.getNoticeYn()), "N") %>/>
            </div>
        </div>
        <div class="divTableRow">
            <div class="divTableCell">작성일
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getRegDt())%>
            </div>
        </div>
        <div class="divTableRow">
            <div class="divTableCell">조회수
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getReadCnt())%>
            </div>
        </div>
        <div class="divTableRow">
            <div class="divTableCell">내용
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getContents())%>
            </div>
        </div>
    </div>
</div>
<div>
    <button id="btnEdit" type="button">수정</button>
    <button id="btnDelete" type="button">삭제</button>
    <button id="btnList" type="button">목록</button>
</div>
</body>
</html>