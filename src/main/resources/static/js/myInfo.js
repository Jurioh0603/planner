document.getElementById('updateForm').addEventListener('submit', function(event) {
    var password1 = document.getElementById('password1').value;
    var password2 = document.getElementById('password2').value;
    var errorMessage = document.getElementById('error-message');

    // 공백 확인
    if (password1.trim() === "" || password2.trim() === "") {
        errorMessage.textContent = '비밀번호는 필수항목입니다.(공백 제외한 문자 또는 숫자)';
        errorMessage.style.display = 'block';
        event.preventDefault(); // 폼 제출 막기
        return; // 이후 코드 실행하지 않음
    }

    // 비밀번호 일치 확인
    if (password1 !== password2) {
        errorMessage.textContent = '비밀번호가 일치하지 않습니다.';
        errorMessage.style.display = 'block';
        event.preventDefault(); // 폼 제출 막기
    } else {
        errorMessage.style.display = 'none';
    }
});

document.querySelector('.r-btn').addEventListener('click', function(event) {
    event.preventDefault(); // 기본 reset 동작을 막음
    if (confirm("정보 수정을 취소하시겠습니까?")) {
        window.location.href = "/mypage/myPage"; // 사용자가 확인을 누르면 이동
    }
});

document.getElementById("myForm").addEventListener("submit", function(event) {
    var passwordInput = document.getElementById("password");
    var errorMessage = document.getElementById("errorMessage");

    if (!passwordInput.value.isEmpty()) {
        errorMessage.style.display = "block";
        event.preventDefault(); // 기본 동작인 폼 제출을 막음
    } else {
        errorMessage.style.display = "none";
    }
});

function confirmLeave() {
    if (confirm("정말 탈퇴하시겠습니까?")) {
        document.getElementById("leaveForm").submit();
    }
}

function updatePassword() {
    var password1 = document.getElementById("password1").value.trim(); // 공백 제거
    var password2 = document.getElementById("password2").value.trim(); // 공백 제거
    if (password1 === password2 && password1 !== "") {
        alert("비밀번호 변경이 완료되었습니다. 다시 로그인 해주세요");
        document.getElementById("leaveForm");
    }
}

function fnChkByte(obj, maxByte) {
    var str = obj.value;
    var str_len = str.length;

    var rbyte = 0;
    var rlen = 0;
    var one_char = "";
    var str2 = "";

    for(var i = 0; i < str_len; i++) {
        one_char = str.charAt(i);
        if (escape(one_char).length > 4) {
            rbyte += 3;
        } else {
            rbyte++;
        }

        if(rbyte <= maxByte) {
            rlen = i + 1;
        }
    }

    if(rbyte > maxByte) {
        alert("최대 " + maxByte + "byte를 초과할 수 없습니다.")
        str2 = str.substr(0, rlen);
        obj.value = str2;
        return;
    }
}