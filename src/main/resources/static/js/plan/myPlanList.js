document.addEventListener("DOMContentLoaded", function() {
    // 현재 날짜를 얻습니다.
    const today = new Date();
    today.setHours(0, 0, 0, 0); // 시간을 00:00:00으로 설정하여 정확한 비교를 합니다.

    // 모든 dDay 요소를 선택합니다.
    const startDayElements = document.querySelectorAll('.startDay');

    // 각 요소를 순회하며 d-day를 계산합니다.
    startDayElements.forEach((element, index) => {
        // 요소의 텍스트 내용을 읽어와서 날짜로 변환합니다.
        const scheduleDate = new Date(element.textContent.trim());
        scheduleDate.setHours(0, 0, 0, 0); // 시간을 00:00:00으로 설정합니다.

        // d-day를 계산합니다.
        const timeDiff = scheduleDate - today;
        const dayDiff = Math.ceil(timeDiff / (1000 * 60 * 60 * 24)); // 밀리초를 일수로 변환합니다.

        // 해당 요소의 하위 요소인 dDay 요소를 선택합니다.
        const dDayElement = element.parentElement.querySelector('.dDay');

        // d-day를 해당 요소에 표시합니다.
        if (dayDiff === 0) {
            dDayElement.textContent = 'D-day';
        } else if (dayDiff > 0) {
            dDayElement.textContent = 'D-' + dayDiff;
        } else {
            dDayElement.textContent = 'D+' + Math.abs(dayDiff);
        }
    });
});

function openFileUploader(index) {
    // div 클릭 시 파일 업로드 창 열기
    document.getElementById('file_' + index).click();

    // 파일이 선택되면 form을 자동으로 제출하기
    document.getElementById('file_' + index).addEventListener('change', function() {
        if (!validateForm(index)) {
            return false; // 제출을 막음
        }

        document.getElementById('uploadForm_' + index).submit();
    });

    // 확장자와 파일 크기 체크
    function validateForm() {
        var fileInput = document.getElementById('file_' + index);

        var fileName = fileInput.files[0].name;
        var validExtensions = ['jpg', 'jpeg', 'png', 'gif'];
        var fileExtension = fileName.split('.').pop().toLowerCase();

        if (!validExtensions.includes(fileExtension)) {
            alert("올바른 이미지 확장자 jpg, jpeg, png, gif 를 선택하세요.");
            return false;
        }

        var fileSize = fileInput.files[0].size;
        var maxSize = 10 * 1024 * 1024; //10MB 사이즈 제한

        if (fileSize > maxSize){
            alert("파일첨부 사이즈는 10MB 이내로 가능합니다.");
            return false;
        }

        return true;
    }
}

// 모달이 열릴 때 data-schedule-no 값을 가져와서 confirmDeleteBtn에 저장
var deleteBtns = document.querySelectorAll('.deleteBtn');
var confirmDeleteModal = document.getElementById('confirmDeleteModal');
var confirmDeleteBtn = document.getElementById('confirmDeleteBtn');

confirmDeleteModal.addEventListener('show.bs.modal', function (event) {
    var button = event.relatedTarget; // 모달을 연 버튼
    var scheduleNo = button.getAttribute('data-schedule-no');
    confirmDeleteBtn.setAttribute('data-schedule-no', scheduleNo);
});

// confirmDeleteBtn 클릭 시 scheduleNo를 사용하여 삭제 링크로 이동
confirmDeleteBtn.addEventListener('click', function () {
    var scheduleNo = this.getAttribute('data-schedule-no');
    location.href = '/plan/delete?no=' + scheduleNo;
});

// 제목 수정
document.querySelectorAll('.editTitleBtn').forEach(function(button) {
    button.addEventListener('click', function() {
        const no = button.getAttribute('data-schedule-no');
        const title = button.getAttribute('data-title');

        // 수정할 요소 정보 수정 폼에 세팅
        document.getElementById('no').value = no;
        document.getElementById('editTitle').value = title;

        // 모달 열기
        const modal = new bootstrap.Modal(document.getElementById('editTitleModal'));
        modal.show();
    });
});

document.getElementById('edit').addEventListener('click', function() {
    const editTitle = document.getElementById('editTitle').value.trim();

    // 입력되었는지 확인
    if (!editTitle) {
        alert('제목을 입력해주세요.');
        return;
    }

    // 제출
    document.getElementById('editTitleForm').submit();
});

//click 이벤트가 겹치기 때문에 div click 이벤트는 JS에서 처리
const outerElements = document.querySelectorAll(".outer");

outerElements.forEach(outer => {
    var scheduleNo = outer.getAttribute('data-scheduleNo');
    outer.addEventListener("click", () => {
        window.location.assign("/plan/write?no=" + scheduleNo);
    });
});