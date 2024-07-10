$(document).ready(function () {
    $('#checkUsername').click(function () {
        var username = $('#username').val();
        $.ajax({
            url: '/checkusername',
            type: 'Get',
            data: {username: username},
            success: function (response) {
                showNicknameResult(response, 'green', '#usernameResult');
            },
            error: function (error) {
                var message = getErrorMessage(error);
                showNicknameResult(message, 'red', '#usernameResult');
            }
        });
    });
});

$(document).ready(function () {
    $('#checkNickname').click(function () {
        var nickname = $('#nickname').val();
        $.ajax({
            url: '/checknickname',
            type: 'Get',
            data: { nickname: nickname },
            success: function (response) {
                showNicknameResult(response, 'green','#nicknameResult');
            },
            error: function (error) {
                var message = getErrorMessage(error);
                showNicknameResult(message, 'red','#nicknameResult');
            }
        });
    });
});

$(document).ready(function () {
    $('#checkEmail').click(function () {
        var email = $('#email').val();
        $.ajax({
            url: '/checkemail',
            type: 'Get',
            data: { email: email },
            success: function (response) {
                showNicknameResult(response, 'green','#emailResult');
            },
            error: function (error) {
                var message = getErrorMessage(error);
                showNicknameResult(message, 'red','#emailResult');
            }
        });
    });
});

$(document).ready(function () {
    $('#checkEmailNum').click(function () {
        var emailNum = $('#emailNum').val();
        $.ajax({
            url: '/checkmailnum',
            type: 'Get',
            data: { emailNum: emailNum },
            success: function (response) {
                showNicknameResult(response, 'green','#checkNumResult');
            },
            error: function (error) {
                var message = getErrorMessage(error);
                showNicknameResult(message, 'red','#checkNumResult');
            }
        });
    });
});



function showNicknameResult(message, color,result) {
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



