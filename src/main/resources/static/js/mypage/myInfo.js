document.getElementById('updateForm').addEventListener('submit', function(event) {
    var password1 = document.getElementById('password1').value;
    var password2 = document.getElementById('password2').value;
    var errorMessage = document.getElementById('error-message');

    if (password1 !== password2) {
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