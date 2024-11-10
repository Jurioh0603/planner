// 작성된 시간을 표시할 요소 선택
var writeTimeElement = document.getElementById('writeTime');

// 작성된 시간 가져오기
var writeDate = new Date(writeTime);

// 현재 시간
var currentDate = new Date();

// 현재 시간과 작성된 시간의 차이 계산 (밀리초 단위)
var timeDiff = currentDate - writeDate;

// 밀리초를 시간으로 변환
var seconds = Math.floor(timeDiff / 1000);
var minutes = Math.floor(seconds / 60);
var hours = Math.floor(minutes / 60);
var days = Math.floor(hours / 24);

// 시간 전에 대한 표시를 작성된 시간 요소에 추가
var displayText = '';
if (days > 0) {
    displayText = days + '일 전';
} else if (hours > 0) {
    displayText = hours + '시간 전';
} else if (minutes > 0) {
    displayText = minutes + '분 전';
} else if (seconds > 0) {
    displayText = seconds + '초 전';
} else {
    displayText = '방금 전';
}
writeTimeElement.innerText = displayText;

document.addEventListener('DOMContentLoaded', function() {
    var deleteBtn = document.getElementById("deleteBtn");

    //작성자가 아닌 경우 삭제 버튼이 존재하지 않는다. 따라서 존재 여부를 파악하여 이벤트 리스너를 추가한다.
    if (deleteBtn) {
        deleteBtn.addEventListener("click", function () {
            const modal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));
            modal.show();
        });
    }
});

function toggleReplyForm(button) {
    //댓글 쓰기 버튼이 속한
    var replyContainer = button.closest('.replyContainer');
    //replyForm을 찾는다.
    var replyForm = replyContainer.querySelector('.replyForm');
    //보였다, 안보였다
    if (replyForm.style.display == 'none') {
        replyForm.style.display = 'block';
        button.innerText = '댓글 취소';
    } else {
        replyForm.style.display = 'none';
        button.innerText = '댓글 쓰기';
    }
}

function toggleReplyModifyForm(button) {
    //댓글 수정 버튼이 속한
    var replyContainer = button.closest('.replyContainer');
    //replyModifyForm을 찾는다.
    var replyModifyForm = replyContainer.querySelector('.replyModifyForm');
    //보였다, 안보였다
    if (replyModifyForm.style.display == 'none') {
        replyModifyForm.style.display = 'block';
        button.innerText = '취소';
    } else {
        replyModifyForm.style.display = 'none';
        button.innerText = '수정';
    }
}

//'^=' 는 주어진 문자열로 시작하는 요소를 선택
document.querySelectorAll('div[id^="replyDeleteBtn-"]').forEach(function(button) {
    button.addEventListener('click', function() {
        const deleteRno = button.getAttribute('data-rno');
        document.getElementById('modalRno').value = deleteRno;
        const modal = new bootstrap.Modal(document.getElementById('confirmDeleteReplyModal'));
        modal.show();
    });
});