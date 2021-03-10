// 0 -> 공백
// 1 -> 올바르지 않은 이메일 형식
// 2 -> 동일하지 않은 이메일
// 3 -> 8~16자 사이의 비밀번호 형식이 아님
// 4 -> 동일하지 않은 비밀번호
// 5 -> 검증 완료
// 6 -> 이메일 검증 완료
var text = [
    {msg : "올바르지 않은 형식입니다."},
    {msg : "올바르지 않은 형식입니다."},
    {msg : "동일하지 않은 이메일입니다."},
    {msg : "비밀번호는 8 ~ 16자리 이하여야 합니다."},
    {msg : "비밀번호가 일치하지 않습니다."},
    {msg : "올바른 형식입니다."}];
var blank = false;

$(function(){
    var validationCode = 0;
    $('.signup_button button').click(function () {
        if (!blankChecker($('.input_email').val())) {
            $('.email_hidden_message').empty();
            $(incorrect(text[0].msg)).appendTo($('.email_hidden_message'));
            validationCode = -1;
            blank = true;
            return;
        }
        if (!blankChecker($('.email_confirm').val())) {
            $('.email_confirm_hidden_message').empty();
            $(incorrect(text[0].msg)).appendTo($('.email_confirm_hidden_message'));
            validationCode = -1;
            blank = true;
            return;
        }
        if (!blankChecker($('.input_password').val())) {
            $('.password_hidden_message').empty();
            $(incorrect(text[0].msg)).appendTo($('.password_hidden_message'));
            validationCode = -1;
            blank = true;
            return;
        }
        if (!blankChecker($('.password_confirm').val())) {
            $('.password_confirm_hidden_message').empty();
            $(incorrect(text[0].msg)).appendTo($('.password_confirm_hidden_message'));
            validationCode = -1;
            blank = true;
            return;
        }

        if($('.password_confirm').val() == $('.input_password').val()){
            blank = false;
        }else{
            blank = true;
        }
        if (validationCode == 5 && !blank) {
            $('#signupForm').submit();
        } else {
            alert("올바르지 않은 데이터가 있습니다.");
        }
    });
    $('.input_email').on('blur', function () {
        $('.email_hidden_message').empty();
        if (validateEmail($(this).val())) {
            $(correct(text[5].msg)).appendTo($('.email_hidden_message'));
            validationCode = 5;
        } else {
            $(incorrect(text[1].msg)).appendTo($('.email_hidden_message'));
            validationCode = 0;
        }
    });
    $('.email_confirm').on('blur', function () {
        $('.email_confirm_hidden_message').empty();
        if (validateEmail($(this).val())) {
            if ($(this).val() == $('.input_email').val()) {
                $('.email_confirm_hidden_message').empty();
                validationCode = 5;
            } else {
                $(incorrect(text[2].msg)).appendTo($('.email_confirm_hidden_message'));
                validationCode = 1;
            }
        } else {
            $(incorrect(text[1].msg)).appendTo($('.email_confirm_hidden_message'));
            validationCode = 0;
        }
    });
    $('.input_password').on('blur', function () {
        $('.password_hidden_message').empty();
        var pw = $(this).val();
        if (validatePassword(pw)) {
            $('.password_hidden_message').empty();
            validationCode = 5;
        } else {
            $(incorrect(text[3].msg)).appendTo($('.password_hidden_message'));
            validationCode = 2;
        }
    });
    $('.password_confirm').on('blur', function () {
        $('.password_confirm_hidden_message').empty();
        var pw = $(this).val();
        if (validatePassword(pw)) {
            if ($(this).val() == $('.input_password').val()) {
                $('.password_confirm_hidden_message').empty();
                validationCode = 5;
            } else {
                $(incorrect(text[4].msg)).appendTo($('.password_confirm_hidden_message'));
                validationCode = 3;
            }
        } else {
            $(incorrect(text[3].msg)).appendTo($('.password_confirm_hidden_message'));
            validationCode = 2;
        }
    });
});

function correct(text){
    return "<span class='correct'>"+ text +"</span>";
}

function incorrect(text){
    return "<span class='incorrect'>"+ text +"</span>";
}

function blankChecker(inputObj) {
    if (inputObj.trim().length >= 8) {
        return true;
    } else {
        return false;
    }
}

function validatePassword(sPassword){
    var filter =  /^[A-Za-z0-9]{8,16}$/;
    if(filter.test(sPassword)){
        return true;
    }else{
        return false;
    }
}

function validateEmail(sEmail) {
    var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    if (filter.test(sEmail)) {
        return true;
    } else {
        return false;
    }
}