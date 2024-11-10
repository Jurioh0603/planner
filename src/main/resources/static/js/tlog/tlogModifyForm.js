var imageUploadFlag = 1; // 이미지를 업로드했는지 체크하기 위한 변수
var planUploadFlag = 1; // 일정을 업로드했는지 체크하기 위한 변수

function openPopup() {
    var url = "/tlog/select";
    var width = 500;
    var height = 600;
    var left = (screen.width - width) / 2;
    var top = (screen.height - height) / 2;
    var pop = window.open(url, "계획 추가", "width=" + width + ",height=" + height + ",left=" + left + ",top=" + top + ",resizable=no");
}

window.onload=function(){
    var target=document.getElementById('file');
    target.addEventListener('change',function(){
        imageUploadFlag = 0; // 이미지 업로드
        var fileInfo = '';
        for(var i = 0; i < target.files.length; i++) {
            fileInfo += target.files[i].name + ', ';
        }
        if(fileInfo.length > 0) {
            fileInfo = fileInfo.slice(0, -2);
        }
        console.log(fileInfo);
        document.getElementById('fileName').innerText = fileInfo;
    });

    var deleteImageBtn = document.getElementById('deleteImageBtn');
    deleteImageBtn.addEventListener('click', function () {
        imageUploadFlag = 0; // 업로드한 이미지 삭제
        target.value = ''; // 파일 입력 필드 초기화
        document.getElementById('fileName').innerText = '선택한 파일이 없습니다.'; // 표시된 파일 이름 제거
    });

    var deletePlanBtn = document.getElementById('deletePlanBtn');
    deletePlanBtn.addEventListener('click', function () {
        planUploadFlag = 0; // 업로드한 일정 삭제
        document.getElementById('scheduleNo').value = '';
        document.getElementById('planName').innerText = '선택한 일정이 없습니다.';
    });

}

function addPlan(title, no) {
    planUploadFlag = 0; // 일정 업로드
    document.getElementById('planName').innerText = title;
    document.getElementById('scheduleNo').value = no;
}

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

    if(imageUploadFlag == 0) {
        //이미지 체크
        var fileInput = document.getElementById('file');

        if(fileInput.files.length == 0) {
            alert("이미지를 반드시 하나 이상 첨부하여야 합니다.");
            return false;
        }

        var validExtensions = ['jpg', 'jpeg', 'png', 'gif'];
        var maxSize = 10 * 1024 * 1024; //10MB 사이즈 제한(개당)
        var maxTotalSize = 50 * 1024 * 1024; //50MB 사이즈 제한(총)
        var totalSize = 0;
        for(var i = 0; i < fileInput.files.length; i++) {
            var fileName = fileInput.files[i].name;
            var fileSize = fileInput.files[i].size;
            totalSize += fileSize;
            var fileExtension = fileName.split('.').pop().toLowerCase();

            if (!validExtensions.includes(fileExtension)) {
                alert("올바른 이미지 확장자 jpg, jpeg, png, gif 를 선택하세요.");
                return false;
            }

            if (fileSize > maxSize){
                alert("각 파일의 사이즈는 10MB 이내로 가능합니다.");
                return false;
            }

            if (totalSize > maxTotalSize) {
                alert("파일 사이즈의 총합은 50MB를 넘을 수 없습니다.");
                return false;
            }
        }
    }

    if(planUploadFlag == 0) {
        //일정 체크
        var scheduleNoInput = document.getElementById('scheduleNo');

        if(scheduleNoInput.value == '') {
            alert("일정을 반드시 첨부하여야 합니다.");
            return false;
        }
    }

    //제출
    document.getElementById("form").submit();
});