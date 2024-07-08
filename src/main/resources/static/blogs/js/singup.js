$(document).ready(function () {
    $('#checkUsername').click(function () {
        var username = $('#username').val();
        $.ajax({
            url: '/checkusername',
            type: 'POST',
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
            type: 'POST',
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

