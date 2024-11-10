document.getElementById("saveBtn").addEventListener("click", function () {
    //제목 체크
    var titleInput = document.getElementById('title');

    if(titleInput.value == '') {
        alert("제목은 필수 사항입니다.");
        return false;
    }

    //내용 체크
    var contentInput = document.getElementById('content');

    if(contentInput.value == '') {
        alert("내용은 필수 사항입니다.");
        return false;
    }

    //제출
    document.getElementById("form").submit();
});