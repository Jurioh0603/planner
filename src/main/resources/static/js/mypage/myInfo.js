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