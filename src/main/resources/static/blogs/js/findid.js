$(document).ready(function () {
    $('#find_id_btn').click(function () {
        let email = $('#id_email').val();
        $.ajax({
            url: '/checkfindid',
            type: 'GET',
            data: { id_email: email },
            success: function (response) {
                $('#findId .user-box').hide();
                $('#find_id_btn').hide();

                $('#id_Result').html('<p>아이디 : ' + response + '</p>')
                    .css('color', 'white')
                    .css('font-size', '28px');
            },
            error: function (error) {
                let message = getErrorMessage(error);
                showFindResult(message, 'red','#id_Result');
            }
        });
    });
});



$(document).ready(function () {
    $('#find_password_btn').click(function () {
        let id = $('#pass_id').val();
        let email = $('#pass_email').val();

        $.ajax({
            url: '/findpassword',
            type: 'GET',
            data: {pass_id: id, pass_email: email},
            success: function (response) {
                if (response === "ok") {
                    $('#findPassword .user-box').hide();
                    $('#find_password_btn').hide();

                    $('#pass_Result').html('<p>임시 비밀번호가 이메일로 전송되었습니다.</p>')
                        .css('color', 'white')
                        .css('font-size', '28px');
                    // TODO: 완료 화면으로 이동하거나 추가 작업 수행
                }
            },
            error: function (error) {
                let message = getErrorMessage(error);
                showFindResult(message, 'red','#pass_Result');
            }
        });
    });
});

function showFindResult(message, color,result) {
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

$(document).ready(function () {
    $(".tab").click(function () {
        $(".tab").removeClass("active");
        $(this).addClass("active");

        const target = $(this).data("target");
        $("#findId, #findPassword").hide();
        $(target).show();
    });
});