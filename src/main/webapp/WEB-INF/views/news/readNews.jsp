<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>신문기사 읽어주기</title>
    <link rel="stylesheet" href="/css/table.css"/>
</head>

<body>

<h2>신문기사 URL 입력</h2>
<hr/>

<div class="divTable minimalistBlack">

    <!-- URL 입력 -->
    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableHead">
                신문기사 URL
            </div>
        </div>
    </div>

    <div class="divTableBody">
        <div class="divTableRow">
            <div class="divTableHead">

                <input type="text"
                       id="url"
                       size="80"
                       placeholder="신문기사 URL을 입력하세요"/>

                <button type="button" onclick="getNews()">
                    기사 가져오기
                </button>

            </div>
        </div>
    </div>

</div>

<br/>

<!-- 기사 제목 -->
<div class="divTable minimalistBlack">

    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableHead">
                기사 제목
            </div>
        </div>
    </div>

    <div class="divTableBody">
        <div class="divTableRow">
            <div class="divTableHead">
                <div id="title">
                    기사를 가져오면 제목이 표시됩니다.
                </div>
            </div>
        </div>
    </div>

</div>

<br/>

<!-- 기사 내용 -->
<div class="divTable minimalistBlack">

    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableHead">
                기사 내용
            </div>
        </div>
    </div>

    <div class="divTableBody">
        <div class="divTableRow">
            <div class="divTableHead">

                <div id="contents"
                     style="min-height: 200px;">
                    기사를 가져오면 기사 내용이 표시됩니다.
                </div>

            </div>
        </div>
    </div>

</div>

<br/>

<!-- 읽어주기 버튼 -->
<div>
    <button type="button" onclick="readNews()">
        기사 읽어주기
    </button>

    <button type="button" onclick="stopNews()">
        읽기 중지
    </button>
</div>


<script>

    // 크롤링된 기사 전체 내용을 저장
    let newsText = "";


    // 신문기사 가져오기
    function getNews() {

        const url = document.getElementById("url").value;

        // URL 입력 여부 확인
        if (url === "") {

            alert("신문기사 URL을 입력해주세요.");
            return;

        }

        // Controller의 /news/crawl 호출
        fetch("${pageContext.request.contextPath}/news/crawl?url="
            + encodeURIComponent(url))

            .then(response => {

                if (!response.ok) {
                    throw new Error("기사 가져오기에 실패했습니다.");
                }

                return response.json();

            })

            .then(data => {

                // 제목 출력
                document.getElementById("title").innerText =
                    data.title || "제목을 가져오지 못했습니다.";

                // 내용 출력
                document.getElementById("contents").innerText =
                    data.contents || "기사 내용을 가져오지 못했습니다.";

                // 제목 + 내용 저장
                newsText =
                    (data.title || "") + ". "
                    + (data.contents || "");

            })

            .catch(error => {

                console.error(error);

                alert("신문기사 가져오기에 실패했습니다.");

            });

    }


    // 기사 읽어주기
    function readNews() {

        // 기사 내용이 없는 경우
        if (newsText === "") {

            alert("먼저 기사를 가져와주세요.");
            return;

        }

        // 기존 음성 읽기 중지
        window.speechSynthesis.cancel();

        // 음성 객체 생성
        const speech = new SpeechSynthesisUtterance(newsText);

        // 한국어 설정
        speech.lang = "ko-KR";

        // 음성 속도
        speech.rate = 1.0;

        // 음성 높낮이
        speech.pitch = 1.0;

        // 음성 출력
        window.speechSynthesis.speak(speech);

    }


    // 읽기 중지
    function stopNews() {

        window.speechSynthesis.cancel();

    }

</script>

</body>
</html>