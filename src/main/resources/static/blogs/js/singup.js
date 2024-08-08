let successColor = '#2Db400';

$(document).ready(function () {
    $('#checkUsername').click(function () {
        let username = $('#username').val();
        $.ajax({
            url: '/checkusername',
            type: 'Get',
            data: {username: username},
            success: function (response) {
                showNicknameResult(response, successColor, '#usernameResult');
            },
            error: function (error) {
                let message = getErrorMessage(error);
                showNicknameResult(message, 'red', '#usernameResult');
            }
        });
    });
});

$(document).ready(function () {
    $('#checkNickname').click(function () {
        let nickname = $('#nickname').val();
        $.ajax({
            url: '/checknickname',
            type: 'Get',
            data: {nickname: nickname},
            success: function (response) {
                showNicknameResult(response, successColor, '#nicknameResult');
            },
            error: function (error) {
                let message = getErrorMessage(error);
                showNicknameResult(message, 'red', '#nicknameResult');
            }
        });
    });
});


$(document).ready(function () {
    $('#checkEmail').click(function () {
        let email = $('#email').val();

        // 로딩 인디케이터 표시
        $('#loadingIndicator').show();

        // 버튼 비활성화
        $('#checkEmail').prop('disabled', true);

        $.ajax({
            url: '/checkemail',
            type: 'GET',
            data: {email: email},
            success: function (response) {
                showNicknameResult(response, successColor, '#emailResult');

                // 인증 번호 입력란과 인증 버튼 활성화
                $('#emailNum').prop('disabled', false);
                $('#checkEmailNum').prop('disabled', false);
            },
            error: function (error) {
                let message = getErrorMessage(error);
                showNicknameResult(message, 'red', '#emailResult');

                // 인증 번호 입력란과 인증 버튼 비활성화
                $('#emailNum').prop('disabled', true);
                $('#checkEmailNum').prop('disabled', true);
            },
            complete: function () {
                // 로딩 인디케이터 숨김
                $('#loadingIndicator').hide();

                // 버튼 다시 활성화
                $('#checkEmail').prop('disabled', false);
            }
        });
    });
});


$('#checkEmailNum').click(function () {
    let emailNum = $('#emailNum').val();
    $.ajax({
        url: '/checkmailnum',
        type: 'GET',
        data: {emailNum: emailNum},
        success: function (response) {
            showNicknameResult(response, successColor, '#checkNumResult');
        },
        error: function (error) {
            let message = getErrorMessage(error);
            showNicknameResult(message, 'red', '#checkNumResult');
        }
    });
});


function showNicknameResult(message, color, result) {
    $(result).text(message)
        .css('color', color)
        .css('font-size', '16px')
        .css('font-weight', 'bold');
}

function getErrorMessage(error) {
    if (error.status === 400) {
        return error.responseText;
    } else if (error.status === 409) {
        return error.responseText;
    } else {
        return '오류가 발생했습니다. 다시 시도해주세요.';
    }
}

const passwordInputs = document.querySelectorAll('.user-box input[type="password"]');
const toggleBtns = document.querySelectorAll('.user-box .password-toggle-btn');

toggleBtns.forEach((btn, index) => {
    btn.addEventListener('click', () => {
        const passwordInput = passwordInputs[index];
        const eyeIcon = btn.querySelector('i');

        if (passwordInput.type === 'password') {
            passwordInput.type = 'text';
            eyeIcon.classList.remove('fa-eye');
            eyeIcon.classList.add('fa-eye-slash');
        } else {
            passwordInput.type = 'password';
            eyeIcon.classList.remove('fa-eye-slash');
            eyeIcon.classList.add('fa-eye');
        }
    });
});



