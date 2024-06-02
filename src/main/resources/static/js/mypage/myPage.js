const modal = document.querySelector('.modal');
const modalOpen = document.querySelector('.box-text');
const modalClose = document.querySelector('.close_btn');

//열기 버튼을 눌렀을 때 모달팝업이 열림
modalOpen.addEventListener('click',function(){
    //display 속성을 block로 변경
    modal.style.display = 'block';
});
//닫기 버튼을 눌렀을 때 모달팝업이 닫힘
modalClose.addEventListener('click',function(){
   //display 속성을 none으로 변경
    modal.style.display = 'none';
});

//모달창에서 이미지 미 선택 시 적용

document.addEventListener('DOMContentLoaded', (event) => {
    const form = document.getElementById('updateForm');
    const imageInput = document.getElementById('imageInput');
    const imagePreview = document.getElementById('imagePreview');
    const defaultImage = '/images/profile.jpg';
    const maxFileSize = 10 * 1024 * 1024; // 10MB
    const allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i;

    imageInput.addEventListener('change', function() {
        const file = imageInput.files[0];

        if (file) {
            if (!allowedExtensions.exec(file.name)) {
                alert("올바른 확장자가 아닙니다. jpg, jpeg, png, gif 파일만 업로드할 수 있습니다.");
                imageInput.value = ''; // 입력 필드 초기화
                imagePreview.src = defaultImage; // 이미지 미리보기 기본 이미지로 변경
                return;
            }

            if (file.size > maxFileSize) {
                alert("사진은 10MB를 초과하였습니다.");
                imageInput.value = ''; // 입력 필드 초기화
                imagePreview.src = defaultImage; // 이미지 미리보기 기본 이미지로 변경
                return;
            }

            const reader = new FileReader();
            reader.onload = function(e) {
                imagePreview.src = e.target.result; // 선택한 이미지로 미리보기 변경
            }
            reader.readAsDataURL(file);
        } else {
            imagePreview.src = defaultImage; // 파일이 선택되지 않았을 때 기본 이미지로 변경
        }
    });

    form.addEventListener('submit', function(event) {
        const file = imageInput.files[0];

        if (file && (!allowedExtensions.exec(file.name) || file.size > maxFileSize)) {
            alert("올바른 확장자가 아니거나 파일 크기가 너무 큽니다.");
            event.preventDefault(); // 폼 제출 중단
            imageInput.value = ''; // 입력 필드 초기화
            imagePreview.src = defaultImage; // 이미지 미리보기 기본 이미지로 변경
            return;
        }

        const hiddenInput = document.createElement('input');
        hiddenInput.setAttribute('type', 'hidden');
        hiddenInput.setAttribute('name', 'defaultImage');
        hiddenInput.setAttribute('value', defaultImage);
        form.appendChild(hiddenInput);

        if (!imageInput.value) {
            hiddenInput.value = defaultImage; // 파일이 선택되지 않았을 때 기본 이미지 경로를 설정
        } else {
            hiddenInput.value = '';
        }
    });
});
